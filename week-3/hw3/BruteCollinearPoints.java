import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private int numSegments = 0;
    private LineSegment[] lineSegments;
    private Point[] points;

    public BruteCollinearPoints(Point[] inputPoints) {
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
        int N = points.length;

        int counter = 0;

        for (int i = 0; i < N; i++) {
            Point p = points[i];

            for (int j = i+1; j < N; j++) {
                Point q = points[j];

                double slopePQ = p.slopeTo(q);

                for (int k = j+1; k < N; k++) {
                    Point r = points[k];

                    double slopePR = p.slopeTo(r);

                    if (slopePQ != slopePR) continue;

                    for (int l = k+1; l < N; l++) {
                        Point s = points[l];

                        double slopePS = p.slopeTo(s);

                        if (slopePS != slopePQ) {
                            // System.out.println("PROBLEM");
                            continue;
                        }

                        // System.out.println(slopePQ + " " + slopePR + " " + slopePS);
                        // System.out.println(p + " " + q + " " + r + " " + s);
                        counter++;
                    }
                }
            }

            // System.out.println("----------------------------");
        }

        return counter;
    }

    public LineSegment[] segments() {
        int N = points.length;

        int counter = 0;

        // Declare an array of LineSegments whose size is the number of line segments
        lineSegments = new LineSegment[numberOfSegments()];

        for (int i = 0; i < N; i++) {
            Point p = points[i];

            for (int j = i+1; j < N; j++) {
                Point q = points[j];

                double slopePQ = p.slopeTo(q);

                for (int k = j+1; k < N; k++) {
                    Point r = points[k];

                    double slopePR = p.slopeTo(r);

                    if (slopePQ != slopePR) continue;

                    for (int l = k+1; l < N; l++) {
                        Point s = points[l];

                        double slopePS = p.slopeTo(s);

                        if (slopePQ != slopePS) continue;

                        // System.out.println(slopePQ + " " + slopePR + " " + slopePS);
                        System.out.println(p + " " + q + " " + r + " " + s);
                        lineSegments[counter] = new LineSegment(p, s);
                        counter++;
                    }
                }
            }
        }

        return lineSegments;
    }

    // Test client
    /*
    public static void main(String[] args) {
        Point[] points = new Point[7];
        points[0] = new Point(67, 7345);
        points[1] = new Point(1, 1);
        points[2] = new Point(2, 2);
        points[3] = new Point(3, 2);
        points[4] = new Point(3, 3);
        points[5] = new Point(4, 2);
        points[6] = new Point(5, 2);

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        // System.out.println(bcp.numberOfSegments());

        LineSegment[] segs = bcp.segments();

        for (int i = 0; i < segs.length; i++) {
            System.out.println(segs[i]);
        }
    }
    */

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
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (Point p : points) {
            p.draw();
        }

        StdDraw.show();

        // Print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        System.out.println(collinear.numberOfSegments());

        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }
}
