/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;
import java.util.Comparator;


/**
 *
 * @author I827590
 */
class GoodnessComparator<S extends StreamedPoint2D<? extends Point>> implements Comparator<S> {

    @Override
    public int compare(S p1, S p2) {
        return (int) Math.signum(p1.getGoodnessMeasure() - p2.getGoodnessMeasure());
    }
    
}
