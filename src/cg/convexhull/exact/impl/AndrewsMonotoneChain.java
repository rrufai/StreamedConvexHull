/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.impl;

import cg.common.comparators.LexicographicComparator;
import cg.common.comparators.LexicographicComparator.Direction;
import cg.common.comparators.RadialComparator;
import cg.common.iterators.ReverseIterator;
import cg.convexhull.exact.ConvexHull;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 *
 *
 * @author rrufai
 */
public class AndrewsMonotoneChain implements ConvexHull {

    private List<Point2D> pointset;
    private List<Point2D> convexHullVertices;
    private boolean valid;

    /**
     *
     */
    public AndrewsMonotoneChain() {
        this(new ArrayList());
    }

    /**
     *
     * @param pointset
     */
    public AndrewsMonotoneChain(List<Point2D> pointset) {
        this.pointset = pointset;
        valid = false;
    }

    /**
     *
     * @return
     */
    private List<Point2D> convexHull() {
        if (pointset == null) {
            throw new RuntimeException("Pointset is null!");
        }
        List<Point2D> pset = new ArrayList<>();
        pset.addAll(pointset);
        Collections.sort((ArrayList) pset, new LexicographicComparator(Direction.BOTTOM_UP));
        List<Point2D> vertices = pset;
        if (pset.size() > 3) {
            final List<Point2D> upper = computeUpperHull(pset);
            final List<Point2D> lower = computeLowerHull(pset);
            vertices = mergeHulls(upper, lower);
        }

        return vertices;
    }

    /**
     *
     * @param upper
     * @param lower
     * @return
     */
    private List<Point2D> mergeHulls(final List<Point2D> upper, final List<Point2D> lower) {
        upper.remove(0);
        upper.remove(upper.size() - 1);

        lower.addAll(upper);
        return lower;
    }

    /**
     *
     * @param pset
     * @return
     */
    private List<Point2D> computeUpperHull(final List<Point2D> pset) {
        return computeHalfHull(pset.iterator());
    }

    /**
     *
     * @param pset
     * @return
     */
    private List<Point2D> computeLowerHull(final List<Point2D> pset) {
        return computeHalfHull(new ReverseIterator(pset));
    }

    /**
     *
     * @param iter
     * @return
     */
    private List<Point2D> computeHalfHull(final Iterator<Point2D> iter) {
        final Stack<Point2D> stack = new Stack();

        while (iter.hasNext()) {
            final Point2D current = iter.next();

            while (isRightTurn(stack, current)) {
                stack.pop();
            }

            stack.push(current);
        }

        valid = true;
        List<Point2D> partialHull = new ArrayList(stack.size());
        //partialHull.addAll(stack);
        while (!stack.empty()) {
            //partialHull.add(stack.remove(stack.size() - 1));
            partialHull.add(stack.pop());
        }
        return partialHull;
    }

    /**
     *
     * @param stack
     * @param current
     * @return
     */
    private boolean isRightTurn(final Stack<Point2D> stack, final Point2D current) {
        boolean retValue = false;
        if (stack.size() > 1) {
            retValue = isRightTurn(stack.elementAt(stack.size() - 2),
                    stack.peek(), current);
        }

        return retValue;
    }

    /**
     *
     * @param bottom
     * @param middle
     * @param top
     * @return
     */
    private boolean isRightTurn(final Point2D bottom, final Point2D middle, final Point2D top) {
        return new RadialComparator(bottom).compare(middle, top) > 0;

    }

    /**
     * Compute convex hull.
     *
     * @return the vertices on the convex hull.
     */
    @Override
    public Geometry compute(Geometry geom) {
        if (!valid) {
            valid = true;
            pointset = geom.getVertices();
            convexHullVertices = convexHull();
        }

        return new Polygon2D(convexHullVertices);
    }

}
