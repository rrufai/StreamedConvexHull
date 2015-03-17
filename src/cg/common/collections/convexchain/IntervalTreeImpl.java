package cg.common.collections.convexchain;

import cg.common.Utilities;
import cg.common.comparators.RadialComparator;
import cg.convexlayers.events.IntervalTree;
import cg.geometry.primitives.Point;
import com.rits.cloning.Cloner;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author rrufai@gmu.edu
 */
class IntervalTreeImpl<K extends Point> implements IntervalTree<K> {

    private ConvexChain<K> hullChain;
    private int level;
    private IntervalTreeImpl<K> left;
    private IntervalTreeImpl<K> right;
    private ConvexLayersIntervalTree<K> delegate;
    private K leftBridgePoint;
    private K rightBridgePoint;
    private String START = "--------------------START--------------------";
    private String END = "--------------------END--------------------";

    public IntervalTreeImpl(IntervalTree<K> parent,
            ConvexLayersIntervalTree<K> delegate) {
        hullChain = new ConvexChainImpl<>();
        this.delegate = delegate;
        this.level = parent != null ? parent.getLevel() + 1 : 0;

    }

    @Override
    public List<K> extractHull() {
        ConvexChain<K> rootLayer = new Cloner().deepClone(hullChain);

        System.out.println(START);
        delete(rootLayer);
        System.out.println(END);

        return rootLayer;
    }

    @Override
    public void delete(ConvexChain<K> chain) {
        System.out.println("entering delete");
        System.out.println("chain: \n" + chain);
        System.out.println("this before: \n" + this);
        if (!isNull(chain)) {
            K al = this.predecessor(chain.getFirst());
            K ar = this.successor(chain.getLast());
            ConvexChain<K> L1 = new ConvexChainImpl<>();
            ConvexChain<K> R1 = new ConvexChainImpl<>();
            if (!isNull(left)) {
                K p = left.hullChain.getFirst();

                while (p != left.successor(leftBridgePoint) && counterClockwise(left.predecessor(p), p, al)) {
                    L1.add(p);
                    p = left.successor(p);
                }

                if (p.equals(left.successor(leftBridgePoint)) && !isNull(right)) {
                    p = rightBridgePoint;
                    K pp = leftBridgePoint;

                    while (p != right.hullChain.getRightSentinel() && counterClockwise(pp, p, al)) {
                        R1.add(0, p);
                        pp = p;
                        p = right.successor(p);
                    }
                }

            } else {
                K p = right.hullChain.getFirst();
                K pp = right.predecessor(p);
                while (p != right.hullChain.getRightSentinel() && counterClockwise(pp, p, al)) {
                    R1.add(0, p);
                    pp = p;
                    p = right.successor(p);
                }
            }

            ConvexChain<K> L2 = new ConvexChainImpl<>();
            ConvexChain<K> R2 = new ConvexChainImpl<>();
            if (!isNull(right)) {
                K p = right.hullChain.getLast();

                while (p != right.predecessor(rightBridgePoint) && counterClockwise(ar, p, right.successor(p))) {
                    R2.add(0, p);
                    p = right.predecessor(p);
                }

                if (p.equals(right.predecessor(rightBridgePoint)) && !isNull(left)) {
                    p = leftBridgePoint;
                    K pp = rightBridgePoint;

                    while (p != left.hullChain.getLeftSentinel() && counterClockwise(ar, p, pp)) {
                        L2.add(0, p);
                        pp = p;
                        p = left.predecessor(p);
                    }
                }

            } else {
                K p = left.hullChain.getLast();

                while (p != left.hullChain.getLeftSentinel() && counterClockwise(ar, p, left.successor(p))) {
                    L2.add(0, p);
                    p = left.predecessor(p);
                }
            }

            System.out.println("L1: \n" + L1);
            System.out.println("L2: \n" + L2);
            System.out.println("R1: \n" + R1);
            System.out.println("R2: \n" + R2);

            K blp = this.successor(this.leftBridgePoint);
            K brp = this.predecessor(this.rightBridgePoint);
            int j = this.hullChain.delete(chain);

            ConvexChain<K> L = L1.insert(L2);
            ConvexChain<K> R = R1.insert(R2);
            Collection<K> shunt = L.insert(R);
            this.hullChain.addAll(j, shunt);

            left.delete(L);
            right.delete(R);
            
            // Case I: Nothing to do -- Both Roof leftovers are above the bridge
            if (!isNull(hullChain) && isAboveBridge(al) && !isBelowBridge(ar)) {
                System.out.println("Case I -- nothing to do!  Both Roof leftovers are above the bridge. \n");
            }
            //Case II: Only Left Roof leftover is above bridge
            if (!isNull(hullChain) && isAboveBridge(al) && isBelowBridge(ar)) {
                System.out.println("Case II --  Only Left Roof leftover is above bridge. \n");
                this.goLeft(blp, right.hullChain.getLast());
            }

            //Case III: Only Right Roof leftover is above bridge
            if (!isNull(hullChain) && isBelowBridge(al) && isAboveBridge(ar)) {
                System.out.println("Case III --  Only Right Roof leftover is above bridge. \n");
                this.goRight(left.hullChain.getFirst(), brp);
            }
            // Case IV: Both roof leftovers are below the bridge -- includes the case when the roof is empty.
            if (isNull(hullChain) || (isBelowBridge(al) && isBelowBridge(ar))) {
                System.out.println("Case IV \n -- Both roof leftovers are below the bridge. Eg. when the entire roof is being deleted !\n");
                if (isBelowEdge(blp, left.successor(blp), brp)) {
                    this.goLeft(blp, right.hullChain.getLast());
                } else if (isBelowEdge(brp, blp, right.predecessor(brp))) {
                    this.goRight(left.hullChain.getFirst(), brp);
                }
            } 
        }
        System.out.println("this after: \n" + this);
        System.out.println("exiting delete");
    }

