/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;

import cg.common.collections.convexchain.ConvexLayersIntervalTree;
import cg.common.collections.convexchain.ConvexLayersIntervalTreeImpl;
import cg.common.comparators.LexicographicComparator;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.Polygon;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author I827590
 */
public class ConvexLayersPseudoIntervalTreePeeling<K extends Point> implements ConvexLayers<K> {

    private List<K> pointset;
    private ConvexLayersIntervalTree<K> intervalTreeNE;
    private ConvexLayersIntervalTree<K> intervalTreeNW;
    private ConvexLayersIntervalTree<K> intervalTreeSE;
    private ConvexLayersIntervalTree<K> intervalTreeSW;

    public ConvexLayersPseudoIntervalTreePeeling(List<K> pointset) {
        this.pointset = pointset;
    }

    public ConvexLayersPseudoIntervalTreePeeling() {
        this.pointset = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Polygon<K>> compute() {

        initializeTrees(pointset);

        List<Polygon<K>> layersNW = new ArrayList<>();
        while (!intervalTreeNW.isEmpty()) {
            List<K> layer = intervalTreeNW.extractRoot();

            layersNW.add(new Polygon2D<>(layer));
        }
        System.out.println("layersNW" + layersNW);

        List<Polygon<K>> layersNE = new ArrayList<>();
//        while (!intervalTreeNE.isEmpty()) {
//            List<K> layer = intervalTreeNE.extractRoot();
//
//            layersNE.add(new Polygon2D<>(layer));
//        }
//
//        System.out.println("LayersNE" + layersNE);
//
        List<Polygon<K>> layersSW = new ArrayList<>();
//        while (!intervalTreeSW.isEmpty()) {
//            List<K> layer = intervalTreeSW.extractRoot();
//
//            layersSW.add(new Polygon2D<>(layer));
//        }
//        System.out.println("layersSW" + layersSW);

        List<Polygon<K>> layersSE = new ArrayList<>();
//        while (!intervalTreeSE.isEmpty()) {
//            List<K> layer = intervalTreeSE.extractRoot();
//
//            layersSE.add(new Polygon2D<>(layer));
//        }
//        System.out.println("layersSE" + layersSE);

        List<Polygon<K>> layers = mergeLayers(layersNE, layersNW, layersSW, layersSE);
        return layers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<K> getPointset() {
        return pointset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPointset(List<K> pointset) {
        this.pointset = pointset;
    }

    private List<Polygon<K>> mergeLayers(List<Polygon<K>> layersNE, List<Polygon<K>> layersNW, List<Polygon<K>> layersSW, List<Polygon<K>> layersSE) {
        return layersNW;
    }

    private void initializeTrees(List<K> pointset) {
        intervalTreeNW = new ConvexLayersIntervalTreeImpl<>(pointset,
                new LexicographicComparator(LexicographicComparator.Direction.BOTTOM_UP),
                new LexicographicComparator(LexicographicComparator.Direction.LEFT_TO_RIGHT));

//        intervalTreeNE = new ConvexLayersIntervalTreeImpl<>(pointset,
//                new LexicographicComparator(LexicographicComparator.Direction.TOP_DOWN),
//                new LexicographicComparator(LexicographicComparator.Direction.RIGHT_TO_LEFT));
//
//        intervalTreeSW = new ConvexLayersIntervalTreeImpl<>(pointset,
//                new LexicographicComparator(LexicographicComparator.Direction.TOP_DOWN),
//                new LexicographicComparator(LexicographicComparator.Direction.LEFT_TO_RIGHT));
//
//        intervalTreeSE = new ConvexLayersIntervalTreeImpl<>(pointset,
//                new LexicographicComparator(LexicographicComparator.Direction.BOTTOM_UP),
//                new LexicographicComparator(LexicographicComparator.Direction.RIGHT_TO_LEFT));

        //System.out.println("intervalTreeNE:\n" + intervalTreeNE);
        System.out.println("intervalTreeNW:\n" + intervalTreeNW);
        //System.out.println("intervalTreeSW:\n" + intervalTreeSW);
        //System.out.println("intervalTreeSE:\n" + intervalTreeSE);
    }
}
