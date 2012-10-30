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

    Number getX();

    Number getY();

    void setLocation(Number x, Number y);

    public double distance(Point terminus);

}
