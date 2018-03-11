/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;  // x-coordinate of this Point
    private final int y;  // y-coordinate of this Point

    /**
     * Initializes a new point
     *
     * @param  x, the x-coordinate of this point
     * @param  y, the y-coordinate of this point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point ot standard draw
     *
     * @param that, the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0)/(x1 - x0). For completeness, the slope is defined to be +0.0 if the line segment connecting the two points is horizontal; Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal
     *
     * @param  that, the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double slope;

        if (this.y == that.y) {
            // If (x0, y0) = (x1, y1), slope = -infinity
            if (this.x == that.x) slope = Double.NEGATIVE_INFINITY;
            // If y0 = y1 but x0 != x1, slope = +0.0 (horizontal line)
            else slope = +0.0;
        } else {
            // If x0 = x1, but y0 != y1, slope = +infinity (vertical line)
            if (this.x == that.x) slope = Double.POSITIVE_INFINITY;
            // Else slope = (y1 - y0) / (x1 - x0)
            // BE CAREFUL ABOUT INTEGER DIVISION!!!
            else slope = (double) (that.y - this.y) / (double) (that.x - this.x);
        }

        return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties with x-coordinate
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1
     *
     * @param  that, the other point
     * @return the value 0 if this point is equal to the argument;
     *         a negative integer if this point is less than the argument point;
     *         and a positive integer is this point is greater than the argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) {
            // y0 < y1 => this < that
            return -1;
        } else if (this.y > that.y) {
            // y0 > y1 => this > that
            return 1;
        } else {
            // y0 = y1 and x0 < x1 => this < that
            if (this.x < that.x) return -1;
            // y0 = y1 and x0 > x1 => this > that
            else if (this.x > that.x) return 1;
            // y0 = y1 and x0 = x1 => this = that
            else return 0;
        }
    }

    /**
     * Compares two points by the slope they make with this point
     * The slope is defined as in the slopeTo() method
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point v, Point w) {
            double s1 = slopeTo(v);
            double s2 = slopeTo(w);

            if (s1 < s2) return -1;
            else if (s1 > s2) return 1;
            else return 0;
        }
    }

    /**
     * Returns a string representation of this point
     * This method is provided for debugging; your program should not rely on the format of the string representation
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }


    /**
     * You should not call the hashCode() method on this assignment
     * This means that you should not use java.util.HashMap or java.util.HashSet
     */

    /*
    private int hashCode() {
        // DO NOT MODIFY
        throw new UnsupportedOperationException("calling hashCode() is not permitted on this assignment");
    }
    */

    /**
     * You should not call the equals() method on this assignment
     * This means that you should not use java.util.TreeMap or java.util.TreeSet
     */
     /*
    private boolean equals(Object that) {
        // DO NOT MODIFY
        throw new UnsupportedOperationException("calling equals() is not permitted on this assignment");
    }
    */

    /**
     * Unit tests the Point data type
     */
    public static void main(String[] args) {
        Point p = new Point(0, 0);
        Point q = new Point(1, 0);
        Point r = new Point(1, 1);
        Point s = new Point(0, 1);

        System.out.println(p.slopeTo(q));
        System.out.println(p.slopeTo(r));
        System.out.println(p.slopeTo(s));

        Comparator<Point> comp = p.slopeOrder();
        System.out.println(comp.compare(r, q));
    }
}
