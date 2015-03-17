/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;

import cg.common.comparators.GeometricComparator;
import cg.common.comparators.LexicographicComparator;
import cg.common.comparators.LexicographicComparator.Direction;
import cg.common.comparators.RadialComparator;
import cg.convexlayers.events.Event;
import cg.convexlayers.events.EvictionEvent;
import cg.convexlayers.events.NewPointEvent;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.Polygon;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    private List<Polygon<K>> convexLayers;
    private List<TreeSet<K>> upper;
    private List<TreeSet<K>> lower;
    private List<TreeSet<K>> leftToRight;
    private List<TreeSet<K>> rightToLeft;
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private double EPSILON = GeometricComparator.EPSILON;
    private int maxLayers;
    private HashSet usedMap;

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
    }

    private boolean isGreaterThanOrEqual(double coord1, double coord2) {
        return Math.abs(coord1 - coord2) < EPSILON || coord1 - coord2 > EPSILON;
    }

    /**
     * Returns the convex layers of this object's pointset. The method will
     * return a cached copy if pointset has not changed since the last
     * invocation.
     *
     * @return
     */
    @Override
    public List<Polygon<K>> compute() {
        if (pointset == null || pointset.isEmpty()) {
            return Collections.<Polygon<K>>emptyList();
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

            // convexLayers = mergeLayers_experimental(upper, lower, leftToRight, rightToLeft);
            convexLayers = mergeLayers(upper, lower, leftToRight, rightToLeft);
        } else {
            Collections.sort(pointset, new RadialComparator<>(pointset.get(0)));
            convexLayers.add(new Polygon2D<>(pointset));
        }

        return convexLayers;
    }

    public List<Polygon<K>> mergeLayers(List<TreeSet<K>> upperLayers, List<TreeSet<K>> lowerLayers, List<TreeSet<K>> leftToRightLayers, List<TreeSet<K>> rightToLeftLayers) {
        List<Polygon<K>> mergedLayers = new ArrayList<>();
        usedMap = new HashSet<>();
        int nonTrivialLayer = findInnermostNonTrivialLayer(upperLayers, lowerLayers, leftToRightLayers, rightToLeftLayers);

        for (int i = 0; i < nonTrivialLayer; i++) {
            Polygon<K> mergedPolygon = computeMergedLayer(upperLayers.get(i), lowerLayers.get(i), leftToRightLayers.get(i), rightToLeftLayers.get(i));


            if (mergedPolygon != null && !mergedPolygon.isEmpty()) {
                mergedLayers.add(mergedPolygon);
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
            int layer = -1;
            LOGGER.log(Level.INFO, event.toString());
            if (event instanceof EvictionEvent) {
                layer = ((EvictionEvent<K>) event).getLayer();
                partialHull.get(layer).remove(event.getBasePoint());
            }

            // NB: NewPointEvent insert into layer 0
            insert(event.getBasePoint(), eventQueue, partialHull, layer + 1, direction);
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

    /**
     * @param pointset the point set to set
     */
    @Override
    public void setPointset(List<K> pointset) {
        this.pointset = pointset;
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

    /**
     * New version that maintains already used up hull vertices from enclosing
     * layers in a hashMap.
     *
     * @param i
     * @param upperLayers
     * @param lowerLayers
     * @param leftToRightLayers
     * @param rightToLeftLayers
     * @return
     */
    private Polygon<K> computeMergedLayer(TreeSet<K> upperLayer, TreeSet<K> lowerLayer, TreeSet<K> leftToRightLayer, TreeSet<K> rightToLeftLayer) {
        List<K> vertexList = new ArrayList<>();

        for (K p : upperLayer) {
            if (!usedMap.contains(p)) {
                vertexList.add(p);
                usedMap.add(p);
            }
        }
        for (K p : leftToRightLayer) {
            if (!usedMap.contains(p)) {
                vertexList.add(p);
                usedMap.add(p);
            }
        }
        for (K p : lowerLayer) {
            if (!usedMap.contains(p)) {
                vertexList.add(p);
                usedMap.add(p);
            }
        }

        for (K p : rightToLeftLayer) {
            if (!usedMap.contains(p)) {
                vertexList.add(p);
                usedMap.add(p);
            }
        }
        return new Polygon2D<>(vertexList);
    }
}
