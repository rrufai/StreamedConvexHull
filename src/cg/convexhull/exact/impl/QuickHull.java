/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cg.convexhull.exact.impl;

import java.awt.geom.*;
import java.util.*;

/**
 * A class to find the convex hull for a given set of space points.
 * This class uses Quick sort method to find the convex hull.
 * @author Kevin Liang
 * @version Prototype v2.0
 * @since Sept 1, 2002
 */
public class QuickHull {
    // fields 

    ArrayList<Line2D> hullEdges;
    Point2D[] points;
    Point2D[] verts = null;
    Line2D aq = new Line2D.Double();

    QuickHull(Point2D[] points) {
        this.points = points;
    }

    // static geometry helpers {{{
    static double leftTurn(Point2D p, Point2D q, Point2D r) {
        return (p.getX() - q.getX()) * (r.getY() - q.getY()) -
                (p.getY() - q.getY()) * (r.getX() - q.getX());
    }

    static double ccw(Point2D p, Point2D q, Point2D r) {
        return leftTurn(p, q, r);
    }

    static double length(Line2D line) {
        return line.getP1().distance(line.getP2());
    }

    static double angle(Line2D l) {
        return Math.atan2(l.getY2() - l.getY1(), l.getX2() - l.getX1());
    }

    static double angle(Line2D l1, Line2D l2) {
        double result = angle(l1) - angle(l2);
        if (result < -Math.PI) {
            return result + Math.PI * 2.0;
        } else if (result > Math.PI) {
            return result - Math.PI * 2.0;
        } else {
            return result;
        }
    }
 
    void quickhull(Line2D ab, int l, int r) // {{{
    {
        if (l <= r) {
            int i, s1, s2, pivot;
            double maxDist = 0.0;
            double dist;
            Point2D P, q, tmp;
            Point2D a = ab.getP1(), b = ab.getP2();
            Line2D aP, Pb;

            pivot = l;
            for (i = l; i <= r; i++) {
                q = points[i];
                aq.setLine(a, q);
                dist = length(aq) * Math.sin(angle(ab, aq));
                if (dist < 0.0) {
                    dist = -dist;
                }
                if (dist >= maxDist) {
                    maxDist = dist;
                    pivot = i;
                }
            }

            tmp = points[l];
            points[l] = points[pivot];
            points[pivot] = tmp;
            P = points[l];

            aP = new Line2D.Double(a, P);
            Pb = new Line2D.Double(P, b);

            hullEdges.add(aP);
            hullEdges.add(Pb);
            hullEdges.remove(ab);

            i = l + 1;
            s1 = l;
            s2 = r + 1;

            while (i < s2) {
                q = points[i];
                if (ccw(a, P, q) > 0.0) {
                    tmp = points[++s1];
                    points[s1] = points[i];
                    points[i++] = tmp;
                } else if (ccw(P, b, q) > 0.0) {
                    tmp = points[--s2];
                    points[s2] = points[i];
                    points[i] = tmp;
                } else {
                    i++;
                }
            }

            quickhull(aP, l + 1, s1);
            quickhull(Pb, s2, r);
        }
    } // }}}

    void computeConvexHull() // {{{
    {
        Point2D a, b, q, tmp;
        double minX, maxX, x;
        int i, j, iLeft, iRight, iLower, iUpper;
        Line2D ab, ba;

        /*
         *  An initial guess for the size of the hull
         */
        int initialSize = (int) Math.log((double) (points.length));
        hullEdges = new ArrayList(initialSize);

        /*
         * find the left and right extrema. These define the chord that
         * separates the upper and lower sets
         */
        minX = points[0].getX();
        maxX = points[0].getX();
        iLeft = iRight = 0;
        for (i = 1; i < points.length; i++) {
            q = points[i];
            x = q.getX();
            if (x > maxX) {
                maxX = x;
                iRight = i;
            }
            if (x < minX) {
                minX = x;
                iLeft = i;
            }
        }

        /*
         * Partition the points into upper and lower sets
         */
        tmp = points[0];
        points[0] = points[iRight];
        points[iRight] = tmp;

        //added. Kevin Liang 09/27/2002.
        if (iLeft == 0) {
            iLeft = iRight;
        }

        a = points[0];
        b = points[iLeft];

        ab = new Line2D.Double(a, b);
        ba = new Line2D.Double(b, a);

        iUpper = 0;
        iLower = points.length;
        i = 1;

        while (i < iLower) {
            q = points[i];
            if (ccw(b, a, q) < 0.0) {
                tmp = points[++iUpper];
                points[iUpper] = points[i];
                points[i++] = tmp;
            } else if (ccw(b, a, q) > 0.0) {
                tmp = points[--iLower];
                points[iLower] = points[i];
                points[i] = tmp;
            } else {
                i++;
            }
        }

        hullEdges.add(ab);
        hullEdges.add(ba);

        quickhull(ab, 1, iUpper);
        quickhull(ba, iLower, points.length - 1);

        int hullSize = hullEdges.size();
        Line2D[] orderedEdges = new Line2D[hullSize];
        Line2D curEdge = (Line2D) hullEdges.get(0), nextEdge = null;
        orderedEdges[0] = curEdge;
        for (i = 1; i < hullSize; i++) {
            for (j = 1; j < hullSize; j++) {
                nextEdge = (Line2D) hullEdges.get(j);
            }
            if (nextEdge.getP1().equals(curEdge.getP2())) {
                orderedEdges[i] = nextEdge;
            }
            curEdge = nextEdge;
            break;
        }
        verts = new Point2D[hullSize];
        for (i = 0; i < hullSize; i++) {
            verts[i] = orderedEdges[i].getP1();
        }
    }
//}}

    /**
     *
     * @param obj
     * @return
     */
    public static Point2D[] computeConvexHull(Point2D[] obj) {
        QuickHull qh = new QuickHull(obj);
        qh.computeConvexHull();
        return qh.verts;
    }

}//~

