/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives.impl;

import cg.geometry.primitives.Edge;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author rrufai
 */
public class Face2DTest {
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of contains method, of class Face2D.
     */
    @Test @Ignore
    public void testContains() {
        System.out.println("contains");
        Point2D p = null;
        Face2D instance = null;
        boolean expResult = false;
        boolean result = instance.contains(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of area method, of class Face2D.
     */
    @Test @Ignore
    public void testArea() {
        System.out.println("area");
        Face2D instance = null;
        double expResult = 0.0;
        double result = instance.area();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveToFile method, of class Face2D.
     */
    @Test @Ignore
    public void testSaveToFile() throws Exception {
        System.out.println("saveToFile");
        String fileName = "";
        Face2D instance = null;
        instance.saveToFile(fileName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVertices method, of class Face2D.
     */
    @Test
    public void testGetVertices() {
        System.out.println("getVertices");
        List<Edge<Point2D>> edges = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Edge<Point2D> edge = new Edge2D<>(new Point2D(i, i*i), new Point2D(i+1, (i+1)*(i+1)));
            edges.add(edge);
        }
        Face2D<Point2D> instance = new Face2D<>(edges);
        int expResult = 11;
        int result = instance.getVertices().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEdges method, of class Face2D.
     */
    @Test @Ignore
    public void testGetEdges() {
        System.out.println("getEdges");
        Face2D instance = null;
        List expResult = null;
        List result = instance.getEdges();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFaces method, of class Face2D.
     */
    @Test
    @Ignore
    public void testGetFaces() {
        System.out.println("getFaces");
        Face2D instance = null;
        List expResult = null;
        List result = instance.getFaces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of draw method, of class Face2D.
     */
    @Test @Ignore
    public void testDraw() {
        System.out.println("draw");
        Graphics canvas = null;
        Face2D instance = null;
        instance.draw(canvas);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
