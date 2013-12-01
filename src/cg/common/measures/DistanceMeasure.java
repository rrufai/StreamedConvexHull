/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.measures;

import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;

/**
 *
 * @author I827590
 */
public interface DistanceMeasure<T extends Point> {
   double measure(Geometry<T> geom1, Geometry<T> geom2);
}
