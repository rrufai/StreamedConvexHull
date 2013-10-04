/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.main;

import cg.common.visualization.CanvasImpl;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author rrufai
 */
public class StreamedConvexHullMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Path2D path = new Path2D.Double();
        BasicStroke pathStroke =
                new BasicStroke(2);


        // Create the path to be clipped:       
        int pathPoints[] = {0, 10, 30, 10};
        path.moveTo(pathPoints[ 0], pathPoints[1]);
        for (int i = 2; i < pathPoints.length; i += 2) {
            path.lineTo(pathPoints[ i], pathPoints[ i + 1]);
        }
        // Create the shape representing the clip area
        Polygon clipShape = new Polygon();
        int triPoints[] = {10, 0, 20, 20, 20, 0};
        for (int i = 0; i < triPoints.length; i += 2) {
            clipShape.addPoint(triPoints[i], triPoints[i + 1]);
        }
        // Create the path with area using the stroke
        Shape clippedPath = pathStroke.createStrokedShape(path);

        // Apply a scale so the image is easier to see
        int scale = 15;
        AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
        Shape sPath = at.createTransformedShape(path);
        Shape sClip = at.createTransformedShape(clipShape);

        // Create the Area shape that represents the path that is clipped by the clipShape
        Area clipArea = new Area(sClip);
        clipArea.intersect(new Area(at.createTransformedShape(clippedPath)));
        Rectangle rect =  sPath.getBounds();
        rect.add(sClip.getBounds());
        CanvasImpl canvas = new CanvasImpl(rect);


        //imageObject.
        canvas.drawShape(sPath, Color.black);
        canvas.drawShape(sClip, Color.red);
        canvas.drawShape(clipArea, Color.green);
        canvas.saveToFile("PNG", "img1.png");
        Collection<Point2D> points = new ArrayList<>();
//                double[][] chdata = {
//            {-20.7067, -7.5420},
//            {-15.6558, -21.0232},
//            {6.7786, -19.5432},
//            {13.5400, -12.1944},
//            {22.8319, 1.5574},
//            {15.1514, 15.8849},
//            {5.4365, 19.6453},
//            {-8.9209, 22.1216},
//            {-14.9373, 10.3122}};
                //double[][] chdata = {{-15.6558, -21.0232}, {6.7786, -19.5432}, {13.54, -12.1944}, {11.6677, -10.2834}, {-1.6561, -10.1621}, {-1.6561, -10.1621}, {8.2579, -9.559}, {17.9243, -4.9549}, {22.8319, 1.5574}, {15.1514, 15.8849}, {5.4365, 19.6453}, {-8.9209, 22.1216}, {-14.9373, 10.3122}, {-20.7067, -7.542}, {-13.7513, -9.2455}, {-1.6561, -10.1621}, {-1.6561, -10.1621}, {-3.3256, -10.8778}, {-3.3256, -10.8778}};
             double[][] chdata = {{-20.7067, -7.542}, {-15.6558, -21.0232}, {6.7786, -19.5432}, {13.54, -12.1944}, {22.8319, 1.5574}, {15.1514, 15.8849}, {5.4365, 19.6453}, {-8.9209, 22.1216}, {-14.9373, 10.3122} };
        int FACTOR = 5;
              int XTRANSLATE = 21;
              int YTRANSLATE = 22;
        for(int i = 0; i < chdata.length; i++){
            points.add(new Point2D(FACTOR *(chdata[i][0] + XTRANSLATE), FACTOR * (chdata[i][1] + YTRANSLATE)));
        }
        Polygon2D polygon = new Polygon2D(points);
        List<Point2D> vertices = polygon.getVertices();
        System.out.println("vertices: " + vertices);
        polygon.saveToFile("polygon.png");
    }
}
