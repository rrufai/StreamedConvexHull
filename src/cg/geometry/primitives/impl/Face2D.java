/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.common.collections.CircularArrayList;
import cg.common.visualization.CanvasImpl;
import cg.geometry.primitives.Edge;
import cg.geometry.primitives.Face;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author rrufai
 */
public class Face2D implements Face {

    private Path2D.Double path;

    public Face2D(List<Edge> outerBoundaryList) {
        this(outerBoundaryList, null);
    }

    public Face2D(List<Edge> outerBoundaryList, List<Edge> innerBoundaryList) {
        path = new Path2D.Double();

        if (outerBoundaryList != null && outerBoundaryList.size() > 0) {
            final Point2D origin = outerBoundaryList.get(0).getOrigin();
            path.moveTo(origin.getX(), origin.getY());
            for (Edge edge : outerBoundaryList) {
                path.append(edge, true);
            }
        }

        if (innerBoundaryList != null) {
            for (Edge edge : innerBoundaryList) {
                path.append(edge, true);
            }
        }

        path.closePath();
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
        List<Point2D> vertices = new CircularArrayList<>();
        double[] coords = new double[6];
        
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            if(type != PathIterator.SEG_CLOSE){
                vertices.add(new Point2D(coords[0], coords[1]));
            }
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

    @Override
    public Point2D getCentroid() {
        List<Point2D> vertices = getVertices();
        double x = 0.0, y = 0.0;
        if (vertices.size() == 0) {
            return new Point2D(x, y);
        }
        for (Point2D v : vertices) {
            x = x + v.getX();
            y = y + v.getY();
        }

        return new Point2D(x / vertices.size(), y / vertices.size());
    }

    // TODO: should be testes
    @Override
    public Point2D getPredecessor(Point2D point) {

        List<Point2D> vertices = getVertices();
        int index = vertices.indexOf(point);

        if (index == 0) {
            return vertices.get(vertices.size() - 1);
        }

        return vertices.get(index - 1);
    }

    // TODO: should be tested
    @Override
    public Point2D getSuccessor(Point2D point) {

        List<Point2D> vertices = getVertices();
        int index = vertices.indexOf(point);

        if (index == vertices.size() - 1) {
            return vertices.get(0);
        }

        return vertices.get(index + 1);
    }
}
