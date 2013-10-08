/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

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
public class StreamedConvexUtilityTest {
    
    public StreamedConvexUtilityTest() {
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
     * Test of polar method, of class StreamedConvexUtility.
     */
    @Test
    public void testPolar() {
        System.out.println("polar");
        Point a = null;
        Point b = null;
        double expResult = 0.0;
        double result = StreamedConvexUtility.polar(a, b);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of area method, of class StreamedConvexUtility.
     */
    @Test
    public void testArea() {
        System.out.println("area");
        Point a = null;
        Point b = null;
        Point c = null;
        double expResult = 0.0;
        double result = StreamedConvexUtility.area(a, b, c);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
