/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact;

import cg.geometry.primitives.Geometry;

/**
 *
 * @author rrufai
 */
public interface ConvexHull {
    Geometry compute(Geometry geom);
}
