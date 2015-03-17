/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers.events;

import cg.geometry.primitives.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author I827590
 */
class AbstractEvent<K extends Point> implements Event<K> {

    private K p;
    
    public AbstractEvent(K p){
        this.p = p;
    }

    /**
     *
     * @return
     */
    @Override
    public K getBasePoint() {
        return p;
    }

    @Override
    public double getX() {
        return p.getX();
    }

    @Override
    public double getY() {
        return p.getY();
    }

    @Override
    public void setLocation(double x, double y) {
        p.setLocation(x, y);
    }

    @Override
    public double distance(Point terminus) {
        return getPoint().distance(terminus.getPoint());
    }

    @Override
    public Point2D getPoint() {
        return p.getPoint();
    }

    @Override
    public int compareTo(Object o) {
        return p.compareTo(o);
    }

    @Override
    public String getName() {
        return p.getName();
    }
}
