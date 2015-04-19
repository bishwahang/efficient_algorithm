package week10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hang on 21.12.14.
 */
public class Attic {
    public static void main(String[] args) {
        Attic attic = new Attic();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            long k = sc.nextLong();
            System.out.println("Case #"+tc+": "+attic.mainLoop(k));
        }
    }

    long mainLoop(long k) {
        long possibleX = k + 1;
        while (true) {
            if (isPrime(possibleX)) {
                ArrayList<Long> perms = getUniquePermutations(possibleX);
                int countPrimePerms = 0;
                for (long x : perms) {
                    if(isPrime(x)) {
                        countPrimePerms++;
                    }
                }
                if (countPrimePerms == perms.size() || countPrimePerms > Math.log10(possibleX)) {
                    break;
                }
            }
            possibleX++;
        }
        return possibleX;
    }

    ArrayList<Long> getUniquePermutations(long i) {
        ArrayList<Long> perms = new ArrayList<Long>();
        List<Integer> digits = new ArrayList<Integer>();
        while (i > 0) {
            digits.add((int) i % 10);
            i /= 10;
        }
        Collections.sort(digits);
        long value = 0;
        for (int x : digits) {
            value = value * 10 + x;
        }
        perms.add(value);
        while(true) {
            // find k
            int k = -1;
            for (int j = 0; j < digits.size() - 1; j++) {
                if (digits.get(j) < digits.get(j + 1)) {
                    k = j;
                }
            }
            if(k == -1){
                break;
            }
            // find l
            int l = -1;
            for (int j = k + 1; j < digits.size(); j++) {
                if (digits.get(k) < digits.get(j)) {
                    l = j;
                }
            }
            // swap values of k and l
            Collections.swap(digits, k, l);
            int left = k + 1;
            int right = digits.size() - 1;
            while (left < right) {
                Collections.swap(digits, left, right);
                left++;
                right--;
            }

            value = 0;
            for (int d : digits) {
                value = value * 10 + d;
            }
            perms.add(value);

        }
        return perms;
    }

    boolean isPrime(long n) {
        if(n == 2) return true;
        if(n % 2 == 0) return false;
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}
