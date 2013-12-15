/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;

import cg.common.comparators.GeometricComparator;
import cg.common.comparators.LexicographicComparator;
import cg.common.comparators.LexicographicComparator.Direction;
import cg.common.comparators.RadialComparator;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Triangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A sweep line algorithm to compute the convex layers of a given set of points.
 *
 * @param <K>
 * @author Raimi Rufai <rrufai@gmu.edu>
 */
public class ConvexLayerSweepAlgo<K extends Point> implements ConvexLayers<K> {

    private List<K> pointset;
    private List<List<K>> convexLayers;
    private List<TreeSet<K>> upper;
    private List<TreeSet<K>> lower;
    private List<TreeSet<K>> leftToRight;
    private List<TreeSet<K>> rightToLeft;
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private double EPSILON = GeometricComparator.EPSILON;
    private Map<Integer, List<Triangle2D<K>>> triangleMap;
    private int maxLayers;

    /**
     *
     */
    public ConvexLayerSweepAlgo() {
        this(new ArrayList());
    }

    /**
     *
     * @param pointset
     */
    public ConvexLayerSweepAlgo(List<K> pointset) {
        convexLayers = new ArrayList<>();
        this.pointset = pointset;
        triangleMap = new HashMap<>();
    }

    boolean isGreaterThanOrEqual(double coord1, double coord2) {
        return Math.abs(coord1 - coord2) < EPSILON || coord1 - coord2 > EPSILON;
    }

    /**
     * @param pointset the point set to set
     */
    @Override
    public void setPointset(List<K> pointset) {
        this.pointset = pointset;
    }

    /**
     * Returns the convex layers of this object's pointset. The method will
     * return a cached copy if pointset has not changed since the last
     * invocation.
     *
     * @return
     */
    @Override
    public List<List<K>> compute() {
        if (pointset == null || pointset.isEmpty()) {
            return Collections.<List<K>>emptyList();
        }

        if (pointset.size() > 3) {
            upper = computeUpperLayers(pointset);
            LOGGER.log(Level.INFO, "Upper layers: {0}", upper.toString());
            lower = computeLowerLayers(pointset);
            LOGGER.log(Level.INFO, "Lower layers: {0}", lower.toString());

            leftToRight = computeLeftToRightLayers(pointset);
            LOGGER.log(Level.INFO, "LeftToRight layers: {0}", leftToRight.toString());
            rightToLeft = computeRightToLeftLayers(pointset);
            LOGGER.log(Level.INFO, "RightToLeft layers: {0}", rightToLeft.toString());


            convexLayers = mergeLayers(upper, lower, leftToRight, rightToLeft);
        } else {
            Collections.sort(pointset, new RadialComparator<>(pointset.get(0)));
            convexLayers.add(pointset);
        }

        return convexLayers;
    }

    protected List<K> computeMergedLayer(int i, List<TreeSet<K>> upperLayers, List<TreeSet<K>> lowerLayers) {
        TreeSet<K> upperLayer = upperLayers.get(i);
        TreeSet<K> lowerLayer = lowerLayers.get(i);

        List<K> resultList = new ArrayList<>();
        try {
            K leftIntersection = computeIntersection(upperLayer, lowerLayer);
            K rightIntersection = computeIntersection(lowerLayer, upperLayer);

            try {
                upperLayer = (TreeSet<K>) upperLayer.subSet(leftIntersection, rightIntersection);
                resultList.addAll(upperLayer);
                if (leftIntersection.equals(rightIntersection)
                        && !resultList.contains(rightIntersection)) {
                    resultList.add(rightIntersection);
                }
            } catch (Exception ex) {
                System.err.println("Message: " + ex.getMessage()
                        + "\nCause: " + ex.getCause()
                        + "\n rightIntersection: " + rightIntersection
                        + "\n leftIntersection: " + leftIntersection);
            }


            try {
                lowerLayer = (TreeSet<K>) lowerLayer.subSet(rightIntersection, leftIntersection);
                resultList.addAll(lowerLayer);
                if (rightIntersection.equals(leftIntersection)
                        && !resultList.contains(rightIntersection)) {
                    resultList.add(rightIntersection);
                }
            } catch (Exception ex) {
                System.err.println("Message: " + ex.getMessage()
                        + "\nCause: " + ex.getCause()
                        + "\n bottomRightIntersection: " + rightIntersection
                        + "\n bottomLeftIntersection: " + leftIntersection);
            }

//            if (upperLayer != null && !upperLayer.isEmpty() && lowerLayer != null && !lowerLayer.isEmpty()
//                    && !upperLayer.first().equals(lowerLayer.last())) {
//                //query the content of the triangle T if it's empty
//                //If it's not empty then traverse 
//                Triangle2D<K> triangle = new Triangle2D(leftIntersection, upperLayer.first(), lowerLayer.last());
//                List<Triangle2D<K>> list = triangleMap.get(i);
//                if (list == null) {
//                    list = new ArrayList<>();
//                }
//                list.add(triangle);
//                triangleMap.put(i, list);
//
//                if (i > 0 && !triangleMap.get(i - 1).isEmpty()) {
//                    List<Triangle2D<K>> triangleList = triangleMap.get(i - 1);
//                    for (Triangle2D<K> t : triangleList) {
//                        if (t.contains(leftIntersection)) {
//                        }
//                    }
//                }
//            }
        } catch (Exception ex) {
            System.err.println("Message: " + ex.getMessage()
                    + "\nCause: " + ex.getCause());
        }


        return resultList;
    }

