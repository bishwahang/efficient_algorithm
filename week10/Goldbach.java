package week10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by hang on 19.12.14.
 */
public class Goldbach {
    boolean[] isprime;
    public static void main(String[] args) {
        Goldbach gb = new Goldbach();
        Scanner sc = new Scanner(System.in);
        int[] list = gb.getPrimes(100000000);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int N = sc.nextInt();
            ArrayList<Integer> result = new ArrayList<Integer>();
            StringBuilder sb = new StringBuilder();
            int left, right;
            if (N % 2 == 0) {

                int count = -1;
                for (int i = 0; i < list.length; i++) {
                    if(list[i] >= N) break;
                    count++;
                }
                left = 0;
                right = count;
                while (left <= right) {
                    if (list[left] + list[right] == N) break;
                    else if (list[left] + list[right] < N) left++;
                    else right--;
                }
                result.add(list[left]);
                result.add(list[right]);
            } else {
                if(gb.isprime[N - 4]){
                    result.add(2);
                    result.add(2);
                    result.add(N - 4);
                }else{
                    result.add(3);
                    int count = -1;
                    for (int i = 0; i < list.length; i++) {
                        if(list[i] >= N - 3) break;
                        count++;
                    }
                    left = 0;
                    right = count;
                    while (left <= right) {
                        if (list[left] + list[right] == N - 3) break;
                        else if (list[left] + list[right] < N - 3) left++;
                        else right--;
                    }
                    result.add(list[left]);
                    result.add(list[right]);
                }
            }
            Collections.sort(result);

            for (int i = 0; i < result.size(); i++) {
                sb.append(result.get(i) + " ");
            }
            System.out.println("Case #"+tc+": "+sb.toString().trim());
        }
    }
    int[] getPrimes(int N){
        isprime = new boolean[N];

        for (int i = 2; i < N; i++)
            isprime[i] = true;

        // determine primes < N using Sieve of Eratosthenes
        for (int i = 2; i * i < N; i++) {
            if (isprime[i]) {
                for (int j = i; i * j < N; j++)
                    isprime[i * j] = false;
            }
        }

        // count primes
        int primes = 0;
        for (int i = 2; i < N; i++)
            if (isprime[i]) primes++;


        // store primes in list
        int[] list = new int[primes];
        int n = 0;
        for (int i = 0; i < N; i++)
            if (isprime[i]) list[n++] = i;

        return list;
    }
}