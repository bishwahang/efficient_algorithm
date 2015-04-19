package week6;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by hang on 16.11.14.
 */
public class Prime {
    public static void main(String[] args) {
        Prime p = new Prime();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            BigInteger num = sc.nextBigInteger();
            System.out.format("Case #%d: %s", p.getResult(num));
        }
    }

    private String getResult(BigInteger num) {
        for (BigInteger bi = BigInteger.valueOf(0);
             bi.compareTo(BigInteger.ZERO) > 0;
             bi = bi.subtract(BigInteger.ONE)) {

            System.out.println(bi);
        }
        num.isProbablePrime(1);
        return null;
    }
}
