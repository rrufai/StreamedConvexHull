/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Point;
import java.util.List;
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
public class StreamedInitializerTest {
    
    public StreamedInitializerTest() {
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
     * Test of getPointSequence method, of class StreamedInitializer.
     */
    @Test
    public void testGetPointSequence() {
        System.out.println("getPointSequence");
        Point[] geometry = null;
        StreamedInitializer instance = new StreamedInitializerImpl();
        List expResult = null;
        List result = instance.getPointSequence(geometry);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class StreamedInitializerImpl implements StreamedInitializer {

        public List<StreamedPoint2D> getPointSequence(Point[] geometry) {
            return null;
        }
    }
}
