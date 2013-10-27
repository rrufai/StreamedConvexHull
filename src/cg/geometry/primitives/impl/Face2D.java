/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.common.collections.CircularArrayList;
import cg.common.visualization.CanvasImpl;
import cg.geometry.primitives.Edge;
import cg.geometry.primitives.Face;
import cg.geometry.primitives.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author rrufai
 */
public class Face2D<T extends Point> implements Face<T> {

    private Path2D.Double path;
    private boolean showPoints = true;
    private double minX = Integer.MAX_VALUE;
    private double minY = Integer.MAX_VALUE;

    public Face2D(List<Edge<T>> outerBoundaryList) {
        path = new Path2D.Double(Path2D.WIND_EVEN_ODD);
        //path = new Path2D.Double();

        if (outerBoundaryList != null && outerBoundaryList.size() > 0) {
            for (Edge<T> edge : outerBoundaryList) {
                T origin = edge.getOrigin();
                minX = Math.min(minX, origin.getX());
                minY = Math.min(minY, origin.getY());
            }
        }

        if (outerBoundaryList != null && outerBoundaryList.size() > 0) {
            final T origin = outerBoundaryList.get(0).getOrigin();
            path.moveTo(origin.getX(), origin.getY());
            for (Edge<T> edge : outerBoundaryList) {
                path.append(edge, true);
            }

            path.closePath();
        }
    }

    @Override
    public boolean contains(T p) {
        return path.contains(p.getPoint());
    }

    public double area() {
        return 0; // todo: decide to remove or implement
    }

    /**
     *
     * @param fileName
     * @throws IOException
     */
    @Override
    public void saveToFile(String fileName) throws IOException {
        CanvasImpl canvas = new CanvasImpl(path.getBounds());
        //canvas.drawShape(path, Color.black);
        AffineTransform transform = AffineTransform.getTranslateInstance(-minX, -minX);
        Shape transformedPath = transform.createTransformedShape(path);
        canvas.drawShape(transformedPath, Color.blue);
        if (showPoints) {
            canvas.drawPoints(this.getVertices(transformedPath), Color.red);
        }
        canvas.saveToFile("PNG", fileName);
    }

    @Override
    public List<T> getVertices() {
        return getVertices(path);
    }

    private List<T> getVertices(Shape path) {
        PathIterator iterator = path.getPathIterator(null);
        List<T> vertices = new CircularArrayList<>();
        double[] coords = new double[6];

        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            if (type != PathIterator.SEG_CLOSE) {
                vertices.add((T) new Point2D(coords[0], coords[1]));
            }
            iterator.next();
        }

        return vertices;
    }

    @Override
    public List<Edge<T>> getEdges() {
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

    /**
     *
     * @param <T>
     * @return
     */
    @Override
    public T getCentroid() {
        List<T> vertices = getVertices();
        double x = 0.0, y = 0.0;
        if (vertices.isEmpty()) {
            return (T) new Point2D(x, y);
        }
        for (T v : vertices) {
            x = x + v.getX();
            y = y + v.getY();
        }

        return (T) new Point2D(x / vertices.size(), y / vertices.size());
    }

    // TODO: should be tested
    @Override
    public T getPredecessor(T point) {

        List<T> vertices = getVertices();
        int index = vertices.indexOf(point);
        int size = vertices.size();

        return vertices.get((index + size - 1) % size);
    }

    // TODO: should be tested
    @Override
    public T getSuccessor(T point) {

        List<T> vertices = getVertices();
        int index = vertices.indexOf(point);
        int size = vertices.size();

        return vertices.get((index + 1) % size);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[" + getVertices() + "]";
    }
}