    public List<List<K>> mergeLayers(List<TreeSet<K>> upperLayers, List<TreeSet<K>> lowerLayers, List<TreeSet<K>> leftToRightLayers, List<TreeSet<K>> rightToLeftLayers) {
        List<List<K>> mergedLayers = new ArrayList();
        int nonTrivialLayer = findInnermostNonTrivialLayer(upperLayers, lowerLayers, leftToRightLayers, rightToLeftLayers);

        for (int i = 0; i < nonTrivialLayer; i++) {
            List<K> resultList = computeMergedLayer(i, upperLayers, lowerLayers);

            //eliminateMultiLayerVertices(mergedLayers, upperLayers.get(i), lowerLayers.get(i));
            //eliminateMultiLayerVertices(mergedLayers, leftToRightLayers.get(i), rightToLeftLayers.get(i));
            if (resultList.size() > 0) {
                mergedLayers.add(resultList);
            }

            resultList = computeMergedLayer(i, leftToRightLayers, rightToLeftLayers);
            if (resultList.size() > 0) {
                mergedLayers.add(resultList);
            }
        }

        //cleanup 
        int i = 0;
        while (i < nonTrivialLayer && i < mergedLayers.size()) {
            if (mergedLayers.get(i).isEmpty()) {
                mergedLayers.remove(i);
                nonTrivialLayer--;
            } else {
                i++;
            }
        }

        maxLayers = mergedLayers.size();
        return mergedLayers;
    }

    protected int findInnermostNonTrivialLayer(List<TreeSet<K>> upperLayers, List<TreeSet<K>> lowerLayers, List<TreeSet<K>> leftToRightLayers, List<TreeSet<K>> rightToLeftLayers) {
        //find innermost nontrivial layer
        int nonTrivialLayerIndex = Math.min(Math.min(leftToRightLayers.size(), rightToLeftLayers.size()), Math.min(upperLayers.size(), lowerLayers.size())) - 1;
        for (int i = nonTrivialLayerIndex; i > -1; i--) {
            boolean nontrivialTopDown = isGreaterThanOrEqual(highest(upperLayers.get(i)).getY(), lowest(lowerLayers.get(i)).getY());
            boolean nontrivialLeftRight = isGreaterThanOrEqual(highest(rightToLeftLayers.get(i)).getX(), lowest(leftToRightLayers.get(i)).getX());

            if (nontrivialTopDown || nontrivialLeftRight) { //ignore points
                nonTrivialLayerIndex = i;
                break;
            }
        }
        return nonTrivialLayerIndex;
    }

    private K lowest(TreeSet<K> set) {
        K min = set.first();

        for (K p : set) {
            if (isGreaterThanOrEqual(min.getY(), p.getY())) {
                min = p;
            }
        }

        return min;
    }

    private K highest(TreeSet<K> set) {
        K max = set.first();

        for (K p : set) {
            if (isGreaterThanOrEqual(p.getY(), max.getY())) {
                max = p;
            }
        }

        return max;
    }

