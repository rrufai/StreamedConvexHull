/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives;

import java.awt.Graphics;
import java.io.IOException;
import java.util.List;

/**
 * A representation of a a 2-dimensional graph embedding. 
 * Possible implementation could be a doubly-connected edge list (DCEL).
 * 
 * @author rrufai
 */
public interface Geometry<T extends Point> {
    List<T> getVertices();
    List<Edge<T>> getEdges();
    List<Face> getFaces();
    T getCentroid();
    T getPredecessor(T point);
    T getSuccessor(T point);
    void draw(Graphics canvas);
    boolean contains(T p);
    double getArea();
    double getDiameter();
    double getHausdorffDistance(Geometry<T> polygon);
    void saveToFile(String string)  throws IOException;
}
