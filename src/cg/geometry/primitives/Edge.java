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
public interface Edge extends Shape{

   /**
     * @return the origin of this edge
     */
    Point2D getOrigin();

    /**
     * @return the terminus of this edge
     */
    Point2D getTerminus();

    /**
     * @param origin the origin to set
     */
    void setOrigin(Point2D origin);

    /**
     * @param terminus the terminus to set
     */
    void setTerminus(Point2D terminus);
    
    /**
     * @return length of this edge.
     */
    double length();
}
