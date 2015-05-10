package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;

/**
 *
 * @author I827590
 * @param <T>
 */
public class StreamedPoint2D<T extends Point> extends Point2D implements Point {

    private double polar; //polar angle of point relative to a center point
    private boolean marked; //to be used for lazy deletion
    private double goodness;

    /**
     *
     * @param point
     */
    public StreamedPoint2D(T point) {
        super(point.getX(), point.getY());
    }

    public StreamedPoint2D(double x, double y) {
        super(x, y);
    }

    public StreamedPoint2D(T point, double polar) {
        super(point.getX(), point.getY());
        this.polar = polar;
    }

    public StreamedPoint2D(T point, double polar, double dogEar) {
        super(point.getX(), point.getY());
        this.polar = polar;
    }

    public void mark() {
        marked = true;
    }

    public boolean isMarked() {
        return marked;
    }

    public double getPolar() {
        return polar;
    }

    public void setPolar(double polar) {
        this.polar = polar;
    }
    public double getGoodnessMeasure() {
        return goodness;
    }

    public void setGoodnessMeasure(double goodness) {
        this.goodness = goodness;
    }
}
