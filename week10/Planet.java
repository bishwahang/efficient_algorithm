package week10;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by hang on 18.12.14.
 */
public class Planet {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            BigInteger n = sc.nextBigInteger();
            BigInteger m = sc.nextBigInteger();
            System.out.println("Case #"+tc+": "+n.gcd(m).toString());
        }

    }
}
