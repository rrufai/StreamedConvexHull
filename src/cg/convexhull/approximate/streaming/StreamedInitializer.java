package cg.convexhull.approximate.streaming;

import java.util.List;

import cg.geometry.primitives.Point;

public interface StreamedInitializer {
	
	public List<StreamedPoint2D> getPointSequence(Point... geometry);
	

}
