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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author rrufai
 */
@Ignore
@RunWith(Parameterized.class)
public class GrahamScanTest {

    private List<Point2D> pointset;
    private List<Point2D> chVertices;

    public GrahamScanTest(TestCase<Point2D> testCase) {
        // TestCases: "simple","medium","complex", "big", "smallnumbers"
        //TestCase<Point2D> testCase = (new TestData()).get("simple");
        pointset = testCase.getInputPoints();
        chVertices = testCase.getHullVertices();
    }

    @Parameterized.Parameters
    public static Collection getTestData() {
        Map<String, TestCase<Point2D>> testData = new TestData();
        return Arrays.asList(new Object[][]{
                    {testData.get("simple")},
                    {testData.get("medium")},
                    {testData.get("complex")},
                    {testData.get("smallnumbers")}
                });
    }

    /**
     * Test of compute method, of class GrahamScan.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        GrahamScan<Point2D> instance = new GrahamScan<>();
        Geometry<Point2D> geom = new Polygon2D<>(pointset);
        List<Point2D> expResult = chVertices;
        List<Point2D> result = instance.compute(geom).getVertices();

        assertEquals(expResult, result);
    }
}