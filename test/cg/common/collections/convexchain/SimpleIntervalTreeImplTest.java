/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.convexchain;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.List;
import java.util.Map.Entry;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author I827590
 */
public class SimpleIntervalTreeImplTest {
    private SimpleIntervalTreeImpl<Point2D> intervalTree;
    
    public SimpleIntervalTreeImplTest() {
        intervalTree = new SimpleIntervalTreeImpl<>(null, new ConvexLayersIntervalTreeStub());
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of extractHull method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testExtractHull() {
        System.out.println("extractHull");
        SimpleIntervalTreeImpl instance = null;
        List expResult = null;
        List result = instance.extractHull();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testDelete_ConvexChain() {
        System.out.println("delete");
        SimpleIntervalTreeImpl<Point> instance = null;
        Point point = null;
        instance.delete(point);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testInsert_ConvexChain() {
        System.out.println("insert");
        Point point = null;
        SimpleIntervalTreeImpl<Point> instance = null;
        boolean expResult = false;
        boolean result = instance.insert(point);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHullChain method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testGetHullChain() {
        System.out.println("getHullChain");
        SimpleIntervalTreeImpl instance = null;
        ConvexChain expResult = null;
        ConvexChain result = instance.getHullChain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SimpleIntervalTreeImpl instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        Point point = null;
        SimpleIntervalTreeImpl instance = null;
        boolean expResult = false;
        boolean result = instance.insert(point);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Point point = null;
        SimpleIntervalTreeImpl instance = null;
        boolean expResult = false;
        boolean result = instance.add(point);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Point point = null;
        SimpleIntervalTreeImpl instance = null;
        instance.delete(point);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLevel method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testGetLevel() {
        System.out.println("getLevel");
        SimpleIntervalTreeImpl instance = null;
        int expResult = 0;
        int result = instance.getLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        SimpleIntervalTreeImpl instance = null;
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of bridgePoints method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testBridgePoints() {
        System.out.println("bridgePoints");
        SimpleIntervalTreeImpl instance = null;
        SimpleIntervalTreeImpl left = null;
        SimpleIntervalTreeImpl right = null;
        Entry expResult = null;
        Entry result = instance.bridgePoints(left, right);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
        /**
     * Test of bridgePoints method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testBridgeTangents() {
        System.out.println("bridgePoints");
        SimpleIntervalTreeImpl instance = null;
        SimpleIntervalTreeImpl left = null;
        SimpleIntervalTreeImpl right = null;
        Entry expResult = null;
        Entry result = instance.bridgePoints(left, right);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    static class ConvexLayersIntervalTreeStub implements ConvexLayersIntervalTree<Point2D> {

        public ConvexLayersIntervalTreeStub() {
        }

        @Override
        public List<Point2D> extractRoot() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean goRight(Point2D point, int level) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isEmpty() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
