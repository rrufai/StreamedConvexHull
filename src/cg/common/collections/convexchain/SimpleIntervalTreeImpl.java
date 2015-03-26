package cg.common.collections.convexchain;

import cg.common.Utilities;
import cg.common.comparators.RadialComparator;
import cg.convexlayers.events.IntervalTree;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import com.rits.cloning.Cloner;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
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
    private final IntervalTree<K> parent;

    public SimpleIntervalTreeImpl(IntervalTree<K> parent,
            ConvexLayersIntervalTree<K> delegate) {
        hullChain = new ConvexChainImpl<>();
        this.delegate = delegate;
        this.level = parent != null ? parent.getLevel() + 1 : 0;
        this.parent = parent;

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
        if (!isNull(chain) && !isNull(hullChain)) {
            System.out.println("this before: \n" + this);
            // Note: the segment al-ar is the segment that used to be below the 
            // chain that has just been deleted from hullChain.
            int j = hullChain.indexOf(chain.getFirst());
            K al = hullChain.predecessor(j);
            K ar = hullChain.get(j + chain.size());
            j = hullChain.delete(chain);
            Entry<ConvexChain<K>, ConvexChain<K>> roof = hullChain.split(j);
            if (!isNull(left) || !isNull(right)) {
                Entry<K, K> bridge = bridgePoints(left, right);
                Entry<K, K> bcw = new SimpleEntry<>(left.successor(bridge.getKey()), right.predecessor(bridge.getValue()));
                System.out.printf("al = %s, ar = %s \n", al, ar);

                // Case I: Nothing to do -- Both Roof leftovers are above the bridge
                // Neither subtree contributes to new roof
                if (!counterClockwise(al, ar, bridge.getKey()) && !counterClockwise(al, ar, bridge.getValue())) {
                    //if (!counterClockwise(al, ar, bridge.getKey()) && !counterClockwise(al, ar, bridge.getValue())) {
                    //do nothing
                    System.out.println("Case I: Nothing to do -- Both Roof leftovers are above the bridge");
                }

                //Case II: Only Left Roof leftover (.. al) is above bridge
                // Only right subtree contributes to new roof
                if ((isNull(left) || counterClockwise(bridge.getKey(), bridge.getValue(), al)) && (Utilities.isMonotonic(bridge.getValue(), ar) || !counterClockwise(bridge.getKey(), bridge.getValue(), ar))) {
                    //if ((isNull(left) || !counterClockwise(al, ar, bridge.getKey())) && counterClockwise(al, ar, bridge.getValue())) {
                    System.out.println("Case II: Only Left Roof leftover (.. al) is above bridge");
                    Entry<K, K> ik = tangents(al, ar, right, right);
                    int fromIndex = right.hullChain.indexOf(ik.getKey());
                    int toIndex = right.hullChain.indexOf(ik.getValue()) + 1;
                    ConvexChain<K> chainToDelete = new ConvexChainImpl(right.hullChain.subList(fromIndex, toIndex));

                    ConvexChain<K> newHullChain = new ConvexChainImpl<>(roof.getKey());
                    newHullChain.addAll(chainToDelete);
                    newHullChain.addAll(roof.getValue());
                    hullChain = new ConvexChainImpl<>(newHullChain);
                    Entry<K, K> newCursors = getNewCursors(bcw);
                    leftToRightCursor = newCursors.getKey();
                    rightToLeftCursor = newCursors.getValue();

                    right.delete(chainToDelete);
                }

                //Case III: Only Right Roof leftover (ar ...) is above bridge
                //Only left subtree contributes to new roof
                if (!isNull(left) && !counterClockwise(bridge.getKey(), bridge.getValue(), al) && (isNull(right) || counterClockwise(bridge.getKey(), bridge.getValue(), ar))) {
                    //if (!isNull(left) && counterClockwise(al, ar, bridge.getKey()) && (isNull(right) || !counterClockwise(al, ar, bridge.getValue()))) {    
                    System.out.println("Case III: Only Right Roof leftover (ar ...) is above bridge");
                    Entry<K, K> ik = tangents(al, ar, left, left);
                    int fromIndex = left.hullChain.indexOf(ik.getKey());
                    int toIndex = left.hullChain.indexOf(ik.getValue()) + 1;
                    ConvexChain<K> chainToDelete = new ConvexChainImpl(left.hullChain.subList(fromIndex, toIndex));

                    ConvexChain<K> newHullChain = new ConvexChainImpl<>(roof.getKey());
                    newHullChain.addAll(chainToDelete);
                    newHullChain.addAll(roof.getValue());
                    hullChain = new ConvexChainImpl<>(newHullChain);
                    Entry<K, K> newBC = getNewCursors(bcw);
                    leftToRightCursor = newBC.getKey();
                    rightToLeftCursor = newBC.getValue();

                    left.delete(chainToDelete);
                }

                // Case IV: Both roof leftovers are below the bridge
                // both subtrees contribute to building the new roof
                //if (!isNull(left) && !isNull(right) && counterClockwise(al, ar, bridge.getKey()) && counterClockwise(al, ar, bridge.getValue())) {
                if (!isNull(left) && !isNull(right) && !counterClockwise(bridge.getKey(), bridge.getValue(), al) && !counterClockwise(bridge.getKey(), bridge.getValue(), ar)) {
                    System.out.println("Case IV: Both roof leftovers are below the bridge.");
                    Entry<K, K> ik = tangents(al, ar, left, right);
                    int fromIndex = left.hullChain.indexOf(ik.getKey());
                    int toIndex = left.hullChain.indexOf(bridge.getKey()) + 1;
                    ConvexChain<K> leftChainToDelete = new ConvexChainImpl<>(left.hullChain.subList(fromIndex, toIndex));

                    fromIndex = right.hullChain.indexOf(bridge.getValue());
                    toIndex = right.hullChain.indexOf(ik.getValue()) + 1;
                    ConvexChain<K> rightChainToDelete = new ConvexChainImpl(right.hullChain.subList(fromIndex, toIndex));

                    ConvexChain<K> newHullChain = new ConvexChainImpl<>(roof.getKey());
                    newHullChain.addAll(leftChainToDelete);
                    newHullChain.addAll(rightChainToDelete);
                    newHullChain.addAll(roof.getValue());
                    hullChain = new ConvexChainImpl<>(newHullChain);
                    Entry<K, K> newBC = getNewCursors(bcw);
                    leftToRightCursor = newBC.getKey();
                    rightToLeftCursor = newBC.getValue();
                    left.delete(leftChainToDelete);
                    right.delete(rightChainToDelete);
                }

            } else {
                if (!hullChain.isEmpty()) {
                    leftToRightCursor = hullChain.getFirst();
                    rightToLeftCursor = hullChain.getLast();
                    hullChain.setLeftSentinel(hullChain.getLeftSentinel());
                    hullChain.setRightSentinel(hullChain.getRightSentinel());
                }
            }
            System.out.println("this after: \n" + this);
        }
        System.out.println("exiting delete");
    }

    @Override
    public boolean insert(ConvexChain<K> chain) {
        System.out.println("entering insert:");
        System.out.println("input: chain = " + chain);

        ConvexChain<K> extractedPoints = hullChain.insert(chain);

        System.out.println("ExtractedPoints to be pushed down: " + extractedPoints);
        pushDown(extractedPoints);

        getHullChain().setLeftSentinel(getHullChain().getLeftSentinel());
        getHullChain().setRightSentinel(getHullChain().getRightSentinel());
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
            }

            if (!isNull(chainPair.getValue())) {
                right.insert(chainPair.getValue());
            }
            this.leftToRightCursor = hullChain.getFirst();
            this.rightToLeftCursor = hullChain.getLast();
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
                + "  b: " + leftToRightCursor + ", c: " + rightToLeftCursor + "\n";
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

        K leftBridgePoint = null;
        K rightBridgePoint = null;

        if (!isNull(left)) {
            if (left.leftToRightCursor == null) {
                left.leftToRightCursor = left.hullChain.getFirst();
            }
            assert left.leftToRightCursor != null;
            leftBridgePoint = left.leftToRightCursor;
        }
