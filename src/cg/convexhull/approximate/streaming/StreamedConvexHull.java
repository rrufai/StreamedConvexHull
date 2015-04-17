/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.common.comparators.RadialComparator;
import cg.convexhull.exact.ConvexHull;
import cg.convexhull.exact.impl.AndrewsMonotoneChain;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Polygon2D;
import cg.geometry.primitives.impl.Triangle2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 *
 * @author rrufai
 */
public class StreamedConvexHull<T extends Point> implements ConvexHull<T>, Streaming<T> {

    private int budget; // maximum number of points allowed to be kept in memory (memory budget).
    private int count = 0; // number of streamed points seen so far.
    private ConvexHull convexHull;
    private List<T> inputPoints;
    private TreeMap<Double, StreamedPoint2D<T>> heightBalancedTree = new TreeMap<>();
    private PriorityQueue<StreamedPoint2D<T>> minHeap;
    private T centroid;
    private int centroidDirtyCount = 0;

    public StreamedConvexHull(int budget) {
        this.budget = budget;
        this.convexHull = new AndrewsMonotoneChain();
        inputPoints = new ArrayList<>();
        this.minHeap = new PriorityQueue<>(budget, new DogEarComparator());
    }

    /**
     *
     * @param points
     * @return
     */
    @Override
    public Geometry<T> compute(List<T> points) {
        for (T point : points) {
            process(point);
        }

        return query();
    }

    @Override
    public Geometry<T> compute(Geometry<T> geom) {
        List<T> points = geom.getVertices();

        return compute(points);
    }

    @Override
    public Geometry<T> compute() {
        throw new UnsupportedOperationException("Unsupported Method!");
    }

    private static class DogEarComparator<S extends StreamedPoint2D<? extends Point>> implements Comparator<S> {

        @Override
        public int compare(S p1, S p2) {
            return (int) Math.signum(p1.getGoodnessMeasure() - p2.getGoodnessMeasure());
        }
    }

    /**
     *
     * @param geometry
     */
    @Override
    public void initialize(List<T> geometry) {
        count = geometry.size();
        processInitialPoints(geometry);
    }

    @Override
    public void update(T point) {
        count++;
        StreamedPoint2D streamedPoint = new StreamedPoint2D(point, StreamedConvexUtility.polar(centroid, point));
        updateHull(streamedPoint);

        // lemma: heightBalancedTree.size() <= budget + 1
        if (heightBalancedTree.size() > budget) {
            shrinkHull();
        }
    }

    private void updateHull(StreamedPoint2D<T> q) {
        StreamedPoint2D<T> p = predecessor(q);
        StreamedPoint2D<T> r = successor(q);
        List<Double> keysToRemove = new ArrayList<>();
        if (p != null & r != null) {
            Triangle2D<Point> triangle = new Triangle2D<>(p, r, centroid); // does not always work
            //Geometry<T> triangle = query();
            if (!triangle.contains((T) q)) {
                // point is exterior
                Entry<StreamedPoint2D<T>, StreamedPoint2D<T>> tangents = getTangents(q, p, r);
                p = tangents.getKey();
                r = tangents.getValue();
                //Note: p and q are the tangents, so they're guaranteed to exist in T,
                // but their dogears will have to be recomputed!
                double predTangentKey = p.getPolar();
                double succTangentKey = r.getPolar();
                if (predTangentKey < succTangentKey) { // stale vertices form a single chain in T
                    NavigableMap<Double, StreamedPoint2D<T>> subMap = heightBalancedTree.subMap(predTangentKey, false, succTangentKey, false);
                    for (Entry<Double, StreamedPoint2D<T>> entry : subMap.entrySet()) {
                        keysToRemove.add(entry.getKey());
                        entry.getValue().mark();
                    }
                } else { // stale vertices must be deleted as two separate chains
                    NavigableMap<Double, StreamedPoint2D<T>> subMap = heightBalancedTree.tailMap(predTangentKey, false);
                    for (Entry<Double, StreamedPoint2D<T>> entry : subMap.entrySet()) {
                        keysToRemove.add(entry.getKey());
                        entry.getValue().mark();
                    }

                    subMap = heightBalancedTree.headMap(succTangentKey, false);
                    for (Entry<Double, StreamedPoint2D<T>> entry : subMap.entrySet()) {
                        keysToRemove.add(entry.getKey());
                        entry.getValue().mark();
                    }
                }

                for (Double key : keysToRemove) {
                    heightBalancedTree.remove(key);
                }

                //recompute dogEars for p, q, and r. And then insert q into both T and heap
                q.setGoodnessMeasure(StreamedConvexUtility.area(p, q, r));
                p.setGoodnessMeasure(StreamedConvexUtility.area(predecessor(p), p, q));
                r.setGoodnessMeasure(StreamedConvexUtility.area(q, r, successor(r)));
                heightBalancedTree.put(q.getPolar(), q);

                minHeap.add(q);

                StreamedPoint2D<T> vertex = minHeap.peek();
                while (vertex != null && vertex.isMarked()) {
                    vertex = minHeap.poll();
                }
            }
        }
    }

