/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.impl;

import cg.common.comparators.RadialComparator;
import cg.convexhull.exact.ConvexHull;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author rrufai
 */
public class GrahamScan<T extends Point> implements ConvexHull<T> {

    private List<T> pointset;
    private List<T> convexHullVertices;
    private T anchor;
    private boolean valid;

    /**
     *
     */
    public GrahamScan() {
        this(new ArrayList<T>());
    }

    /**
     *
     * @param pointset
     */
    public GrahamScan(List<T> pointset) {
        this.pointset = pointset;
        convexHullVertices = new ArrayList<>();
        valid = false;
    }

    private Geometry<T> convexHull() {
        if (pointset == null || pointset.isEmpty()) {
            return new Polygon2D(Collections.<Point2D>emptyList());
        }

        if (pointset.size() < 4) {
            return new Polygon2D(pointset);
        }

        anchor = findAnchor();

        RadialComparator comparator = new RadialComparator(anchor);
        Collections.sort((ArrayList) pointset, comparator);
        //   System.out.println(pset);
        Stack<T> stack = new Stack<>();

        //assert (pointset.get(0) == anchor);

        //push first two points
        stack.push(pointset.get(0));
        stack.push(pointset.get(1));
        for (int i = 2; i < pointset.size(); i++) {
            final T currentPoint = pointset.get(i);
            if (stack.size() > 1) {
                comparator.setAnchor(stack.elementAt(stack.size() - 2));

                while (stack.size() > 1 && comparator.compare(stack.peek(), currentPoint) == RadialComparator.Orientation.COUNTERCLOCKWISE.getCode()) {
                    stack.pop(); //throw away

                    if (stack.size() > 1) {
                        comparator.setAnchor(stack.elementAt(stack.size() - 2));
                    }
                }
            }

            stack.push(currentPoint);
        }

        //push all stack content to collection -- they should come out in counter-clockwise order
        this.convexHullVertices.addAll(stack);

        return new Polygon2D<>(convexHullVertices);
    }

    /**
     * Returns a point to use as the center of reference to measure angles
     * between other points and the horizontal. Required to do radial sorting.
     * Another way to look as an anchor is as an endpoint of a segment. Suppose
     * we are to compare p1 to p2. We can view this as comparing the segment
     * (anchor, p1) to the point p2, to see if it falls to the left or to the
     * right of the segment.
     *
     * @returns
     */
    private T findAnchor() {
        assert (pointset != null && pointset.size() > 0);

        T min = this.pointset.get(0);
        for (T p : this.pointset) {
            if (min.getY() > p.getY()) {
                min = p;
            }
        }

        anchor = min;
        return anchor;
    }

    @Override
    public Geometry<T> compute(Geometry geom) {
        valid = false;
        return compute();
    }

    @Override
    public Geometry<T> compute() {
        Geometry<T> hull;
        if (valid) {
            hull = new Polygon2D<>(convexHullVertices);
        } else {
            hull = convexHull();
            valid = true;
        }
        
        return hull;
    }
}
