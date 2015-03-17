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
public class PushDownEvent<K extends Point> extends AbstractEvent<K> {

    private IntervalTree parent;

    /**
     *
     * @param p
     * @param layer
     */
    public PushDownEvent(K p, IntervalTree<K> parent) {
        super(p);
        this.parent = parent;
    }


    @Override
    public String toString() {
        return "@" + getClass() + "[point: " + getBasePoint().toString() + ", parent: " + parent + "]";
    }

    public IntervalTree getParent() {
        return parent;
    }

}
