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

/**
 *
 * @author I827590
 */
public class Experiment {

    public static void main(String[] args) {
        measureDistanceError();
        measureAreaError();
        measureRuntime();
    }

    public static void measureAreaError() {
        //PointSequence pointSequence = new UniformInBoxPointSequence2D(10000, 100, 100);
        PointSequence pointSequence = new ConcentricRandomPointSequence(10000, 100);
        int budget = 16;
        ConvexHull<Point2D> exactHull = new AndrewsMonotoneChain<>();
        final Geometry<Point2D> exactHullGeom = exactHull.compute(pointSequence.shuffle());
        final double exactHullArea = exactHullGeom.getArea();
        System.out.println(" \t k \t Area Error \t #Evictions \t Goodness");


        for (int k = budget; k < 31 + budget; k++) {
            ConvexHull<Point2D> earAreaStreamedHull = new StreamedConvexHull<>(k, new EarAreaGoodness());
            ConvexHull<Point2D> earHeightStreamedHull = new StreamedConvexHull<>(k, new EarHeightGoodness());
            ConvexHull<Point2D> earAngleStreamedHull = new StreamedConvexHull<>(k, new EarCentroidAngleGoodness());
            double aveAreaError = 0.0;
            double aveHeightAreaError = 0.0;
            double aveAreaAngleError = 0.0;
            int aveEarEvictionCount = 0;
            int aveHeightEvictionCount = 0;
            int minEarEvictionCount = Integer.MAX_VALUE;
            int minHeightEvictionCount = Integer.MAX_VALUE;
            int minEarAreaAngleEvictionCount = Integer.MAX_VALUE;

            for (int i = 0; i < 33; i++) {
                Geometry<Point2D> shuffledGeometry = pointSequence.shuffle();
                Geometry<Point2D> earAreaStreamHullGeom = earAreaStreamedHull.compute(shuffledGeometry);
                Geometry<Point2D> earHeightStreamHullGeom = earHeightStreamedHull.compute(shuffledGeometry);
                Geometry<Point2D> earAngleStreamHullGeom = earAngleStreamedHull.compute(shuffledGeometry);

                final int earAreaEvictionCount = ((Streaming) earAreaStreamedHull).getEvictionCount();
                final int earHeightEvicationCount = ((Streaming) earHeightStreamedHull).getEvictionCount();
                final int earAngleEvicationCount = ((Streaming) earAngleStreamedHull).getEvictionCount();

                double areaError = (exactHullArea - earAreaStreamHullGeom.getArea()) / exactHullArea;
                double earHeightAreaDrror = (exactHullArea - earHeightStreamHullGeom.getArea()) / exactHullArea;
                double earAngleAreaError = (exactHullArea - earAngleStreamHullGeom.getArea()) / exactHullArea;

                aveAreaError = (i * aveAreaError + areaError) / (i + 1);
                aveHeightAreaError = (i * aveHeightAreaError + earHeightAreaDrror) / (i + 1);
                aveAreaAngleError = (i * aveAreaAngleError + earAngleAreaError) / (i + 1);
                aveEarEvictionCount = (i * aveEarEvictionCount + earAreaEvictionCount) / (i + 1);
                aveHeightEvictionCount = (i * aveHeightEvictionCount + earHeightEvicationCount) / (i + 1);

                minEarEvictionCount = Math.min(minEarEvictionCount, earHeightEvicationCount);
                minHeightEvictionCount = Math.min(minHeightEvictionCount, earHeightEvicationCount);
                minEarAreaAngleEvictionCount = Math.min(minEarAreaAngleEvictionCount, earAngleEvicationCount);
            }

            System.out.printf("%d \t%d \t %.12f \t %d \tArea %n", 3 * (k - budget) + 1, k, aveAreaError, minEarEvictionCount);
            System.out.printf("%d \t%d \t %.12f \t %d \tHeight %n", 3 * (k - budget) + 2, k, aveHeightAreaError, minHeightEvictionCount);
            System.out.printf("%d \t%d \t %.12f \t %d \tAngle %n", 3 * (k - budget) + 3, k, aveAreaAngleError, minEarAreaAngleEvictionCount);
        }
    }

    
    
