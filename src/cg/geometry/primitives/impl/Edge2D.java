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
import java.awt.geom.Rectangle2D;

/**
 *
 * @author rrufai
 */
public class Edge2D implements Edge {

    private Point2D origin;
    private Point2D terminus;

    public Edge2D(Point2D origin, Point2D terminus) {
        this.origin = origin;
        this.terminus = terminus;
    }

    /**
     * {@inheritDoc
     */
    @Override
    public Point2D getOrigin() {
        return origin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrigin(Point2D origin) {
        this.origin = origin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getTerminus() {
        return terminus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTerminus(Point2D terminus) {
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
    
    public boolean contains(Point2D p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean contains(java.awt.geom.Point2D p) {
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
        return new PathIterator() {
            private Point currentPoint = origin;
            private int type = PathIterator.SEG_MOVETO;
            private boolean done;
        
            @Override
            public int getWindingRule() {
                return PathIterator.WIND_EVEN_ODD;
            }

            @Override
            public boolean isDone() {
                return done;
            }

            @Override
            public void next() {
                if(currentPoint == terminus){
                    done = true;
                }
                currentPoint = terminus;
                type = PathIterator.SEG_LINETO;                
            }
                

            @Override
            public int currentSegment(float[] coords) {
                coords[0] = (float)currentPoint.getX();
                coords[1] = (float)currentPoint.getY();
               return type ;
            }

            @Override
            public int currentSegment(double[] coords) {
                coords[0] = currentPoint.getX();
                coords[1] = currentPoint.getY();
             return type;
            }
        };

    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
