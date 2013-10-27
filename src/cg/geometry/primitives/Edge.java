/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives;

import cg.geometry.primitives.impl.Point2D;
import java.awt.Shape;

/**
 *
 * @author rrufai
 */
public interface Edge<T extends Point> extends Shape{

   /**
     * @return the origin of this edge
     */
    T getOrigin();

    /**
     * @return the terminus of this edge
     */
    T getTerminus();

    /**
     * @param origin the origin to set
     */
    void setOrigin(T origin);

    /**
     * @param terminus the terminus to set
     */
    void setTerminus(T terminus);
    
    /**
     * @return length of this edge.
     */
    double length();
}
