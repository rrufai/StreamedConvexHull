/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives;

/**
 *
 * @author rrufai
 */
public interface Point {

    double getX();

    double getY();

    void setLocation(double x, double y);

    public double distance(Point terminus);

}
