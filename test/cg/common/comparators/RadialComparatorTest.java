/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cg.common.comparators;

import cg.geometry.primitives.impl.Point2D;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author rrufai
 */
public class RadialComparatorTest {
    /**
     * Test of ccw method, of class RadialComparator.
     */
    @Test
    public void testCcw() {
        System.out.println("ccw");
        Point2D p1 = new Point2D(1, 2);
        Point2D p2 = new Point2D(2, 1);
        Point2D p3 = new Point2D(4, 9);
        RadialComparator instance = new RadialComparator(p1);
        double expResult = 10.0;
        double result = instance.ccw(p1, p2, p3);
        assertEquals(expResult, result, RadialComparator.EPSILON);
    }

    /**
     * Test of compare method, of class RadialComparator.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Point2D o1 = new Point2D(1, 2);
        Point2D o2 = new Point2D(2, 2);
        RadialComparator instance = new RadialComparator(new Point2D(0, 0));
        int expResult = RadialComparator.Orientation.CLOCKWISE.getCode();
        int result = instance.compare(o1, o2);
        assertEquals(expResult, result);
        assertEquals(RadialComparator.Orientation.COUNTERCLOCKWISE.getCode(), instance.compare(o2, o1));
        assertEquals(RadialComparator.Orientation.COLLINEAR.getCode(), instance.compare(o1, o1));
    }

    /**
     * Test of setAnchor method, of class RadialComparator.
     */
    @Test
    public void testSetAnchor() {
        System.out.println("setAnchor");
        Point2D anchor = new Point2D(1, 1);
        RadialComparator<Point2D> instance = new RadialComparator(new Point2D(0, 0));
        assertFalse(anchor.equals(instance.getAnchor()));
        instance.setAnchor(anchor);
        assertTrue(anchor.equals(instance.getAnchor()));
    }

}