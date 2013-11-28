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
    /**
     * Random in a rectangle constructor
     */
    public UniformInBoxPointSequence2D(int size, double width, double height) {
        super((List<T>) generateRandomPoints(size, width, height));
    }

    private static <T extends Point> List<T> generateRandomPoints(int size, double width, double height) {
        List<T> adversarialPointset = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            double x = (random.nextBoolean() ? 0.5 : -0.5) * width * random.nextDouble();
            double y = (random.nextBoolean() ? 0.5 : -0.5) * height * random.nextDouble();

            adversarialPointset.add((T) new Point2D(x, y));
        }

        return adversarialPointset;
    }
}
