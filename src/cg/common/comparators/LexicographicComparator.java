/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.comparators;

import cg.geometry.primitives.Point;

/**
 *
 * @author rrufai
 */
public class LexicographicComparator<K extends Point> implements GeometricComparator<K> {

    protected static final long serialVersionUID = 0L;
    protected Direction order = Direction.LEFT_TO_RIGHT;

    public LexicographicComparator(Direction order) {
        super();
        this.order = order;
    }

    public LexicographicComparator() {
        super();
    }

    /**
     * Compares two operands.
     *
     * @param o1
     * @param o2
     * @return 0 when o1 == o2, -1 when o1 < o2, +1 when o1 > o2
     */
    @Override
    public int compare(K o1, K o2) {
        int returnValue = 0;
        switch (order) {
            case TOP_DOWN:
                returnValue = compareTopDown(o1, o2);
                break;
            case BOTTOM_UP:
                returnValue = compareBottomUp(o1, o2);
                break;
            case LEFT_TO_RIGHT:
                returnValue = compareLeftToRight(o1, o2);
                break;

            case RIGHT_TO_LEFT:
                returnValue = compareRightToLeft(o1, o2);

        }
        return returnValue;
    }

    /**
     * Comparison function to be used to sort from Bottom to Top. So, p1 is
     * greater if it lies below p2.
     *
     * @param p1
     * @param p2
     * @return
     */
    protected int compareBottomUp(K p1, K p2) {
        double d = p1.getY() - p2.getY();
        int retVal = LexicographicComparator.Orientation.COLLINEAR.getCode();
        if (d > EPSILON) {
            //greater
            retVal = LexicographicComparator.Orientation.COUNTERCLOCKWISE.getCode();
        } else if (d < -EPSILON) {
            retVal = LexicographicComparator.Orientation.CLOCKWISE.getCode();
        } else {
            //same Y values
            d = p2.getX() - p1.getX();
            if (d > EPSILON) {
                //greater
                retVal = LexicographicComparator.Orientation.COUNTERCLOCKWISE.getCode();
            } else if (d < -EPSILON) {
                retVal = LexicographicComparator.Orientation.CLOCKWISE.getCode();
            } else {
                //same X and Y values
                //retVal = 0;
            }
        }
        return retVal;
    }

    protected int compareTopDown(K o1, K o2) {
        return -compareBottomUp(o1, o2);
    }

    /**
     * Comparison function to compare left to right. So, a point p1 is LESS
     * THAN point p2 if p1 lies to the LEFT of p2.
     *
     * @param p1
     * @param p2
     * @return
     */
    protected int compareLeftToRight(K p1, K p2) {
        int retVal = LexicographicComparator.Orientation.COLLINEAR.getCode();
        double d = p1.getX() - p2.getX();
        if (d > EPSILON) {
            //greater
            retVal = LexicographicComparator.Orientation.COUNTERCLOCKWISE.getCode();
        } else if (d < -EPSILON) {
            retVal = LexicographicComparator.Orientation.CLOCKWISE.getCode();
        } else {
            //same X values
            d = p1.getY() - p2.getY();
            if (d > EPSILON) {
                //greater
                retVal = LexicographicComparator.Orientation.COUNTERCLOCKWISE.getCode();
            } else if (d < -EPSILON) {
                retVal = LexicographicComparator.Orientation.CLOCKWISE.getCode();
            } else {
                //same X and Y values
                //retVal = 0;
            }
        }
        return retVal;
    }

    protected int compareRightToLeft(K o1, K o2) {
        return -compareLeftToRight(o1, o2);
    }

    public static enum Direction {

        BOTTOM_UP, TOP_DOWN,
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }
}
