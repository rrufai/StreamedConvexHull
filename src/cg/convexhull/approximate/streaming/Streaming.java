/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import cg.geometry.primitives.Geometry;

/**
 *
 * @author rrufai
 */
interface Streaming {
    Geometry initialize(Geometry geometry);
    
    Geometry update(Geometry geometry);
}
