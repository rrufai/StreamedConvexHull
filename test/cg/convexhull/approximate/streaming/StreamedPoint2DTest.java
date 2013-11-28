/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.impl.Point2D;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author I827590
 */
public class StreamedPoint2DTest {

    /**
     * Test of getPolar method, of class StreamedPoint2D.
     */
    @Test
    public void testGetPolar() {
        System.out.println("getPolar");
        double polar = 0.0;
        StreamedPoint2D<Point2D> instance = new StreamedPoint2D<>(new Point2D(0.0, 0.0));
        instance.setPolar(polar);
        double expResult = 0.0;
        double result = instance.getPolar();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setPolar method, of class StreamedPoint2D.
     */
    @Test
    public void testSetPolar() {
        System.out.println("setPolar");
        double polar = 0.0;
        StreamedPoint2D<Point2D> instance = new StreamedPoint2D<>(new Point2D(0.0, 0.0));
        instance.setPolar(polar);
        double expResult = polar;
        double result = instance.getPolar();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getDogear method, of class StreamedPoint2D.
     */
    @Test
    public void testGetDogear() {
        System.out.println("getDogear");
        double dogEar = 0.0;
        StreamedPoint2D<Point2D> instance = new StreamedPoint2D<>(new Point2D(0.0, 0.0));
        instance.setGoodnessMeasure(dogEar);
        double expResult = dogEar;
        double result = instance.getGoodnessMeasure();

        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setDogEar method, of class StreamedPoint2D.
     */
    @Test
    public void testSetDogEar() {
        System.out.println("setDogEar");
        double dogEar = 0.0;
        StreamedPoint2D<Point2D> instance = new StreamedPoint2D<>(new Point2D(0.0, 0.0));
        instance.setGoodnessMeasure(dogEar);
        double expResult = dogEar;
        double result = instance.getGoodnessMeasure();
        
        assertEquals(expResult, result, 0.0);
    }
}
