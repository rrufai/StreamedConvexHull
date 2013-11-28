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

    Geometry<T> compute(Geometry<T> geom);

    Geometry<T> compute();
}
