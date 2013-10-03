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
    private static double EPSILON = 1e-9;

    public Point2D(double x, double y) {
        point = new java.awt.geom.Point2D.Double(x, y);
    }

    @Override
    public double getX() {
        return getPoint().getX();
    }

    @Override
    public double getY() {
        return getPoint().getY();
    }

    @Override
    public void setLocation(double x, double y) {
        setLocation(x, y);
    }

    public double distanceSq(double px, double py) {
        px -= getPoint().getX();
        py -= getPoint().getY();
        return (px * px + py * py);
    }

    public double distanceSq(Point pt) {
        return this.distance(pt.getX(), pt.getY());
    }

    public double distance(double px, double py) {
        px -= getPoint().getX();
        py -= getPoint().getY();
        return Math.sqrt(px * px + py * py);
    }

    @Override
    public double distance(Point pt) {
        return distance(pt.getX(), pt.getY());
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
        return getPoint().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point p2d = (Point) obj;
            return (getX() - p2d.getX()) < EPSILON && (getY() - p2d.getY()) < EPSILON;
        }
        return super.equals(obj);
    }

    /**
     * @return the point
     */
    public java.awt.geom.Point2D getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(java.awt.geom.Point2D point) {
        this.point = new java.awt.geom.Point2D.Double(point.getX(), point.getY());
    }

    @Override
    public String toString() {
        String textForm = "{" + point.x + ", " + point.y + "}";
        return textForm;
    }

    @Override
    public int compareTo(Object obj) {
        if (obj instanceof Point) {
            Point p2d = (Point) obj;
            double xDistance = getX() - p2d.getX();
            double yDistance = getY() - p2d.getY();
            if (Math.abs(xDistance) < EPSILON && Math.abs(yDistance) < EPSILON) {
                return 0;
            } else if (xDistance > EPSILON || (Math.abs(xDistance) < EPSILON && yDistance > EPSILON)) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return 1;
        }
    }
}
