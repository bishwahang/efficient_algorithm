package week11;

import java.util.Scanner;

/**
 * Created by hang on 14.01.15.
 */
public class WTF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            System.out.println("Case #" + tc + ": ");
            int n = sc.nextInt();
            int k = sc.nextInt();
            Edge[] edges = new Edge[n];
            int index = 0;
            double x, y;
            for (int i = 0; i < n; i++) {
                edges[i] = new Edge(new Point2D(sc.nextDouble(), sc.nextDouble()), new Point2D(sc.nextDouble(), sc.nextDouble()), i + 1);
            }
//            System.out.println("source: "+edges[0].s.x+","+edges[0].s.y+" and destination: "+edges[0].d.x+", "+edges[0].d.y+" and index: "+edges[0].index);
            for (int i = 0; i < n; i++) {
                Edge a = edges[i];
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        continue;
                    }
                    Edge b = edges[j];
                    if (Edge.intersect(a, b)) {
                    }

                }
            }
        }
    }

    public static class Edge {
        private final Point2D s;
        private final Point2D d;
        private final int index;

        public Edge(Point2D s, Point2D d, int index) {
            this.s = s;
            this.d = d;
            this.index = index;
        }

        public static boolean intersect(Edge a, Edge b) {

            double det = (b.d.x * a.d.y) - (b.d.y * a.d.x);
            if (det == 0) {
                return false;
            }
            double u = (((a.s.x * a.d.y) + (a.d.x * b.s.y)) - ((a.d.x * a.s.y) + (b.s.x * a.d.y))) / det;
            double v = ((b.s.x + (b.d.x * u)) - a.s.x) / a.d.x;
            if (u >= 0 && v >= 0) {
                System.out.println(b.s.y + (b.d.y * v));
                System.out.println(b.s.y + (b.d.y * v));
                return true;
            }
            return false;
        }
    }

    public static class Point2D {
        private final double x;    // x coordinate
        private final double y;    // y coordinate

        public Point2D(double x, double y) {
            if (x == 0.0) x = 0.0;  // convert -0.0 to +0.0
            if (y == 0.0) y = 0.0;  // convert -0.0 to +0.0
            this.x = x;
            this.y = y;
        }
    }
}
