/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author I827590
 */
public class StreamedConvexHullTest {
    
    public StreamedConvexHullTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of compute method, of class StreamedConvexHull.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        Geometry geom = null;
        StreamedConvexHull instance = null;
        Geometry expResult = null;
        Geometry result = instance.compute(geom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class StreamedConvexHull.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        Point[] geometry = null;
        StreamedConvexHull instance = null;
        instance.initialize(geometry);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class StreamedConvexHull.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Point point = null;
        StreamedConvexHull instance = null;
        instance.update(point);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of query method, of class StreamedConvexHull.
     */
    @Test
    public void testQuery() {
        System.out.println("query");
        StreamedConvexHull instance = null;
        Geometry expResult = null;
        Geometry result = instance.query();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
