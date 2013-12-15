/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;

import cg.geometry.primitives.Point;


/**
 *
 * @param <K>
 * @author rrufai
 */
public class EvictionEvent<K extends Point>  extends AbstractEvent<K> {

    private int layer;

    /**
     *
     * @param p
     * @param layer
     */
    public EvictionEvent(K p, int layer) {
        super(p);
        this.layer = layer;
    }

   
    /**
     *
     * @return
     */
    public int getLayer() {
        return layer;
    }

    @Override
    public String toString() {
        return "@" + getClass() + "[point: " + getBasePoint().toString() + ", layer: " + layer + "]";
    }

}
