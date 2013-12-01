/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.pointsequences;

import cg.geometry.primitives.Point;
import java.util.List;

/**
 *
 * @author I827590
 * @param <T>
 */
public class FixedPointSequence2D<T extends Point> extends AbstractPointSequence<T> {

    /**
     * Points from a list
     */
    public FixedPointSequence2D(List<T> pointSequence) {
        super(pointSequence);
    } 

}
