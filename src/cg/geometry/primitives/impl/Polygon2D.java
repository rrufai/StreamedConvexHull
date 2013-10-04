/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.geometry.primitives.Edge;
import cg.geometry.primitives.Polygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author rrufai
 */
public class Polygon2D extends Face2D implements Polygon {

    /**
     * A polygon bounded by the give set of edges
     */
    public Polygon2D(List<Edge> boundary) {
        super(boundary, null);
    }

    /**
     * A polygon bounded by the give set of vertices
     */
    public Polygon2D(Collection<Point2D> boundary) {
        this(toEdgeList(boundary));
    }

    private static List<Edge> toEdgeList(Collection<Point2D> vertices) {
        List<Edge> edges = new ArrayList<>();
        if(vertices == null){
            return edges;
        }
        
        Point2D destination = null;
        for(Point2D vertex : vertices){
            Point2D origin = destination;
            destination = vertex;
            if(origin != null){
                edges.add(new Edge2D(origin, destination));
            }
        }
        return edges;
    }
}
