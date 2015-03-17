/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.convexchain;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.Deque;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author I827590
 */
public interface ConvexChain<K extends Point> extends List<K>, Deque<K> {
    Point LEFT_SENTINEL = new Point2D(-0.5 * Double.MAX_VALUE, -0.5 * Double.MAX_VALUE);
    Point RIGHT_SENTINEL = new Point2D(0.5 * Double.MAX_VALUE, 0.5 * Double.MAX_VALUE);
    /**
     * Inserts a new vertex
     * <code>newPoint</code> into a convex chain of vertices. this involves two
     * cases:
     *
     * 1. the new point is not interior to the convex chain, compute the tangent
     * to the convex chain. There convex chain of points below (but not
     * including) the tangent point are extracted and replaced by
     * {@code newPoint}. The extracted chain is then returned.
     *
     * 2. The new point is interior to this convex chain. In this case, an
     * {@link IllegalArgumentException} is thrown.
     *
     * @param newPoint
     * @return The extracted chain replaced by {@code newPoint}.
     * @throws IllegalArgumentException
     */
    ConvexChain<K> insert(ConvexChain<K> newPoint) throws IllegalArgumentException;

    /**
     * Inserts a new vertex
     * <code>newPoint</code> into a convex chain of vertices, right before
     * <code>existingPoint</code>.
     *
     * @param newPoint
     * @param existingPoint
     */
    void insertBefore(K newPoint, K existingPoint) throws IllegalArgumentException;

    /**
     * Inserts a new vertex
     * <code>newPoint</code> into a convex chain of vertices, right after
     * <code>existingPoint</code>.
     *
     * @param newPoint
     * @param existingPoint
     */
    void insertAfter(K newPoint, K existingPoint);

    /**
     * Computes the point of tangency made by the given point with this convex
     * chain.
     *
     * @param newPoint
     * @return the tangent point
     * @throws IllegalArgumentException
     */
    int getTangentPoint(K newPoint) throws IllegalArgumentException;

    /**
     * Returns {@code true} if the given point {@code newPoint} is interior to
     * this convex chain, {@code false} otherwise.
     *
     * @param newPoint
     * @return {@code true} if the input {@code newPoint} is interior,
     * {@code false} otherwise.
     */
    boolean isInterior(K newPoint);

    int delete(ConvexChain<K> chain);

    Entry<ConvexChain<K>, ConvexChain<K>> split(int i);

    K getRightSentinel();

    K getLeftSentinel();
    K predecessor(int j);
    K successor(int i);
    
    boolean dominates(ConvexChain<K> chain);
}