    //@Override
    public void delete0(ConvexChain<K> chain) {
        System.out.println("entering delete");
        System.out.println("chain: \n" + chain);
        System.out.println("this before: \n" + this);
        if (!isNull(chain) && !isNull(hullChain)) {
            int j = hullChain.delete(chain);

            // Note: the segment al-ar is the segment that used to be below the 
            // chain that has just deleted from hullChain.
            K al = hullChain.predecessor(j);
            K ar = hullChain.get(j);

            System.out.printf("al = %s, ar = %s \n", al, ar);

            int blpi = isNull(left) ? -1 : left.hullChain.indexOf(leftBridgePoint) + 1;
            int br = isNull(right) ? -1 : right.hullChain.indexOf(rightBridgePoint);

            // Case IV: Both roof leftovers are below the bridge -- includes the case when the roof is empty.
            if (isNull(hullChain) || (isBelowBridge(al) && isBelowBridge(ar))) {
                handleCaseIV(blpi, br, al, ar, j);
            } else // Case I: Nothing to do -- Both Roof leftovers are above the bridge
            if (!isNull(hullChain) && isAboveBridge(al) && !isBelowBridge(ar)) {
                handleCaseI();
            } else //Case II: Only Left Roof leftover is above bridge
            if (!isNull(hullChain) && isAboveBridge(al) && isBelowBridge(ar)) {
                handleCaseII(blpi, al, ar, j);
            } else //Case III: Only Right Roof leftover is above bridge
            if (!isNull(hullChain) && isBelowBridge(al) && isAboveBridge(ar)) {
                handleCaseIII(br, ar, j);
            }
        }

        System.out.println("this after: \n" + this);
        System.out.println("exiting delete");
    }

    //"Case I -- nothing to do!  Both Roof leftovers are above the bridge.
    private void handleCaseI() {
        System.out.println("Case I -- nothing to do!  Both Roof leftovers are above the bridge. \n");
    }

    // Case II: Only left roof leftover is above the bridge.
    private void handleCaseII(int blpi, K al, K ar, int j) throws IllegalArgumentException {
        System.out.println("Case II --  Only Left Roof leftover is above bridge. \n");

        Entry<ConvexChain<K>, ConvexChain<K>> leftPair = left.hullChain.split(blpi);

        int i = leftPair == null ? 0 : leftPair.getKey().getTangentPoint(al) + 1;
        //i = i < 0 ? 0 : i; // i.e. adjust index if the left sentinel is the tangent!
        ConvexChainImpl toMoveUpL = new ConvexChainImpl(leftPair.getKey().subList(0, i));


        ConvexChain<K> newChain = new ConvexChainImpl(hullChain.subList(0, j));
        newChain.addAll(toMoveUpL);
        newChain.addAll(hullChain.subList(j, hullChain.size()));

        hullChain = newChain;

        if (!isNull(left) && !isNull(toMoveUpL)) {
            left.delete(toMoveUpL);
        }

        K blp = null;

        if (leftPair != null && !isNull(leftPair.getValue())) {
            blp = leftPair.getValue().getFirst();
        } else {
            if (left != null && !isNull(left.hullChain)) {
                leftBridgePoint = left.hullChain.getFirst();
            } else {
                leftBridgePoint = null;
            }
        }

        this.updateBridges(2, blp, null, null, null);
    }

