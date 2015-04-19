package week12;

import java.util.Scanner;

/**
 * Created by hang on 16.01.15.
 */
public class Euler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            System.out.println("Case #" + tc + ":");
            Point p1 = new Point(sc.nextDouble(), sc.nextDouble(), 1.0);
            Point p2 = new Point(sc.nextDouble(), sc.nextDouble(), 1.0);
            Point p3 = new Point(sc.nextDouble(), sc.nextDouble(), 1.0);
            Point result = findCentroid(p1, p2, p3);
            System.out.println(result.x + " " + result.y);
            result = findOrthoCenter(p1, p2, p3);
            System.out.println(result.x + " " + result.y);
            result = findCircumCenter(p1, p2, p3);
            System.out.println(result.x + " " + result.y);

        }
    }

    private static Point findCentroid(Point p1, Point p2, Point p3) {
        Point p = new Point();
        p.x = (p1.x + p2.x + p3.x) / 3.0;
        p.y = (p1.y + p2.y + p3.y) / 3.0;
        p.z = (p1.z + p2.z + p3.z) / 3.0;
        return normalizePoint(p);
    }

    private static Point normalizePoint(Point p) {
        Point result = new Point();
        result.x = p.x / p.z;
        result.y = p.y / p.z;
        result.z = 1;
        return result;
    }

    private static Point findCrossProduct(Point a, Point b) {
        Point result = new Point();
        result.x = a.y * b.z - b.y * a.z;
        result.y = b.x * a.z - a.x * b.z;
        result.z = a.x * b.y - b.x * a.y;
        return result;
    }

    private static Point findPerpendicular(Point a) {
        Point result = new Point();
        result.x = a.y;
        result.y = -a.x;
        result.z = a.z;
        return result;
    }

    private static Point findOrthoCenter(Point p1, Point p2, Point p3) {
        Point d = new Point(0.0, 0.0, 1.0);

        Point l1 = findCrossProduct(findCrossProduct(p1, p2), d);
        Point m1 = findCrossProduct(findPerpendicular(l1), p3);

        Point l2 = findCrossProduct(findCrossProduct(p1, p3), d);
        Point m2 = findCrossProduct(findPerpendicular(l2), p2);
        return normalizePoint(findCrossProduct(m1, m2));
    }

    private static Point findCircumCenter(Point p1, Point p2, Point p3) {

        Point mid = new Point();
        mid.x = (p1.x + p2.x) / 2.0;
        mid.y = (p1.y + p2.y) / 2.0;
        mid.z = (p1.z + p2.z) / 2.0;
        Point d = new Point(0.0, 0.0, 1.0);

        Point l1 = findCrossProduct(findCrossProduct(p1, p2), d);
        Point m1 = findCrossProduct(findPerpendicular(l1), mid);

        mid.x = (p1.x + p3.x) / 2;
        mid.y = (p1.y + p3.y) / 2;
        mid.z = (p1.z + p3.z) / 2;
        Point l2 = findCrossProduct(findCrossProduct(p1, p3), d);
        Point m2 = findCrossProduct(findPerpendicular(l2), mid);
        return normalizePoint(findCrossProduct(m1, m2));
    }

    static class Point {
        double x;
        double y;
        double z;

        public Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point() {
        }
    }
}
