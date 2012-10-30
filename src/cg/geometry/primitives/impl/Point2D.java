/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.geometry.primitives.Point;

/**
 *
 * @author rrufai
 */
public class Point2D implements Point {
    private java.awt.geom.Point2D.Double point;

    @Override
    public Number getX() {
        return point.getX();
    }

    @Override
    public Number getY() {
        return point.getY();
    }

    @Override
    public void setLocation(Number x, Number y) {
        setLocation(x.doubleValue(), y.doubleValue());
    }

    public double distanceSq(double px, double py) {
        px -= point.getX();
        py -= point.getY();
        return (px * px + py * py);
    }

    public double distanceSq(Point pt) {
        return this.distance(pt.getX().doubleValue(), pt.getY().doubleValue());
    }

    public double distance(double px, double py) {
        px -= point.getX();
        py -= point.getY();
        return Math.sqrt(px * px + py * py);
    }

    public double distance(Point pt) {
        return distance(pt.getX().doubleValue(), pt.getY().doubleValue());
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // should never happen
            throw new InternalError();
        }
    }

    @Override
    public int hashCode() {
        return point.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point p2d = (Point) obj;
            return (getX() == p2d.getX()) && (getY() == p2d.getY());
        }
        return super.equals(obj);
    }
    
}
