/*
 * CGApp.java
 */

package cg.convexlayers.apps;

import cg.convexlayers.ui.CGView;
import cg.convexlayers.ui.actions.CGListener;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class CGConvexHullApp extends SingleFrameApplication {

    //private CGView mainFrame;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        CGListener listener = new CGListener();
        final CGView mainFrame = new CGView(this, listener, listener);
        show(mainFrame);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     * @param root
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of CGApp
     */
    public static CGConvexHullApp getApplication() {
        return Application.getInstance(CGConvexHullApp.class);
    }

    /**
     * Main method launching the application.
     * @param args
     */
    public static void main(final String[] args) {
        launch(CGConvexHullApp.class, args);
    }

    
}
