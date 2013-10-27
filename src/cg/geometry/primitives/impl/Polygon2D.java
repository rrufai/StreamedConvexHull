/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.geometry.primitives.Edge;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author rrufai
 */
public class Polygon2D<T extends Point> extends Face2D<T> implements Polygon<T> {

    /**
     * A polygon bounded by the give set of edges
     */
    public Polygon2D(List<Edge<T>> boundary) {
        super(boundary);
    }

    /**
     * A polygon bounded by the give set of vertices
     */
    public Polygon2D(Collection<T> boundary) {
        this(toEdgeList(boundary));
    }
    
    /**
     *
     * @param p
     */
    public Polygon2D(T... ps) {
        this(toEdgeList(Arrays.asList(ps)));
    }

    private static <P extends Point> List<Edge<P>> toEdgeList(Collection<P> vertices) {
        List<Edge<P>> edges = new ArrayList<>();
        if (vertices == null) {
            return edges;
        }

        Point destination = null;
        for (Point vertex : vertices) {
            Point origin = destination;
            destination = vertex;
            if (origin != null && destination != null) {
                edges.add(new Edge2D(origin, destination));
            }
        }
        return edges;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean equals(Object o) {
        boolean retValue = false;

        if (!(o instanceof Polygon) || o == null) {
            retValue = false;
        } else {
            Polygon polygon = (Polygon) o;

            if (polygon.getVertices().size() == this.getVertices().size()) {
                retValue = polygon.getVertices().equals(this.getVertices());
            }
        }
        return retValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for(T p : this.getVertices()){
            hash = (hash * p.hashCode()) % Integer.MAX_VALUE;
        }
        return hash;
    }
}
