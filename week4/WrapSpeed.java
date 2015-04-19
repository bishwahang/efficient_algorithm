package week4;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by hang on 01.11.14.
 */
public class WrapSpeed {
    public static void main(String[] args) {
        WrapSpeed ws = new WrapSpeed();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        BigInteger result;
        for (int tc = 1; tc <= t; tc++) {
            result = sc.nextBigInteger();
            int k = sc.nextInt();
            int N = sc.nextInt();
            int m = sc.nextInt();
            BigInteger x = sc.nextBigInteger();
            int times = N / m;
            int left = N % m;
            for (int i = 0; i < times; i++) {
                for (int j = 0; j < m ; j++) {
                    result = result.multiply(BigInteger.valueOf(k));
                }
                result = result.add(x);
            }
            for (int i = 0; i < left; i++) {
                result = result.multiply(BigInteger.valueOf(k));
            }
            System.out.format("Case #%d: %s\n", tc, result.toString());
        }
        sc.close();
    }
}
