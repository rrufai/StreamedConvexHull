/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.geometry.primitives.Edge;
import cg.geometry.primitives.Face;
import java.awt.geom.Path2D;
import java.util.List;

/**
 *
 * @author rrufai
 */
public class Face2D implements Face {

    private List<Edge> outerBoundaryList;
    private List<Edge> innerBoundaryList;
    private Path2D.Double path;

    public Face2D(List<Edge> outer, List<Edge> inner) {
        outerBoundaryList = outer;
        innerBoundaryList = inner;
        if (outerBoundaryList != null) {
            for (Edge edge : outerBoundaryList) {
                path.append(edge, true);
            }

            path.closePath();
        }

        if (innerBoundaryList != null) {
            for (Edge edge : innerBoundaryList) {
                path.append(edge, true);
            }
        path.closePath();
        }
    }

public double area() {
        return 0d;
    }
}
