/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rrufai
 */
public class Toolkit {

    /**
     * Compute the intersection between the line segment (p1, p2) and the
     * segment (p3, p4).
     *
     * @param p1 first endpoint of segment (p1, p2)
     * @param p2 second endpoint of segment (p1, p2)
     * @param p3 first endpoint of segment (p3, p4)
     * @param p4 second endpoint of segment (p3, p4)
     * @return
     */
    public static <K extends Point> K computeIntersection0(K p1, K p2, K p3, K p4) {
        BigDecimal xD1, yD1, xD2, yD2, xD3, yD3;
        BigDecimal ua, div;
        final MathContext ROUNDING = MathContext.DECIMAL128;
        if ((p1 == null) || (p2 == null) || (p3 == null) || (p4 == null)) {
            return null;
        }
// calculate differences

        xD1 = BigDecimal.valueOf(p2.getX()).subtract(BigDecimal.valueOf(p1.getX()));
        xD2 = BigDecimal.valueOf(p4.getX()).subtract(BigDecimal.valueOf(p3.getX()));
        yD1 = BigDecimal.valueOf(p2.getY()).subtract(BigDecimal.valueOf(p1.getY()));
        yD2 = BigDecimal.valueOf(p4.getY()).subtract(BigDecimal.valueOf(p3.getY()));
        xD3 = BigDecimal.valueOf(p1.getX()).subtract(BigDecimal.valueOf(p3.getX()));
        yD3 = BigDecimal.valueOf(p1.getY()).subtract(BigDecimal.valueOf(p3.getY()));

        div = xD1.multiply(yD2).subtract(yD1.multiply(xD2)); //(xD1 * yD2 - yD1 * xD2 ;

        if (div.abs().compareTo(BigDecimal.ZERO) == 0) { //parallel lines
            return null;
        }

        ua = xD2.multiply(yD3).subtract(yD2.multiply(xD3)).divide(div, ROUNDING); //(xD2 * yD3 - yD2 * xD3) / div;
        double x = p1.getX() + ua.multiply(xD1).doubleValue();
        double y = p1.getY() + ua.multiply(yD1).doubleValue();
        K pt = (K) new Point2D(x, y);

        return pt;
    }

    public static <K extends Point> K computeCentroid(Set<K> pointset) {
        List<K> list = new ArrayList();
        list.addAll(pointset);
        return computeCentroid(list);
    }

    /**
     * Computes the centroid of a given
     * <code>List</code> of points.
     *
     * @param pointset
     * @return the centroid of the pointset given.
     */
    public static <K extends Point> K computeCentroid(List<K> pointset) {
        K c = null;
        for (int i = 0; i < pointset.size(); i++) {
            K p = pointset.get(i);
            if (c == null) {
                c = (K) new Point2D(p.getX(), p.getY());
            } else {
                c.setLocation((i * c.getX() + p.getX()) / (i + 1),
                        (i * c.getY() + p.getY()) / (i + 1));
            }
        }

        return c;
    }

