package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;

public class StreamedConvexUtility {
	
	public static double polar( Point a , Point b) {
		
		if (a.getX() - b.getX() == 0) {
			return  Math.signum(a.getY() - b.getY()) * Math.PI/2.0 ;
		}		
		return Math.atan((a.getY() - b.getY())/(a.getX() - b.getX()));
		
	}
	
	public static double area(Point a , Point b , Point c) {		
		return 0.5 * Math.abs(a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX() * (a.getY() - b.getY()));		
	}

}