    /**
     * Search for tangent for q, using p as the starting point for searching
     * left-wards, and r as starting point to search right-wards.
     *
     * @param q
     * @param p
     * @param r
     * @return
     */
    private Entry<StreamedPoint2D<T>, StreamedPoint2D<T>> getTangents(StreamedPoint2D<T> q, StreamedPoint2D<T> pred, StreamedPoint2D<T> succ) {
        pred = leftSearch(q, pred);
        succ = rightSearch(q, succ);

        return new AbstractMap.SimpleEntry<>(pred, succ);
    }

    private StreamedPoint2D<T> leftSearch(StreamedPoint2D<T> q, StreamedPoint2D<T> p) {
        StreamedPoint2D<T> l = predecessor(p);

        while (RadialComparator.relativeCCW(q, p, l) != RadialComparator.Orientation.CLOCKWISE.getCode()) {
            p = l;
            l = predecessor(l);
        }

        return p;
    }

    private StreamedPoint2D<T> rightSearch(StreamedPoint2D<T> q, StreamedPoint2D<T> r) {
        StreamedPoint2D<T> l = successor(r);

        while (RadialComparator.relativeCCW(q, r, l) != RadialComparator.Orientation.COUNTERCLOCKWISE.getCode()) {
            r = l;
            l = successor(l);
        }

        return r;
    }

    //assert: heightBalancedTree.size == budget + 1
    private void shrinkHull() {
        StreamedPoint2D<T> vertex = null;
        do {
            vertex = minHeap.poll();
        } while (vertex != null && vertex.isMarked());

        if (vertex != null) {
            heightBalancedTree.remove(vertex.getPolar());
        }
        //lemma: the only operation that can potentially cause the 
        // centroid to become external to the hull interior is the shrinkHull?
        //Note: recomputing centroids only affects polar angles. Dogears are not 
        //affected, so only T (or heightBalancedTree) needs updating.
        recomputeCentroid();
    }

    @Override
    public Geometry<T> query() {
        return (Geometry<T>) new Polygon2D<>(heightBalancedTree.values());
    }

    @Override
    public void process(T point) {
        inputPoints.add(point);
        int size = inputPoints.size();
        if (size == budget) {
            initialize(inputPoints);
        } else if (size > budget) {
            update(point);
        }
        updateCentroidDirtyCounter();
    }

    private void processInitialPoints(List<T> inputPoints) {
        Geometry<T> geometry = new Polygon2D<>(inputPoints);
        Geometry<T> L = convexHull.compute(geometry);
        centroid = L.getCentroid();

        for (T p : L.getVertices()) {
            StreamedPoint2D<T> streamedPoint = new StreamedPoint2D<>(p);
            streamedPoint.setPolar(StreamedConvexUtility.polar(centroid, p));
            streamedPoint.setGoodnessMeasure(StreamedConvexUtility.area(L.getPredecessor(p), p, L.getSuccessor(p)));

            // add the point to treemap  - RB tree
            heightBalancedTree.put(streamedPoint.getPolar(), streamedPoint);
            // add the point to heap  - priority queue map : http://docs.oracle.com/javase/6/docs/api/java/util/PriorityQueue.html
            minHeap.add(streamedPoint);
        }

    }

    private StreamedPoint2D<T> successor(StreamedPoint2D<T> r) {
        Entry<Double, StreamedPoint2D<T>> higherEntry = heightBalancedTree.higherEntry(r.getPolar());
        if (higherEntry == null) {
            higherEntry = heightBalancedTree.firstEntry();
        }

        return higherEntry.getValue();
    }

    private StreamedPoint2D<T> predecessor(StreamedPoint2D<T> p) {
        Entry<Double, StreamedPoint2D<T>> lowerEntry = heightBalancedTree.lowerEntry(p.getPolar());
        if (lowerEntry == null) {
            lowerEntry = heightBalancedTree.lastEntry();
        }

        return lowerEntry.getValue();
    }

    //Note: recomputing centroids only affects polar angles. Dogears are not 
    //affected, so only T (or heightBalancedTree) needs updating.
    private void recomputeCentroid() {
        if (centroidDirtyCount > 0) {
            //recompute centroid and update dogEars
            Geometry<StreamedPoint2D<T>> geometry = new Polygon2D<>(heightBalancedTree.values());
            List<StreamedPoint2D<T>> insertionList = new ArrayList<>(budget);
            List<Double> deletionKeyList = new ArrayList<>(budget);
            centroid = (T) geometry.getCentroid();

            for (Entry<Double, StreamedPoint2D<T>> entry : heightBalancedTree.entrySet()) {
                deletionKeyList.add(entry.getKey());
                StreamedPoint2D<T> refreshedPoint = entry.getValue();
                refreshedPoint.setPolar(StreamedConvexUtility.polar(centroid, refreshedPoint));
                insertionList.add(refreshedPoint);
            }

            for (Double key : deletionKeyList) {
                heightBalancedTree.remove(key);
            }

            for (StreamedPoint2D<T> p : insertionList) {
                heightBalancedTree.put(p.getPolar(), p);
            }

            centroidDirtyCount = 0;
        }
    }

    private void updateCentroidDirtyCounter() {
        if (count % budget == 0) {
            ++centroidDirtyCount;
        }
    }
//    private void shrinkHeap() {
//        for (Iterator<StreamedPoint2D<T>> it = minHeap.iterator(); it.hasNext();) {
//            StreamedPoint2D<T> p = it.next();
//            if (p.isMarked()) {
//                it.remove();
//            }
//        }
//    }
}
