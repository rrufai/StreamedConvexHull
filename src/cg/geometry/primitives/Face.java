/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.geometry.primitives;

import java.io.IOException;

/**
 *
 * @author rrufai
 */
public interface Face extends Geometry{

    void saveToFile(String fileName)  throws IOException;
    
}
