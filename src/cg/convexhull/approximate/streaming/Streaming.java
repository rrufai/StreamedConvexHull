/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.Point;
import java.util.List;

/**
 *
 * @author rrufai
 */
//@Invariant("true")
interface Streaming<T extends Point> {

    void initialize(List<T> geometry);

    void update(T point);

    void process(T point);

    Geometry<T> query();
}
