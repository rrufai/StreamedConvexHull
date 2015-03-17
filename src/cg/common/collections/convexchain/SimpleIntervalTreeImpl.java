package cg.common.collections.convexchain;

import cg.common.comparators.RadialComparator;
import cg.convexlayers.events.IntervalTree;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import com.rits.cloning.Cloner;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author rrufai@gmu.edu
 */
class SimpleIntervalTreeImpl<K extends Point> implements IntervalTree<K> {

    private ConvexChain<K> hullChain;
    private int level;
    private SimpleIntervalTreeImpl<K> left;
    private SimpleIntervalTreeImpl<K> right;
    private ConvexLayersIntervalTree<K> delegate;
    private K leftToRightCursor;
    private K rightToLeftCursor;
    private String START = "--------------------START--------------------";
    private String END = "--------------------END--------------------";

    public SimpleIntervalTreeImpl(IntervalTree<K> parent,
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
        if (!isNull(chain) && !isNull(hullChain)) {
            int j = hullChain.delete(chain);

            // Note: the segment al-ar is the segment that used to be below the 
            // chain that has just been deleted from hullChain.
            K al = hullChain.predecessor(j);
            K ar = hullChain.get(j);
            Entry<K, K> bc = bridgePoints(left, right);
            System.out.printf("al = %s, ar = %s \n", al, ar);

            // Case IV: Both roof leftovers are below the bridge -- includes the case when the roof is empty.
            if (isNull(hullChain) || (isBelowBridge(al) && isBelowBridge(ar))) {
                handleCaseIV();
            } else // Case I: Nothing to do -- Both Roof leftovers are above the bridge
            if (!isNull(hullChain) && isAboveBridge(al) && !isBelowBridge(ar)) {
                handleCaseI();
            } else //Case II: Only Left Roof leftover is above bridge
            if (!isNull(hullChain) && isAboveBridge(al) && isBelowBridge(ar)) {
                handleCaseII();
            } else //Case III: Only Right Roof leftover is above bridge
            if (!isNull(hullChain) && isBelowBridge(al) && isAboveBridge(ar)) {
                handleCaseIII();
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
    private void handleCaseII() throws IllegalArgumentException {
        System.out.println("Case II --  Only Left Roof leftover is above bridge. \n");
    }

    //Case III --  Only Right Roof leftover is above bridge. 
    private void handleCaseIII() throws IllegalArgumentException {
        System.out.println("Case III --  Only Right Roof leftover is above bridge. \n");
    }

    private void handleCaseIV() throws IllegalArgumentException {
        System.out.println("Case IV \n -- Both roof leftovers are below the bridge. Eg. when the entire roof is being deleted !\n");

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
                left = new SimpleIntervalTreeImpl(this, delegate);
            }

            if (isNull(right)) {
                right = new SimpleIntervalTreeImpl(this, delegate);
            }
            if (!isNull(chainPair.getKey())) {
                left.insert(chainPair.getKey());
                if (leftToRightCursor == null) {
                    this.leftToRightCursor = chainPair.getKey().get(0);
                }
            }

            if (!isNull(chainPair.getValue())) {
                if (isNull(right)) {
                    right = new SimpleIntervalTreeImpl(this, delegate);
                }
                right.insert(chainPair.getValue());
                if (rightToLeftCursor == null) {
                    this.rightToLeftCursor = chainPair.getValue().get(0);
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
                + "  LBP: " + leftToRightCursor + ", RBP: " + rightToLeftCursor + "\n";
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
        if (leftToRightCursor != null && rightToLeftCursor != null) {
            return counterClockwise(leftToRightCursor, rightToLeftCursor, al);
        } else if (leftToRightCursor == null && rightToLeftCursor != null) {
            return counterClockwise(rightToLeftCursor, rightToLeftCursor, al);
        } else if (leftToRightCursor != null && rightToLeftCursor == null) {
            return counterClockwise(leftToRightCursor, leftToRightCursor, al);
        } else {
            return false;
        }
    }

    private boolean isBelowBridge(K al) {
        if (leftToRightCursor != null && rightToLeftCursor != null) {
            return clockwise(leftToRightCursor, rightToLeftCursor, al); // al is below bridge
        } else if (leftToRightCursor == null && rightToLeftCursor != null) {
            return clockwise(rightToLeftCursor, rightToLeftCursor, al);
        } else if (leftToRightCursor != null && rightToLeftCursor == null) {
            return clockwise(leftToRightCursor, leftToRightCursor, al);
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

    protected Entry<K, K> bridgePoints(SimpleIntervalTreeImpl<K> left, SimpleIntervalTreeImpl<K> right) {
        K leftBridgePoint = (K) new Point2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        K rightBridgePoint = (K) new Point2D(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);

        if (!isNull(left) && !isNull(right) && counterClockwise(left.leftToRightCursor, right.rightToLeftCursor, left.successor(left.leftToRightCursor))) {
            left.leftToRightCursor = left.successor(left.leftToRightCursor);
            if (left.leftToRightCursor.getX() > left.rightToLeftCursor.getX()) {
                left.rightToLeftCursor = left.leftToRightCursor;
            }
            return bridgePoints(left, right);
        }

        if (!isNull(left) && !isNull(right) && counterClockwise(left.leftToRightCursor, right.rightToLeftCursor, left.predecessor(right.rightToLeftCursor))) {
            right.rightToLeftCursor = left.predecessor(right.rightToLeftCursor);
            if (right.leftToRightCursor.getX() > right.rightToLeftCursor.getX()) {
                right.leftToRightCursor = right.rightToLeftCursor;
            }

            return bridgePoints(left, right);
        }

        //handle the case where left is null but right is not
        if (isNull(left) && !isNull(right)) {
            rightBridgePoint = right.rightToLeftCursor;
            leftBridgePoint = (K) new Point2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        }

        //handle the case where right is null but left is not
        if (!isNull(left) && isNull(right)) {
            leftBridgePoint = left.leftToRightCursor;
            rightBridgePoint = (K) new Point2D(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        }

        return new AbstractMap.SimpleEntry<>(leftBridgePoint, rightBridgePoint);
    }

    SimpleIntervalTreeImpl getLeftChild() {
        return left;
    }

    SimpleIntervalTreeImpl getRightChild() {
        return right;
    }
}
