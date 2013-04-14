/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives;

import cg.geometry.primitives.impl.Point2D;
import java.awt.Graphics;
import java.util.List;

/**
 * A representation of a a 2-dimensional graph embedding. 
 * Possible implementation could be a doubly-connected edge list (DCEL).
 * 
 * @author rrufai
 */
public interface Geometry {
    List<Point2D> getVertices();
    List<Edge> getEdges();
    List<Face> getFaces();
    void draw(Graphics canvas);
}
