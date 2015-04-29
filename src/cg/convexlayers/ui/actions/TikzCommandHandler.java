/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexlayers.ui.actions;

import cg.geometry.primitives.Polygon;
import cg.geometry.primitives.impl.Point2D;
import cg.geometry.primitives.impl.Triangle2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author rrufai
 */
public class TikzCommandHandler {

    private static TikzCommandHandler INSTANCE = new TikzCommandHandler();
    private List<String> tikzStartCommand = new ArrayList<>();
    private List<String> tikzPointDefinitionCommands = new ArrayList<>();
    private List<String> tikzPointDrawingCommands = new ArrayList<>();
    private List<String> tikzPolygonsDrawCommands = new ArrayList<>();
    private List<String> tikzEndCommand = new ArrayList<>();

    public static TikzCommandHandler getInstance() {
        return INSTANCE;
    }

    public void start() {
        tikzStartCommand.add("\\begin{tikzpicture}[scale=5.0]");
    }

    public void end() {
        tikzEndCommand.add("\\end{tikzpicture}");

        System.err.println(toString());
        clear();
    }

    @Override
    public String toString() {
        String text = "";
        for (String line : tikzStartCommand) {
            text += line + "\n";
        }
        for (String line : tikzPointDefinitionCommands) {
            text += line + "\n";
        }

        for (String line : tikzPolygonsDrawCommands) {
            text += line + "\n";
        }

        for (String line : tikzPointDrawingCommands) {
            text += line + "\n";
        }

        for (String line : tikzEndCommand) {
            text += line + "\n";
        }

        return text;
    }

    public void definePoint(Point2D p) {
        String name = getName(p);
        tikzPointDefinitionCommands.add(String.format("\\tkzDefPoint (%.3f, %.3f){%s}", p.getX(), p.getY(), name));
        tikzPointDrawingCommands.add(String.format("\\tkzDrawPoint(%s)", name));
    }

    public void drawPolygon(List<Point2D> polygon, Color color) {
        String pointList = "";
        for (int i = 0; i < polygon.size(); i++) {
            Point2D p = polygon.get(i);
            pointList += getName(p) + ", ";
        }
        if (polygon.size() > 0) {
            pointList += getName(polygon.get(0));
        }
        tikzPolygonsDrawCommands.add(String.format("\\tkzDrawPolySeg[color=%s](%s)", toTikzColor(color), pointList));
    }

    private String toTikzColor(Color color){
        //{rgb:red,179;green,18;blue,18}
        return "{rgb:red," + color.getRed() + ";green," + color.getGreen() + ";blue," +color.getBlue() + "}";
                
    }
    
    private String getName(Point2D p) {
        return p.getName();
    }

    void drawOpenPolygon(Polygon<Point2D> polygon, Color color) {
        String pointList = "";
        for (Point2D p : polygon.getVertices()) {
            pointList += getName(p) + ", ";
        }

        tikzPolygonsDrawCommands.add(String.format("\\tkzDrawPolySeg[color=%s](%s)", color.toString(), pointList));
    }
    
    void drawOpenPolygon(TreeSet<Point2D> polygon, Color color) {
        String pointList = "";
        for (Point2D p : polygon) {
            pointList += getName(p) + ", ";
        }

        tikzPolygonsDrawCommands.add(String.format("\\tkzDrawPolySeg[color=%s](%s)", color.toString(), pointList));
    }

    private void clear() {
        tikzStartCommand = new ArrayList<>();
        tikzPointDefinitionCommands = new ArrayList<>();
        tikzPointDrawingCommands = new ArrayList<>();
        tikzPolygonsDrawCommands = new ArrayList<>();
        tikzEndCommand = new ArrayList<>();
    }

    void drawTriangle(Triangle2D<Point2D> triangle, Color color) {
        String pointList = "";
        for (Point2D p : triangle.getVertices()) {
            pointList += getName(p) + ", ";
        }
        pointList += getName(triangle.getVertices().get(0));
        tikzPolygonsDrawCommands.add(String.format("\\tkzDrawPolySeg[color=%s](%s)", color.toString(), pointList));

    }
}