    /**
     * Compute the intersection between the line segment (p1, p2) and the
     * segment (p3, p4).
     *
     * @param p1 first endpoint of segment (p1, p2)
     * @param p2 second endpoint of segment (p1, p2)
     * @param p3 first endpoint of segment (p3, p4)
     * @param p4 second endpoint of segment (p3, p4)
     * @return
     */
    K computeIntersection(K p1, K p2, K p3, K p4) {
        double xD1, yD1, xD2, yD2, xD3, yD3;
        double ua, div;

        if ((p1 == null) || (p2 == null) || (p3 == null) || (p4 == null)) {
            return null;
        }
// calculate differences

        xD1 = p2.getX() - p1.getX();
        xD2 = p4.getX() - p3.getX();
        yD1 = p2.getY() - p1.getY();
        yD2 = p4.getY() - p3.getY();
        xD3 = p1.getX() - p3.getX();
        yD3 = p1.getY() - p3.getY();


        div = yD2 * xD1 - xD2 * yD1;

        if (Math.abs(div) < EPSILON) { //parallel lines
            return null;
        }

        ua = (xD2 * yD3 - yD2 * xD3) / div;

        K pt = (K) new Point2D(p1.getX() + ua * xD1, p1.getY() + ua * yD1);

        return pt;
    }

    /**
     * Computes the upper hull layers of the given point set. The algorithm
     * follows the sweep-line paradigm. The event queue is initialized with
     * <code>NewPointEvent</code>s. The event queue is then processed one event
     * after the other. While processing a
     * <code>NewPointEvent</code>, some points may need to be moved to inner
     * layer. To do that,
     * <code>EvictionEvent</code>s are generated and added to the event queue.
     *
     * @param pointset A set of points in arbitrary order.
     * @return a list of upper hull layers for the given point set.
     */
    protected List<TreeSet<K>> computeUpperLayers(List<K> pointset) {
        PriorityQueue<Event<K>> eventQueue = new PriorityQueue<>(pointset.size(),
                new LexicographicComparator(Direction.TOP_DOWN));
        for (K p : pointset) {
            eventQueue.add(new NewPointEvent(p));
        }

        return computeHalfLayers(eventQueue, Direction.RIGHT_TO_LEFT);
    }

    /**
     * Computes the lower hull layers of the given point set. The algorithm
     * follows the sweep-line paradigm. The event queue is initialized with
     * <code>NewPointEvent</code>s. The event queue is then processed one event
     * after the other. While processing a
     * <code>NewPointEvent</code>, some points may need to be moved to an inner
     * layer. To do that,
     * <code>EvictionEvent</code>s are generated and added to the event queue.
     *
     * @param pointset A set of points in arbitrary order.
     * @return a list of upper hull layers for the given point set.
     */
    protected List<TreeSet<K>> computeLowerLayers(List<K> pointset) {
        PriorityQueue<Event<K>> eventQueue = new PriorityQueue(pointset.size(),
                new LexicographicComparator(Direction.BOTTOM_UP));
        for (K p : pointset) {
            eventQueue.add(new NewPointEvent(p));
        }

        return computeHalfLayers(eventQueue, Direction.LEFT_TO_RIGHT);
    }

    private List<TreeSet<K>> computeLeftToRightLayers(List<K> pointset) {
        PriorityQueue<Event<K>> eventQueue = new PriorityQueue(pointset.size(),
                new LexicographicComparator(Direction.LEFT_TO_RIGHT));
        for (K p : pointset) {
            eventQueue.add(new NewPointEvent(p));
        }

        return computeHalfLayers(eventQueue, Direction.TOP_DOWN);
    }

    private List<TreeSet<K>> computeRightToLeftLayers(List<K> pointset) {
        PriorityQueue<Event<K>> eventQueue = new PriorityQueue(pointset.size(),
                new LexicographicComparator(Direction.RIGHT_TO_LEFT));
        for (K p : pointset) {
            eventQueue.add(new NewPointEvent(p));
        }

        return computeHalfLayers(eventQueue, Direction.BOTTOM_UP);
    }

    private List<TreeSet<K>> computeHalfLayers(
            PriorityQueue<Event<K>> eventQueue, Direction direction) {
        List<TreeSet<K>> partialHull = new ArrayList();

        while (!eventQueue.isEmpty()) {
            Event<K> event = eventQueue.poll();
            int layer = 0;
            LOGGER.log(Level.INFO, event.toString());
            if (event instanceof EvictionEvent) {
                EvictionEvent<K> evictionEvent = (EvictionEvent<K>) event;
                partialHull.get(evictionEvent.getLayer()).remove(event.getBasePoint());
                layer = evictionEvent.getLayer() + 1;
            }

            // NB: NewPointEvent insert into layer 0
            insert(event.getBasePoint(), eventQueue, partialHull, layer, direction);
        }

        return partialHull;
    }

