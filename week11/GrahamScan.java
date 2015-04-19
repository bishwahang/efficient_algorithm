package week11;

/**
 * Created by hang on 11.01.15.
 */

import java.util.*;

// source: http://algs4.cs.princeton.edu/99hull/GrahamScan.java

public class GrahamScan {
    private Stack<Point2D> hull = new Stack<Point2D>();

    public GrahamScan(Point2D[] pts) {

        // defensive copy
        int N = pts.length;
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++)
            points[i] = pts[i];

        // preprocess so that points[0] has lowest y-coordinate; break ties by x-coordinate
        // points[0] is an extreme point of the convex hull
        // (alternatively, could do easily in linear time)
        Arrays.sort(points);

        // sort by polar angle with respect to base point points[0],
        // breaking ties by distance to points[0]
        Arrays.sort(points, 1, N, points[0].POLAR_ORDER);

        hull.push(points[0]);       // p[0] is first extreme point

        // find index k1 of first point not equal to points[0]
        int k1;
        for (k1 = 1; k1 < N; k1++)
            if (!points[0].equals(points[k1])) break;
        if (k1 == N) return;        // all points equal

        // find index k2 of first point not collinear with points[0] and points[k1]
        int k2;
        for (k2 = k1 + 1; k2 < N; k2++)
            if (Point2D.ccw(points[0], points[k1], points[k2]) != 0) break;
        hull.push(points[k2-1]);    // points[k2-1] is second extreme point

        // Graham scan; note that points[N-1] is extreme point different from points[0]
        for (int i = k2; i < N; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }
    }

    // return extreme points on convex hull in counterclockwise order as an Iterable
    public Iterable<Point2D> hull() {
        Stack<Point2D> s = new Stack<Point2D>();
//        Arr
        for (Point2D p : hull) s.push(p);
        return s;
    }

    static class Point2D implements Comparable<Point2D>{
        /**
         * Compares two points by polar angle (between 0 and 2pi) with respect to this point.
         */
        public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();

        private final double x;    // x coordinate
        private final double y;    // y coordinate
        private final int id;    // id

        public Point2D(double x, double y, int id) {
            if (x == 0.0) x = 0.0;  // convert -0.0 to +0.0
            if (y == 0.0) y = 0.0;  // convert -0.0 to +0.0
            this.x = x;
            this.y = y;
            this.id = id;
        }
        /**
         * Compares this point to that point by y-coordinate, breaking ties by x-coordinate.
         * @param that the other point
         * @return { a negative integer, zero, a positive integer } if this point is
         *    { less than, equal to, greater than } that point
         */
        public int compareTo(Point2D that) {
            if (this.y < that.y) return -1;
            if (this.y > that.y) return +1;
            if (this.x < that.x) return -1;
            if (this.x > that.x) return +1;
            return 0;
        }
        /**
         * Is a->b->c a counterclockwise turn?
         * @param a first point
         * @param b second point
         * @param c third point
         * @return { -1, 0, +1 } if a->b->c is a { clockwise, collinear; counterclocwise } turn.
         */
        public static int ccw(Point2D a, Point2D b, Point2D c) {
            double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
            if      (area2 < 0) return -1;
            else if (area2 > 0) return +1;
            else                return  0;
        }
        // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
        private class PolarOrder implements Comparator<Point2D> {
            public int compare(Point2D q1, Point2D q2) {
                double dx1 = q1.x - x;
                double dy1 = q1.y - y;
                double dx2 = q2.x - x;
                double dy2 = q2.y - y;

                if      (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
                else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
                else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                    if      (dx1 >= 0 && dx2 < 0) return -1;
                    else if (dx2 >= 0 && dx1 < 0) return +1;
                    else                          return  0;
                }
                else return -ccw(Point2D.this, q1, q2);     // both above or below

                // Note: ccw() recomputes dx1, dy1, dx2, and dy2
            }
        }
    }
    // test client
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int N = sc.nextInt();
            Point2D[] points = new Point2D[N];
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                points[i] = new Point2D(x, y, i + 1);
            }
            GrahamScan graham = new GrahamScan(points);
            ArrayList<Integer> indexes = new ArrayList<Integer>();
            StringBuilder sb = new StringBuilder();
            for (Point2D p : graham.hull())
                indexes.add(p.id);
            Collections.sort(indexes);
            for (int i : indexes)
                sb.append(i + " ");
            System.out.println("Case #"+tc+": "+sb.toString().trim());
        }
    }

}