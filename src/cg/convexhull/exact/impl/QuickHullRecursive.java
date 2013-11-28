/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.impl;

import cg.common.comparators.LexicographicComparator;
import cg.common.comparators.LexicographicComparator.Direction;
import cg.common.comparators.RadialComparator;
import cg.convexhull.exact.ConvexHull;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rrufai
 */
public class QuickHullRecursive<T extends Point> implements ConvexHull<T> {

    private List<T> pointset;
    private List<T> convexHullVertices;
    private boolean valid;

    /**
     *
     */
    public QuickHullRecursive() {
        convexHullVertices = new ArrayList<>();
        valid = false;
    }

    /**
     *
     * @param pointset
     */
    public QuickHullRecursive(List<T> pointset) {
        this();
        this.pointset = pointset;

    }

    /**
     * Computes the convex hull of the given point set and returns them in
     * counter-clockwise order, starting with the leftmost point.
     *
     * @return list of points on the convex hull in counter clockwise order
     */
    //private
    public List<T> convexHull() {
        if (pointset == null || pointset.isEmpty()) {
            return pointset;
        }

        final List<T> pset = (List<T>) ((ArrayList<T>) pointset).clone();

        //compute leftmost point, l and rightmost point, r.
        final Map<String, T> map = computeLR(pset);

        final List<T> upper =
                leftHalfHull(map.get("leftmost"), map.get("rightmost"), pset);
        final List<T> lower =
                leftHalfHull(map.get("rightmost"), map.get("leftmost"), pset);

        convexHullVertices = mergeHulls(upper, lower);

        return convexHullVertices;
    }

    /**
     *
     * @param upper
     * @param lower
     * @return
     */
    //private
    public List<T> mergeHulls(final List<T> upper, final List<T> lower) {
        List<T> smaller = lower, bigger = upper;
        
        if(upper.size() < lower.size()){
            smaller = upper;
            bigger = lower;
        }
        
        for(T p : smaller){
            if(!bigger.contains(p)){
                bigger.add(p);
            }
        }
        
        return bigger;
    }

    /**
     * Computes the convex hull of the point set to the left of the line segment
     * left-right.
     *
     * @param left
     * @param right
     * @param pointSet
     * @return
     */
    //private
    public List<T> leftHalfHull(final T left, final T right, final List<T> pointSet) {
        List<T> newPointSet = new ArrayList<>();
        if (pointSet.size() > 0) {
            //compute the point h, farthest from segment l-r
            final T farthest = computeFarthest(left, right, pointSet);
            //partition pointSet into S1 = {points to the left of segment l-h}
            final List<T> subsetL = computeSubset(left, farthest, pointSet);
            // S2 = {points to the left of segment h-r}
            final List<T> subsetR = computeSubset(farthest, right, pointSet);

            if (!farthest.equals(left)) {
                newPointSet.addAll(leftHalfHull(left, farthest, subsetL));
            } else {
                newPointSet.add(farthest);
            }

            if (!farthest.equals(right)) {
                newPointSet.addAll(leftHalfHull(farthest, right, subsetR));
            } else {
                newPointSet.add(farthest);
            }

            //Remove duplicate
            assert (newPointSet.contains(farthest));
            newPointSet.remove(farthest);

            if (!newPointSet.contains(farthest)) {
                newPointSet.add(farthest);
            }
        }

        if(!newPointSet.contains(left)){
            newPointSet.add(left);
        }
        
        if(!newPointSet.contains(right)){
            newPointSet.add(right);
        }

        return newPointSet;
    }

    /**
     * Computes the point farthest in distance to the segment left-right from
     * the given point set, pSet.
     *
     * @param left
     * @param right
     * @param pSet
     * @return
     */
    public T computeFarthest(final T left, final T right, final List<T> pSet) {
        RadialComparator comparator = new RadialComparator();
        T farthest = pSet.get(0);
        double max = comparator.ccw(left, right, farthest);
        if (pSet.size() > 2) {
            for (T point : pSet) {
                if (max < comparator.ccw(left, right, point)) {
                    farthest = point;
                    max = comparator.ccw(left, right, point);
                }
            }
        }
        return farthest;
    }

    /**
     * Computes points to the left of segment left-right given point set, pSet.
     *
     * @param left
     * @param right
     * @param pSet
     * @return
     */
    public List<T> computeSubset(
            final T left, final T right, final List<T> pSet) {
        RadialComparator comparator = new RadialComparator();
        Set<T> subset = new HashSet<>();

        if (pSet.size() > 0) {
            for (T point : pSet) {
                if (comparator.ccw(left, right, point) > 0) {
                    subset.add(point);
                }
            }
        }

        return new ArrayList(subset);
    }

    /**
     * Returns a map that contains the two keys, "leftmost", "rightmost"
     * corresponding to the entries with the leftmost and rightmost point in the
     * given list of points, pset.
     *
     * @param pset
     * @return a map with the leftmost and rightmost points in the a given point
     * set.
     */
    //private
    public Map<String, T> computeLR(List<T> pset) {
        Map<String, T> map = null;
        if (pset.size() > 0) {
            map = new HashMap();
            T leftmost = pset.get(0);
            T rightmost = pset.get(0);
            final LexicographicComparator comp = new LexicographicComparator(Direction.LEFT_TO_RIGHT);
            for (T p : pset) {
                if (comp.compare(p, leftmost) == -1) {
                    leftmost = p;
                }

                if (comp.compare(p, rightmost) == 1) {
                    rightmost = p;
                }
            }
            map.put("leftmost", leftmost);
            map.put("rightmost", rightmost);
        }
        return map;
    }

    List<T> rightHalfHull(T l, T r, List<T> pointset) {
        return leftHalfHull(r, l, pointset);
    }

    /**
     * Compute convex hull.
     *
     * @return the vertices on the convex hull.
     */
    @Override
    public Geometry<T>  compute() {
        Collection<T> vertices;
        if (valid) {
            vertices = this.convexHullVertices;
        } else {
            vertices = convexHull();
            valid = true;
        }

        return new Polygon2D(vertices);
    }
    
        @Override
    public Geometry<T> compute(Geometry<T> geom) {
        pointset = geom.getVertices();
        valid = false;

        return compute();
    }
}
