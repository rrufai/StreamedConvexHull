/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact;

import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;

/**
 *
 * @author rrufai
 */
public interface ConvexHull<T extends Point> {

    /**
     * Computes the convex hull of the given geometry.
     * 
     * @param geom
     * @return convex hull of points passed in.
     */
    Geometry<T> compute(Geometry<T> geom);

    /**
     * Computes the convex hull of its initial point set.
     * 
     * @return convex hull of its initial point set.
     */
    Geometry<T> compute();
}
