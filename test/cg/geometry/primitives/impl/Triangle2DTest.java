/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.common.comparators.GeometricComparator;
import cg.convexhull.exact.testcases.TestCase;
import cg.convexhull.exact.testcases.TestData;
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
public class Triangle2DTest {

    private Triangle2D triangle;
    private Point2D p1;
    private boolean expResult;
    private double expectedArea;
    private final double expectedDiameter;

    public Triangle2DTest(Triangle2D triangle, Point2D p1, boolean expectedContainsResult, double expectedArea, double diameter) {
        this.triangle = triangle;
        this.p1 = p1;
        this.expResult = expectedContainsResult;
        this.expectedArea = expectedArea;
        this.expectedDiameter = diameter;
    }

    @Parameterized.Parameters
    public static Collection getTestData() {
        Map<String, TestCase<Point2D>> testData = new TestData();
        return Arrays.asList(new Object[][]{
                    {new Triangle2D(new Point2D(0, 0), new Point2D(3, 0), new Point2D(1, 1)), new Point2D(0.5, 0.5), true, 1.5, 3.0},
                    {new Triangle2D(new Point2D(0, 0), new Point2D(3, 0), new Point2D(1, 1)), new Point2D(0, 0), true, 1.5, 3.0},
                    {new Triangle2D(new Point2D(0, 0), new Point2D(3, 0), new Point2D(1, 1)), new Point2D(2, 0), true, 1.5, 3.0},
                    {new Triangle2D(new Point2D(1, 0.5), new Point2D(0.5, 0.375), new Point2D(1, 0)), new Point2D(1.5, 0.2), false, 0.125, 0.625},
                    {new Triangle2D(new Point2D(0, 0), new Point2D(0, 1), new Point2D(1, 1)), new Point2D(1, 1.1), false, 0.5, 1.4142135623730951}
                });
    }

    @Test
    public void testContains() {
        assertEquals(expResult, triangle.contains(p1));
    }

    @Test
    public void testArea() {
        assertEquals(expectedArea, triangle.getArea(), GeometricComparator.EPSILON);
    }

    @Test
    public void testDiameter() {
        assertEquals(expectedDiameter, triangle.getDiameter(), GeometricComparator.EPSILON);
    }
}
