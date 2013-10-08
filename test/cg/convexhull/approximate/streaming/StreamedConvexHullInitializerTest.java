package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StreamedConvexHullInitializerTest {

    //StreamedConvexHull convexHull = new StreamedConvexHull();
    double[][] data;
    static private Point[] points = {
        new Point2D(-3.3256, -10.8778),
        new Point2D(-15.6558, -21.0232),
        new Point2D(2.2533, 10.8634),
        new Point2D(3.8768, -4.1864),
        new Point2D(-10.4647, 4.2737),
        new Point2D(12.9092, 3.3406),
        new Point2D(12.8916, 1.2147),
        new Point2D(0.6237, -9.0394),
        new Point2D(4.2729, -8.4715),
        new Point2D(2.7464, -2.7443),
        new Point2D(-0.8671, -10.8589),
        new Point2D(8.2579, -9.5590),
        new Point2D(-4.8832, 15.7248),
        new Point2D(22.8319, 1.5574)};

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInitialize() {
        //convexHull.initialize(points);
    }

    /**
     * Test of getPointSequence method, of class StreamedConvexHullInitializer.
     */
    @Test
    public void testGetPointSequence() {
        System.out.println("getPointSequence");
        Point[] geometry = null;
        StreamedConvexHullInitializer instance = null;
        List expResult = null;
        List result = instance.getPointSequence(geometry);
        Assert.assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        Assert.fail("The test case is a prototype.");
    }
}
