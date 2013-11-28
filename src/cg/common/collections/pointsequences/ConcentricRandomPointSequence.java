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

/**
 *
 * @author I827590
 */
public class ConcentricRandomPointSequence<T extends Point> extends AbstractPointSequence<T> {

    private static Random random;

    /**
     * Random in a circle constructor
     */
    public ConcentricRandomPointSequence(int size, double radius) {
        super((List<T>) generateConcentricRandomPointSequence(size, radius));
        random = new Random();
    }

    private static <T extends Point> List<T> generateConcentricRandomPointSequence(int size, double radius) {
        List<T> adversarialPointset = new ArrayList<>(size);
        //double angle = Math.PI;
        for (int i = 0; i < size; i++) {
            double angle = 2 * Math.PI * random.nextDouble();

            adversarialPointset.add((T) new Point2D(
                    radius * Math.cos(angle), radius * Math.sin(angle)));
            //angle /= 2;
        }

        return adversarialPointset;
    }
}
