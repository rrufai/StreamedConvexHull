/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers.ui.actions;

/**
 *
 * @author rrufai
 */
public class Configuration {

    private float SCALE_FACTOR = 0.001f;
    private boolean showCoordinates = false;
    private boolean showUpperLayers = true;
    private boolean showLowerLayers = true;
    private boolean showLeftToRightLayers = true;
    private boolean showRightToLeftLayers = true;
    private boolean showPolygons = true;
    private boolean showPoints = true;
    private float POINT_SIZE = 2.5f;
    private boolean generateTikzPictureCode = false;
    private boolean showEvictionTriangle = false;
    
    public Configuration(){}

    /**
     * @return the SCALE_FACTOR
     */
    public float getSCALE_FACTOR() {
        return SCALE_FACTOR;
    }

    /**
     * @param SCALE_FACTOR the SCALE_FACTOR to set
     */
    public void setSCALE_FACTOR(float SCALE_FACTOR) {
        this.SCALE_FACTOR = SCALE_FACTOR;
    }

    /**
     * @return the showCoordinates
     */
    public boolean isShowCoordinates() {
        return showCoordinates;
    }

    /**
     * @param showCoordinates the showCoordinates to set
     */
    public void setShowCoordinates(boolean showCoordinates) {
        this.showCoordinates = showCoordinates;
    }

    /**
     * @return the showUpperLayers
     */
    public boolean isShowUpperLayers() {
        return showUpperLayers;
    }

    /**
     * @param showUpperLayers the showUpperLayers to set
     */
    public void setShowUpperLayers(boolean showUpperLayers) {
        this.showUpperLayers = showUpperLayers;
    }

    /**
     * @return the showLowerLayers
     */
    public boolean isShowLowerLayers() {
        return showLowerLayers;
    }

    /**
     * @param showLowerLayers the showLowerLayers to set
     */
    public void setShowLowerLayers(boolean showLowerLayers) {
        this.showLowerLayers = showLowerLayers;
    }

    /**
     * @return the showLeftToRightLayers
     */
    public boolean isShowLeftToRightLayers() {
        return showLeftToRightLayers;
    }
    /**
     * @param showLeftToRightLayers the showLeftToRightLayers to set
     */
    public void setShowLeftToRightLayers(boolean showLeftToRightLayers) {
        this.showLeftToRightLayers = showLeftToRightLayers;
    }

    /**
     * @return the showRightToLeftLayers
     */
    public boolean isShowRightToLeftLayers() {
        return showRightToLeftLayers;
    }

    /**
     * @param showRightToLeftLayers the showRightToLeftLayers to set
     */
    public void setShowRightToLeftLayers(boolean showRightToLeftLayers) {
        this.showRightToLeftLayers = showRightToLeftLayers;
    }

    /**
     * @return the showPolygons
     */
    public boolean isShowPolygons() {
        return showPolygons;
    }

    /**
     * @param showPolygons the showPolygons to set
     */
    public void setShowPolygons(boolean showPolygons) {
        this.showPolygons = showPolygons;
    }

    /**
     * @return the showPoints
     */
    public boolean isShowPoints() {
        return showPoints;
    }

    /**
     * @param showPoints the showPoints to set
     */
    public void setShowPoints(boolean showPoints) {
        this.showPoints = showPoints;
    }

    /**
     * @return the POINT_SIZE
     */
    public float getPOINT_SIZE() {
        return POINT_SIZE;
    }

    /**
     * @param POINT_SIZE the POINT_SIZE to set
     */
    public void setPOINT_SIZE(float POINT_SIZE) {
        this.POINT_SIZE = POINT_SIZE;
    }

    /**
     * @return the generateTikzPictureCode
     */
    public boolean isGenerateTikzPictureCode() {
        return generateTikzPictureCode;
    }

    /**
     * @param generateTikzPictureCode the generateTikzPictureCode to set
     */
    public void setGenerateTikzPictureCode(boolean generateTikzPictureCode) {
        this.generateTikzPictureCode = generateTikzPictureCode;
    }

    boolean isShowEvictionTriangle() {
        return this.showEvictionTriangle;
    }
}
