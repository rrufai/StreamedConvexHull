/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.convexchain;

import cg.convexlayers.events.IntervalTree;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author I827590
 */
public class IntervalTreeImplTest {
    
    public IntervalTreeImplTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class IntervalTreeImpl.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        
        IntervalTree<Point> instance = new IntervalTreeImpl(null, null);
        assertEquals(true, instance.isEmpty());
        ConvexChain<Point> chain = new ConvexChainImpl<>();
        chain.add(new Point2D(1.0, 1.0));
        instance.insert(chain);
        assertEquals(false, instance.isEmpty());
    }

    /**
     * Test of getLevel method, of class IntervalTreeImpl.
     */
    @Test
    public void testGetLevel() {
        System.out.println("getLevel");
        IntervalTreeImpl instance = new IntervalTreeImpl(null, null);
        int expResult = 0;
        int result = instance.getLevel();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class IntervalTreeImpl.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        IntervalTreeImpl instance = new IntervalTreeImpl(null, null);
        assertEquals(true, instance.isEmpty());
        
        instance.add(new Point2D(1.0, 1.0));
        assertEquals(false, instance.isEmpty());
        
    }

    /**
     * Test of extractHull method, of class IntervalTreeImpl.
     */
    @Test
    public void testExtractHull() {
        System.out.println("extractHull");
        IntervalTree<Point> instance = new IntervalTreeImpl<>(null, null);
        Point point = new Point2D(1.0, 1.0);        
        instance.add(point);
        
        List<Point> expResult = new ConvexChainImpl<>();
        expResult.add(point);
        List result = instance.extractHull();
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class IntervalTreeImpl.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        IntervalTree<Point> instance = new IntervalTreeImpl<>(null, null);
        Point point = new Point2D(1.0, 1.0);        
        instance.add(point);
        assertEquals(false, instance.isEmpty());
        instance.delete(point);
        
        assertEquals(true, instance.isEmpty());
        
        
        
    }

    /**
     * Test of getHullChain method, of class IntervalTreeImpl.
     */
    @Test
    public void testGetHullChain() {
        System.out.println("getHullChain");
        IntervalTreeImpl instance = new IntervalTreeImpl(null, null);
        ConvexChain expResult = new ConvexChainImpl();
        ConvexChain result = instance.getHullChain();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class IntervalTreeImpl.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        IntervalTreeImpl instance = new IntervalTreeImpl(null, null);
        String expResult = "└── []  LBP: null, RBP: null\n";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
