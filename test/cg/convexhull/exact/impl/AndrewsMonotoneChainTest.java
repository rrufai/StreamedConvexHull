/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.impl;

import cg.common.collections.CircularArrayList;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rrufai
 */
public class AndrewsMonotoneChainTest {

    private List<Point2D> pointset;
    private List<Point2D> chVertices;

    /**
     *
     */
    @Before
    public void setUp() {
        pointset = new ArrayList<>();

        double[][] data = {
            {-3.3256, -10.8778},
            {-15.6558, -21.0232},
            {2.2533, 10.8634},
            {3.8768, -4.1864},
            {-10.4647, 4.2737},
            {12.9092, 3.3406},
            {12.8916, 1.2147},
            {0.6237, -9.0394},
            {4.2729, -8.4715},
            {2.7464, -2.7443},
            {-0.8671, -10.8589},
            {8.2579, -9.5590},
            {-4.8832, 15.7248},
            {22.8319, 1.5574},
            {-0.3640, -11.1732},
            {2.1393, 0.5877},
            {11.6677, -10.2834},
            {1.5928, -12.4928},
            {0.0435, -1.6110},
            {-7.3235, 10.5347},
            {3.9441, 2.2864},
            {-12.3618, 7.5647},
            {8.1432, -10.6782},
            {17.2356, -3.6061},
            {-5.9178, -1.6244},
            {9.5800, -11.1315},
            {13.5400, -12.1944},
            {-14.9373, 10.3122},
            {-13.4096, 1.1124},
            {6.7115, -5.4515},
            {-2.9989, 9.0573},
            {7.9000, 3.3163},
            {9.1562, -8.8976},
            {8.1191, 14.3959},
            {13.9025, 3.8950},
            {7.6860, 15.7892},
            {12.9084, 12.3803},
            {-11.0246, -5.8414},
            {0.8021, -11.9194},
            {-0.5672, 0.2707},
            {-15.0409, -2.3060},
            {3.5730, -7.4363},
            {-9.5647, 5.9777},
            {15.1514, 15.8849},
            {-7.0509, -4.4648},
            {6.2874, -7.4676},
            {3.1932, -1.4634},
            {-8.2190, 7.6302},
            {-20.7067, -7.5420},
            {0.4081, -11.0131},
            {-9.1063, -0.1987},
            {7.1446, 0.3471},
            {6.0774, 5.8530},
            {17.9243, -4.9549},
            {6.9128, -0.4967},
            {-5.4360, -3.3475},
            {4.8034, 0.2067},
            {-9.0912, 16.3515},
            {0.8049, -5.0648},
            {0.5178, -12.4736},
            {1.0004, 5.6938},
            {-2.1786, -8.0357},
            {11.9500, 1.3588},
            {-17.7399, -5.2753},
            {5.2818, 6.3540},
            {9.9564, 6.5288},
            {8.3096, -1.0369},
            {6.7786, -19.5432},
            {1.4031, 2.3256},
            {7.7709, 16.9294},
            {6.6890, 11.1841},
            {-1.5565, -14.8040},
            {-2.7747, 0.2134},
            {-1.9589, -5.8166},
            {-13.7513, -9.2455},
            {-1.3400, -11.3435},
            {2.1844, 3.8881},
            {4.1481, -3.2930},
            {15.4351, 1.5580},
            {-2.5097, -2.6787},
            {7.2323, -3.6497},
            {8.9905, 4.7096},
            {10.4089, 8.2828},
            {-8.9209, 22.1216},
            {3.1204, -12.5730},
            {3.3788, -9.2261},
            {-9.0776, 11.3783},
            {-6.4204, -2.8980},
            {11.8229, -12.8127},
            {-0.3150, 4.1554},
            {4.8988, 16.5324},
            {1.8799, 8.0789},
            {-5.3547, 20.5738},
            {-4.5957, 6.0454},
            {5.4365, 19.6453},
            {-8.4990, -2.3981},
            {8.8118, -10.3978},
            {6.6896, -1.1112},
            {-7.2171, 12.9024},
            {-1.6561, -10.1621}};

        for (int i = 0; i < data.length; i++) {
            pointset.add(new Point2D(data[i][0], data[i][1]));
        }

        double[][] chdata = {
            {-20.7067, -7.5420},
            {-15.6558, -21.0232},
            {6.7786, -19.5432},
            {13.5400, -12.1944},
            {22.8319, 1.5574},
            {15.1514, 15.8849},
            {5.4365, 19.6453},
            {-8.9209, 22.1216},
            {-14.9373, 10.3122}};

        this.chVertices = new CircularArrayList<>(chdata.length);
        for (int i = 0; i < chdata.length; i++) {
            chVertices.add(new Point2D(chdata[i][0], chdata[i][1]));
        }
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }


    /**
     * Test of compute method, of class AndrewsMonotoneChain.
     */
    @Test
    public void testCompute() {
        System.out.println("compute");
        AndrewsMonotoneChain instance = new AndrewsMonotoneChain();
        //instance.setPointset(pointset);
        List<Point2D> expectedResult = chVertices;
        final Polygon2D polygon2D = new Polygon2D(pointset);
        List<Point2D> actualResult = instance.compute(polygon2D).getVertices();
        //Comparator<Point2D> comparator = new LexicographicComparator<>(Direction.BOTTOM_UP);
        //Collections.sort(expectedResult, comparator);
        //Collections.sort(actualResult, comparator);
        assertEquals( actualResult, expectedResult);
    }
}