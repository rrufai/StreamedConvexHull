/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives;

/**
 *
 * @author rrufai
 */
public interface Point  extends Comparable {
    String getName();
    
    double getX();

    double getY();

    void setLocation(double x, double y);

    double distance(Point terminus);
    
    java.awt.geom.Point2D getPoint();

}
