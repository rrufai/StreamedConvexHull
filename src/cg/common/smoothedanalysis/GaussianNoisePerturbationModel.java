/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.smoothedanalysis;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author rrufai
 */
public class GaussianNoisePerturbationModel implements PerturbationModel<Point2D.Double, Double> {

    Random random;
    Map<String, Double> map;

    /**
     *
     */
    public GaussianNoisePerturbationModel() {
        random = new Random();
        map = new HashMap();
    }

    /**
     *
     * @param name
     * @param value
     */
    @Override
    public void setParameter(String name, Double value) {
        map.put(name, value);
    }

    /**
     *
     * @param name
     * @param value
     */
    @Override
    public void setParameter(String name, String value) {
        map.put(name, Double.valueOf(value));
    }

    /**
     *
     * @param data
     * @return
     */
    @Override
    public List<Point2D.Double> perturb(List<Point2D.Double> data) {
        List<Point2D.Double> newData = new ArrayList(data.size());
        double windowWidth = map.get("windowWidthRatio") * map.get("radius"); //standard deviation
        for (Point2D.Double point : data) {
            Point2D.Double newPoint = (Point2D.Double) point.clone();
            newPoint.setLocation(point.x + random.nextGaussian() * windowWidth,
                    point.y + random.nextGaussian() * windowWidth);
            newData.add(newPoint);
        }

        return newData;
    }

    /**
     *
     * @param data
     * @param parameters
     * @return
     */
    @Override
    public List<Point2D.Double> perturb(List<Point2D.Double> data, Map<String, Double> parameters) {
        map = parameters;
        return perturb(data);
    }
}
