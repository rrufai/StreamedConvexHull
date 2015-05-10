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
public class EarHeightGoodness implements Goodness{

    @Override
    public double computeGoodness(Point p, Point q, Point r, Point centroid) {
        return 2* StreamedConvexUtility.area(p, q, r)/p.distance(r);
    }

    
}
