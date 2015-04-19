package week10;

import java.util.Scanner;

/**
 * Created by hang on 20.12.14.
 */
public class Nathlon {
    public static void main(String[] args) {
        Nathlon nt = new Nathlon();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            long k = sc.nextLong();
            long[] mods = new long[n];
            long[] remainders = new long[n];
            long mod = 1;
            for (int i = 0; i < n; i++) {
                mods[i] = sc.nextLong();
                mod *= mods[i];
                remainders[i] = sc.nextLong();
            }
            long ea = 0;
            for (int i = 0; i < n; i++) {
                long a = mods[i];
                long b = 1;
                for (int j = 0; j < n; j++) {
                    if (i != j) b *= mods[j];
                }
                ea += nt.performEea(a, b)[1] * b * remainders[i];
            }
            int count = 0;
            long result = ea;
            while ((ea + (mod * count)) <= k ) {
                result = ea + (mod * count);
                count++;
            }
            if (result < 0 || result > k) {
                System.out.println("Case #" + tc + ": impossible");
            }else{
                System.out.println("Case #" + tc + ": " + result);
            }

        }
    }

    long[] performEea(long a, long b) {
        long[] result = new long[2];
        // old
        long old_r = a;
        long old_s = 1;
        long old_t = 0;

        // new
        long r = b;
        long s = 0;
        long t = 1;
        while (r != 0) {
            long q = old_r / r;
            long tempr = old_r % r;
            long temps = old_s - (q * s);
            long tempt = old_t - (q * t);
            // new becomes old
            old_r = r;
            old_s = s;
            old_t = t;

            // temp becomes new
            r = tempr;
            s = temps;
            t = tempt;
        }
        result[0] = old_s;
        result[1] = old_t;
//        System.out.println(old_s + " " + old_t);
        return result;

    }
}
