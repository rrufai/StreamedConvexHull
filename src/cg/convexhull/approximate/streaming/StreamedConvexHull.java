/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.convexhull.exact.ConvexHull;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;

/**
 *
 * @author rrufai
 */
public class StreamedConvexHull implements ConvexHull, Streaming<Point>{
	
	private StreamedConvexHullInitializer initializer;
	
	public StreamedConvexHull(StreamedConvexHullInitializer initializer) {
		this.initializer = initializer;
	}

    @Override
    public Geometry compute(Geometry geom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(Point... geometry) {
    	
    	initializer.getPointSequence(geometry);   
       
    }

    @Override
    public void update(Point point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Geometry query() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
