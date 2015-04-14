/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;

import cg.common.collections.convexchain.ConvexLayersIntervalTree;
import cg.common.collections.convexchain.ConvexLayersIntervalTreeImpl;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.Polygon;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<List<K>> layersNW = new ArrayList<>();
        while (!intervalTreeNW.isEmpty()) {
            List<K> layer = intervalTreeNW.extractRoot();

            layersNW.add(layer);
        }

        List<List<K>> layersNE = new ArrayList<>();
        while (!intervalTreeNE.isEmpty()) {
            List<K> layer = intervalTreeNE.extractRoot();
            layer = rotate(layer, -Math.PI / 2);
            layersNE.add(layer);
        }

        List<List<K>> layersSE = new ArrayList<>();
        while (!intervalTreeSE.isEmpty()) {
            List<K> layer = intervalTreeSE.extractRoot();
            layer = rotate(layer, -3 * Math.PI / 2);
            layersSE.add(layer);
        }

        List<List<K>> layersSW = new ArrayList<>();
        while (!intervalTreeSW.isEmpty()) {
            List<K> layer = intervalTreeSW.extractRoot();
            layer = rotate(layer, -Math.PI);
            layersSW.add(layer);
        }

        System.out.println("layersNW" + layersNW);
        System.out.println("LayersNE" + layersNE);
        System.out.println("layersSE" + layersSE);
        System.out.println("layersSW" + layersSW);


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

    private List<Polygon<K>> mergeLayers(List<List<K>> layersNE, List<List<K>> layersNW, List<List<K>> layersSW, List<List<K>> layersSE) {
        List<Polygon<K>> layers = new ArrayList<>();
        Set<K> marked = new HashSet<>();

        int numberLayers = Math.min(Math.min(layersNE.size(), layersNW.size()), Math.min(layersSW.size(), layersSE.size()));

        for (int i = 0; i < numberLayers; i++) {
            List<K> currentLayer = new ArrayList<>();

            for (K p : layersNW.get(i)) {
                if (!marked.contains(p)) {
                    currentLayer.add(p);
                    marked.add(p);
                }
            }

            for (K p : layersNE.get(i)) {
                if (!marked.contains(p)) {
                    currentLayer.add(p);
                    marked.add(p);
                }
            }

            for (K p : layersSE.get(i)) {
                if (!marked.contains(p)) {
                    currentLayer.add(p);
                    marked.add(p);
                }
            }

            for (K p : layersSW.get(i)) {
                if (!marked.contains(p)) {
                    currentLayer.add(p);
                    marked.add(p);
                }
            }

            System.out.println(layerToString(i, layersNW, "layersNW"));
            System.out.println(layerToString(i, layersNE, "layersNE"));
            System.out.println(layerToString(i, layersSE, "layersSE"));
            System.out.println(layerToString(i, layersSW, "layersSW"));
            
            layers.add(new Polygon2D<>(currentLayer));
        }

        return layers;
    }

    private void initializeTrees(List<K> pointset) {
        intervalTreeNW = new ConvexLayersIntervalTreeImpl<>(pointset);


        List<K> rotatedPointset90 = rotate(pointset, Math.PI / 2);
        intervalTreeNE = new ConvexLayersIntervalTreeImpl<>(rotatedPointset90);

        List<K> rotatedPointset180 = rotate(pointset, Math.PI);
        intervalTreeSW = new ConvexLayersIntervalTreeImpl<>(rotatedPointset180);

        List<K> rotatedPointset270 = rotate(pointset, 3 * Math.PI / 2);
        intervalTreeSE = new ConvexLayersIntervalTreeImpl<>(rotatedPointset270);

        System.out.println("intervalTreeNW:\n" + intervalTreeNW);
        System.out.println("intervalTreeNE:\n" + intervalTreeNE);

        System.out.println("intervalTreeSW:\n" + intervalTreeSW);
        System.out.println("intervalTreeSE:\n" + intervalTreeSE);
    }

    private List<K> rotate(List<K> pointset, double angle) {
        List<K> rotatedPointset = new ArrayList<>(pointset.size());

        for (K p : pointset) {
            rotatedPointset.add((K) p.rotate(angle));
        }

        return rotatedPointset;
    }

    private String layerToString(int i, List<List<K>> layersNW, String name) {
        return name + "[" + i + "]: " + (i < layersNW.size()? layersNW.get(i): "");
    }
}
