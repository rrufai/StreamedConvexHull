/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.convexchain;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author I827590
 */
public class ConvexLayersIntervalTreeTest {

    private double[][] data;
    private List<Point> pointset;

    public ConvexLayersIntervalTreeTest() {
        pointset = new ArrayList<>();
    }

    @Before
    public void setUp() {
        data = new double[][]{
            {0.1, 0.7},
            {0.2, 0.6},
            {0.4, 0.4},
            {0.4, 0.59},
            {0.49, 1.0},
            {0.5, 0.25},
            {0.51, 0.63},
            {0.55, 0.9},
            {0.6, 0.8},
            {0.65, 0.6},
            {0.8, 0.4},
            {0.8, 0.52},
            {0.9, 0.9},
            {1.0, 0.65}
        };
        
        for(int i = 0; i < data.length; i++){
            boolean add = pointset.add(new Point2D(data[i][0], data[i][1]));
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of extractRoot method, of class ConvexLayersIntervalTree.
     */
    @Test
    public void testExtractRoot() {
        System.out.println("extractRoot");
        ConvexLayersIntervalTree<Point> instance = new ConvexLayersIntervalTreeImpl<>(pointset);
        double [][] hull = {{0.4, 0.59}, {0.49, 1.0}, {0.9, 0.9}, {1.0, 0.65}};
        
        List<Point> expResult = new ArrayList<>();
        for(int i = 0; i < hull.length; i++){
            expResult.add(new Point2D(hull[i][0], hull[i][1]));
        }
        
        List<Point> result = instance.extractRoot();
        assertEquals(expResult, result);
    }

    /**
     * Test of goRight method, of class ConvexLayersIntervalTree.
     */
    @Test
    public void testGoRight() {
        System.out.println("goRight");
        Point point = pointset.get(0);
        int level = 0;
        ConvexLayersIntervalTree instance = new ConvexLayersIntervalTreeImpl(pointset);
        boolean expResult = false;
        boolean result = instance.goRight(point, level);
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class ConvexLayersIntervalTree.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        ConvexLayersIntervalTree instance = new ConvexLayersIntervalTreeImpl();
        boolean result = instance.isEmpty();
        assertEquals(true, result);
    }

}
