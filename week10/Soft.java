package week10;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hang on 01.01.15.
 */
public class Soft {
    int triangle[][];
    int[] primeFactors = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23};
    int mod = 223092870;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Soft s = new Soft();
        s.generateTriangle2DArray(24);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            if( n <= 23) {
                System.out.println("Case #" + tc + ": " + s.triangle[n][m]);
            }else{

                System.out.println("Case #" + tc + ": " + s.findCombinations(n, m));
            }

        }
    }
    String findCombinations(int n, int k) {
        int[] remainders = new int[9];
        int count = 0;
        for (int p : primeFactors) {
            ArrayList<Integer> ni = convertToBase(n, p);
            ArrayList<Integer> ki = convertToBase(k, p);
            if (ni.size() > ki.size()) {
                int diff = ni.size() - ki.size();
                for (int i = 0; i < diff; i++) {
                    ki.add(0);
                }
            }else if(ki.size() > ni.size()){
                int diff = ni.size() - ki.size();
                for (int i = 0; i < diff; i++) {
                    ni.add(0);
                }
            }
            int product = 1;
            for (int i = 0; i < ki.size(); i++) {
                int n_i = ni.get(i);
                int k_i = ki.get(i);
                if(n_i < k_i) {
                    product = 0;
                    break;
                }
                product *= triangle[n_i][k_i];
                product %= p;
            }
            remainders[count++] = product % p;
        }
        long ea = 0;
        for (int i = 0; i < primeFactors.length; i++) {
            long a = primeFactors[i];
            long b = 1;
            for (int j = 0; j < primeFactors.length; j++) {
                if (i != j) b *= primeFactors[j];
            }
            ea += performEea(a, b)[1] * b * remainders[i];
        }
        int c = 0;
        long result = ea;
        while (result < 0 ) {
            result = ea + (mod * c);
            c++;
        }
        return result % mod + "";
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
    ArrayList<Integer> convertToBase(int number, int base){
        ArrayList<Integer> digits = new ArrayList<Integer>();
        while (number > 0) {
            digits.add( number % base);
            number /= base;
        }
        return digits;
    }

    void generateTriangle2DArray(int maxRows) {
        triangle = new int[maxRows] [maxRows];
        int[] previousRow;
        int[] currentRow = {1};
        int index = 0;
        triangle[index++] = currentRow;
        previousRow = currentRow;
        for (int i = 2; i <= maxRows; i++) {
            currentRow = new int[i];
            currentRow[0] = 1;
            currentRow[i - 1] = 1;
            for (int j = 0; j <= i - 3; j++) {
                currentRow[j + 1] = previousRow[j] + previousRow[j + 1];
            }
            triangle[index++] = currentRow;
            previousRow = currentRow;
        }
    }
}
