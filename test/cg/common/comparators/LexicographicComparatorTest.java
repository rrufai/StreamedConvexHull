/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.comparators;

import cg.common.comparators.LexicographicComparator.Direction;
import cg.geometry.primitives.impl.Point2D;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author rrufai
 */
public class LexicographicComparatorTest {

    /**
     * Test of compare method, of class VerticalLexicographicComparator.
     */
    @Test
    public void testCompareBottomUpLeftToRight() {
        System.out.println("compare");
        Point2D o1 = new Point2D(1, 2);
        Point2D o2 = new Point2D(2, 2);
        Point2D o3 = new Point2D(2, 3);
        LexicographicComparator instance = new LexicographicComparator(Direction.BOTTOM_UP);
        int expResult = -1;
        int result = instance.compare(o1, o2);
        Assert.assertEquals(expResult, result);
        Assert.assertEquals(-expResult, instance.compare(o2, o1));
        Assert.assertEquals(0, instance.compare(o1, o1));

        result = instance.compare(o2, o3);
        Assert.assertEquals(expResult, result);
        Assert.assertEquals(-expResult, instance.compare(o3, o2));
        Assert.assertEquals(0, instance.compare(o3, o3));
    }

    @Test
    public void testCompareTopDownRightToLeft() {
        System.out.println("compare");
        Point2D o1 = new Point2D(1, 2);
        Point2D o2 = new Point2D(2, 2);
        Point2D o3 = new Point2D(2, 3);
        LexicographicComparator instance = new LexicographicComparator(Direction.TOP_DOWN);
        int expResult = 1;
        int result = instance.compare(o1, o2);
        Assert.assertEquals(expResult, result);
        Assert.assertEquals(-expResult, instance.compare(o2, o1));
        Assert.assertEquals(0, instance.compare(o1, o1));

        result = instance.compare(o2, o3);
        Assert.assertEquals(expResult, result);
        Assert.assertEquals(-expResult, instance.compare(o3, o2));
        Assert.assertEquals(0, instance.compare(o3, o3));
    }
}