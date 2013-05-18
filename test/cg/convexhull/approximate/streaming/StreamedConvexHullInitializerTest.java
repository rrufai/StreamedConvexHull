package cg.convexhull.approximate.streaming;

import org.junit.Test;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;

public class StreamedConvexHullInitializerTest {

	//StreamedConvexHull convexHull = new StreamedConvexHull();
	double[][] data;

	static  private Point[] points = {
		new Point2D (-3.3256, -10.8778),
		new Point2D ( -15.6558, -21.0232),
		new Point2D ( 2.2533, 10.8634),
		new Point2D ( 3.8768, -4.1864),
		new Point2D ( -10.4647, 4.2737),
		new Point2D ( 12.9092, 3.3406),
		new Point2D (12.8916, 1.2147),
		new Point2D ( 0.6237, -9.0394),
		new Point2D ( 4.2729, -8.4715),
		new Point2D ( 2.7464, -2.7443),
		new Point2D ( -0.8671, -10.8589),
		new Point2D ( 8.2579, -9.5590),
		new Point2D ( -4.8832, 15.7248),
		new Point2D ( 22.8319, 1.5574)};


	@Test
	public void testInitialize(){		  
		//convexHull.initialize(points);

	}



}
