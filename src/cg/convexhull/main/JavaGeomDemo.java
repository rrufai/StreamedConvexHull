package cg.convexhull.main;

/* file : JavaGeomDemo.java
 * 
 * Project : Euclide
 *
 * ===========================================
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY, without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. if not, write to :
 * The Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 * 
 * Created on 26 Jul. 2008
 *
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import math.geom2d.AffineTransform2D;
import math.geom2d.Box2D;
import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.StraightLine2D;

public class JavaGeomDemo extends JPanel {

    private static final long serialVersionUID = 7331324136801936514L;

    public JavaGeomDemo() {
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Create some points
        Point2D p1 = new Point2D(40, 30);
        Point2D p2 = new Point2D(180, 100);

        // Draw the points
        p1.draw(g2, 2);
        p2.draw(g2, 2);


        // Create a line
        LineSegment2D line1 = new LineSegment2D(p1, p2);
        line1.draw(g2);

        Point2D p3 = new Point2D(20, 120);
        Point2D p4 = new Point2D(40, 140);

        // Create line segment
        LineSegment2D edge = new LineSegment2D(p3, p4);
        edge.draw(g2);



    }

    public final static void main(String[] args) {
        JPanel panel = new JavaGeomDemo();
        JFrame frame = new JFrame("JavaGeom Demo");
        frame.setContentPane(panel);
        frame.setSize(250, 250);
        frame.setVisible(true);
    }
}