    public static <P extends Point> List<P> generateHexagonalLayer(double radius) {
        List<P> inputPointset = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            double angle = i * Math.PI / 3;
            P point = (P) new Point2D(
                    Math.floor(radius * Math.cos(angle) * 1000) / 1000,
                    Math.floor(radius * Math.sin(angle) * 1000) / 1000);
            inputPointset.add(point);
        }
        return inputPointset;
    }

    public static Color[] getUniqueColors(int n) {
        Color[] cols = new Color[n];
        for (int i = 0; i < n; i++) {
            cols[i] = Color.getHSBColor((float) i / n, .9f, .7f);
        }
        return cols;
    }

    public static Color[] getUniqueColors2(int n) {
        final int lowerLimit = 0x10;
        final int upperLimit = 0xF0;
        final int colorStep = (upperLimit - lowerLimit) / (1 + n);

        Color[] colors = new Color[n];
        for (int i = 0; i < n; i++) {
            colors[i] = new Color(lowerLimit + colorStep * i);
        }
        return colors;
    }

    public static enum PointType {

        CIRCULAR, CIRCULAR_K_LAYERS, HEXAGONAL_K_LAYERS, FIXED_HEXAGONAL_LAYERS, RANDOM, FIXED_RANDOM, FIXED, FIXED2, FIXED_PAPER, CIRCULAR_2_LAYERS,
        POSITIVE_RANDOM;
    }

    public static <P extends Point> List<P> generatePointSet(PointType pointType, int numberOfPointsPerLayer, int numberOfLayers) {
        List<P> inputPointset = new ArrayList<>();
        Random random = new Random();
        switch (pointType) {
            case RANDOM:
                for (int i = 0; i < numberOfPointsPerLayer; i++) {
                    double x = Math.floor(2f * (random.nextFloat() - .5f) * 1000f) / 1000f;
                    double y = Math.floor(2f * (random.nextFloat() - .5f) * 1000f) / 1000f;
                    inputPointset.add((P) new Point2D(x, y));
                    inputPointset.add((P) new Point2D(Math.floor(random.nextDouble() * 1000) / 1000, Math.floor(random.nextDouble() * 1000) / 1000));
                }
                break;
            case POSITIVE_RANDOM:
                for (int i = 0; i < numberOfPointsPerLayer * numberOfLayers; i++) {
                    double x = Math.floor(random.nextFloat() * 1000f) / 1000f;
                    double y = Math.floor(random.nextFloat() * 1000f) / 1000f;
                    inputPointset.add((P) new Point2D(x, y));
                    //inputPointset.add((P) new Point2D(Math.floor(random.nextDouble() * 1000) / 1000, Math.floor(random.nextDouble() * 1000) / 1000));
                }
                break;
            case FIXED:
                double[][] data = {
                    {0.024, 0.275},
                    {0.034, 0.651},
                    {0.119, 0.239},
                    {0.127, 0.048},
                    {0.152, 0.568},
                    {0.187, 0.491},
                    {0.414, 0.433},
                    {0.485, 0.928},
                    {0.52, 0.037},
                    {0.522, 0.097},
                    {0.603, 0.619},
                    {0.697, 0.182},
                    {0.855, 0.764},
                    {0.864, 0.185},
                    {0.914, 0.336},
                    {0.941, 0.49}
                };

                for (int i = 0; i < data.length; i++) {
                    inputPointset.add((P) new Point2D(data[i][0], data[i][1]));
                }
                break;
            case FIXED_PAPER:
                double[][] data1 = {
                    {0.267, 0.150},
                    {0.000, 0.424},
                    {-0.211, 0.052},
                    {0.900, 0.910},
                    {1.000, 0.650},
                    {0.500, 0.250},
                    {0.100, 0.700},
                    {0.400, 0.580},
                    {0.650, 0.600},
                    {0.600, 0.800},
                    {0.550, 0.900},
                    {0.810, 0.520},
                    {0.800, 0.400},
                    {0.410, 0.100},
                    {0.910, 0.710},
                    {0.010, 0.610},
                    //more points 
                    {0.490, 1.000},
                    {0.200, 0.5900},
                    {-0.075, -0.150},};
                for (int i = 0; i < data1.length; i++) {
                    inputPointset.add((P) new Point2D(data1[i][0], data1[i][1]));
                }
                break;

            case FIXED2:
                double[][] data2 = {
                    {0.490, 1.000},
                    {0.900, 0.900},
                    {1.000, 0.650},
                    {0.500, 0.250},
                    {0.100, 0.700},
                    {0.410, 0.590},
                    {0.650, 0.610},
                    {0.600, 0.800},
                    {0.550, 0.900},
                    {0.800, 0.520},
                    {0.810, 0.410},
                    {0.400, 0.400},
                    {0.200, 0.600},
                    {0.510, 0.630}
                };
                for (int i = 0; i < data2.length; i++) {
                    inputPointset.add((P) new Point2D(data2[i][0], data2[i][1]));
                }
                break;
            case CIRCULAR:
                for (int i = 0; i < 200 * numberOfPointsPerLayer; i++) {
                    double angle = Math.PI * 2 * random.nextDouble();
                    P point = (P) new Point2D(
                            Math.cos(angle),
                            Math.sin(angle));
                    inputPointset.add(point);
                }
                break;
            case FIXED_RANDOM:
                double[][] data_random = {
                    {0.856, 0.057},
                    {0.168, 0.079},
                    {0.877, 0.122},
                    {0.953, 0.156},
                    {0.172, 0.274},
                    {0.340, 0.293},
                    {0.936, 0.314},
                    {0.393, 0.361},
                    {0.945, 0.649},
                    {0.926, 0.694},
                    {0.662, 0.734},
                    {0.423, 0.769},
                    {0.175, 0.801},
                    {0.143, 0.877},
                    {0.618, 0.949},
                    {0.081, 0.979}};
                for (int i = 0; i < data_random.length; i++) {
                    inputPointset.add((P) new Point2D(data_random[i][0], data_random[i][1]));
                }
                break;
            case CIRCULAR_2_LAYERS:
                double[][] data3 = {
                    {-0.002, -0.260},
                    {-0.111, 0.235},
                    {0.232, 0.118},
                    {-0.220, -0.139},
                    {-0.241, 0.099},
                    {-0.163, -0.203},
                    {0.248, -0.080},
                    {-0.221, -0.138},
                    {-0.154, -0.210},
                    {-0.259, -0.026},
                    {0.048, -0.256},
                    {0.047, -0.256},
                    {-0.217, -0.144},
                    {0.254, 0.059},
                    {0.216, 0.146},
                    {0.160, 0.205},
                    {0.253, -0.062},
                    {-0.211, 0.152},
                    {0.082, 0.247},
                    {-0.123, -0.229},
                    {0.120, 0.231},
                    {-0.110, 0.236},
                    {-0.175, 0.193},
                    {0.255, -0.051},
                    {0.793, -0.388},
                    {0.404, -0.785},
                    {-0.815, -0.338},
                    {-0.883, 0.009},
                    {-0.484, 0.738},
                    {-0.456, 0.756},
                    {0.820, -0.327},
                    {-0.862, -0.190},
                    {-0.235, -0.851},
                    {-0.620, -0.629},
                    {0.748, -0.469},
                    {-0.677, -0.566},
                    {-0.635, 0.613},
                    {0.021, -0.883},
                    {0.726, -0.502},
                    {-0.837, 0.280},
                    {0.442, 0.764},
                    {-0.847, 0.248},
                    {0.878, 0.090},
                    {0.265, 0.842},
                    {0.355, -0.808},
                    {-0.067, -0.880},
                    {-0.404, -0.785},
                    {-0.098, -0.877},
                    {-0.341, -0.814}
                };
                for (int i = 0; i < data3.length; i++) {
                    inputPointset.add((P) new Point2D(data3[i][0], data3[i][1]));
                }
                break;
            case CIRCULAR_K_LAYERS:
                for (int l = 0; l < numberOfLayers; l++) {
                    double r = Math.random();
                    for (int i = 0; i < numberOfPointsPerLayer; i++) {
                        double angle = Math.PI * 2 * random.nextDouble();
                        P point = (P) new Point2D(
                                r * Math.cos(angle),
                                r * Math.sin(angle));
                        inputPointset.add(point);
                    }
                }
                break;
            case HEXAGONAL_K_LAYERS:
                for (int l = 0; l < numberOfLayers; l++) {
                    List<P> generateHexagonalLayer = generateHexagonalLayer(Math.random());
                    inputPointset.addAll(generateHexagonalLayer);
                }
                break;
            case FIXED_HEXAGONAL_LAYERS:
                double[][] data_hexagon = {
                    {-0.985, 0.07},
                    {-0.845, 0.0},
                    {-0.659, 0.06},
                    {-0.495, -0.853},
                    {-0.493, 0.852},
                    {-0.424, -0.734},
                    {-0.423, 0.731},
                    {-0.33, -0.572},
                    {-0.34, 0.57},
                    {0.328, -0.571},
                    {0.330, 0.579},
                    {0.422, -0.732},
                    {0.425, 0.735},
                    {0.492, -0.854},
                    {0.493, 0.855},
                    {0.658, 0.02},
                    {0.844, 0.03},
                    {0.984, 0.04}
                };
                for (int i = 0; i < data_hexagon.length; i++) {
                    inputPointset.add((P) new Point2D(data_hexagon[i][0], data_hexagon[i][1]));
                }
                break;
        }

        return inputPointset;
    }

    public static enum Layer {

        LOWER, UPPER, LEFT_TO_RIGHT, RIGHT_TO_LEFT, ALL
    }

    public static <P extends Point> List<P> generateLayer(Layer layer, double radius, int size) {
        return generateLayer(layer, radius, size, 1);
    }

    /**
     * Generate points on a parabola facing the direction given in
     * <code>layer</code>.
     *
     * @param <P>
     * @param layer
     * @param radius
     * @param size
     * @param numberOfLayers
     * @return
     */
    public static <P extends Point> List<P> generateLayer(Layer layer, double radius, int size, int numberOfLayers) {
        List<P> layerPoints = new ArrayList<>(size * numberOfLayers);
        // downward facing parabola  y = h - x ^ 2, where h = maximum y coordinate, h > 0
        // upward facing parabola  y = h + x ^ 2, where h = minimum y coordinate, h > 0
        // left facing parabola   y^2 = x - h, where h = min x coordinate, h > 0
        // right facing parabola    y^2 = -x + h, where h = max x coordinate, h > 0
        for (int j = 0; j < numberOfLayers; j++) {
            radius = (j + 1) * radius;
            for (int i = 0; i < size; i++) {
                double angle = generateRandomAngle(layer);
                double x = radius * Math.cos(angle);
                Logger.getAnonymousLogger().log(Level.INFO, "Angle: {0} radians", angle);
                P p = (P) new Point2D(x, radius - x * x);
                layerPoints.add(p);
            }
        }
        return layerPoints;
    }

    private static double generateRandomAngle(Layer layer) {
        double angle = 0.0;
        switch (layer) {
            case UPPER:
                angle = Math.random() * Math.PI;
                break;
            case LEFT_TO_RIGHT:
                angle = 0.5 * Math.PI + Math.random() * Math.PI;
                break;
            case LOWER:
                angle = Math.PI + Math.random() * Math.PI;
                break;
            case RIGHT_TO_LEFT:
                angle = 1.5 * Math.PI + Math.random() * Math.PI;
                break;
            case ALL:
                angle = Math.random() * Math.PI * 2.0;
                break;

        }
        return angle;
    }

    public static <K extends Point> List<K> getSample(List<K> pointset) {
        List<K> sample = new ArrayList<>();
        Random random = new Random();
        final int inputSize = pointset.size();
        int sampleSize = Math.min(3 + random.nextInt(3 * inputSize / 4), inputSize);
        int gap = inputSize / sampleSize;
        for (int i = 0; i < inputSize - 1; i++) {
            sample.add(pointset.get(i));
        }
        return sample;
    }
}
