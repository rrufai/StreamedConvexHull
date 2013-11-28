package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;

/**
 *
 * @author I827590
 * @param <T>
 */
public class StreamedPoint2D<T extends Point> extends Point2D {

    private double polar; //polar angle of point relative to a center point
    private double dogEar; //Goodness measure to keep this point on the hull
    private boolean marked; //to be used for lazy deletion
    private static final double NEGATIVE_INFINITY = -Double.MAX_VALUE;
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
        this.dogEar = dogEar;
    }

    public void mark() {
        marked = true;
        this.dogEar = NEGATIVE_INFINITY;
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
    
//    public double getDogEar() {
//        return dogEar;
//    }
//
//    public void setDogEar(double dogEar) {
//        this.dogEar = dogEar;
//    }
}
