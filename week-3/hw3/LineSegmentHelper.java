import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Iterator;

public class LineSegmentHelper {
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
                return slopePQ == slopePR;
            }
        }

    }

    public String toString() {
        return first + " -> " + second;
    }

    public static void main(String[] args) {
        /**
        // First test out the collinear() method
        Point[] points = new Point[4];

        points[0] = new Point(1, 1);
        points[1] = new Point(2, 2);
        points[2] = new Point(3, 3);
        points[3] = new Point(4, 5);

        LineSegmentHelper lh01 = new LineSegmentHelper(points[0], points[1]);
        LineSegmentHelper lh02 = new LineSegmentHelper(points[0], points[2]);
        LineSegmentHelper lh03 = new LineSegmentHelper(points[0], points[3]);
        LineSegmentHelper lh12 = new LineSegmentHelper(points[1], points[2]);

        System.out.println(lh01.collinear(lh02));
        System.out.println(lh01.collinear(lh03));
        System.out.println(lh12.collinear(lh03));
        **/

        // Read in a file specified by the user in the command line
        In in = new In(args[0]);
        int n = in.readInt();

        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // Initialize new ArrayList to hold the LineSegmentHelper objects
        ArrayList<LineSegmentHelper> segmentList = new ArrayList<LineSegmentHelper>();

        for (int i = 0; i < n; i++) {
            Point p = points[i];

            for (int j = i + 1; j < n; j++) {
                Point q = points[j];

                LineSegmentHelper lh = new LineSegmentHelper(p, q);

                // Version 1: Just add all line segments to the ArrayList
                // segmentList.add(lh);

                // Version 2: Only add the line segment if an equivalent line
                // segment is not already in the list
                Iterator<LineSegmentHelper> it = segmentList.iterator();

                // Initialize lh as a new segment
                boolean newSegment = true;

                // Mark lh as an old segment if we find it inside the iterator
                while (it.hasNext()) {
                    LineSegmentHelper lh2 = it.next();

                    if (lh.collinear(lh2)) {
                        System.out.println("SAME");
                        newSegment = false;
                        break;
                    }
                }

                // Only add lh if an equivalent segment is not already in segmentList
                if (newSegment) {
                    segmentList.add(lh);
                }
            }
        }

        // Create an iterator over segmentList
        Iterator<LineSegmentHelper> it = segmentList.iterator();

        // Print out segmentList
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }

    }
}
