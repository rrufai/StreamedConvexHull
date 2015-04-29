/*
 * CGConvexLayersApp.java
 */
package cg.convexlayers.apps;

import cg.common.Toolkit;
import cg.convexlayers.ui.CGView;
import cg.convexlayers.ui.actions.Configuration;
import cg.convexlayers.ui.actions.ConvexLayerListener;
import java.util.List;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class CGConvexLayersApp extends SingleFrameApplication {

    private final int NUMBER_OF_RANDOM_POINTS_PER_LAYER = 6;
    private int NUMBER_OF_LAYERS = 9;
    //private CGView mainFrame;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
       //List pointset = Toolkit.generatePointSet(Toolkit.PointType.FIXED_RANDOM, NUMBER_OF_RANDOM_POINTS_PER_LAYER, NUMBER_OF_LAYERS);
       //List pointset = Toolkit.generatePointSet(Toolkit.PointType.POSITIVE_RANDOM, NUMBER_OF_RANDOM_POINTS_PER_LAYER, NUMBER_OF_LAYERS);
       //List pointset = Toolkit.generatePointSet(Toolkit.PointType.FIXED_PAPER, NUMBER_OF_RANDOM_POINTS_PER_LAYER, NUMBER_OF_LAYERS);
      //List pointset = Toolkit.generatePointSet(Toolkit.PointType.FIXED_HEXAGONAL_LAYERS, NUMBER_OF_RANDOM_POINTS_PER_LAYER, NUMBER_OF_LAYERS);
       //List pointset = Toolkit.generatePointSet(Toolkit.PointType.FIXED, NUMBER_OF_RANDOM_POINTS_PER_LAYER, NUMBER_OF_LAYERS);
      List pointset = Toolkit.generatePointSet(Toolkit.PointType.FIXED3, NUMBER_OF_RANDOM_POINTS_PER_LAYER, NUMBER_OF_LAYERS);
       
       
        ConvexLayerListener listener = new ConvexLayerListener(pointset, new Configuration());
        final CGView mainFrame = new CGView(this, listener, listener);
        show(mainFrame);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     * @param root
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of CGConvexLayersApp
     */
    public static CGConvexLayersApp getApplication() {
        return Application.getInstance(CGConvexLayersApp.class);
    }

    /**
     * Main method launching the application.
     * @param args
     */
    public static void main(final String[] args) {
        launch(CGConvexLayersApp.class, args);
    }
}