//        else {
//            leftBridgePoint = (left == null || (isNull(left.getHullChain()) && getParent() != null)) ? getParent().getHullChain().getLeftSentinel() : left.hullChain.getLeftSentinel();
//        }

        if (!isNull(right)) {
            if (right.rightToLeftCursor == null) {
                right.rightToLeftCursor = right.hullChain.getLast();
            }
            assert right.rightToLeftCursor != null;
            rightBridgePoint = right.rightToLeftCursor;
        }
//        else {
//            rightBridgePoint = (right == null || isNull(right.hullChain)) ? getParent().getHullChain().getRightSentinel() : right.hullChain.getRightSentinel();
//        }

        Entry<K, K> bridge = new AbstractMap.SimpleEntry<>(leftBridgePoint, rightBridgePoint);

        //search upwards for left bridgepoint
        if (!isNull(left) && !left.leftToRightCursor.equals(left.hullChain.getLast()) && counterClockwise(left.leftToRightCursor, bridge.getValue(), left.successor(left.leftToRightCursor))) {
            left.leftToRightCursor = left.successor(left.leftToRightCursor);
            if (left.leftToRightCursor.getX() > left.rightToLeftCursor.getX()) {
                left.rightToLeftCursor = left.leftToRightCursor;
            }
            bridge = bridgePoints(left, right);
        }

        //search downwards for left bridgepoint
        if (!isNull(left) && !left.leftToRightCursor.equals(left.hullChain.getFirst())
                && counterClockwise(left.leftToRightCursor, bridge.getValue(), left.predecessor(left.leftToRightCursor))) {
            left.leftToRightCursor = left.predecessor(left.leftToRightCursor);

            bridge = bridgePoints(left, right);
        }

        //search downwards for right bridgepoint
        if (!isNull(right) && !right.rightToLeftCursor.equals(right.hullChain.getFirst()) && counterClockwise(bridge.getKey(), right.rightToLeftCursor, right.predecessor(right.rightToLeftCursor))) {
            right.rightToLeftCursor = right.predecessor(right.rightToLeftCursor);
            if (right.leftToRightCursor.getX() > right.rightToLeftCursor.getX()) {
                right.leftToRightCursor = right.rightToLeftCursor;
            }

            bridge = bridgePoints(left, right);
        }

        //search upwards for right bridgepoint
        if (!isNull(right) && !right.rightToLeftCursor.equals(right.hullChain.getLast()) && counterClockwise(bridge.getKey(), right.rightToLeftCursor, right.successor(right.rightToLeftCursor))) {
            right.rightToLeftCursor = right.successor(right.rightToLeftCursor);

            bridge = bridgePoints(left, right);
        }
        return bridge;
    }

    SimpleIntervalTreeImpl getLeftChild() {
        return left;
    }

    SimpleIntervalTreeImpl getRightChild() {
        return right;
    }

    Entry<K, K> tangents(K al, K ar, SimpleIntervalTreeImpl<K> left, SimpleIntervalTreeImpl<K> right) {
        K b;
        K c;

        if (!isNull(left)) {
            b = left.leftToRightCursor;
        } else {
            b = left.getParent().getHullChain().getLeftSentinel();
        }

        if (!isNull(right)) {
            c = right.rightToLeftCursor;
        } else {
            c = right.getParent().getHullChain().getLeftSentinel();
        }

        Entry<K, K> bc = new AbstractMap.SimpleEntry<>(b, c);

        //Search upwards for tangent point
        if (!left.hullChain.getLast().equals(left.leftToRightCursor) && !counterClockwise(al, left.successor(left.leftToRightCursor), left.leftToRightCursor)) {
            left.leftToRightCursor = left.successor(left.leftToRightCursor);

            if (left.leftToRightCursor.getX() > left.rightToLeftCursor.getX()) {
                left.rightToLeftCursor = left.leftToRightCursor;
            }
            bc = tangents(al, ar, left, right);
        }

        // Search downwards for tangent point
        if (!left.hullChain.getFirst().equals(left.leftToRightCursor) && counterClockwise(al, left.leftToRightCursor, left.predecessor(left.leftToRightCursor))) {
            left.leftToRightCursor = left.predecessor(left.leftToRightCursor);

            bc = tangents(al, ar, left, right);
        }

        // Search downwards for tangent point
        if (!right.hullChain.getFirst().equals(right.rightToLeftCursor) && counterClockwise(right.rightToLeftCursor, ar, right.predecessor(right.rightToLeftCursor))) {
            right.rightToLeftCursor = right.predecessor(right.rightToLeftCursor);

            if (right.leftToRightCursor.getX() > right.rightToLeftCursor.getX()) {
                right.leftToRightCursor = right.rightToLeftCursor;
            }
            bc = tangents(al, ar, left, right);
        }

        // Search upwards for tangent point
        if (!right.hullChain.getLast().equals(right.rightToLeftCursor) && counterClockwise(right.rightToLeftCursor, ar, right.successor(right.rightToLeftCursor))) {
            right.rightToLeftCursor = right.successor(right.rightToLeftCursor);

            bc = tangents(al, ar, left, right);
        }

        return bc;
    }

    private SimpleEntry<K, K> getNewCursors(Entry<K, K> bc) {
        K b, c;
        if (hullChain.indexOf(bc.getKey()) <= 0) {
            b = hullChain.getFirst();
        } else {
            b = bc.getKey();
        }

        if (hullChain.indexOf(bc.getValue()) == hullChain.size() - 1 || !hullChain.contains(bc.getValue())) {
            c = hullChain.getLast();
        } else {
            c = bc.getValue();
        }
        return new AbstractMap.SimpleEntry<>(b, c);
    }

    private IntervalTree<K> getParent() {
        return parent;
    }
}
