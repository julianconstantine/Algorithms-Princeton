import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

public class FastCollinearPoints {
    private Point[] points;

    private class LineSegmentHelper {
        public Point first;
        public Point second;

        public LineSegmentHelper(Point p, Point q) {
            this.first = p;
            this.second = q;
        }

        public boolean collinear(LineSegmentHelper that) {
            //System.out.println(this.first);
            //System.out.println(this.second);
            //System.out.println(that.first);
            //System.out.println(that.second);

            //System.out.println("-------------------------");

            //System.out.println(this.first.slopeTo(that.first));
            //System.out.println(this.first.slopeTo(that.second));

            // Let this = (p, s); that = (q, r)
            double slopePQ = this.first.slopeTo(that.first);
            double slopeSR = this.second.slopeTo(that.second);

            double slopePR = this.first.slopeTo(that.second);
            double slopePS = this.first.slopeTo(this.second);

            double slopeSP = this.second.slopeTo(this.first);
            double slopeRQ = that.second.slopeTo(that.first);

            if (slopePQ == Double.NEGATIVE_INFINITY) {
                // Case 1: p = q
                if (slopeSR == Double.NEGATIVE_INFINITY) {
                    //System.out.println("p = q, r = s");
                    // Case 1.1: p = q, r = s, segments are identical
                    return true;
                } else {
                    //System.out.println("p = q, r != s");
                    // Case 1.2: p = q, r != s, segments are collinear <=> slopePR = slopePS
                    return slopePS == slopePR;
                }
            } else {
                // Case 2: p != q
                if (slopeSR == Double.NEGATIVE_INFINITY) {
                    //System.out.println("p != q, r = s");
                    // Case 2.1: p != q, r = s, segments are collinear <=> slopeSP = slopeRQ
                    return slopeSP == slopeRQ;
                } else {
                    //System.out.println("p != q, r != s");
                    //System.out.println("slope(p, q) = " + slopePQ);
                    //System.out.println("slope(p, r) = " + slopePR);
                    // Case 2.2: p != q, r != s, segments are collinear <=> slopePQ = slope PR
                    if (slopePR == Double.NEGATIVE_INFINITY) {
                        // Case 2.2.1: p != q, r != s, p = r
                        return slopeRQ == slopeSR;
                    } else {
                        return slopePQ == slopePR;
                    }
                }
            }
        }

        public String toString() {
            return first + " -> " + second;
        }

        public LineSegment toLineSegment() {
            return new LineSegment(this.first, this.second);
        }
    }

    public LineSegment[] segments() {
        ArrayList<LineSegmentHelper> collinearSegments = new ArrayList<LineSegmentHelper>();

        for (Point p: points) {
            // System.out.println("Point p: " + p.toString());
            // System.out.println();

            // Point comparator with respect to the slope ordering defined by p
            Comparator<Point> comp = p.slopeOrder();

            // Copy of points array
            Point[] copy = points;

            // Sort copy array
            Arrays.sort(copy, comp);

            // for (int i = 0; i < copy.length; i++) {
            //     System.out.print(copy[i] + " ");
            // }
            //
            // System.out.println();
            // System.out.println();

            // Array of slopes
            double[] slopes = new double[copy.length];

            for (int i = 0; i < copy.length; i ++) {
                slopes[i] = p.slopeTo(copy[i]);
                // System.out.print(slopes[i] + " ");
            }
            //
            // System.out.println();
            // System.out.println();

            int i = 0;
            int j = i;

            while (i < copy.length - 1) {
                int last = i;

                while (slopes[i] == slopes[j]) {
                    last = j;

                    if (j == copy.length - 1) {
                        break;
                    } else {
                        j++;
                    }
                }

                if (j - i > 2) {
                    /*
                    for (int k = i; k <= last; k++) {
                        System.out.print(k + " ");
                    }

                    System.out.println();
                    */

                    LineSegmentHelper seg = new LineSegmentHelper(p, copy[i]);

                    boolean discovered = false;

                    // Scan through list of collinear segments to see if lh is not already included
                    Iterator<LineSegmentHelper> lhIterator = collinearSegments.iterator();

                    while (lhIterator.hasNext()) {
                        if (seg.collinear(lhIterator.next())) {
                            discovered = true;
                            // System.out.println("COPY");
                            break;
                        }
                    }

                    if (!discovered) {
                        // System.out.print(p + " ");
                        //
                        // for (int k = i; k <= last+1; k++) {
                        //     System.out.print(copy[k] + " ");
                        // }
                        // System.out.println();

                        collinearSegments.add(seg);
                    }
                }

                i = j;
            }
        }

        // System.out.println();
        // System.out.println("------------------");
        // System.out.println();

        // LineSegment[] array for output
        LineSegment[] lineSegments = new LineSegment[collinearSegments.size()];

        // System.out.println(collinearSegments.size());

        Iterator<LineSegmentHelper> lhIterator = collinearSegments.iterator();
        int i = 0;

        while (lhIterator.hasNext()) {
            LineSegmentHelper lh = lhIterator.next();
            // System.out.println(lh.toString());
            LineSegment ls = lh.toLineSegment();

            lineSegments[i] = ls;
            i++;
        }

        return lineSegments;
    }

    public int numberOfSegments() {
        LineSegment[] ls = segments();

        return ls.length;
    }

    public FastCollinearPoints(Point[] inputPoints) {
        if (inputPoints == null) {
            throw new NullPointerException("null array");
        }

        for (int i = 0; i < inputPoints.length; i++) {
            if (inputPoints[i] == null) {
                throw new NullPointerException("element " + i + " of inputPoints is null");
            }
        }

        points = inputPoints;
    }

    // Helper function to test if two LineSegments are collinear

    public static void main(String[] args) {
        // Read in n points from a file
        In in = new In(args[0]);
        int n = in.readInt();

        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // Draw the points
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        //
        // for (Point p : points) {
        //     p.draw();
        // }
        //
        // StdDraw.show();

        // Print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        LineSegment[] ls = collinear.segments();

        for (int i = 0; i < ls.length; i++) {
            System.out.println (ls[i].toString());
        }

        // System.out.println();
        //
        // Point p = new Point(0, 10000);
        // Point q = new Point(10000, 0);
        //
        // System.out.println(p.slopeTo(q));
        // System.out.println(q.slopeTo(p));


        // for (LineSegment segment : collinear.segments()) {
        //     System.out.println(segment);
        //     segment.draw();
        // }
        //
        // StdDraw.show();
    }
}
