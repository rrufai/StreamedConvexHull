/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.common.collections.pointsequences.ConcentricRandomPointSequence;
import cg.common.collections.pointsequences.PointSequence;
import cg.convexhull.exact.ConvexHull;
import cg.convexhull.exact.impl.AndrewsMonotoneChain;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.impl.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author I827590
 */
public class Experiment {

    public static void main(String[] args) {
        PointSequence pointSequence = new ConcentricRandomPointSequence(10000, 1000.0);
        int budget = 3;
        ConvexHull<Point2D> exactHull = new AndrewsMonotoneChain<>();
        final Geometry<Point2D> exactHullGeom = exactHull.compute(pointSequence.shuffle());
        final double exactHullArea = exactHullGeom.getArea();
        final double exactHullDiameter = exactHullGeom.getDiameter();
        System.out.println("k \t Area \t Distance");
        int hundredth = pointSequence.size() / 10;
        for (int k = budget; k < 60; k++) {
            ConvexHull<Point2D> streamedHull = new StreamedConvexHull<>(k);
            double aveAreaError = 0.0;
            double aveDistanceError = 0.0;

            for (int i = 0; i < 33; i++) {
                Geometry<Point2D> shuffledGeometry = pointSequence.shuffle();
                Geometry<Point2D> streamHullGeom = streamedHull.compute(shuffledGeometry);

                double areaError = (exactHullArea - streamHullGeom.getArea()) / exactHullArea;

                double distanceError = Math.max(exactHullGeom.getHausdorffDistance(streamHullGeom), streamHullGeom.getHausdorffDistance(exactHullGeom)) / exactHullDiameter;
                aveAreaError = (i * aveAreaError + areaError) / (i + 1);
                aveDistanceError = (i * aveDistanceError + distanceError) / (i + 1);
            }

            System.out.printf("%d \t %.12f \t%.12f %n", k, aveAreaError, aveDistanceError);
        }
    }
}
