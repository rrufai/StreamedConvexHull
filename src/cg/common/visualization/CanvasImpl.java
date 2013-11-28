/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.common.visualization;

import cg.geometry.primitives.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author rrufai
 */
public class CanvasImpl {

    private BufferedImage imageObject;
    private Graphics2D g2;
    private int THICKNESS = 2;
    
    private static final Rectangle VIEWPORT_DIMENSION = new Rectangle(300, 300);

    public CanvasImpl(){
        this(VIEWPORT_DIMENSION);
    }
    
    public CanvasImpl(Rectangle rect) {
        // Expand the drawing area      
        rect.width *= 1.1;
        rect.height *= 1.1;
        imageObject = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        g2 = imageObject.createGraphics();
    }

    public void drawShape(Shape shape, Color color) {
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.draw(shape);
    }

    public void drawPoint(Point point, Color color) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        Ellipse2D.Double circle = new Ellipse2D.Double(point.getX(), point.getY(), THICKNESS, THICKNESS);
        g2.fill(circle);
        g2.draw(circle);
    }

    public <T extends Point> void drawPoints(List<T> points, Color color) {
        for (T point : points) {
            drawPoint(point, color);
        }
    }

    public void saveToFile(String imgFormat, String fileName) throws IOException {
        File imageFile = new File(fileName);
        ImageIO.write(imageObject, imgFormat, imageFile);
    }
}
