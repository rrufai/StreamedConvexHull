/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cg.convexlayers.events;

import cg.geometry.primitives.Point;



/**
 *
 * @author rrufai
 */
public interface Event<K extends Point> extends Point{

    K getBasePoint();

}
