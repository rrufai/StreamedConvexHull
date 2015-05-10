package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Triangle2D;

public class StreamedConvexUtility {

    /**
     * Returns the polar angle made by a vector that starts at origin and ends at terminus.
     * So, if you'll like the polar angle of a point p on the convex hull relative to a centroid c internal
     * to the hull, you must call <code>polar(c, p)</code>.
     * 
     * @param origin
     * @param terminus
     * @return 
     */
    public static double polar(Point origin, Point terminus) {
        double xDiff = terminus.getX() - origin.getX();
        double yDiff = terminus.getY() - origin.getY();
        double theta = Math.atan2(yDiff, xDiff);
        
        theta = theta < 0? theta + 2*Math.PI:theta;
        return theta;

    }
    
        /**
     * Returns the polar angle made by a vector that starts at origin and ends at terminus.
     * So, if you'll like the polar angle made by moving from a point p on the convex hull 
     * to another q also on the convex hull, relative to a centroid c internal to the 
     * hull, you must call <code>polar(q, p, c)</code>.
     * 
     * @param origin
     * @param terminus
     * @return 
     */
    public static double polar(Point origin, Point terminus, Point centroid) {
        return polar(terminus, centroid) - polar(origin, centroid);
    }
        
    /**
     * Calculates the angle (in radians) between two vectors pointing outward
     * from one center
     *
     * @param p0 first point
     * @param p1 second point
     * @param c center point
     */
    public static double computeAngle(Point p0, Point p1, Point c) {
        double p0c = Math.sqrt(Math.pow(c.getX() - p0.getX(), 2)
                + Math.pow(c.getY() - p0.getY(), 2)); // p0->c (b)   
        double p1c = Math.sqrt(Math.pow(c.getX() - p1.getX(), 2)
                + Math.pow(c.getY() - p1.getY(), 2)); // p1->c (a)
        double p0p1 = Math.sqrt(Math.pow(p1.getX() - p0.getX(), 2)
                + Math.pow(p1.getY() - p0.getY(), 2)); // p0->p1 (c)
        return Math.acos((p1c * p1c + p0c * p0c - p0p1 * p0p1) / (2 * p1c * p0c));
    }
    
    public static double area(Point a, Point b, Point c) {
        return 0.5 * Math.abs(a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX() * (a.getY() - b.getY()));
    }

    public static boolean contains(Point a, Point b, Point c, Point p) {
        return (new Triangle2D<>(a, b, c)).contains(p);
    }
    
    public static void main(String [] args){
        Point p = new Point2D(0, 1);
        Point q = new Point2D(1, 0);
        Point c = new Point2D(0, 0);
        double angle = StreamedConvexUtility.computeAngle(p, q, c);
        double angle2 = StreamedConvexUtility.polar(p, q, c);
        
        System.out.println("angle: " + angle + ", angle2: " + angle2);
    }
}
