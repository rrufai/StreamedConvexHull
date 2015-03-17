/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.convexchain;

import cg.common.comparators.GeometricComparator;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Random;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author I827590
 */
public class ConvexChainImplTest {

    private ConvexChain<Point> chain;
    private Random random;

    public ConvexChainImplTest() {
        random = new Random(new Date().getTime());
    }

    @Before
    public void setUp() {
        chain = new ConvexChainImpl<>();
        double[][] points = {
            {0.0, 0.0},
            {0.2, 0.2}
        };
        for (int i = 0; i < points.length; i++) {
            chain.add(new Point2D(points[i][0], points[i][1]));
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class ConvexChainImpl.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        ConvexChainImpl<Point> expResult = new ConvexChainImpl<>();
        double[][] points = {
            {0.0, 0.0},
            {0.2, 0.2}
        };
        for (int i = 0; i < points.length; i++) {
            expResult.add(new Point2D(points[i][0], points[i][1]));
        }


        ConvexChain<Point> instance = new ConvexChainImpl<>();
        ConvexChain<Point> result = instance.insert(chain);
        assertEquals(expResult, instance);
        instance.add(new Point2D(0.4, 0.4));
        assertFalse(expResult.equals(instance));
        assertEquals(new ConvexChainImpl<>(), result);
    }

    /**
     * Test of getTangentPoint method, of class ConvexChainImpl.
     */
    @Test
    public void testGetTangentPoint() {
        System.out.println("getTangentPoint");
        Point newPoint = new Point2D(1.0, 1.0);
        ConvexChainImpl<Point> instance = new ConvexChainImpl<>();
        double[][] points = {
            {0.0, 0.0},
            {0.4, 0.4}
        };
        for (int i = 0; i < points.length; i++) {
            instance.add(new Point2D(points[i][0], points[i][1]));
        }
        int expResult = 1;
        int result = instance.getTangentPoint(newPoint);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInterior method, of class ConvexChainImpl.
     */
    @Test
    public void testIsInterior() {
        System.out.println("isInterior");
        Point newPoint = new Point2D(1.0, 1.0);
        ConvexChain<Point> instance = new ConvexChainImpl<>();
        double[][] points = {
            {0.0, 0.0},
            {0.2, 0.2}
        };
        for (int i = 0; i < points.length; i++) {
            instance.add(new Point2D(points[i][0], points[i][1]));
        }
        boolean expResult = false;
        boolean result = instance.isInterior(newPoint);
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class ConvexChainImpl.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        ConvexChainImpl<Point> instance = new ConvexChainImpl<>();
        Point newPoint = new Point2D(1.0, 1.0);
        assertEquals(true, instance.isEmpty());

        instance.add(newPoint);
        assertEquals(false, instance.isEmpty());
    }

    /**
     * Test of delete method, of class ConvexChainImpl.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        int expResult = chain.size();
        Point p = new Point2D(9.9, -9.9);

        chain.add(p);

        assertEquals(expResult + 1, chain.size());

        // case I: delete item(s) in the chain
        ConvexChain<Point> trivialChain = new ConvexChainImpl<>();
        trivialChain.add(p);
        int result = chain.delete(trivialChain);
        assertEquals(expResult, result);

        // case II: delete an item not in the chain
        result = chain.delete(trivialChain);
        assertEquals(-1, result);

        // case II: delete from an empty chain
        result = new ConvexChainImpl<>().delete(chain);
        assertEquals(-1, result);
    }

    /**
     * Test of insertBefore method, of class ConvexChainImpl.
     */
    @Test
    public void testInsertBefore() {
        System.out.println("insertBefore");
        Point newPoint = new Point2D(9.0, 9.0);
        Point existingPoint = new Point2D(0.0, 0.0);
        chain.insertBefore(newPoint, existingPoint);

        assertEquals(chain.getFirst(), newPoint);
        assertEquals(chain.get(1), existingPoint);
    }

    /**
     * Test of insertAfter method, of class ConvexChainImpl.
     */
    @Test
    public void testInsertAfter() {
        System.out.println("insertAfter");
        Point newPoint = new Point2D(9.0, 9.0);
        Point existingPoint = new Point2D(0.0, 0.0);
        chain.insertAfter(newPoint, existingPoint);

        assertEquals(chain.getFirst(), existingPoint);
        assertEquals(chain.get(1), newPoint);
    }

    /**
     * Test of dominates method, of class ConvexChainImpl.
     */
    @Test
    public void testDominates() {
        System.out.println("dominates");
        ConvexChain<Point> trivialChain = new ConvexChainImpl<>();

        //case I: any chain dominates an empty chain
        boolean result = chain.dominates(trivialChain);
        assertEquals(true, result);

        //case II: dominates a chain wholly within it's convex closure.
        trivialChain.add(new Point2D(0.5, 0.1));
        result = chain.dominates(trivialChain);
        assertEquals(true, result);

        trivialChain.add(new Point2D(1.0, 1.0));
        result = chain.dominates(trivialChain);
        assertEquals(false, result);
    }

    /**
     * Test of split method, of class ConvexChainImpl.
     */
    @Test
    public void testSplit() {
        System.out.println("split");

        ConvexChain<Point> instance = new ConvexChainImpl<>();
        double[][] points = {
            {0.0, 0.0},
            {0.2, 0.2}
        };
        for (int i = 0; i < points.length; i++) {
            instance.add(new Point2D(points[i][0], points[i][1]));
        }

        int k = 1;
        Entry<ConvexChain<Point>, ConvexChain<Point>> result = instance.split(k);
        assertEquals(k, result.getKey().size());
        assertEquals(instance.size() - k, result.getValue().size());
    }

    /**
     * Test of getRightSentinel method, of class ConvexChainImpl.
     */
    @Test
    public void testGetRightSentinel() {
        System.out.println("getRightSentinel");
        ConvexChain<Point> instance = new ConvexChainImpl<>();
        Point rightSentinel = chain.getRightSentinel();
        Point result = instance.getRightSentinel();
        assertFalse(rightSentinel.equals(result));
        assertEquals(chain.getLast().getY(), rightSentinel.getY(), GeometricComparator.EPSILON);
        assertTrue(Math.abs(chain.getLast().getX() - rightSentinel.getX()) > GeometricComparator.EPSILON);
    }

    /**
     * Test of getLeftSentinel method, of class ConvexChainImpl.
     */
    @Test
    public void testGetLeftSentinel() {
        System.out.println("getLeftSentinel");
        ConvexChain<Point> instance = new ConvexChainImpl<>();
        Point leftSentinel = chain.getLeftSentinel();
        Point result = instance.getLeftSentinel();
        assertFalse(leftSentinel.equals(result));
        assertEquals(chain.getFirst().getX(), leftSentinel.getX(), GeometricComparator.EPSILON);
        assertTrue(Math.abs(chain.getFirst().getY() - leftSentinel.getY()) > GeometricComparator.EPSILON);
    }

    /**
     * Test of predecessor method, of class ConvexChainImpl.
     */
    @Test
    public void testPredecessor() {
        System.out.println("predecessor");
        int j = 1;
        Point expResult = new Point2D(0.0, 0.0);
        Point result = chain.predecessor(j);
        assertEquals(expResult, result);
    }

    /**
     * Test of successor method, of class ConvexChainImpl.
     */
    @Test
    public void testSuccessor() {
        System.out.println("successor");
        int j = 0;

        Point expResult = new Point2D(0.2, 0.2);
        Point result = chain.successor(j);
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class ConvexChainImpl.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        int i = chain.size();
        Point expResult = new Point2D(1.0, 1.0);
        boolean noEviction = chain.add(expResult);
        Point result = chain.get(i);
        assertEquals(true, noEviction);
        assertEquals(expResult, result);

    }
}