    //Case III --  Only Right Roof leftover is above bridge. 
    private void handleCaseIII(int br, K ar, int j) throws IllegalArgumentException {
        System.out.println("Case III --  Only Right Roof leftover is above bridge. \n");
        Entry<ConvexChain<K>, ConvexChain<K>> rightPair = right.hullChain.split(br);

        int k = rightPair == null ? 0 : rightPair.getValue().getTangentPoint(ar) + 1;
        //k = k < 0 ? 0 : k;

        ConvexChainImpl toMoveUpR = rightPair == null ? new ConvexChainImpl<K>()
                : new ConvexChainImpl(rightPair.getValue().subList(k, rightPair.getValue().size()));

        ConvexChain<K> newChain = new ConvexChainImpl(hullChain.subList(0, j));

        newChain.addAll(toMoveUpR);
        newChain.addAll(hullChain.subList(j, hullChain.size()));

        hullChain = newChain;

        if (!isNull(right) && !isNull(toMoveUpR)) {
            right.delete(toMoveUpR);
        }

        K brp = null;

        if (rightPair != null && !isNull(rightPair.getKey())) {
            brp = rightPair.getKey().getLast();
        } else {
            if (right != null && !isNull(right.hullChain)) {
                rightBridgePoint = right.hullChain.getFirst();
            } else {
                rightBridgePoint = null;
            }
        }

        this.updateBridges(3, null, brp, null, null);
    }

    private void handleCaseIV(int blpi, int br, K al, K ar, int j) throws IllegalArgumentException {
        System.out.println("Case IV \n -- Both roof leftovers are below the bridge. Eg. when the entire roof is being deleted !\n");
        Entry<ConvexChain<K>, ConvexChain<K>> leftPair = isNull(left) ? null : left.hullChain.split(blpi); //blpi == indexOf succ(bl)
        Entry<ConvexChain<K>, ConvexChain<K>> rightPair = isNull(right) ? null : right.hullChain.split(br);

        ConvexChain<K> leftKey = leftPair == null ? new ConvexChainImpl<K>() : leftPair.getKey();
        ConvexChain<K> newChain = new Cloner().deepClone(leftKey);

        if (!isNull(left) && !isNull(leftKey)) {
            left.delete(leftKey);
        }

        newChain.addAll(hullChain);
        if (leftBridgePoint == null || Utilities.isMonotonic(leftBridgePoint, rightBridgePoint)) {
            ConvexChain<K> rightValue = rightPair == null ? new ConvexChainImpl<K>() : rightPair.getValue();

            if (!isNull(right) && !isNull(rightValue) && (leftBridgePoint == null || Utilities.isMonotonic(leftBridgePoint, rightBridgePoint))) {
                newChain.addAll(rightValue);
                right.delete(rightValue);
            }
        }

        this.hullChain = newChain;

        K blp = null;
        K blpp = null;
        K brp = null;
        K brpp = null;
        if (leftPair != null && !isNull(leftPair.getValue())) {
            blp = leftPair.getValue().getFirst();
            blpp = left.hullChain.predecessor(left.hullChain.indexOf(blp));
        } else {
            if (left != null && !isNull(left.hullChain)) {
                //leftBridgePoint = left.hullChain.getFirst();
                blp = left.hullChain.getRightSentinel();
                blpp = left.hullChain.getLast();
            } else {
                leftBridgePoint = null;
            }
        }

        if (Utilities.isMonotonic(leftBridgePoint, rightBridgePoint)) {
            if (rightPair != null && !isNull(rightPair.getKey())) {
                brp = rightPair.getKey().getLast();
                brpp = right.hullChain.successor(right.hullChain.indexOf(brp));
            } else {
                if (right != null && !isNull(right.hullChain)) {
                    //rightBridgePoint = right.hullChain.getFirst();
                    brp = right.hullChain.getLeftSentinel();
                    brpp = right.hullChain.getFirst();
                } else {
                    rightBridgePoint = null;
                }
            }
        } else {
            if (rightPair != null && !isNull(rightPair.getValue())) {
                brp = rightPair.getValue().getFirst();
                brpp = right.hullChain.successor(right.hullChain.indexOf(brp));
            } else {
                if (right != null && !isNull(right.hullChain)) {
                    //rightBridgePoint = right.hullChain.getFirst();
                    brp = right.hullChain.getLeftSentinel();
                    brpp = right.hullChain.getFirst();
                } else {
                    rightBridgePoint = null;
                }
            }
        }
        this.updateBridges(4, blp, brp, blpp, brpp);
    }

