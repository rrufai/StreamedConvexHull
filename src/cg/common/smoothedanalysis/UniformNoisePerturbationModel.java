/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.smoothedanalysis;

import cg.geometry.primitives.impl.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author rrufai
 */
public class UniformNoisePerturbationModel implements PerturbationModel<Point2D, Double> {

    Random random;
    Map<String, Double> map;

    /**
     *
     */
    public UniformNoisePerturbationModel() {
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
    public List<Point2D> perturb(List<Point2D> data) {
        List<Point2D> newData = new ArrayList(data.size());
        double windowWidth = map.get("windowWidthRatio") * map.get("radius");
        for (Point2D point : data) {
            Point2D newPoint = new Point2D(point.getX() + (random.nextDouble() - 0.5) * windowWidth, 
                    point.getY() + (random.nextDouble() - 0.5)  * windowWidth);
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
    public List<Point2D> perturb(List<Point2D> data, Map<String, Double> parameters) {
        map = parameters;
        return perturb(data);
    }
}
