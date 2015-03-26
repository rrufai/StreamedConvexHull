/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.convexchain;

import cg.common.Toolkit;
import cg.common.comparators.GeometricComparator;
import cg.common.comparators.LexicographicComparator;
import cg.convexlayers.events.IntervalTree;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.AbstractMap;
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
        List<Point2D> pointset = Toolkit.generatePointSet(Toolkit.PointType.FIXED_PAPER, 0, 0);

        intervalTree = getIntervalTree(pointset);
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
        SimpleIntervalTreeImpl<Point2D> nonEmptyIntervalTree = getIntervalTree(Toolkit.<Point2D>generatePointSet(Toolkit.PointType.FIXED_PAPER, 0, 0));
        ConvexChain<Point2D> chain = new ConvexChainImpl<>(nonEmptyIntervalTree.getHullChain());
        nonEmptyIntervalTree.delete(chain);
        
        assertNotSame(chain, nonEmptyIntervalTree.getHullChain());
        
        chain = new ConvexChainImpl<>(nonEmptyIntervalTree.getHullChain());
        nonEmptyIntervalTree.delete(chain);
        
        assertNotSame(chain, nonEmptyIntervalTree.getHullChain());
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
        SimpleIntervalTreeImpl instance = new SimpleIntervalTreeImpl(null, null);
        ConvexChain expResult = new ConvexChainImpl();
        ConvexChain result = instance.getHullChain();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SimpleIntervalTreeImpl instance = new SimpleIntervalTreeImpl(null, null);
        String expResult = "└── []  LBP: null, RBP: null\n";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of insert method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        SimpleIntervalTreeImpl<Point> instance = new SimpleIntervalTreeImpl(null, null);
        assertEquals(true, instance.isEmpty());
        ConvexChain<Point> chain = new ConvexChainImpl<>();
        chain.add(new Point2D(1.0, 1.0));
        instance.insert(chain);
        assertEquals(false, instance.isEmpty());
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
        SimpleIntervalTreeImpl<Point2D> nonEmptyTrees = getIntervalTree(Toolkit.<Point2D>generatePointSet(Toolkit.PointType.CIRCULAR_2_LAYERS, 0, 0));
        SimpleIntervalTreeImpl<Point2D> left = nonEmptyTrees.getLeftChild();
        SimpleIntervalTreeImpl<Point2D> right = nonEmptyTrees.getRightChild();

        assertEquals(0, nonEmptyTrees.getLevel());
        assertEquals(1, left.getLevel());
        assertEquals(1, right.getLevel());
    }

    /**
     * Test of isEmpty method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        SimpleIntervalTreeImpl emptyIntervalTree = new SimpleIntervalTreeImpl<>(null, new ConvexLayersIntervalTreeImpl());
        SimpleIntervalTreeImpl<Point2D> nonEmptyTrees = getIntervalTree(Toolkit.<Point2D>generatePointSet(Toolkit.PointType.CIRCULAR_2_LAYERS, 0, 0));

        assertEquals(true, emptyIntervalTree.isEmpty());
        assertEquals(false, nonEmptyTrees.isEmpty());
    }

    /**
     * Test of bridgePoints method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testBridgePointsNonEmptyTrees() {
        System.out.println("bridgePoints -- non empty subtrees");
        SimpleIntervalTreeImpl<Point2D> nonEmptyIntervalTree = getIntervalTree(Toolkit.<Point2D>generatePointSet(Toolkit.PointType.FIXED_PAPER, 0, 0));
        SimpleIntervalTreeImpl<Point2D> left = nonEmptyIntervalTree.getLeftChild();
        SimpleIntervalTreeImpl<Point2D> right = nonEmptyIntervalTree.getRightChild();
        Entry<Point2D, Point2D> expResult = new AbstractMap.SimpleEntry<>(
                new Point2D(0.0, 0.424),
                new Point2D(0.6, 0.8));
        Entry<Point2D, Point2D> result = nonEmptyIntervalTree.bridgePoints(left, right);
        assertEquals(expResult.getKey().getX(), result.getKey().getX(), GeometricComparator.EPSILON);
        assertEquals(expResult.getKey().getY(), result.getKey().getY(), GeometricComparator.EPSILON);
        assertEquals(expResult.getValue().getX(), result.getValue().getX(), GeometricComparator.EPSILON);
        assertEquals(expResult.getValue().getY(), result.getValue().getY(), GeometricComparator.EPSILON);
    }

    @Test
    public void testBridgePointsMoreNonEmptyTrees() {
        System.out.println("bridgePoints -- more non empty subtrees");
        SimpleIntervalTreeImpl<Point2D> nonEmptyTrees = getIntervalTree(Toolkit.<Point2D>generatePointSet(Toolkit.PointType.CIRCULAR_2_LAYERS, 0, 0));
        SimpleIntervalTreeImpl<Point2D> left = nonEmptyTrees.getLeftChild();
        SimpleIntervalTreeImpl<Point2D> right = nonEmptyTrees.getRightChild();
        Entry<Point2D, Point2D> expResult = new AbstractMap.SimpleEntry<>(
                new Point2D(-0.862, -0.19),
                new Point2D(0.442, 0.764));
        Entry<Point2D, Point2D> result = nonEmptyTrees.bridgePoints(left, right);
        assertEquals(expResult.getKey().getX(), result.getKey().getX(), GeometricComparator.EPSILON);
        assertEquals(expResult.getKey().getY(), result.getKey().getY(), GeometricComparator.EPSILON);
        assertEquals(expResult.getValue().getX(), result.getValue().getX(), GeometricComparator.EPSILON);
        assertEquals(expResult.getValue().getY(), result.getValue().getY(), GeometricComparator.EPSILON);
    }

    @Test
    public void testBridgePointsEmptyTrees() {
        System.out.println("bridgePoints -- empty subtrees");
        SimpleIntervalTreeImpl emptyIntervalTree = new SimpleIntervalTreeImpl<>(null, new ConvexLayersIntervalTreeImpl());
        SimpleIntervalTreeImpl left = emptyIntervalTree.getLeftChild();
        SimpleIntervalTreeImpl right = emptyIntervalTree.getRightChild();
        Entry<Point2D, Point2D> expResult = new AbstractMap.SimpleEntry<>(
                new Point2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY),
                new Point2D(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
        Entry<Point2D, Point2D> result = emptyIntervalTree.bridgePoints(left, right);
        assertEquals(expResult.getKey().getX(), result.getKey().getX(), GeometricComparator.EPSILON);
        assertEquals(expResult.getKey().getY(), result.getKey().getY(), GeometricComparator.EPSILON);
        assertEquals(expResult.getValue().getX(), result.getValue().getX(), GeometricComparator.EPSILON);
        assertEquals(expResult.getValue().getY(), result.getValue().getY(), GeometricComparator.EPSILON);
    }

    /**
     * Test of bridgePoints method, of class SimpleIntervalTreeImpl.
     */
    @Test
    public void testTangents() {
        System.out.println("bridgePoints");
        SimpleIntervalTreeImpl<Point2D> nonEmptyTrees = getIntervalTree(Toolkit.<Point2D>generatePointSet(Toolkit.PointType.FIXED_PAPER, 0, 0));
        SimpleIntervalTreeImpl<Point2D> left = nonEmptyTrees.getLeftChild();
        SimpleIntervalTreeImpl<Point2D> right = nonEmptyTrees.getRightChild();
        Point2D al = new Point2D(0.0, 0.0);
        Point2D ar = new Point2D(1.0, 1.0);

        Entry<Point2D, Point2D> result = nonEmptyTrees.tangents(al, ar, left, right);

        Entry<Point2D, Point2D> expResult = new AbstractMap.SimpleEntry<>(
                new Point2D(0.0, 0.424),
                new Point2D(0.6, 0.8));

        assertEquals(expResult.getKey().getX(), result.getKey().getX(), GeometricComparator.EPSILON);
        assertEquals(expResult.getKey().getY(), result.getKey().getY(), GeometricComparator.EPSILON);
        assertEquals(expResult.getValue().getX(), result.getValue().getX(), GeometricComparator.EPSILON);
        assertEquals(expResult.getValue().getY(), result.getValue().getY(), GeometricComparator.EPSILON);
    }

    private SimpleIntervalTreeImpl<Point2D> getIntervalTree(List<Point2D> pointset) {
        ConvexLayersIntervalTreeImpl<Point2D> convexLayersIntervalTree = new ConvexLayersIntervalTreeImpl<>(pointset,
                new LexicographicComparator(LexicographicComparator.Direction.BOTTOM_UP),
                new LexicographicComparator(LexicographicComparator.Direction.LEFT_TO_RIGHT));

        return (SimpleIntervalTreeImpl<Point2D>) convexLayersIntervalTree.getIntervalTree();
    }
}
