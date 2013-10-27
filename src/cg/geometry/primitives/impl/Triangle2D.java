/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.geometry.primitives.Point;

/**
 * A 2D triangle which is also a shape.
 */
public class Triangle2D<T extends Point> extends Polygon2D<T> {

    public Triangle2D(T p1, T p2, T p3) {
        super(p1, p2, p3);
    }
}