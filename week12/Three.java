package week12;

import java.util.Scanner;

/**
 * Created by hang on 17.01.15.
 */
public class Three {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            double x1 = sc.nextDouble();
            double y1 = sc.nextDouble();
            double x2 = sc.nextDouble();
            double y2 = sc.nextDouble();
            double x3 = sc.nextDouble();
            double y3 = sc.nextDouble();
            double realArea = Math.abs((((x1 * (y2 - y3)) - (x2 * (y1 - y3)) + (x3 * (y1 - y2))) * 0.5));
            double upperArea = 3.0 * Math.ceil(realArea / 3.0);
            double lowerArea = 3.0 * Math.floor(realArea / 3.0);
            double area = (realArea - lowerArea) < (upperArea - realArea) ? lowerArea : upperArea;
            double realNumber = area / 3.0;
            double upperNumber = 3.0 * Math.ceil(realNumber / 3.0);
            double lowerNumber = 3.0 * Math.floor(realNumber / 3.0);
            double number = (realNumber - lowerNumber) < (upperNumber - realNumber) ? lowerNumber : upperNumber;
            System.out.println("Case #" + tc + ": " + (int) number);
        }
    }
}
