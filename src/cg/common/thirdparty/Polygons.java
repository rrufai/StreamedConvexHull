/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.thirdparty;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.Polygon;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.awt.geom.Point2D.Double;


/**
 *
 * @author I827590
 */
public class Polygons {

    private static <P extends Point> Poly toPoly(Polygon<P> polygon) {
        Poly newPoly = new PolySimple();

        for (P vertex : polygon.getVertices()) {
            newPoly.add(new Double(vertex.getX(), vertex.getY()));
        }

        return newPoly;
    }

    public static <P extends Point>  Polygon<P> intersection(Polygon<P> convPolygon1, Polygon<P> convPolygon2){
        Poly poly1 = toPoly(convPolygon1);
        Poly poly2 = toPoly(convPolygon2);
        
        if(poly1.isEmpty() || poly2.isEmpty()) {
            return null;
        }
        
        Poly intersectionPoly = Clip.intersection(poly1, poly2);
        
        return toPolygon(intersectionPoly);
    }
    
    private static <P extends Point> Polygon<P> toPolygon(Poly poly) {
        P[] pointList = (P[])new Point[poly.getNumPoints()];

        for (int i = 0; i < poly.getNumPoints(); i++) {
            pointList[i] = (P)new Point2D(poly.getX(i), poly.getY(i));
        }

        return new Polygon2D<>(pointList);
    }
}
