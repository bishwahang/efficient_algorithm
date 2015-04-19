package week12;

import java.util.Scanner;

/**
 * Created by hang on 18.01.15.
 */
public class Area51 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            Point[] vertices = new Point[n];
            for (int i = 0; i < n; i++) {
                vertices[i] = new Point(sc.nextDouble(), sc.nextDouble());
            }
            double sum = 0;
            for (int i = 0; i < n; i++) {
                Point a = vertices[i];
                Point b = vertices[(i + 1)% n];
                sum += (a.x * b.y) - (b.x * a.y);
            }
            System.out.println("Case #" + tc + ": " + Math.abs(sum / 2));
        }
    }
    static class Point{
        final double x;
        final double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
