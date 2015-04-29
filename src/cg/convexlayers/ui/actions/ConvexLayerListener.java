package cg.convexlayers.ui.actions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import cg.common.Toolkit;
import cg.convexlayers.ConvexLayers;
import cg.convexlayers.ConvexLayersPseudoIntervalTreePeeling;
import cg.geometry.primitives.Polygon;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Triangle2D;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author rrufai
 */
public class ConvexLayerListener implements GLEventListener, MouseListener {

    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private ConvexLayers<Point2D> cLayers;
    private GLU glu;
    //private GLUT glut = new GLUT();
    private GLCapabilities capabilities;
    private TextRenderer textRenderer;
    private Configuration configuration;
    private int height;
    private int width;
    private double aspectRatio = 1.0;

    /**
     * Create an instance.
     */
    public ConvexLayerListener(List pointset, Configuration configuration) {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 24);
        textRenderer = new TextRenderer(font, true);
        //cLayers = new ConvexLayerSweepAlgo<>(pointset);
        cLayers = new ConvexLayersPseudoIntervalTreePeeling<>(pointset);
        this.configuration = configuration;
        //LOGGER.setLevel(configuration.getLogLevel());
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(configuration.getLogLevel());
        Logger.getAnonymousLogger().addHandler(consoleHandler);
    }

    /**
     *
     * @param drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        height = drawable.getHeight();
        width = drawable.getWidth();
        capabilities = new GLCapabilities(drawable.getGLProfile());
        capabilities.setDoubleBuffered(true);
        capabilities.setHardwareAccelerated(true);

        GL2 gl = (GL2) drawable.getGL();
//
        aspectRatio = (double) width / (double) height;
//        //drawable.getGL().glViewport(0, 0, width, height);
//        gl.glPointSize(configuration.getPOINT_SIZE());
//        gl.glColor3i(0, 1, 1);
//        gl.glClearColor(1.0f, 1.0f, 0.9f, 1.0f);
//
//        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//        gl.glMatrixMode(GL2.GL_PROJECTION);
//        gl.glLoadIdentity();
        gl.glOrtho(-aspectRatio, aspectRatio, -1, 1, 1, -1);
//
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
//
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_DONT_CARE);
//
//        glu.gluLookAt(0, 0, 7.0, 0, 0, 0, 0, 1, 0);
//        

//        GL2 gl = (GL2) drawable.getGL();
        gl.glPointSize(5f);
        gl.glColor3i(0, 1, 1);
        gl.glClearColor(.17f, .65f, 0.92f, 1.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        glu.gluLookAt(0, 0, 7.0, 0, 0, 0, 0, 1, 0);
    }

    private void renderStrokeString(String string, double x, double y, double z, Color color) {
        // Center Our Text On The Screen
        Rectangle2D dim = this.textRenderer.getBounds(string);

        textRenderer.setUseVertexArrays(true);
        float _width = (float) dim.getWidth() * configuration.getSCALE_FACTOR();

        textRenderer.setColor(color);
        textRenderer.begin3DRendering();
        textRenderer.draw3D(string, (float) (x - _width / 2f), (float) y, (float) z, configuration.getSCALE_FACTOR());
        textRenderer.end3DRendering();
    }

    /**
     *
     * @param drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        if (configuration.isGenerateTikzPictureCode()) {
            TikzCommandHandler.getInstance().start();
        }

        GL2 gl = (GL2) drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glColor3f(1.0f, 0.0f, 1.0f);

        List<Polygon<Point2D>> layers = cLayers.compute();

        if (configuration.isShowPolygons()) {
            Color[] colors = Toolkit.getUniqueColors(layers.size());
            for (int i = 0; i < layers.size(); i++) {
                drawPolygon(drawable, layers.get(i), colors[i]);
            }
        }

//        if (configuration.isShowEvictionTriangle()) {
//            Map<Integer, List<Triangle2D<Point2D>>> map = cLayers.getTriangleMap();
//            for (List<Triangle2D<Point2D>> triangles : map.values()) {
//                if (triangles == null) {
//                    continue;
//                }
//                for (Triangle2D<Point2D> triangle : triangles) {
//                    drawTriangle(drawable, triangle, Color.lightGray);
//                }
//            }
//
//        }


        if (configuration.isShowLayers()) {
            drawLayers(drawable, layers, Color.BLACK);
        }

        if (configuration.isShowPoints()) {
            for (Point2D p : cLayers.getPointset()) {
                drawPoint(drawable, p, Color.BLACK);
            }
        }

        if (configuration.isGenerateTikzPictureCode()) {
            TikzCommandHandler.getInstance().end();
        }
    }

    /**
     *
     * @param drawable
     * @param x
     * @param y
     * @param _width
     * @param height
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    private void drawPoint(GLAutoDrawable drawable, Point2D p, int width, int height, Color color) {
        GL2 gl = (GL2) drawable.getGL();
        gl.glColor3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
        gl.glPointSize(configuration.getPOINT_SIZE());

        gl.glBegin(GL.GL_POINTS);
        gl.glVertex3d(p.getX(), p.getY(), 0.0);
        gl.glEnd();
    }

    private void drawPoint(GLAutoDrawable drawable, Point2D p, Color color) {
        drawPoint(drawable, p, drawable.getWidth(), drawable.getHeight(), color);
        if (configuration.isShowCoordinates()) {
            renderStrokeString(String.format("(%.3f, %.3f)", +p.getX(), p.getY()), p.getX(), p.getY(), 0.0, Color.RED);
        }
        if (configuration.isGenerateTikzPictureCode()) {
            TikzCommandHandler.getInstance().definePoint(p);
        }

    }

    private void drawPolygon(GLAutoDrawable drawable, Polygon<Point2D> polygon, Color color) {
        GL2 gl = (GL2) drawable.getGL();
        gl.glColor3d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);


        gl.glBegin(GL2.GL_POLYGON);
        for (Point2D p : polygon.getVertices()) {
            gl.glVertex3d(p.getX(), p.getY(), 0.0);
        }
        gl.glEnd();

        if (configuration.isGenerateTikzPictureCode()) {
            TikzCommandHandler.getInstance().drawPolygon(polygon.getVertices(), color);
        }
    }

    private void drawLayers(GLAutoDrawable drawable, List<Polygon<Point2D>> halfLayers, Color color) {
        GL2 gl = (GL2) drawable.getGL();
        gl.glColor3d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);

        for (int i = 0; i < halfLayers.size(); i++) {
            Polygon<Point2D> halfLayer = halfLayers.get(i);
            gl.glBegin(GL.GL_LINE_STRIP);
            for (Point2D vertex : halfLayer.getVertices()) {
                gl.glVertex3d(vertex.getX(), vertex.getY(), 0.0);
            }

            Point2D vertex = halfLayer.getVertices().get(0);
            gl.glVertex3d(vertex.getX(), vertex.getY(), 0.0);
            gl.glEnd();

            if (configuration.isGenerateTikzPictureCode()) {
                TikzCommandHandler.getInstance().drawOpenPolygon(halfLayer, color);
            }
        }
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        cLayers.getPointset().add(map(p));
        LOGGER.log(Level.INFO, "mouseEvent.getPoint(): {0}", p);
        LOGGER.log(Level.INFO, "map(mouseEvent.getPoint()): {0}", map(p));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private Point2D map(Point p) {
        // return new Point2D(aspectRatio * ((2 * p.x / (float) width) - 1), 1 - (2 * p.y / (float) height));
        LOGGER.log(Level.INFO, "::::::::::::::in: {0}", p);
        Point2D pout = new Point2D(2 * p.getX() / width - 1.0, 1.0 - 2 * p.getY() / height);
        LOGGER.log(Level.INFO, "::::::::::::::out: {0}", pout);
        return pout;
    }

    private void drawTriangle(GLAutoDrawable drawable, Triangle2D<Point2D> triangle, Color color) {
        GL2 gl = (GL2) drawable.getGL();
        gl.glColor3d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);

        gl.glBegin(GL2.GL_POLYGON);
        for (Point2D p : triangle.getVertices()) {
            gl.glVertex3d(p.getX(), p.getY(), 0.0);
        }
        gl.glEnd();

        if (configuration.isGenerateTikzPictureCode()) {
            TikzCommandHandler.getInstance().drawTriangle(triangle, color);
        }
    }
}