    public static void measureDistanceError() {
        //PointSequence pointSequence = new UniformInBoxPointSequence2D(10000, 100, 100);
        PointSequence pointSequence = new ConcentricRandomPointSequence(10000, 100);
        int budget = 16;
        ConvexHull<Point2D> exactHull = new AndrewsMonotoneChain<>();
        final Geometry<Point2D> exactHullGeom = exactHull.compute(pointSequence.shuffle());
        final double exactHullDiameter = exactHullGeom.getDiameter();
        System.out.println(" \t k \t Distance Error \t #Evictions \t Goodness");


        for (int k = budget; k < 31 + budget; k++) {
            ConvexHull<Point2D> earAreaStreamedHull = new StreamedConvexHull<>(k, new EarAreaGoodness());
            ConvexHull<Point2D> earHeightStreamedHull = new StreamedConvexHull<>(k, new EarHeightGoodness());
            ConvexHull<Point2D> earAngleStreamedHull = new StreamedConvexHull<>(k, new EarCentroidAngleGoodness());
            double aveEarAreaDistanceError = 0.0;
            double aveHeightDistanceError = 0.0;
            double aveAngleDistanceError = 0.0;
            int aveEarEvictionCount = 0;
            int aveHeightEvictionCount = 0;
            int minEarEvictionCount = Integer.MAX_VALUE;
            int minHeightEvictionCount = Integer.MAX_VALUE;
            int minEarAreaAngleEvictionCount = Integer.MAX_VALUE;

            for (int i = 0; i < 33; i++) {
                Geometry<Point2D> shuffledGeometry = pointSequence.shuffle();
                Geometry<Point2D> earAreaStreamHullGeom = earAreaStreamedHull.compute(shuffledGeometry);
                Geometry<Point2D> earHeightStreamHullGeom = earHeightStreamedHull.compute(shuffledGeometry);
                Geometry<Point2D> earAngleStreamHullGeom = earAngleStreamedHull.compute(shuffledGeometry);

                final int earAreaEvictionCount = ((Streaming) earAreaStreamedHull).getEvictionCount();
                final int earHeightEvicationCount = ((Streaming) earHeightStreamedHull).getEvictionCount();
                final int earAngleEvicationCount = ((Streaming) earAngleStreamedHull).getEvictionCount();

                double earAreaDistanceError = Math.max(exactHullGeom.getHausdorffDistance(earAreaStreamHullGeom), earAreaStreamHullGeom.getHausdorffDistance(exactHullGeom)) / exactHullDiameter;
                double earAngleDistanceError = Math.max(exactHullGeom.getHausdorffDistance(earAngleStreamHullGeom), earAngleStreamHullGeom.getHausdorffDistance(exactHullGeom)) / exactHullDiameter;
                double heightDistanceError = Math.max(exactHullGeom.getHausdorffDistance(earHeightStreamHullGeom), earHeightStreamHullGeom.getHausdorffDistance(exactHullGeom)) / exactHullDiameter;
                
                aveEarAreaDistanceError = (i * aveEarAreaDistanceError + earAreaDistanceError) / (i + 1);
                aveHeightDistanceError = (i * aveHeightDistanceError + heightDistanceError) / (i + 1);
                aveAngleDistanceError = (i * aveAngleDistanceError + earAngleDistanceError) / (i + 1);
                aveEarEvictionCount = (i * aveEarEvictionCount + earAreaEvictionCount) / (i + 1);
                aveHeightEvictionCount = (i * aveHeightEvictionCount + earHeightEvicationCount) / (i + 1);

                minEarEvictionCount = Math.min(minEarEvictionCount, earHeightEvicationCount);
                minHeightEvictionCount = Math.min(minHeightEvictionCount, earHeightEvicationCount);
                minEarAreaAngleEvictionCount = Math.min(minEarAreaAngleEvictionCount, earAngleEvicationCount);
            }

            System.out.printf("%d \t%d \t %.12f \t %d \tArea %n", 3 * (k - budget) + 1, k, aveEarAreaDistanceError, minEarEvictionCount);
            System.out.printf("%d \t%d \t %.12f \t %d \tDistance %n", 3 * (k - budget) + 2, k, aveHeightDistanceError, minHeightEvictionCount);
            System.out.printf("%d \t%d \t %.12f \t %d \tAngle %n", 3 * (k - budget) + 3, k, aveAngleDistanceError, minEarAreaAngleEvictionCount);
        }
    }
    
    
    public static void measureRuntime() {
        //PointSequence pointSequence = new UniformInBoxPointSequence2D(10000, 100, 100);
//        PointSequence pointSequence = new ConcentricRandomPointSequence(10000, 100);
//        int budget = 16;
//        ConvexHull<Point2D> exactHull = new AndrewsMonotoneChain<>();
//        final Geometry<Point2D> exactHullGeom = exactHull.compute(pointSequence.shuffle());
//        final double exactHullArea = exactHullGeom.getArea();
//        final double exactHullDiameter = exactHullGeom.getDiameter();
        System.out.println("#Points \t k \t Duration");


        int budget = 16;
        for (int n = 1; n < 51; n++) {
            PointSequence pointSequence = new ConcentricRandomPointSequence(n * 1000, 100);

            ConvexHull<Point2D> exactHull = new AndrewsMonotoneChain<>();
            final Geometry<Point2D> exactHullGeom = exactHull.compute(pointSequence.shuffle());
            final double exactHullArea = exactHullGeom.getArea();
            final double exactHullDiameter = exactHullGeom.getDiameter();
            //for (int k = budget; k < 31 + budget; k++) {
            ConvexHull<Point2D> earAreaStreamedHull = new StreamedConvexHull<>(budget, new EarAreaGoodness());
            ConvexHull<Point2D> earHeightStreamedHull = new StreamedConvexHull<>(budget, new EarHeightGoodness());
            double aveAreaError = 0.0;
            double aveDistanceError = 0.0;
            int aveEvictionCount = 0;
            int minEvictionCount = Integer.MAX_VALUE;
            long duration = 0;
            double aveDuration = 0.0;
            for (int i = 0; i < 33; i++) {
                Geometry<Point2D> shuffledGeometry = pointSequence.shuffle();
                long startMilli = System.currentTimeMillis();
                Geometry<Point2D> earAreaStreamHullGeom = earAreaStreamedHull.compute(shuffledGeometry);
                long finishMilli = System.currentTimeMillis();


                final int evictionCount = ((Streaming) earAreaStreamedHull).getEvictionCount();
                double areaError = (exactHullArea - earAreaStreamHullGeom.getArea()) / exactHullArea;

                double distanceError = Math.max(exactHullGeom.getHausdorffDistance(earAreaStreamHullGeom), earAreaStreamHullGeom.getHausdorffDistance(exactHullGeom)) / exactHullDiameter;
                aveAreaError = (i * aveAreaError + areaError) / (i + 1);
                aveDistanceError = (i * aveDistanceError + distanceError) / (i + 1);
                aveEvictionCount = (i * aveEvictionCount + evictionCount) / (i + 1);
                minEvictionCount = Math.min(minEvictionCount, evictionCount);
                duration = finishMilli - startMilli;
                aveDuration = (i * aveDuration + duration) / (i + 1);
            }

            System.out.printf("%d \t%d \t%.3f %n", n * 1000, budget, aveDuration);
//            System.out.printf("%d \t%d \t %.12f \t %d \tArea %n", 2 * (k - budget) + 1, k, aveAreaError, minEvictionCount);
//            System.out.printf("%d \t%d \t %.12f \t %d \tDistance %n", 2 * (k - budget) + 2, k, aveHeightAreaError, minEvictionCount);
        }
    }
}
