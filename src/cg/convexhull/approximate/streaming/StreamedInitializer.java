package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;
import java.util.List;

public interface StreamedInitializer {
	
	public List<StreamedPoint2D> getPointSequence(Point... geometry);
	

}
