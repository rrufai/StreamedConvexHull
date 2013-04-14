/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.impl;

import cg.common.comparators.LexicographicComparator;
import cg.common.comparators.LexicographicComparator.Direction;
import cg.convexhull.exact.ConvexHull;
import cg.common.comparators.RadialComparator;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rrufai
 */
public class QuickHullRecursive implements ConvexHull {

    private List<Point2D> pointset;
    private List<Point2D> convexHullVertices;
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
    public QuickHullRecursive(List<Point2D> pointset) {
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
    public List<Point2D> convexHull() {
        if (pointset == null || pointset.isEmpty()) {
            return pointset;
        }

        final List<Point2D> pset = (List<Point2D>) ((ArrayList) pointset).clone();

        //compute leftmost point, l and rightmost point, r.
        final Map<String, Point2D> map = computeLR(pset);

        final List<Point2D> upper =
                leftHalfHull(map.get("leftmost"), map.get("rightmost"), pset);
        final List<Point2D> lower =
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
    public List<Point2D> mergeHulls(final List<Point2D> upper, final List<Point2D> lower) {
        List<Point2D> smaller = lower, bigger = upper;
        
        if(upper.size() < lower.size()){
            smaller = upper;
            bigger = lower;
        }
        
        for(Point2D p : smaller){
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
    public List<Point2D> leftHalfHull(final Point2D left, final Point2D right, final List<Point2D> pointSet) {
        List<Point2D> newPointSet = new ArrayList();
        if (pointSet.size() > 0) {
            //compute the point h, farthest from segment l-r
            final Point2D farthest = computeFarthest(left, right, pointSet);
            //partition pointSet into S1 = {points to the left of segment l-h}
            final List<Point2D> subsetL = computeSubset(left, farthest, pointSet);
            // S2 = {points to the left of segment h-r}
            final List<Point2D> subsetR = computeSubset(farthest, right, pointSet);

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
    public Point2D computeFarthest(final Point2D left, final Point2D right, final List<Point2D> pSet) {
        RadialComparator comparator = new RadialComparator();
        Point2D farthest = pSet.get(0);
        double max = comparator.ccw(left, right, farthest);
        if (pSet.size() > 2) {
            for (Point2D point : pSet) {
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
    public List<Point2D> computeSubset(
            final Point2D left, final Point2D right, final List<Point2D> pSet) {
        RadialComparator comparator = new RadialComparator();
        Set<Point2D> subset = new HashSet<>();

        if (pSet.size() > 0) {
            for (Point2D point : pSet) {
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
    public Map<String, Point2D> computeLR(List<Point2D> pset) {
        Map<String, Point2D> map = null;
        if (pset.size() > 0) {
            map = new HashMap();
            Point2D leftmost = pset.get(0);
            Point2D rightmost = pset.get(0);
            final LexicographicComparator comp = new LexicographicComparator(Direction.LEFT_TO_RIGHT);
            for (Point2D p : pset) {
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

    List<Point2D> rightHalfHull(Point2D l, Point2D r, List<Point2D> pointset) {
        return leftHalfHull(r, l, pointset);
    }

    /**
     * Compute convex hull.
     *
     * @return the vertices on the convex hull.
     */
    @Override
    public Geometry compute(Geometry geom) {
        List<Point2D> vertices;
        if (valid) {
            vertices = this.convexHullVertices;
        } else {
            valid = true;
            this.pointset = geom.getVertices();
            vertices = convexHull();
        }

        return new Polygon2D(vertices);
    }
}
