/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.convexhull.main.CanvasImpl;
import cg.geometry.primitives.Edge;
import cg.geometry.primitives.Face;
import cg.geometry.primitives.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rrufai
 */
public class Face2D implements Face {
    private Path2D.Double path;

    public Face2D(List<Edge> outerBoundaryList){
        this(outerBoundaryList, null);
    }
    
    public Face2D(List<Edge> outerBoundaryList, List<Edge> innerBoundaryList) {
        path = new Path2D.Double();
        
        if (outerBoundaryList != null && outerBoundaryList.size() > 0) {
            final Point2D origin = outerBoundaryList.get(0).getOrigin();
            path.moveTo(origin.getPoint().getX(), origin.getX());
            for (Edge edge : outerBoundaryList) {                
                path.append(edge, true);
            }

            //path.closePath();
        }

        if (innerBoundaryList != null) {
            for (Edge edge : innerBoundaryList) {
                path.append(edge, true);
            }
            //path.closePath();
        }
    }

    public boolean contains(Point2D p) {
        return path.contains(p.getPoint());
    }

    public double area() {
        return 0d;
    }

    /**
     * 
     * @param fileName
     * @throws IOException 
     */
    @Override
    public void saveToFile(String fileName) throws IOException {
     CanvasImpl canvas = new CanvasImpl(path.getBounds());
     canvas.drawShape(path, Color.black);
     canvas.saveToFile("PNG", fileName);
    }

    @Override
    public List<Point2D> getVertices() {
        PathIterator iterator = path.getPathIterator(null);
        List<Point2D> vertices = new ArrayList<>();
        double[] coords = new double[6];
        while(!iterator.isDone()){
            iterator.currentSegment(coords);
            vertices.add(new Point2D(coords[0], coords[1]));
            iterator.next();
        }
        return vertices;
    }

    @Override
    public List<Edge> getEdges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Face> getFaces() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Graphics canvas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}