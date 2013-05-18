package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.impl.Point2D;

public class StreamedPoint2D {
	
	Point2D point;
	private double polar;
	private double dogear;
	
	public StreamedPoint2D (Point2D point) { 
		this.point = point;
	}

	public double getPolar() {
		return polar;
	}

	public void setPolar(double polar) {
		this.polar = polar;
	}

	public double getDogear() {
		return dogear;
	}

	public void setDogear(double dogear) {
		this.dogear = dogear;
	}

}