    private void updateBridges(int iCase, K blp, K brp, K blpp, K brpp) {
        if (isNull(left) && isNull(right)) {
            this.leftBridgePoint = null; //(K) ConvexChain.LEFT_SENTINEL;
            this.rightBridgePoint = null; //(K) ConvexChain.RIGHT_SENTINEL;
        }

        if (!isNull(left) && isNull(right)) {
            this.leftBridgePoint = left.hullChain.getLast();
            this.rightBridgePoint = null; // (K) ConvexChain.RIGHT_SENTINEL;
        }

        if (isNull(left) && !isNull(right)) {
            this.rightBridgePoint = right.hullChain.getFirst();
            this.leftBridgePoint = null; // (K) ConvexChain.LEFT_SENTINEL;
        }

        if (!isNull(left) && !isNull(right)) {
            switch (iCase) {
                case 2:
                    goLeft(blp, right.hullChain.getLast());
                    break;
                case 3:
                    goRight(left.hullChain.getFirst(), brp);
                    break;
                case 4:
                    if (isBelowEdge(blp, blpp, brp)) {
                        goLeft(blpp, brp);
                    } else if (isBelowEdge(brp, blp, brpp)) {
                        goRight(blp, brpp);
                    } else if (blp.equals(left.hullChain.getRightSentinel()) && brp.equals(right.hullChain.getLeftSentinel())) {
                        goRight(left.hullChain.getFirst(), brpp);
                    } else {
                        this.leftBridgePoint = blp.equals(left.hullChain.getRightSentinel()) ? blpp : blp;
                        this.rightBridgePoint = brp.equals(right.hullChain.getLeftSentinel()) ? brpp : brp;
                    }
                    break;
            }
        }
    }

    @Override
    public boolean insert(ConvexChain<K> chain) {
        System.out.println("entering insert:");
        System.out.println("input: chain = " + chain);

        ConvexChain<K> extractedPoints = hullChain.insert(chain);

        System.out.println("ExtractedPoints to be pushed down: " + extractedPoints);
        pushDown(extractedPoints);

        System.out.println("Result after pushdown: \n" + this);
        System.out.println("Exiting insert.");
        return true;
    }

    private void pushDown(ConvexChain<K> chain) {
        if (!isNull(chain)) {
            //decide to go left or right -- using the pointToIndexMap
            int i = 0;
            while (i < chain.size() && !delegate.goRight(chain.get(i), level + 1)) {
                i++;
            }

            Map.Entry<ConvexChain<K>, ConvexChain<K>> chainPair = chain.split(i);
            if (isNull(left)) {
                left = new IntervalTreeImpl(this, delegate);
            }

            if (isNull(right)) {
                right = new IntervalTreeImpl(this, delegate);
            }
            if (!isNull(chainPair.getKey())) {
                left.insert(chainPair.getKey());
                if (leftBridgePoint == null) {
                    this.leftBridgePoint = chainPair.getKey().get(0);
                }
                updateBridge(this.leftBridgePoint, chainPair.getKey());
            }

            if (!isNull(chainPair.getValue())) {
                if (isNull(right)) {
                    right = new IntervalTreeImpl(this, delegate);
                }
                right.insert(chainPair.getValue());
                if (rightBridgePoint == null) {
                    this.rightBridgePoint = chainPair.getValue().get(0);
                }
                updateBridge(this.rightBridgePoint, chainPair.getValue());
            }
        }
    }

