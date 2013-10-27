/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.comparators;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author rrufai
 */
public interface GeometricComparator<K> extends Comparator<K>, Serializable {

    final double EPSILON = 1.0E-9;

    /**
     *
     */
    public enum Orientation {
        CLOCKWISE(-1), COUNTERCLOCKWISE(1), COLLINEAR(0);
        private int code;

        private Orientation(int c) {
            code = c;
        }

        public int getCode() {
            return code;
        }
    }
}
