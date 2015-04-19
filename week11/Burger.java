package week11;

import java.util.Scanner;

/**
 * Created by hang on 12.01.15.
 */
public class Burger {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int r = sc.nextInt();
            int n = sc.nextInt();
            if(n > 1){

                double sin = Math.sin(Math.PI / n);
                double R = r * ((1 - sin)) / sin;
                double plateRadius = Math.ceil(R + 2 * r);
                System.out.println("Case #"+tc+": "+(int)plateRadius);
            }else{
                System.out.println("Case #"+tc+": "+ r);

            }
        }
    }
}
