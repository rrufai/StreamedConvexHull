/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;


import cg.geometry.primitives.Point;
import java.util.List;

/**
 *
 * @author rrufai
 */
public interface ConvexLayers<K extends Point> {

    /**
     * Returns the convex layers of this object's pointset. The method will return
     * a cached copy if pointset has not changed since the last invocation.
     *
     * @return
     */
    List<List<K>> compute();

    List<K> getPointset();

    /**
     * @param pointset the point set to set
     */
    void setPointset(List<K> pointset);
    
}
