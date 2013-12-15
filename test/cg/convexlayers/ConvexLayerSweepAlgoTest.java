/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;

import cg.common.Toolkit;
import cg.common.comparators.LexicographicComparator;
import cg.common.comparators.LexicographicComparator.Direction;
import cg.convexhull.exact.ConvexHull;
import cg.convexhull.exact.impl.AndrewsMonotoneChain;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author rrufai
 */
public class ConvexLayerSweepAlgoTest {

    private List<Point2D> pointset;
    private List<TreeSet<Point2D>> expectedLayers;
    private Comparator comparator = new LexicographicComparator(Direction.BOTTOM_UP);
    private double EPSILON = 1.0E-9;

    public ConvexLayerSweepAlgoTest() {
    }

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

    private List<TreeSet<Point2D>> computeConvexLayers(List<Point2D> points) {
        List<TreeSet<Point2D>> list = new ArrayList<>();
        ConvexHull<Point2D> instance = new AndrewsMonotoneChain<>();
        while (!points.isEmpty()) {
            Geometry<Point2D> result = instance.compute(new Polygon2D<>(points));
            TreeSet<Point2D> sortedResult = new TreeSet<>(comparator);
            sortedResult.addAll(result.getVertices());
            list.add(sortedResult);
            points.removeAll(result.getVertices());
        }

        return list;
    }

    /**
     * Test of computeIntersection method, of class ConvexLayerSweepAlgo.
     */
    @Test
    public void testComputeIntersection() {
        System.out.println("computeIntersection");
        Point2D p1 = new Point2D(0.0, 1.0);
        Point2D p2 = new Point2D(1.0, 0.0);
        Point2D p3 = new Point2D(0.0, 0.0);
        Point2D p4 = new Point2D(1.0, 1.0);
        ConvexLayerSweepAlgo<Point2D> instance = new ConvexLayerSweepAlgo<>();
        Point2D expResult = new Point2D(0.5, 0.5);
        Point2D result = instance.computeIntersection(p1, p2, p3, p4);
        Assert.assertEquals(expResult, result);

    }

    /**
     * Test of computeIntersection method, of class ConvexLayerSweepAlgo.
     */
    @Test
    public void testComputeIntersectionParallelSegments() {
        System.out.println("computeIntersection - parallel segments");
        Point2D p1 = new Point2D(0.0, 0.0);
        Point2D p2 = new Point2D(5.0, 5.0);
        Point2D p3 = new Point2D(10.0, 10.0);
        Point2D p4 = new Point2D(2.0, 2.0);
        ConvexLayerSweepAlgo<Point2D> instance = new ConvexLayerSweepAlgo<>();
        Point2D result = instance.computeIntersection(p1, p2, p3, p4);
        System.out.println("result: " + result);
        Assert.assertNull(result);
    }

    /**
     * Test of computeIntersection method, of class ConvexLayerSweepAlgo.
     */
    @Test
    public void testComputeIntersectionOverlappingSegments() {
        System.out.println("computeIntersection - overlapping segments");
        Point2D p1 = new Point2D(0.0, 0.0);
        Point2D p2 = new Point2D(4.0, 4.0);
        Point2D p3 = new Point2D(1.0, 1.0);
        Point2D p4 = new Point2D(5.0, 5.0);
        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<>();
        Point2D result = instance.computeIntersection(p1, p2, p3, p4);
        System.out.println("result: " + result);
        Assert.assertNull(result);
    }

