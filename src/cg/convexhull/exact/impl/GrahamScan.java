/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.impl;

import cg.convexhull.exact.ConvexHull;
import cg.common.comparators.RadialComparator;
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
public class GrahamScan implements ConvexHull {

    private List<Point2D> pointset;
    private List<Point2D> convexHullVertices;
    private Point2D anchor;
    private boolean valid;

    /**
     *
     */
    public GrahamScan() {
        this(new ArrayList<Point2D>());
    }

    /**
     *
     * @param pointset
     */
    public GrahamScan(List<Point2D> pointset) {
        this.pointset = pointset;
        convexHullVertices = new ArrayList<>(); 
        valid = false;
    }

    private Geometry convexHull() {
        if (pointset == null || pointset.isEmpty()) {
            return new Polygon2D(Collections.<Point2D>emptyList());
        }

        if (pointset.size() > -1 && pointset.size() < 4) {
            return new Polygon2D(pointset);
        }

        anchor = findAnchor();
        List<Point2D> pset = (List<Point2D>) ((ArrayList) pointset).clone();
        pset.remove(anchor);
        RadialComparator comparator = new RadialComparator(anchor);
        Collections.sort((ArrayList) pset, comparator);
        //   System.out.println(pset);
        Stack<Point2D> stack = new Stack();

        //assert (pointset.get(0) == anchor);

        stack.push(anchor);
        stack.push(pset.get(0));

        for (int i = 1; i < pset.size(); i++) {
            if (stack.size() > 1) {
                comparator.setAnchor(stack.elementAt(stack.size() - 2));

                while (comparator.compare(stack.peek(), pset.get(i)) > 0) {
                    stack.pop();
                    if (stack.size() > 1) {
                        comparator.setAnchor(stack.elementAt(stack.size() - 2));
                    } else {
                        break;
                    }
                }
            }

            stack.push(pset.get(i));

        }

        valid = true;

        //unwind the stack, so the points come out counter-clockwise
        while (!stack.empty()) {
            this.convexHullVertices.add(stack.remove(stack.size() - 1));
        }


        return new Polygon2D(convexHullVertices);
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
    private Point2D findAnchor() {
        assert (pointset != null && pointset.size() > 0);

        Point2D min = this.pointset.get(0);
        for (Point2D p : this.pointset) {
            if (min.getY() > p.getY()) {
                min = p;
            }
        }

        anchor = min;
        return anchor;
    }

    @Override
    public Geometry compute(Geometry geom) {
        if (valid) {
            return new Polygon2D(convexHullVertices);
        } else {
            this.pointset = geom.getVertices();
            return convexHull();
        }
    }
}
