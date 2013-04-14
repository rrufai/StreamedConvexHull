/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author rrufai
 */
public class CanvasImpl {

    private BufferedImage imageObject;
    private Graphics2D g2;

    public CanvasImpl(Rectangle rect){
        // Expand the drawing area      
        rect.width *= 1.1;
        rect.height *= 1.1;
        rect.x -= rect.width * .05;
        rect.y -= rect.height * .05;
        imageObject = new BufferedImage(rect.width + 10, rect.height + 10, BufferedImage.TYPE_INT_ARGB);
        g2 = imageObject.createGraphics();
    }   
    
    public void drawShape(Shape shape, Color color) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //        g2.setStroke(pathStroke);
        g2.setColor(color);
        g2.draw(shape);
        g2.drawChars("00".toCharArray(), 0, "00".length(), imageObject.getMinX(), imageObject.getMinY());
    }

    public void saveToFile(String imgFormat, String fileName) throws IOException {
        File imageFile = new File(fileName);
        ImageIO.write(imageObject, imgFormat, imageFile);
    }
}
