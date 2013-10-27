/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.impl;

import cg.convexhull.exact.testcases.TestCase;
import cg.convexhull.exact.testcases.TestData;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author rrufai
 */
@Ignore
public class QuickHullRecursiveTest {

    private List<Point2D> pointset;
    private List<Point2D> chVertices;
    private double EPSILON = 1.0E-25;

    /**
     *
     */
    @Before
    public void setUp() {
        TestCase<Point2D> testCase = (new TestData()).get("big");
        pointset = testCase.getInputPoints();
        chVertices = testCase.getHullVertices();
    }

    /**
     * Test of compute method, of class QuickHullRecursive.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        QuickHullRecursive<Point2D> instance = new QuickHullRecursive<>();
        Geometry<Point2D> geom = new Polygon2D<>(pointset);
        List<Point2D> expResult = chVertices;
        List<Point2D> result = instance.compute(geom).getVertices();

        assertEquals(expResult, result);
    }

    /**
     * Test of convexHull method, of class QuickHullRecursive.
     */
    @Test
    public void testConvexHull() {
        System.out.println("convexHull");
        QuickHullRecursive instance = new QuickHullRecursive(pointset);
        List<Point2D> expResult = chVertices;
        List<Point2D> result = instance.convexHull();

        assertEquals(expResult, result);
    }

    /**
     * Test of mergeHulls method, of class QuickHullRecursive.
     */
    @Test
    public void testMergeHulls() {
        System.out.println("mergeHulls");
        QuickHullRecursive instance = new QuickHullRecursive(pointset);
        Map<String, Point2D> map = instance.computeLR(pointset);
        List<Point2D> upper = instance.leftHalfHull(map.get("leftmost"), map.get("rightmost"), pointset);
        List<Point2D> lower = instance.rightHalfHull(map.get("leftmost"), map.get("rightmost"), pointset);

        List expResult = chVertices;
        List result = instance.mergeHulls(upper, lower);

        assertEquals(expResult, result);
    }

    /**
     * Test of leftHalfHull method, of class QuickHullRecursive.
     */
    @Test
    public void testQuickHull() {
        System.out.println("quickHull");
        List<Point2D> pSet = pointset;
        QuickHullRecursive instance = new QuickHullRecursive();
        Map<String, Point2D> map = instance.computeLR(pSet);
        Point2D left = map.get("leftmost");
        Point2D right = map.get("rightmost");
        List lower = instance.leftHalfHull(right, left, pSet);
        List upper = instance.leftHalfHull(left, right, pSet);
        List result = instance.mergeHulls(lower, upper);
        System.out.println("quickHull: " + result);
        assertTrue(result.contains(left) && result.contains(right));
    }

    /**
     * Test of computeFarthest method, of class QuickHullRecursive.
     */
    @Test
    public void testComputeFarthest() {
        System.out.println("computeFarthest");

        List<Point2D> pSet = pointset;
        QuickHullRecursive<Point2D> instance = new QuickHullRecursive<>();
        Map<String, Point2D> map = instance.computeLR(pSet);
        Point2D left = map.get("leftmost");
        Point2D right = map.get("rightmost");
        System.out.println("leftmost: " + left + ", rightmost: " + right);
        Point2D expResult = new Point2D(0.0, 1.0);

        Point2D result = instance.computeFarthest(left, right, pSet);
        System.out.println("farthest: " + result);
        assertTrue(expResult.distanceSq(result) < EPSILON);
    }

    /**
     * Test of computeSubset method, of class QuickHullRecursive.
     */
    @Test
    public void testComputeSubset() {
        System.out.println("computeSubset");

        QuickHullRecursive<Point2D> instance = new QuickHullRecursive<>();
        List<Point2D> pSet = pointset;
        Map<String, Point2D> map = instance.computeLR(pSet);
        Point2D left = map.get("leftmost");
        Point2D right = map.get("rightmost");

//        List<Point2D.Double> expResult = null;
        List<Point2D> result = instance.computeSubset(left, right, pSet);

        System.out.println("subset(L, R): " + result);
        List<Point2D> result2 = instance.computeSubset(right, left, pSet);
        System.out.println("subset(R, L): " + result2);
        assertTrue(result.size() == 5 && result2.isEmpty());
    }

    /**
     * Test of computeLR method, of class QuickHullRecursive.
     */
    @Test
    public void testComputeLR() {
        System.out.println("computeLR");
        QuickHullRecursive<Point2D> instance = new QuickHullRecursive<>();
        List<Point2D> pSet = pointset;
        Map<String, Point2D> map = instance.computeLR(pSet);
        Point2D left = map.get("leftmost");
        Point2D right = map.get("rightmost");

        assertTrue(left.distance(0.0, 0.0) < EPSILON
                && right.distance(3.0, 0.0) < EPSILON);
    }
}
