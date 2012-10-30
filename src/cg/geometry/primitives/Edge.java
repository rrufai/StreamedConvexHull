/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives;

import java.awt.Shape;

/**
 *
 * @author rrufai
 */
public interface Edge extends Shape{

   /**
     * @return the origin of this edge
     */
    Point getOrigin();

    /**
     * @return the terminus of this edge
     */
    Point getTerminus();

    /**
     * @param origin the origin to set
     */
    void setOrigin(Point origin);

    /**
     * @param terminus the terminus to set
     */
    void setTerminus(Point terminus);
    
    /**
     * @return length of this edge.
     */
    double length();
}
