package cg.common.comparators;

import cg.geometry.primitives.Point;

/**
 *
 * @author rrufai
 * @param <K>
 */
public class RadialComparator<K extends Point> implements GeometricComparator<K> {

    private K anchor;

    /**
     * No argument constructor.
     */
    public RadialComparator() {
    }

    /**
     *
     * @param anchor
     */
    public RadialComparator(K anchor) {
        this.anchor = anchor;
    }

    /**
     * Three points are a counter-clockwise turn if ccw &gt; 0, clockwise if ccw
     * &lt; 0, and collinear if ccw = 0 because ccw is a determinant that gives
     * twice the signed area of the triangle formed by p1, p2, and p3.
     *
     * @param p1
     * @param p2
     * @param p3
     * @return twice the signed area of the triangle formed by p1, p2, and p3
     */
    public double ccw(K p1, K p2, K p3) {
        double b1 = (p2.getX() - p1.getX())
                * (p3.getY() - p1.getY());

        double b2 = (p2.getY() - p1.getY())
                * (p3.getX() - p1.getX());

        return b1 - b2;
    }

    @Override
    public int compare(K o1, K o2) {
        return compare(anchor, o1, o2);
    }

    private int compare( K anchor, K o1, K o2) {
        double d = ccw(anchor, o1, o2);
        int retVal = Orientation.COLLINEAR.getCode();

        if (d > EPSILON) {
            //CCW
            retVal = Orientation.COUNTERCLOCKWISE.getCode();
        } else if (d < -EPSILON) {
            //CW
            retVal = Orientation.CLOCKWISE.getCode();
        } else {
            //Collinear
           // retVal = Orientation.COLLINEAR.getCode(); //superflous
        }

        return retVal;
    }

    /**
     * @param anchor the anchor to set
     */
    public void setAnchor(K anchor) {
        this.anchor = anchor;
    }

    K getAnchor() {
        return anchor;
    }

    /**
     *
     * @param left
     * @param right
     * @param top
     * @return
     */
    public boolean isRightTurn(final K left, final K right, final K top) {
        return ccw(left, right, top) > EPSILON;
    }

    public static <T extends Point> int relativeCCW(T p1, T p2, T p3) {
        return new RadialComparator().compare(p1, p2, p3);
    }
}
