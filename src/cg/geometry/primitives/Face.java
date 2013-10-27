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
public interface Face<T extends Point> extends Geometry<T>{

    void saveToFile(String fileName)  throws IOException;
    
}
