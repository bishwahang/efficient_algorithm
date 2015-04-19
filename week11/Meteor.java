package week11;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by hang on 09.01.15.
 */
public class Meteor {
    public static void main(String[] args) {
        Meteor m = new Meteor();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int impact_x = sc.nextInt();
            int impact_y = sc.nextInt();
            Point p = new Point(impact_x, impact_y);
            int n = sc.nextInt();
            Point point = null;
            Point[] vertices = new Point[2 * n];
            int index = 0;
            for (int i = 0; i < n; i++) {
                int x1 = sc.nextInt();
                int y1 = sc.nextInt();
                vertices[index++] = new Point(x1, y1);
                int x2 = sc.nextInt();
                int y2 = sc.nextInt();
                vertices[index++] = new Point(x2, y2);
            }
            int j = 1;
            boolean oddNodes = false;
            for (int i = 0; i < 2 * n; i += 2) {
                if ((vertices[i].getY() < p.getY() && vertices[j].getY() >= p.getY()) ||
                        (vertices[j].getY() < p.getY() && vertices[i].getY() >= p.getY())) {
                    if (vertices[i].getX() +
                            (p.getY() - vertices[i].getY()) / (vertices[j].getY() - vertices[i].getY()) * (vertices[j].getX() - vertices[i].getX()) < p.getX()) {
                        oddNodes = !oddNodes;
                    }
                }
                j += 2;
            }

            if (oddNodes) {
                System.out.println("Case #" + tc + ": " + "jackpot");
            } else {
                System.out.println("Case #" + tc + ": " + "too bad");
            }

        }
    }
}
