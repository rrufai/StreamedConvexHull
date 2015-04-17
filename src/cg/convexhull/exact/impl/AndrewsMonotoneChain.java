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
import cg.geometry.primitives.Point;
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
public class AndrewsMonotoneChain<T extends Point> implements ConvexHull<T> {

    private List<T> pointset;
    private List<T> convexHullVertices;
    private boolean valid;

    /**
     *
     */
    public AndrewsMonotoneChain() {
        this(new ArrayList<T>());
    }

    /**
     *
     * @param pointset
     */
    public AndrewsMonotoneChain(List<T> pointset) {
        this.pointset = pointset;
        valid = false;
    }

    /**
     *
     * @param pointset
     */
    public AndrewsMonotoneChain(Geometry<T> geometry) {
        this.pointset = geometry.getVertices();
        valid = false;
    }

    /**
     *
     * @return
     */
    private List<T> convexHull() {
        if (pointset == null) {
            throw new RuntimeException("Pointset is null!");
        }
        List<T> pset = new ArrayList<>();
        pset.addAll(pointset);
        Collections.sort((ArrayList) pset, new LexicographicComparator(Direction.LEFT_TO_RIGHT));
        List<T> vertices = pset;
        if (pset.size() > 3) {
            final List<T> upper = computeUpperHull(pset);
            final List<T> lower = computeLowerHull(pset);
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
    private List<T> mergeHulls(final List<T> upper, final List<T> lower) {
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
    private List<T> computeUpperHull(final List<T> pset) {
        return computeHalfHull(pset.iterator());
    }

    /**
     *
     * @param pset
     * @return
     */
    private List<T> computeLowerHull(final List<T> pset) {
        return computeHalfHull(new ReverseIterator(pset));
    }

    /**
     *
     * @param iter
     * @return
     */
    private List<T> computeHalfHull(final Iterator<T> iter) {
        final Stack<T> stack = new Stack();

        while (iter.hasNext()) {
            final T current = iter.next();

            while (!isRightTurn(stack, current) && stack.size() > 1) {
                stack.pop();
            }

            stack.push(current);
        }

        valid = true;
        List<T> partialHull = new ArrayList(stack.size());
        partialHull.addAll(stack);

        return partialHull;
    }

    /**
     *
     * @param stack
     * @param current
     * @return
     */
    private boolean isRightTurn(final Stack<T> stack, final T current) {
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
    private boolean isRightTurn(final T bottom, final T middle, final T top) {
        return new RadialComparator(bottom).compare(middle, top) == RadialComparator.Orientation.COUNTERCLOCKWISE.getCode();
    }

    /**
     * Compute convex hull.
     *
     * @return the vertices on the convex hull.
     */
    @Override
    public Geometry<T> compute(Geometry<T> geom) {
        return compute(geom.getVertices());
    }

    @Override
    public Geometry<T> compute(List<T> pointset) {
        valid = false;
        this.pointset = pointset;

        if (!valid) {
            convexHullVertices = convexHull();
            valid = true;
        }

        return new Polygon2D<>(convexHullVertices);
    }

    @Override
    public Geometry<T> compute() {
        if (!valid) {
            convexHullVertices = convexHull();
            valid = true;
        }

        return new Polygon2D<>(convexHullVertices);
    }
}
