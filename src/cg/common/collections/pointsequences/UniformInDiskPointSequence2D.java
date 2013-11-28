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

public class UniformInDiskPointSequence2D<T extends Point> extends AbstractPointSequence<T> {

    protected static Random random = new Random();

    /**
     * Random in a circle constructor
     */
    public UniformInDiskPointSequence2D(int size, double radius) {
        super((List<T>) generateRandomPoints(size, radius));
    }

    /**
     * Generates a
     * <code>size</code> random points distributed uniformly within a disk of
     * radius
     * <code>radius</code> centered at the origin.
     *
     * @param <T>
     * @param size
     * @param radius
     * @return
     */
    private static <T extends Point> List<T> generateRandomPoints(int size, double radius) {
        List<T> adversarialPointset = new ArrayList<>(size);
        //double angle = Math.PI;
        for (int i = 0; i < size; i++) {
            double angle = 2 * Math.PI * random.nextDouble();
            double distance = (random.nextBoolean() ? 1 : -1) * random.nextDouble() * radius;

            adversarialPointset.add((T) new Point2D(
                    distance * Math.cos(angle), radius * Math.sin(angle)));
        }

        return adversarialPointset;
    }
}
