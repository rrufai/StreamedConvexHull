/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.collections.pointsequences;

import cg.geometry.primitives.Point;
import cg.geometry.primitives.impl.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformInBoxPointSequence2D<T extends Point> extends AbstractPointSequence<T> {

    protected static Random random = new Random();
    private double width;
    private double height;

    /**
     * Random in a rectangle constructor
     */
    public UniformInBoxPointSequence2D(int size, double width, double height) {
        super(size);
        this.width = width;
        this.height = height;
    }


    @Override
    public T newPoint() {
        double x = (random.nextBoolean() ? 0.5 : -0.5) * width * random.nextDouble();
        double y = (random.nextBoolean() ? 0.5 : -0.5) * height * random.nextDouble();
        return (T) new Point2D(x, y);
    }
}
