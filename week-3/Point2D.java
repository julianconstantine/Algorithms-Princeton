/*
  2D POINT DATA TYPE
 */

public class Point2D {
    public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();

    private final double x;
    private final double y;

    // Class constructor
    public Point2D {
    	this.x = x;
    	this.y = y;
    }

    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x - a.x)*(c.y - a.y) - (b.y - a.y)*(c.x - a.x);

        if (area2 < 0) return -1;  // Clockwise
        if (area2 > 0) return 1;  // Counterclockwise
        else return 0;  // Collinear
    }

    private class PolarOrder implements Comparator<Point2D> {
        public int compare(Point2D q1, Point2D q2) {
            double dy1 = q1.y - y;
            double dy2 = q1.y - y;

            // p, q1, q2 are horizontal
            if (dy1 == 0 && dy2 == 0) return 0;

            // q1 above p, q2 below p
            else if (dy1 >= 0 && dy2 < 0) return -1;

            // q1 below p, q2 above p
            else if (dy2 >= 0 && dy1 < 0) return 1;

            // Both above or below p
            // NOTE: Use Point2D.this to access invoking point from within private class
            else return -ccw(Point2D.this, q1, q2);
        }
    }
}