    private void insert(K eventPoint, PriorityQueue<Event<K>> eventQueue,
            List<TreeSet<K>> partialHull, int layer, Direction direction) {
        K me = eventPoint;

        if (partialHull.size() < layer + 1) {
            partialHull.add(layer, new TreeSet(
                    new LexicographicComparator(direction)));
        }

        K higher = partialHull.get(layer).higher(me);
        K _lower = partialHull.get(layer).lower(me);
        K llower = null;
        K hhigher = null;

        if (_lower != null) {
            llower = partialHull.get(layer).lower(_lower);
        }

        if (higher != null) {
            hhigher = partialHull.get(layer).higher(higher);
        }

        if (higher != null && _lower != null) {
            if (isRightTurn(_lower, me, higher)) {
                partialHull.get(layer).add(me);
                if (higher != null) {
                    while (_lower != null && isRightTurn(higher, me, _lower)) {
                        eventQueue.add(new EvictionEvent(_lower, layer)); // insert instead and get rid of previous line?
                        me = _lower;
                        _lower = partialHull.get(layer).lower(me);
                    }
                }

                if (_lower != null) {
                    while (higher != null && isRightTurn(higher, me, _lower)) {
                        eventQueue.add(new EvictionEvent(higher, layer)); // insert instead and get rid of previous line?
                        me = higher;
                        higher = partialHull.get(layer).higher(me);
                    }
                }
            } else {
                insert(eventPoint, eventQueue, partialHull, layer + 1, direction);
            }
        } else {
            partialHull.get(layer).add(me);
            while (llower != null && isRightTurn(me, _lower, llower)) {
                eventQueue.add(new EvictionEvent(_lower, layer)); // insert instead and get rid of previous line?
                _lower = llower;
                llower = partialHull.get(layer).lower(_lower);
            }

            while (hhigher != null && isRightTurn(hhigher, higher, me)) {
                eventQueue.add(new EvictionEvent(higher, layer)); // insert instead and get rid of previous line?
                higher = hhigher;
                hhigher = partialHull.get(layer).higher(higher);
            }
        }
    }

    private boolean isRightTurn(K bottom, K middle, K top) {
        return new RadialComparator(bottom).compare(middle, top) < 0;
    }

    /**
     * @return the upper layers
     */
    public List<TreeSet<K>> getUpper() {
        return upper;
    }

    /**
     * @return the lower layers
     */
    public List<TreeSet<K>> getLower() {
        return lower;
    }

    @Override
    public List<K> getPointset() {
        return pointset;
    }

    private K computeIntersection(TreeSet<K> upper, TreeSet<K> lower) {
        K upper_left = upper.first();
        K upper_left_next = upper.higher(upper_left);
        K lower_left = lower.last();
        K lower_left_next = lower.lower(lower_left);

        K leftIntersection = computeIntersection(
                upper_left, upper_left_next,
                lower_left, lower_left_next);
        System.out.println("Intersection: " + leftIntersection);

        return leftIntersection;
    }

    /**
     * @return the triangleMap
     */
    public Map<Integer, List<Triangle2D<K>>> getTriangleMap() {
        return triangleMap;
    }

    private void eliminateMultiLayerVertices(List<List<K>> mergedLayers, TreeSet<K> upperLayer, TreeSet<K> lowerLayer) {
        // Eliminate multi-layer vertices -- perhaps could be done faster
        if (mergedLayers.size() > 0) {
            int layers = mergedLayers.size();
            for (int j = 0; j < layers; j++) {
                for (K v : mergedLayers.get(j)) {
                    if (upperLayer.contains(v)) {
                        upperLayer.remove(v);
                    }

                    if (lowerLayer.contains(v)) {
                        lowerLayer.remove(v);
                    }
                }
            }
        }
    }

    public List<TreeSet<K>> getLeftToRight() {
        return leftToRight;
    }

    public List<TreeSet<K>> getRightToLeft() {
        return rightToLeft;
    }

    public int getMaxLayers() {
        return maxLayers;
    }
}
