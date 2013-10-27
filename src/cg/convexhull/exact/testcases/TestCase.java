/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.testcases;

import cg.geometry.primitives.Point;
import java.util.List;

/**
 *
 * @author I827590
 */
public class TestCase<T extends Point> {
    private List<T> inputPoints;
    private List<T> hullVertices;
    private String name;
    
    public TestCase(String name, List<T> inputPoints, List<T> hullVertices){
        this.name = name;
        this.inputPoints = inputPoints;
        this.hullVertices = hullVertices;        
    }
    /**
     * @return the inputPoints
     */
    public List<T> getInputPoints() {
        return inputPoints;
    }

    /**
     * @return the hullVertices
     */
    public List<T> getHullVertices() {
        return hullVertices;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }   
}