    /**
     * Test of compute method, of class ConvexLayerSweepAlgo.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        ConvexLayerSweepAlgo<Point2D> instance = new ConvexLayerSweepAlgo<>(pointset);
        List<TreeSet<Point2D>> expectedResult = expectedLayers;

        List<List<Point2D>> actualResult = instance.compute();
        Assert.assertEquals(actualResult.size(), expectedResult.size());
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void testSingleLayerCompute() {
        System.out.println("compute");
        int pointCount = 3 + new Random().nextInt(50);
        List<Point2D> circularPointSet = Toolkit.<Point2D>generateLayer(Toolkit.Layer.ALL, 1.0, pointCount);
        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> (circularPointSet);
        List<List<Point2D>> result = instance.compute();

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testKLayersCompute() {
        System.out.println("compute");

        Random random = new Random();
        final int pointCount = 3 + random.nextInt(20);
        final int layerCount = 2 + random.nextInt(10);

        List<Point2D> circularPointSet = Toolkit.generateLayer(Toolkit.Layer.ALL, 1.0, pointCount, layerCount);
        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> (circularPointSet);
        List<List<Point2D>> result = instance.compute();

        Assert.assertEquals(layerCount, result.size());
    }

    @Test
    public void testKCircularLayersCompute() {
        System.out.println("compute");

        Random random = new Random();
        final int pointCount = 3 + random.nextInt(100);
        final int layerCount = 3; // + random.nextInt(10);

        List<Point2D> circularPointSet = Toolkit.<Point2D>generatePointSet(Toolkit.PointType.CIRCULAR_K_LAYERS, pointCount, layerCount);
        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> (circularPointSet);
        List<List<Point2D>> result = instance.compute();

        Assert.assertEquals(layerCount, result.size());
    }

    
    @Test
    public void testKHexagonalLayersCompute() {
        System.out.println("compute");

        Random random = new Random();
        final int pointCount = 3 + random.nextInt(100);
        final int layerCount = 2 + random.nextInt(10);

        List<Point2D> circularPointSet = Toolkit.generatePointSet(Toolkit.PointType.HEXAGONAL_K_LAYERS, pointCount, layerCount);
        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> (circularPointSet);
        List<List<Point2D>> result = instance.compute();

        Assert.assertEquals(layerCount, result.size());
        for(List<Point2D> layer : result){
            Assert.assertEquals(6, layer.size());
        }
    }

    @Test
    public void testEmptyPointsetCompute() {
        System.out.println("compute");
        List<Point2D> circularPointSet = new ArrayList();
        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> (circularPointSet);
        List<List<Point2D>> result = instance.compute();

        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testComputeUpperLayers() {
        System.out.println("compute");
        double radius = 1.0; //Math.random();

        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> ();
        List<TreeSet<Point2D>> upperLayers = instance.computeUpperLayers(Toolkit.<Point2D>generateLayer(Toolkit.Layer.UPPER, radius, 10));
        Assert.assertEquals(1, upperLayers.size());

        upperLayers = instance.computeUpperLayers(Toolkit.<Point2D>generateLayer(Toolkit.Layer.UPPER, radius, 20, 2));
        Assert.assertEquals(2, upperLayers.size());

        Random random = new Random();
        int k = 2 + random.nextInt(50);

        upperLayers = instance.computeUpperLayers(Toolkit.<Point2D>generateLayer(Toolkit.Layer.UPPER, radius, 20, k));
        Assert.assertEquals(k, upperLayers.size());
    }

    @Test
    public void testComputeLowerLayers() {
        System.out.println("compute");
        double radius = 1.0; //Math.random();

        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> ();
        List<TreeSet<Point2D>> lowerLayers = instance.computeLowerLayers(Toolkit.<Point2D>generateLayer(Toolkit.Layer.LOWER, radius, 10));
        Assert.assertEquals(1, lowerLayers.size());

        lowerLayers = instance.computeLowerLayers(Toolkit.<Point2D>generateLayer(Toolkit.Layer.LOWER, radius, 10, 2));
        Assert.assertEquals(2, lowerLayers.size());

        Random random = new Random();
        int k = 2 + random.nextInt(50);

        lowerLayers = instance.computeLowerLayers(Toolkit.<Point2D>generateLayer(Toolkit.Layer.LOWER, radius, 10, k));
        Assert.assertEquals(k, lowerLayers.size());
    }

    @Test @Ignore
    public void testSingleLayerMerge() {
        System.out.println("compute");
        double radius = 1.0; //Math.random();

        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> ();
        List<Point2D> _pointset = Toolkit.generateLayer(Toolkit.Layer.UPPER, radius, 10);
        _pointset.addAll(Toolkit.<Point2D>generateLayer(Toolkit.Layer.RIGHT_TO_LEFT, radius, 10));
        _pointset.addAll(Toolkit.<Point2D>generateLayer(Toolkit.Layer.LOWER, radius, 10));
        _pointset.addAll(Toolkit.<Point2D>generateLayer(Toolkit.Layer.LEFT_TO_RIGHT, radius, 10));
        instance.setPointset(_pointset);
        List<TreeSet<Point2D>> upperLayer = instance.computeUpperLayers(_pointset);
        List<TreeSet<Point2D>> lowerLayer = instance.computeLowerLayers(_pointset);
        //List<List<Point2D>> result = instance.mergeLayers(upperLayer,lowerLayer);
        //Assert.assertEquals(1, result.size());
    }

    @Test @Ignore
    public void testKLayerMerge() {
        System.out.println("compute");
        double radius = 1.0; //Math.random();
        int numberOfLayers = 2;

        ConvexLayerSweepAlgo<Point2D>  instance = new ConvexLayerSweepAlgo<> ();
        List<Point2D> _pointset = Toolkit.generateLayer(Toolkit.Layer.UPPER, radius, 10, numberOfLayers);
        _pointset.addAll(Toolkit.<Point2D>generateLayer(Toolkit.Layer.RIGHT_TO_LEFT, radius, 10, numberOfLayers));
        _pointset.addAll(Toolkit.<Point2D>generateLayer(Toolkit.Layer.LOWER, radius, 10, numberOfLayers));
        _pointset.addAll(Toolkit.<Point2D>generateLayer(Toolkit.Layer.LEFT_TO_RIGHT, radius, 10, numberOfLayers));
        instance.setPointset(_pointset);
        List<TreeSet<Point2D>> upperLayer = instance.computeUpperLayers(_pointset);
        List<TreeSet<Point2D>> lowerLayer = instance.computeLowerLayers(_pointset);
       //List<List<Point2D>> result = instance.mergeLayers(upperLayer, lowerLayer);
        //Assert.assertEquals(numberOfLayers, result.size());
    }
}
