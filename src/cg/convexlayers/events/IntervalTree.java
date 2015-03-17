/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers.events;

import cg.common.collections.convexchain.ConvexChain;
import cg.geometry.primitives.Point;
import java.util.List;

/**
 *
 * @author rrufai@gmu.edu
 */
public interface IntervalTree<K extends Point> {

    int getLevel();

    boolean insert(ConvexChain<K> chain);

    boolean insert(K point);

    boolean isEmpty();

    List<K> extractHull();

    void delete(ConvexChain<K> chain);

    ConvexChain<K> getHullChain();

    boolean add(K point);

    void delete(K point);
}
