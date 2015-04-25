/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.common.collections.pointsequences.ConcentricRandomPointSequence;
import cg.common.collections.pointsequences.PointSequence;
import cg.common.collections.pointsequences.UniformInBoxPointSequence2D;
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
        //PointSequence pointSequence = new UniformInBoxPointSequence2D(10000, 100, 100);
        PointSequence pointSequence = new ConcentricRandomPointSequence(10000, 100);
        int budget = 3;
        ConvexHull<Point2D> exactHull = new AndrewsMonotoneChain<>();
        final Geometry<Point2D> exactHullGeom = exactHull.compute(pointSequence.shuffle());
        final double exactHullArea = exactHullGeom.getArea();
        final double exactHullDiameter = exactHullGeom.getDiameter();
        System.out.println("id \t k \t Error \t Evictions \t Type");

        for (int k = budget; k < 31 + budget; k++) {
            ConvexHull<Point2D> streamedHull = new StreamedConvexHull<>(k);
            double aveAreaError = 0.0;
            double aveDistanceError = 0.0;
            int aveEvictionCount = 0;
            int minEvictionCount = Integer.MAX_VALUE;
            for (int i = 0; i < 33; i++) {
                Geometry<Point2D> shuffledGeometry = pointSequence.shuffle();
                Geometry<Point2D> streamHullGeom = streamedHull.compute(shuffledGeometry);
                final int evictionCount = ((Streaming) streamedHull).getEvictionCount();
                double areaError = (exactHullArea - streamHullGeom.getArea()) / exactHullArea;

                double distanceError = Math.max(exactHullGeom.getHausdorffDistance(streamHullGeom), streamHullGeom.getHausdorffDistance(exactHullGeom)) / exactHullDiameter;
                aveAreaError = (i * aveAreaError + areaError) / (i + 1);
                aveDistanceError = (i * aveDistanceError + distanceError) / (i + 1);
                aveEvictionCount = (i*aveEvictionCount + evictionCount)/(i+1);
                minEvictionCount = Math.min(minEvictionCount, evictionCount);
            }
            

            System.out.printf("%d \t%d \t %.12f \t %d \tArea %n", 2 * (k - budget) + 1, k, aveAreaError, minEvictionCount);
            System.out.printf("%d \t%d \t %.12f \t %d \tDistance %n", 2 * (k - budget) + 2, k, aveDistanceError, minEvictionCount);
        }
    }
}
