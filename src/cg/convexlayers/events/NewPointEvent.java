/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers.events;

import cg.geometry.primitives.Point;

/**
 *
 * @param <K>
 * @author rrufai
 */
public class NewPointEvent<K extends Point> extends AbstractEvent<K> {

    /**
     *
     * @param p
     */
    public NewPointEvent(K p) {
        super(p);
    }


    @Override
    public String toString() {
        return "@" + getClass() + "[point: " + getBasePoint().toString() + "]";
    }
}