    private void updateBridge(K b, ConvexChain<K> chain) {
//        System.out.println("entering updateBridge");
//        System.out.println("OldBridgePoint: " + b);
//        System.out.println("New chain: " + chain);

        if (!isNull(chain)) {
            for (K p : chain) {
                updateBridgePoint(p, b);
                if (isNull(left) && !isNull(right)) { // handles the case where the left subtree is null
                    break;
                }
            }
        } else {
            if (b != null && b.equals(this.leftBridgePoint)) {
                this.leftBridgePoint = left.leftBridgePoint;
            }
            if (b != null && b.equals(this.rightBridgePoint)) {
                this.rightBridgePoint = right.rightBridgePoint;
            }
        }
    }

    private void updateBridgePoint(K p, K b) {
        if (isNull(left) && isNull(right)) {
            this.leftBridgePoint = null; //(K) ConvexChain.LEFT_SENTINEL;
            this.rightBridgePoint = null; //(K) ConvexChain.RIGHT_SENTINEL;
        }

        if (!isNull(left) && isNull(right)) {
            this.leftBridgePoint = left.hullChain.getLast();
            this.rightBridgePoint = null; // (K) ConvexChain.RIGHT_SENTINEL;
        }

        if (isNull(left) && !isNull(right)) {
            this.rightBridgePoint = right.hullChain.getFirst();
            this.leftBridgePoint = null; // (K) ConvexChain.LEFT_SENTINEL;
        }

        if (!isNull(left) && !isNull(right)) {
            if (b != null && b.equals(this.leftBridgePoint)) {
                this.leftBridgePoint = p;
                if (this.rightBridgePoint != null) {
                    int i = right.hullChain.indexOf(rightBridgePoint);
                    while (i < right.hullChain.size() - 1 && isAboveBridge(right.hullChain.successor(i))) {
                        rightBridgePoint = right.hullChain.successor(i);
                        i++;
                    }
                } else if (this.rightBridgePoint != null) {
                    int i = right.hullChain.indexOf(rightBridgePoint);
                    while (i > 0 && isAboveBridge(right.hullChain.predecessor(i))) {
                        rightBridgePoint = right.hullChain.predecessor(i);
                        i--;
                    }
                }
            }

            if (b != null && b.equals(this.rightBridgePoint)) {
                this.rightBridgePoint = p;
                if (leftBridgePoint != null) {
                    int i = left.hullChain.indexOf(leftBridgePoint);
                    while (i < left.hullChain.size() - 1 && isAboveBridge(left.hullChain.successor(i))) {
                        leftBridgePoint = left.hullChain.successor(i);
                        i++;
                    }
                }

                if (leftBridgePoint != null) {
                    int i = left.hullChain.indexOf(leftBridgePoint);
                    while (i > 0 && isAboveBridge(left.hullChain.predecessor(i))) {
                        leftBridgePoint = left.hullChain.predecessor(i);
                        i--;
                    }
                }
            }
        }
    }

    private <T extends IntervalTree<K>> boolean isNull(T c) {
        return c == null || c.isEmpty();
    }

    private <T extends Collection<K>> boolean isNull(T c) {
        return c == null || c.isEmpty();
    }

    @Override
    public ConvexChain<K> getHullChain() {
        return hullChain;
    }

    @Override
    public String toString() {
        return print("", true, "");
    }

    private String print(String prefix, boolean isTail, String printedTree) {
        printedTree += prefix + (isTail ? "└── " : "├── ") + hullChain
                + "  LBP: " + leftBridgePoint + ", RBP: " + rightBridgePoint + "\n";
        if (left != null) {
            printedTree = left.print(prefix + (isTail ? "\t" : "│\t"), false, printedTree);
        }

        if (right != null) {
            printedTree = right.print(prefix + (isTail ? "\t" : "│\t"), true, printedTree);
        }

        return printedTree;
    }

    private boolean counterClockwise(K l, K r, K p) {
        return RadialComparator.<K>relativeCCW(l, r, p) > 0;
    }

    private boolean clockwise(K l, K r, K p) {
        return RadialComparator.<K>relativeCCW(l, r, p) < 0;
    }

