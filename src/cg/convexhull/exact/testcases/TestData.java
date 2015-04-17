/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.testcases;

import cg.common.collections.CircularArrayList;
import cg.common.collections.pointsequences.ConcentricRandomPointSequence;
import cg.common.collections.pointsequences.PointSequence;
import cg.convexhull.exact.ConvexHull;
import cg.convexhull.exact.impl.AndrewsMonotoneChain;
import cg.geometry.primitives.impl.Point2D;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author I827590
 */
public class TestData extends AbstractMap<String, TestCase<Point2D>> {

    private static Map<String, TestCase<Point2D>> testData = new HashMap<>();

    private static List<Point2D> generateRandom(int size) {
        PointSequence<Point2D> pointSequence = new ConcentricRandomPointSequence<>(size, 100.0);

        return pointSequence.getPointSeqence().getVertices();
    }

    public TestData() {
        initialize();
    }

    @Override
    public Set<Entry<String, TestCase<Point2D>>> entrySet() {
        return testData.entrySet();
    }

    /**
     *
     */
    private static void initialize() {
        List<Point2D> pointsets[] = new List[]{
            getDataSet(0), getDataSet(1), getDataSet(2), getDataSet(3), getDataSet(4), getDataSet(5), getDataSet(6)
        };
        TestCase[] testcases = new TestCase[]{
            new TestCase<>("random25", pointsets[6], getResult(pointsets[6])),
            new TestCase<>("simple", pointsets[0], getResult(pointsets[0])),
            new TestCase<>("medium", pointsets[1], getResult(pointsets[1])),
            new TestCase<>("complex", pointsets[2], getResult(pointsets[2])),
            new TestCase<>("big", pointsets[3], getResult(pointsets[3])),
            new TestCase<>("smallnumbers", pointsets[4], getResult(pointsets[4])),
            new TestCase<>("random1000", pointsets[5], getResult(pointsets[5])),};
        for (int i = 0; i < testcases.length; i++) {
            testData.put(testcases[i].getName(), testcases[i]);
        }
    }

    //TODO: move test data into a text file(s)
    private static List<Point2D> getDataSet(int i) {
        double[][] data = {
            {0.0, 0.0},
            {0.0, 1.0},
            {1.0, 0.5},
            {1.0, 0.0},
            {1.5, 0.2},
            {1.75, 0.3},
            {2.0, 0.0},
            {2.0, 0.5},
            {3.0, 0.0}
        };

        List<Point2D> pointset = new CircularArrayList<>(data.length);
        switch (i) {
            case 0:
                for (int j = 0; j < data.length; j++) {
                    pointset.add(new Point2D(data[j][0], data[j][1]));
                }
                break;
            case 1:
                pointset = generateRandom(50);
                break;
            case 2:
                pointset = generateRandom(100);
                break;
            case 3:
                pointset = generateRandom(200);
                break;
            case 4:
                pointset = generateRandom(500);
                break;
            case 5:
                pointset = generateRandom(1000);
                break;
            case 6:
                pointset = generateRandom(25);
                break;
        }


        return pointset;
    }

    private static List<Point2D> getResult(List<Point2D> points) {
        ConvexHull hull = new AndrewsMonotoneChain<>(points);
        return hull.compute(points).getVertices();
    }
}
