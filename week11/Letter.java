package week11;

import java.util.Scanner;

/**
 * Created by hang on 13.01.15.
 */
public class Letter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            Point2D[] vertices = new Point2D[n];
            double sumX = 0;
            double sumY = 0;
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                vertices[i] = new Point2D(x, y);
                sumX += x;
                sumY += y;
            }
            double centroidX = sumX / n;
            double centroidY = sumY / n;
            int totalWays = 0;
            for (int i = 0; i < n; i++) {
                int j = (i + 1) % n;
                Point2D currA = vertices[i];
                Point2D currB = vertices[j];
                int count = 0;
                int index = (j + 1) % n;
                int defaultCCW = Point2D.ccw(currA, currB, vertices[index]);
                if(defaultCCW == 0){
                    continue;
                }
                index = (index + 1) % n;
                boolean allInOneSide = true;
                while (count < (n - 3)) {
                    int check = Point2D.ccw(currA, currB, vertices[index]);
                    if (check != defaultCCW) {
                        allInOneSide = false;
                        break;
                    }
                    index = (index + 1) % n;
                    count++;
                }
                if(allInOneSide) {
                    double[] v0 = new double[]{centroidX - currA.x, centroidY - currA.y};
                    double[] v2 = new double[]{currB.x - currA.x, currB.y - currA.y};
                    double fallsOnEdge = Point2D.dotProd(v0, v2) / Point2D.dotProd(v2, v2);
                    if(fallsOnEdge < 1 && fallsOnEdge > 0){
                        totalWays++;
                    }
                }
            }
            System.out.println("Case #" + tc + ": " + totalWays);
        }
    }

    public static class Point2D{
        private final double x;    // x coordinate
        private final double y;    // y coordinate

        public Point2D(double x, double y) {
            if (x == 0.0) x = 0.0;  // convert -0.0 to +0.0
            if (y == 0.0) y = 0.0;  // convert -0.0 to +0.0
            this.x = x;
            this.y = y;
        }
        public static int ccw(Point2D a, Point2D b, Point2D c) {
            double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
            if      (area2 < 0) return -1;
            else if (area2 > 0) return +1;
            else                return  0;
        }

        public static double dotProd(double[] a, double[] b){
            double sum = 0;
            for(int i = 0; i < a.length; i++){
                sum += a[i] * b[i];
            }
            return sum;
        }
    }
}
