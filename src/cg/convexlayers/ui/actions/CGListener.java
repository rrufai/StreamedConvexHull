/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers.ui.actions;

import cg.convexhull.exact.impl.AndrewsMonotoneChain;
import cg.geometry.primitives.Geometry;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Polygon2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class CGListener implements GLEventListener, MouseListener {

    private List<Point2D> pointset;
    private Geometry<Point2D> hullPoints;
    private AndrewsMonotoneChain<Point2D> chull;
    //private Point2D centroid;
    private GLU glu;
    private GLCapabilities capabilities;
    private double height;
    private double width;

    /**
     *
     */
    public CGListener() {
        pointset = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            pointset.add(new Point2D(2 * (random.nextDouble() - .5), 2 * (random.nextDouble() - .5)));
        }

        chull = new AndrewsMonotoneChain<>(pointset);
        hullPoints = chull.compute(pointset);
        //hullPoints = new GrahamScan(pointset).compute();
        //centroid = chull.centroid();
    }

    /**
     *
     * @param drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        width = drawable.getWidth();
        height = drawable.getHeight();
        capabilities = new GLCapabilities(drawable.getGLProfile());
        capabilities.setDoubleBuffered(false);

        GL2 gl = (GL2) drawable.getGL();
        gl.glPointSize(5f);
        gl.glColor3i(0, 1, 1);
        gl.glClearColor(.17f, .65f, 0.92f, 1.0f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        glu.gluLookAt(0, 0, 7.0, 0, 0, 0, 0, 1, 0);

    }

    /**
     *
     * @param drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = (GL2) drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glColor3f(1.0f, 0.0f, 1.0f);
        drawPolygon(drawable, hullPoints);

        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glPointSize(5f);

        for (Point2D p : pointset) {
            drawPoint(drawable, p);
        }

        //gl.glColor3f(1.0f, 1.0f, 1.0f);
        //drawPoint(drawable, centroid);

    }

    /**
     *
     * @param drawable
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @param drawable
     * @param modeChanged
     * @param deviceChanged
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    private void drawPoint(GLAutoDrawable drawable, Point2D p, int width, int height) {
        double x = p.getX();
        double y = p.getY();
        p.setLocation(x, y);
        System.out.println(p);
        GL2 gl = (GL2) drawable.getGL();
        //gl.glColor3d(Math.random(), Math.random(), Math.random());
        //gl.glPointSize(50);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex3d(x, y, 0.0);
        gl.glEnd();

    }

    private void drawPoint(GLAutoDrawable drawable, Point2D p) {
        drawPoint(drawable, p, drawable.getWidth(), drawable.getHeight());
    }

    private void drawPolygon(GLAutoDrawable drawable, Geometry<Point2D> hullPoints) {
        GL2 gl = (GL2) drawable.getGL();
        //gl.glColor3d(Math.random(), Math.random(), Math.random());
        //gl.glPointSize(50);
        gl.glBegin(GL2.GL_POLYGON);
        for (Point2D p : hullPoints.getVertices()) {
            gl.glVertex3d(p.getX(), p.getY(), 0.0);
        }
        gl.glEnd();
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        Point p = e.getPoint();

        pointset.add(new Point2D(2 * p.getX() / width - 1.0, 1.0 - 2 * p.getY() / height));
        hullPoints = chull.compute(new Polygon2D<>(pointset));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
