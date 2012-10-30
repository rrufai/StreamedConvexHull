/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.geometry.primitives.Edge;
import cg.geometry.primitives.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author rrufai
 */
public class Edge2D implements Edge {

    private Point origin;
    private Point terminus;

    /**
     * {@inheritDoc
     */
    @Override
    public Point getOrigin() {
        return origin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point getTerminus() {
        return terminus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTerminus(Point terminus) {
        this.terminus = terminus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double length() {
        return origin.distance(terminus);
    }

    @Override
    public Rectangle getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Point2D p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
