/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

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
public class StreamedPoint2DTest {
    
    public StreamedPoint2DTest() {
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
     * Test of getPolar method, of class StreamedPoint2D.
     */
    @Test
    public void testGetPolar() {
        System.out.println("getPolar");
        StreamedPoint2D instance = null;
        double expResult = 0.0;
        double result = instance.getPolar();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPolar method, of class StreamedPoint2D.
     */
    @Test
    public void testSetPolar() {
        System.out.println("setPolar");
        double polar = 0.0;
        StreamedPoint2D instance = null;
        instance.setPolar(polar);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDogear method, of class StreamedPoint2D.
     */
    @Test
    public void testGetDogear() {
        System.out.println("getDogear");
        StreamedPoint2D instance = null;
        double expResult = 0.0;
        double result = instance.getDogear();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDogEar method, of class StreamedPoint2D.
     */
    @Test
    public void testSetDogEar() {
        System.out.println("setDogEar");
        double dogear = 0.0;
        StreamedPoint2D instance = null;
        instance.setDogEar(dogear);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
