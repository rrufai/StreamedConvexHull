/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.testcases;

import cg.convexhull.exact.ConvexHull;
import cg.convexhull.exact.impl.AndrewsMonotoneChain;
import cg.geometry.primitives.impl.Point2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author I827590
 */
public class TestData extends AbstractMap<String, TestCase<Point2D>> {

    private static Map<String, TestCase<Point2D>> testData = new HashMap<>();

    private static double[][] generateRandom(int size) {
        Random random = new Random(System.currentTimeMillis());
        double [][] randomPoints = new double[size][2];
        for(int i = 0; i < size; i++){
            randomPoints[i][0] = size * random.nextDouble();
            randomPoints[i][1] = size * random.nextDouble();
        }
        
        return randomPoints;
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
        TestCase[] testcases = new TestCase[]{
            new TestCase<>("simple", getDataSet(0), getResult(0)),
            new TestCase<>("medium", getDataSet(1), getResult(1)),
            new TestCase<>("complex", getDataSet(2), getResult(2)),
            new TestCase<>("big", getDataSet(3), getResult(3)),
            new TestCase<>("smallnumbers", getDataSet(4), getResult(4)),
            new TestCase<>("random1000", getDataSet(5), getResult(5)),
        };
        for (int i = 0; i < testcases.length; i++) {
            testData.put(testcases[i].getName(), testcases[i]);
        }
    }

    //TODO: move test data into a text file(s)
    private static List<Point2D> getDataSet(int i) {
        double[][] data0 = {
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

        double[][] data1 = {
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

        double[][] data2 = {
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

        double[][] data3 = {
            {22.8319, 1.5574},
            {13.54, -12.1944},
            {-14.9373, 10.3122},
            {15.1514, 15.8849},
            {-20.7067, -7.542},
            {6.7786, -19.5432},
            {-8.9209, 22.1216},
            {5.4365, 19.6453},
            {9.58, -11.1315},
            {-13.4096, 1.1124},
            {-9.5647, 5.9777},
            {-7.0509, -4.4648},
            {6.2874, -7.4676},
            {0.4081, -11.0131},
            {-9.1063, -0.1987},
            {8.3096, -1.0369},
            {8.8118, -10.3978},
            {-7.2171, 12.9024},
            {3.1204, -12.573},
            {11.8229, -12.8127},
            {-8.499, -2.3981},
            {6.6896, -1.1112},
            {15.4351, 1.558},
            {-1.6561, -10.1621},
            {-15.6558, -21.0232}};
        double[][] data4 = {
            {0.3215348546593775, 0.03629583077160248},
            {0.02402358131857918, -0.2356728797179394},
            {0.04590851212470659, -0.4156409924995536},
            {0.3218384001607433, 0.1379850698988746},
            {0.11506479756447, -0.1059521474930943},
            {0.2622539999543261, -0.29702873322836},
            {-0.161920957418085, -0.4055339716426413},
            {0.1905378631228002, 0.3698601009043493},
            {0.2387090918968516, -0.01629827079949742},
            {0.07495888748668034, -0.1659825110491202},
            {0.3319341836794598, -0.1821814101954749},
            {0.07703635755650362, -0.2499430638271785},
            {0.2069242999022122, -0.2232970760420869},
            {0.04604079532068295, -0.1923573186549892},
            {0.05054295812784038, 0.4754929463150845},
            {-0.3900589168910486, 0.2797829520700341},
            {0.3120693385713448, -0.0506329867529059},
            {0.01138812723698857, 0.4002504701728471},
            {0.009645149586391732, 0.1060251100976254},
            {-0.03597933197019559, 0.2953639456959105},
            {0.1818290866742182, 0.001454397571696298},
            {0.444056063372694, 0.2502497166863175},
            {-0.05301752458607545, -0.06553921621808712},
            {0.4823896228171788, -0.4776170002088109},
            {-0.3089226845734964, -0.06356112199235814},
            {-0.271780741188471, 0.1810810595574612},
            {0.4293626522918815, 0.2980897964891882},
            {-0.004796652127799228, 0.382663812844701},
            {0.430695573269106, -0.2995073500084759},
            {0.1799668387323309, -0.2973467472915973},
            {0.4932166845474547, 0.4928094162538735},
            {-0.3521487911717489, 0.4352656197131292},
            {-0.4907368011686362, 0.1865826865533206},
            {-0.1047924716070224, -0.247073392148198},
            {0.4374961861758457, -0.001606279519951237},
            {0.003256207800708899, -0.2729194320486108},
            {0.04310378203457577, 0.4452604050238248},
            {0.4916198379282093, -0.345391701297268},
            {0.001675087028811806, 0.1531837672490476},
            {-0.4404289572876217, -0.2894855991839297}};
        
        double[][] data5 = generateRandom(1000);

        double data[][] = null;
        switch (i) {
            case 0:
                data = data0;
                break;
            case 1:
                data = data1;
                break;
            case 2:
                data = data2;
                break;
            case 3:
                data = data3;
                break;
            case 4:
                data = data4;
                break;
            case 5:
                data = data5;
                break;
        }

        ArrayList<Point2D> pointset = new ArrayList<>(data.length);
        for (int j = 0; j < data.length; j++) {
            pointset.add(new Point2D(data[j][0], data[j][1]));
        }
        return pointset;
    }

    private static List<Point2D> getResult(int k) {
        ConvexHull hull = new AndrewsMonotoneChain<>(getDataSet(k));
        return hull.compute().getVertices();
    }

}
