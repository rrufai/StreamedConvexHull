/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.common.comparators.GeometricComparator;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author I827590
 */
public class StreamedConvexUtilityTest {

    /**
     * Test of polar method, of class StreamedConvexUtility.
     */
    @Test
    public void testPolar() {
        System.out.println("polar");
        Point a = new Point2D(0.0, 0.0);
        Point b = new Point2D(0.0, 0.0);
        double expResult = 0.0;
        double result = StreamedConvexUtility.polar(a, b);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPolar90degrees() {
        System.out.println("polar");
        Point a = new Point2D(0.0, 0.0);
        Point b = new Point2D(0.0, 0.1);
        double expResult = Math.PI / 2;
        double result = StreamedConvexUtility.polar(a, b);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of area method, of class StreamedConvexUtility.
     */
    @Test
    public void testAreaOfAPoint() {
        System.out.println("area");
        Point a = new Point2D(0.0, 0.0);
        Point b = new Point2D(0.0, 0.0);
        Point c = new Point2D(0.0, 0.0);
        double expResult = 0.0;
        double result = StreamedConvexUtility.area(a, b, c);
        assertEquals(expResult, result, 0.0);
    }

    // area((0,0), (5,4), and (8,2)) == 11
    @Test
    public void testAreaNonEmptyTriangle() {
        System.out.println("area");
        Point a = new Point2D(0.0, 0.0);
        Point b = new Point2D(5.0, 4.0);
        Point c = new Point2D(8.0, 2.0);
        double expResult = 11.0;
        double result = StreamedConvexUtility.area(a, b, c);
        assertEquals(expResult, result, GeometricComparator.EPSILON);
    }

    @Test
    public void testAreaLineSegment() {
        System.out.println("area");
        Point a = new Point2D(0.0, 0.0);
        Point b = new Point2D(0.0, 4.0);
        Point c = new Point2D(0.0, 2.0);
        double expResult = 0.0;
        double result = StreamedConvexUtility.area(a, b, c);
        assertEquals(expResult, result, GeometricComparator.EPSILON);
    }
}
