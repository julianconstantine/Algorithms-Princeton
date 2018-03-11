import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

import java.util.Comparator;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] points;

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

    public int numberOfSegments() {
        int counter = 0;

        for (Point p: points) {
            Comparator<Point> comp = p.slopeOrder();

            // for (int i = 0; i < points.length; i++) System.out.print(points[i] + " ");
            // System.out.println();
            // System.out.println();
            //
            // for (int i = 0; i < points.length; i++) System.out.print(p.slopeTo(points[i]) + " ");
            // System.out.println();
            // System.out.println();

            Point[] copy = points;

            Arrays.sort(copy, comp);

            double[] slopes = new double[copy.length];
            for (int i = 0; i < copy.length; i++) slopes[i] = p.slopeTo(copy[i]);


            // for (int i = 0; i < copy.length; i++) System.out.print(copy[i] + " ");
            // System.out.println();
            // System.out.println();

            for (int i = 0; i < points.length; i++) System.out.print(p.slopeTo(copy[i]) + " ");
            System.out.println();
            System.out.println();


            int i = 0;

            while (i < copy.length) {
                int j = i;

                System.out.print(i + ": ");

                while (slopes[j] == slopes[i]) {
                    System.out.print(j + " ");

                    j++;

                    if (j >= copy.length) break;
                }

                if (j >= i+2) {
                    counter++;

                    LineSegment ls = new LineSegment(copy[i], copy[j]);
                    System.out.println(ls.toString());
                }

                i = j;

                System.out.println();
            }
        }

        return counter;
    }

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

        System.out.println(collinear.numberOfSegments());

        // for (LineSegment segment : collinear.segments()) {
        //     System.out.println(segment);
        //     segment.draw();
        // }
        //
        // StdDraw.show();
    }
}
