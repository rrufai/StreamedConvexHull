/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.approximate.streaming;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author I827590
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({cg.convexhull.approximate.streaming.StreamedConvexHullTest.class, 
    cg.convexhull.approximate.streaming.StreamedConvexHullInitializerTest.class, 
    cg.convexhull.approximate.streaming.StreamedPoint2DTest.class, 
    cg.convexhull.approximate.streaming.StreamedConvexUtilityTest.class, 
    cg.convexhull.approximate.streaming.StreamedInitializerTest.class})
public class StreamingSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
