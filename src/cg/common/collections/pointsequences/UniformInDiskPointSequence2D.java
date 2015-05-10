/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.pointsequences;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.Random;

public class UniformInDiskPointSequence2D<T extends Point> extends AbstractPointSequence<T> {

    protected Random random = new Random();
    private final double radius;

    /**
     * Random in a circle constructor
     */
    public UniformInDiskPointSequence2D(int size, double radius) {
        super(size);
        this.radius = radius;
    }

    @Override
    public T newPoint() {
        double angle = 2 * Math.PI * random.nextDouble();
        double distance = (random.nextBoolean() ? 1 : -1) * random.nextDouble() * radius;
        return (T) new Point2D(
                distance * Math.cos(angle), radius * Math.sin(angle));
    }
}
