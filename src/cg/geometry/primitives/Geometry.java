/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives;

import java.util.List;

/**
 * A representation of a a 2-dimensional graph embedding. 
 * Possible implementation could be a doubly-connected edge list (DCEL).
 * 
 * @author rrufai
 */
public interface Geometry {
    List<Point> getVertices();
    List<Edge> getEdges();
    List<Face> getFaces();

}
