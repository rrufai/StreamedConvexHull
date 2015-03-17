/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers;


import cg.geometry.primitives.Point;
import cg.geometry.primitives.Polygon;
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
    List<Polygon<K>> compute();

    /**
     * 
     * @return the set of points whose convex layers is computed
     */
    List<K> getPointset();

    /**
     * @param pointset the set of points whose convex layers is sought
     */
    void setPointset(List<K> pointset);
    
}
