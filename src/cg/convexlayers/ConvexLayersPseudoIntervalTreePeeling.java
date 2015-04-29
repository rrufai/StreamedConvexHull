/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;

import cg.common.collections.convexchain.ConvexLayersIntervalTree;
import cg.common.collections.convexchain.ConvexLayersIntervalTreeImpl;
import cg.convexlayers.ui.actions.Configuration;
import cg.geometry.primitives.Point;
import cg.geometry.primitives.Polygon;
import cg.geometry.primitives.impl.Polygon2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Logger.getAnonymousLogger().setLevel(Configuration.getInstance().getLogLevel());
    }

    public ConvexLayersPseudoIntervalTreePeeling() {
        this.pointset = new ArrayList<>();
        Logger.getAnonymousLogger().setLevel(Configuration.getInstance().getLogLevel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Polygon<K>> compute() {

        initializeTrees(pointset);

        List<Polygon<K>> layers = mergeLayers(intervalTreeNW, intervalTreeNE, intervalTreeSE, intervalTreeSW);
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

    private List<Polygon<K>> mergeLayers(ConvexLayersIntervalTree<K> intervalTreeNW,
            ConvexLayersIntervalTree<K> intervalTreeNE,
            ConvexLayersIntervalTree<K> intervalTreeSE,
            ConvexLayersIntervalTree<K> intervalTreeSW) {
        List<Polygon<K>> layers = new ArrayList<>();
        Set<K> marked = new HashSet<>();

        while (marked.size() < pointset.size()) {
            List<K> currentLayer = new ArrayList<>();

            for (K p : marked) {
                intervalTreeNW.delete(p);
                intervalTreeNE.delete((K) p.rotate(90));
                intervalTreeSE.delete((K) p.rotate(180));
                intervalTreeSW.delete((K) p.rotate(270));
            }

            List<K> layersNW = intervalTreeNW.extractRoot();
            for (K p : layersNW) {
                if (!marked.contains(p)) {
                    currentLayer.add(p);
                    marked.add(p);
                }
            }


            List<K> layersNE = unrotate(intervalTreeNE.extractRoot(), 90);
            for (K p : layersNE) {
                if (!marked.contains(p)) {
                    currentLayer.add(p);
                    marked.add(p);
                }
            }


            List<K> layersSE = unrotate(intervalTreeSE.extractRoot(), 180);
            for (K p : layersSE) {
                if (!marked.contains(p)) {
                    currentLayer.add(p);
                    marked.add(p);
                }
            }

            List<K> layersSW = unrotate(intervalTreeSW.extractRoot(), 270);
            for (K p : layersSW) {
                if (!marked.contains(p)) {
                    currentLayer.add(p);
                    marked.add(p);
                }
            }

            layers.add(new Polygon2D<>(currentLayer));

        }

        return layers;
    }

    private void initializeTrees(List<K> pointset) {
        intervalTreeNW = new ConvexLayersIntervalTreeImpl<>(pointset);

        List<K> rotatedPointset90 = rotate(pointset, 90);
        intervalTreeNE = new ConvexLayersIntervalTreeImpl<>(rotatedPointset90);

        List<K> rotatedPointset180 = rotate(pointset, 180);
        intervalTreeSE = new ConvexLayersIntervalTreeImpl<>(rotatedPointset180);

        List<K> rotatedPointset270 = rotate(pointset, 270);
        intervalTreeSW = new ConvexLayersIntervalTreeImpl<>(rotatedPointset270);


        Logger.getAnonymousLogger().log(Configuration.getInstance().getLogLevel(), "intervalTreeNW:\n{0}", intervalTreeNW);
        Logger.getAnonymousLogger().log(Configuration.getInstance().getLogLevel(), "intervalTreeNE:\n{0}", intervalTreeNE);

        Logger.getAnonymousLogger().log(Configuration.getInstance().getLogLevel(), "intervalTreeSW:\n{0}", intervalTreeSW);
        Logger.getAnonymousLogger().log(Configuration.getInstance().getLogLevel(), "intervalTreeSE:\n{0}", intervalTreeSE);
    }

    private List<K> rotate(List<K> pointset, int angle) {
        List<K> rotatedPointset = new ArrayList<>(pointset.size());

        for (K p : pointset) {
            rotatedPointset.add((K) p.rotate(angle));
        }

        return rotatedPointset;
    }

    private List<K> unrotate(List<K> pointset, int angle) {
        List<K> rotatedPointset = new ArrayList<>(pointset.size());

        for (K p : pointset) {
            rotatedPointset.add((K) p.unrotate(angle));
        }

        return rotatedPointset;
    }

    private String layerToString(int i, List<List<K>> layersNW, String name) {
        return name + "[" + i + "]: " + (i < layersNW.size() ? layersNW.get(i) : "");
    }
}