    private boolean isAboveBridge(K al) {
        if (leftBridgePoint != null && rightBridgePoint != null) {
            return counterClockwise(leftBridgePoint, rightBridgePoint, al);
        } else if (leftBridgePoint == null && rightBridgePoint != null) {
            return counterClockwise(rightBridgePoint, rightBridgePoint, al);
        } else if (leftBridgePoint != null && rightBridgePoint == null) {
            return counterClockwise(leftBridgePoint, leftBridgePoint, al);
        } else {
            return false;
        }
    }

    private boolean isBelowBridge(K al) {
        if (leftBridgePoint != null && rightBridgePoint != null) {
            return clockwise(leftBridgePoint, rightBridgePoint, al); // al is below bridge
        } else if (leftBridgePoint == null && rightBridgePoint != null) {
            return clockwise(rightBridgePoint, rightBridgePoint, al);
        } else if (leftBridgePoint != null && rightBridgePoint == null) {
            return clockwise(leftBridgePoint, leftBridgePoint, al);
        } else {
            return false;
        }
    }

    /**
     * Returns {@code true} if the point p lies below the edge connect l to r.
     *
     * @param p
     * @param l
     * @param r
     * @return
     */
    private boolean isBelowEdge(K p, K l, K r) {
        return !counterClockwise(l, r, p);
    }

    /**
     * Returns {@code true} if the point p lies above the edge l-r.
     *
     * @param p
     * @param l
     * @param r
     * @return
     */
    private boolean isAboveEdge(K p, K l, K r) {
        return counterClockwise(l, r, p);
    }

    private void goLeft(K blp, K last) {
        if (blp != null) {
            leftBridgePoint = blp;
        }
        if (last != null) {
            rightBridgePoint = last;
        }
        int blpi = blp == null ? left.hullChain.size() - 1 : left.hullChain.indexOf(blp);
        int brpi = right.hullChain.size() - 1;

        while (blpi > 0 && isAboveEdge(left.hullChain.predecessor(blpi), leftBridgePoint, rightBridgePoint)) {
            leftBridgePoint = left.hullChain.predecessor(blpi);
            --blpi;

            while (brpi > 0 && isAboveEdge(right.hullChain.predecessor(brpi), leftBridgePoint, rightBridgePoint)) {
                rightBridgePoint = right.hullChain.predecessor(brpi);
                --brpi;
            }
        }

        while (brpi > 0 && isAboveEdge(right.hullChain.predecessor(brpi), leftBridgePoint, rightBridgePoint)) {
            rightBridgePoint = right.hullChain.predecessor(brpi);
            --brpi;
        }
    }

    private void goRight(K first, K brp) {
        if (first != null) {
            leftBridgePoint = first;
        }
        if (brp != null) {
            rightBridgePoint = brp;
        }
        int blpi = 0;
        int brpi = brp == null ? 0 : right.hullChain.indexOf(brp);

        while (blpi < left.hullChain.size() && isAboveEdge(left.hullChain.successor(blpi), leftBridgePoint, rightBridgePoint)) {
            leftBridgePoint = left.hullChain.successor(blpi);
            ++blpi;

            while (brpi < right.hullChain.size() && isAboveEdge(right.hullChain.successor(brpi), leftBridgePoint, rightBridgePoint)) {
                rightBridgePoint = right.hullChain.successor(brpi);
                ++brpi;
            }
        }

        while (brpi < right.hullChain.size() && isAboveEdge(right.hullChain.successor(brpi), leftBridgePoint, rightBridgePoint)) {
            rightBridgePoint = right.hullChain.successor(brpi);
            ++brpi;
        }
    }

    @Override
    public boolean insert(K point) {
        return add(point);
    }

    @Override
    public boolean add(K point) {
        ConvexChain<K> trivialChain = new ConvexChainImpl<>();
        trivialChain.add(point);

        return insert(trivialChain);
    }

    @Override
    public void delete(K point) {
        ConvexChain<K> trivialChain = new ConvexChainImpl<>();
        trivialChain.add(point);

        delete(trivialChain);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public boolean isEmpty() {
        return hullChain.isEmpty();
    }

    private K successor(K p) {
        int k = hullChain.indexOf(p);
        return hullChain.successor(k);
    }

    private K predecessor(K p) {
        int k = hullChain.indexOf(p);
        return hullChain.predecessor(k);
    }
}
