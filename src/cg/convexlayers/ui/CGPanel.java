/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers.ui;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.EventListener;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLCapabilitiesImmutable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JPanel;

/**
 *
 * @author rrufai
 */
public class CGPanel extends JPanel {

    private static final int PWIDTH = 512;
    private static final int PHEIGHT = 512;
    private FPSAnimator animator;
    private GLJPanel canvas;

    /**
     *
     */
    public CGPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
        //GLProfile.initSingleton(true);
        //GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
        //capabilities.setAlphaBits(8);
        canvas = new GLJPanel();

        animator = new FPSAnimator(canvas, 20, true);

        this.add(canvas, BorderLayout.CENTER);
    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {
        canvas.addMouseListener(mouseListener);
    }

    public void addEventListeners(EventListener... glEventListeners) {
        for (EventListener glEventListener : glEventListeners) {
            canvas.addGLEventListener((GLEventListener) glEventListener);
        }
    }

    /**
     *
     */
    public void start() {
        animator.start();
    }

    /**
     * 
     */
    public void stop() {
        animator.stop();
    }
}
