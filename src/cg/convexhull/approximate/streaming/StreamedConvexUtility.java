package cg.convexhull.approximate.streaming;

import cg.common.comparators.GeometricComparator;
import cg.geometry.primitives.Point;
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
//        if (Math.abs(xDiff) < GeometricComparator.EPSILON) {
//            return Math.signum(yDiff) * Math.PI / 2.0;
//        }
        double theta = Math.atan2(yDiff, xDiff);
        
        theta = theta < 0? theta + 2*Math.PI:theta;
        return theta;

    }

    public static double area(Point a, Point b, Point c) {
        return 0.5 * Math.abs(a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX() * (a.getY() - b.getY()));
    }

    public static boolean contains(Point a, Point b, Point c, Point p) {
        return (new Triangle2D<>(a, b, c)).contains(p);
    }
}
