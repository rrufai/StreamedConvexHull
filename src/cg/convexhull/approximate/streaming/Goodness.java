/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;

/**
 *
 * @author I827590
 */
public interface Goodness {

    double computeGoodness(Point p, Point q, Point r, Point centroid);
    
}
