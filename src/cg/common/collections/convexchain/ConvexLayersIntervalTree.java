/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.convexchain;

import cg.geometry.primitives.Point;
import java.util.List;

/**
 *
 * @author I827590
 */
public interface ConvexLayersIntervalTree<K extends Point> {

    List<K> extractRoot();

    boolean goRight(K point, int level);
    
    boolean isEmpty();
}
