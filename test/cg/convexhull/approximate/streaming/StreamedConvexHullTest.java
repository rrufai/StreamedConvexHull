/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.convexhull.exact.ConvexHull;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author I827590
 */
@RunWith(Parameterized.class)
public class StreamedConvexHullTest {

    private List<Point2D> chVertices;
    private List<Point2D> pointset;
    private int budget;

    public StreamedConvexHullTest(TestCase<Point2D> testCase) {
        // TestCases: "simple","medium","complex", "big", "smallnumbers"
        //TestCase<Point2D> testCase = (new TestData()).get("simple");
        pointset = testCase.getInputPoints();
        chVertices = testCase.getHullVertices();
        budget = Math.max(4, chVertices.size());
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
     * Test of compute method, of class StreamedConvexHull.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");

        final Polygon2D<Point2D> polygon2D = new Polygon2D<>(pointset);
        ConvexHull<Point2D> instance = new StreamedConvexHull<>(budget);
        Geometry<Point2D> result = instance.compute(polygon2D);
        List<Point2D> actualResult = result.getVertices();
        List<Point2D> expectedResult = chVertices;

        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test of initialize method, of class StreamedConvexHull.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        final int BUDGET = Math.min(10, chVertices.size());

        List<Point2D> hullVertices = chVertices.subList(0, BUDGET);
        StreamedConvexHull<Point2D> instance = new StreamedConvexHull<>(BUDGET);
        instance.initialize(hullVertices);

        Geometry<Point2D> expectedResult = new Polygon2D<>(hullVertices);
        Geometry<Point2D> actualResult = instance.query();

        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test of update method, of class StreamedConvexHull.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        final int BUDGET = Math.min(10, chVertices.size()) - 1;

        List<Point2D> geometry = chVertices.subList(0, BUDGET);
        StreamedConvexHull<Point2D> instance = new StreamedConvexHull<>(BUDGET);
        instance.initialize(geometry);
        instance.update(chVertices.get(BUDGET));
        Geometry<Point2D> actualResult = instance.query();

        StreamedConvexHull<Point2D> instance2 = new StreamedConvexHull<>(BUDGET);
        final Polygon2D<Point2D> polygon = new Polygon2D<>(chVertices.subList(0, BUDGET + 1));
        Geometry<Point2D> expectedResult = instance2.compute(polygon);

        assertEquals(expectedResult, actualResult);


    }

    /**
     * Test of query method, of class StreamedConvexHull.
     */
    @Test
    public void testQuery() {
        System.out.println("query");
        final int BUDGET = Math.min(10, chVertices.size());

        List<Point2D> hullVertices = chVertices.subList(0, BUDGET);
        StreamedConvexHull<Point2D> instance = new StreamedConvexHull<>(BUDGET);
        instance.initialize(hullVertices);

        Geometry<Point2D> expectedResult = new Polygon2D<>(hullVertices);
        Geometry<Point2D> actualResult = instance.query();

        assertEquals(expectedResult, actualResult);
    }
}
