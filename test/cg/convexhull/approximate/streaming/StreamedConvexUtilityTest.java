/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.common.comparators.GeometricComparator;
import cg.convexhull.exact.testcases.TestCase;
import cg.convexhull.exact.testcases.TestData;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author I827590
 */
@RunWith(Parameterized.class)
public class StreamedConvexUtilityTest {

    private Point2D from;
    private Point2D to;
    private double expResult;

    public StreamedConvexUtilityTest(Point2D from, Point2D to, double expResult) {
        this.from = from;
        this.to = to;
        this.expResult = expResult;
    }

    @Parameterized.Parameters
    public static Collection getTestData() {
        Map<String, TestCase<Point2D>> testData = new TestData();
        return Arrays.asList(new Object[][]{
                    {new Point2D(0.5, 0.375), new Point2D(0, 0), Math.PI * 2 -2.498091545},
                    {new Point2D(0.5, 0.375), new Point2D(0.0, 1.0), 2.245537269}, 
                    {new Point2D(0, 0), new Point2D(0.0, 0.0), 0},  
                    {new Point2D(0, 0), new Point2D(1.0, 0.0), 0},  
                    {new Point2D(0, 0), new Point2D(0.0, 1.0), Math.PI / 2},  
                    {new Point2D(1, 0), new Point2D(2.0, 0.0), 0.0},  
                    {new Point2D(2, 0), new Point2D(1.0, 0.0), Math.PI}, 
        });
    }
    /**
     * Test of polar method, of class StreamedConvexUtility.
     */
    @Test
    public void testPolar() {
        System.out.println("polar");
        assertEquals(expResult, StreamedConvexUtility.polar(from, to), GeometricComparator.EPSILON);
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
