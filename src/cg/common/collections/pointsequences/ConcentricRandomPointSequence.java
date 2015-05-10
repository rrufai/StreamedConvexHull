/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.pointsequences;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.Random;

/**
 *
 * @author I827590
 */
public class ConcentricRandomPointSequence<T extends Point> extends AbstractPointSequence<T> {

    private Random random = new Random();
    private double radius;

    /**
     * Random in a circle constructor
     */
    public ConcentricRandomPointSequence(int size, double radius) {
        super(size);
        this.radius = radius;
    }


    @Override
    public T newPoint() {
        double angle = 2 * Math.PI * random.nextDouble();
        return (T) new Point2D(
                radius * Math.cos(angle), radius * Math.sin(angle));
    }
}
