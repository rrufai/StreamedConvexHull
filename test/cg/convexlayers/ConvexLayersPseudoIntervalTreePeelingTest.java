/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;

import cg.common.comparators.LexicographicComparator;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author I827590
 */
public class ConvexLayersPseudoIntervalTreePeelingTest {

    private List<Point> pointset;
    private List<TreeSet<Point2D>> expectedLayers;
    private Comparator comparator = new LexicographicComparator(LexicographicComparator.Direction.BOTTOM_UP);
    private double EPSILON = 1.0E-9;

    @Before
    public void setUp() {
        double[][] data = {
            {.49, 1.0},
            {0.9, 0.9},
            {1.0, 0.65},
            {.5, .25},
            {.1, .7},
            {.4, .59},
            {.65, .6},
            {.6, .8},
            {.55, .9},
            {.8, .52},
            {.8, .4},
            {.4, .4},
            {.2, .6},
            {.51, .63}
        };



        if (pointset == null) {
            pointset = new ArrayList<>();
        }
        for (int i = 0; i < data.length; i++) {
            pointset.add(new Point2D(data[i][0], data[i][1]));
        }

        double[][][] layers = {
            {{.1, .7}, {.49, 1.0}, {.9, .9}, {1.0, 0.65}, {.8, .4}, {.5, .25}},
            {{.2, .6}, {.55, .9}, {.8, .52}, {.4, .4}},
            {{.4, .59}, {.65, .6}, {.6, .8}},
            {{.51, .63}}
        };

        expectedLayers = new ArrayList<>();
        for (int i = 0; i < layers.length; i++) {
            TreeSet<Point2D> layer = new TreeSet<>(comparator);
            for (int j = 0; j < layers[i].length; j++) {
                layer.add(new Point2D(layers[i][j][0], layers[i][j][1]));
            }
            expectedLayers.add(layer);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of compute method, of class ConvexLayersPseudoIntervalTreePeeling.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        ConvexLayersPseudoIntervalTreePeeling instance = new ConvexLayersPseudoIntervalTreePeeling(pointset);
        List expResult = expectedLayers;
        List result = instance.compute();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPointset method, of class
     * ConvexLayersPseudoIntervalTreePeeling.
     */
    @Test
    public void testGetPointset() {
        System.out.println("getPointset");
        ConvexLayersPseudoIntervalTreePeeling instance = new ConvexLayersPseudoIntervalTreePeeling(pointset);
        List expResult = pointset;
        List result = instance.getPointset();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPointset method, of class
     * ConvexLayersPseudoIntervalTreePeeling.
     */
    @Test
    public void testSetPointset() {
        System.out.println("setPointset");
        ConvexLayersPseudoIntervalTreePeeling instance = new ConvexLayersPseudoIntervalTreePeeling();
        instance.setPointset(pointset);
        List expResult = pointset;
        List result = instance.getPointset();
        assertEquals(expResult, result);
    }
}
