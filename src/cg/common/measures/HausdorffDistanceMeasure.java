/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.measures;

import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import java.util.List;

/**
 * Computes an approximation of the Hausdorff distance, using only distances
 * between vertices of the two polygons. True Hausdorff also considers points
 * along the polygon edges.
 *
 * @author I827590
 * @param <T>
 */
public class HausdorffDistanceMeasure<T extends Point> implements DistanceMeasure<T> {

    /**
     * $$h(r_1,r_2)=\max_{a \in r_1}\min_{b\in r_2}\|r_1-r_2\|$$
     *
     * @param geom1
     * @param geom2
     * @return
     */
    private double h(Geometry<T> geom1, Geometry<T> geom2) {

        double distance = 0.0;
        List<T> vs1 = geom1.getVertices();
        List<T> vs2 = geom2.getVertices();

        for (T v1 : vs1) {
            double localDistance = Double.MAX_VALUE;
            for (T v2 : vs2) {
                if (v1.distance(v2) < localDistance) {
                    localDistance = v1.distance(v2);
                }
            }
            if (distance < localDistance) {
                distance = localDistance;
            }
        }

        return distance;
    }

    /**
     * Two sided Hausdorff distance is calculated as
     * $$H(r_1,r_2)=\max\{h(r_1,r_2),h(r_2,r_1)\}$$
     *
     * where
     *
     * $r_1$ and $r_2$ are two non empty, finite sets
     *
     * @param geom1
     * @param geom2
     * @return
     */
    @Override
    public double measure(Geometry<T> geom1, Geometry<T> geom2) {
        return Math.max(h(geom1, geom2), h(geom2, geom1));
    }
}