package cg.convexhull.approximate.streaming;

import cg.convexhull.exact.ConvexHull;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class StreamedConvexHullInitializer implements StreamedInitializer {
	
	private ConvexHull convexHull;
	private List<Point2D> inputPoints;
	private List<StreamedPoint2D> outputPoints;
	TreeMap<?, ?> customSortingMap = new TreeMap();

			
	public StreamedConvexHullInitializer(ConvexHull convexHull) {
		this.convexHull = convexHull;
		inputPoints = new ArrayList<>();
		outputPoints = new ArrayList<>();
	}
	
	
	@Override
	public List<StreamedPoint2D> getPointSequence(Point... geometry) {
		
		for ( Point p: geometry) {
			inputPoints.add((Point2D)p);
		}
		
		Geometry g = new Polygon2D(inputPoints);
		Geometry L = convexHull.compute(g);
		Point2D centroid = L.getCentroid();
		StreamedPoint2D streamedPoint;
		
		for (Point2D p : L.getVertices()) {
			streamedPoint = new StreamedPoint2D(p);
			streamedPoint.setPolar(StreamedConvexUtility.polar(p, centroid) ); 
			streamedPoint.setDogear(StreamedConvexUtility.area(L.getPredecessor(p), p, L.getSuccessor(p)));	
			outputPoints.add(streamedPoint);
			
			// add the point to treemap  - RB tree
			// add the point to heap  - priority queue map : http://docs.oracle.com/javase/6/docs/api/java/util/PriorityQueue.html
		}
		
		
		return outputPoints;
	}

}
