package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hang on 22.11.14.
 */

public class Zombie {
    int N, W;
//    protected int solutionWeight = 0;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        Zombie zombie = new Zombie();
//        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            String[] firstLine = br.readLine().split(" ");
            int m = Integer.parseInt(firstLine[0]);
            int n = Integer.parseInt(firstLine[1]);
            zombie.W = m;
            int count = 0;
            ArrayList<Integer> items = new ArrayList<Integer>();
            ArrayList<Integer> value = new ArrayList<Integer>();
            ArrayList<Integer> name = new ArrayList<Integer>();

            for (int i = 1; i <= n; i++) {
                String[] iItem = br.readLine().split(" ");
                int p = Integer.parseInt(iItem[0]);
                int l = Integer.parseInt(iItem[1]);
                int s = Integer.parseInt(iItem[2]);
                name.add(p);
                for (int j = 1; j <= p; j++) {
                    items.add(l);
                    value.add(s);
                    count++;
                }
            }
            zombie.N = count;
            // calculate the solution
            sb.append("Case #" + tc + ": " + zombie.calcSolution(items, value, name) + "\n");
            if (tc < t) {
                br.readLine();
            }
        }
        System.out.print(sb.toString());
    }

    // source http://rosettacode.org/wiki/Knapsack_problem/Bounded#Java

    // calculte the solution of 0-1 knapsack problem with dynamic method:
    public String calcSolution(ArrayList<Integer> items, ArrayList<Integer> value, ArrayList<Integer> name) {
        int[] profit = new int[N + 1];
        int[] weight = new int[N + 1];
        int[] itemName = new int[N + 1];
        // generate random instance, items 1..N
        for (int n = 1; n <= N; n++) {
            profit[n] = value.get(n - 1);
            weight[n] = items.get(n - 1);
        }
        int start = 1;
        for (int i = 0; i < name.size(); i++) {
            int times = name.get(i);
            for (int j = start; (j< start + times && j < itemName.length) ; j++) {
                itemName[j] = i +1;
            }
            start = start + times;
        }

        int[][] opt = new int[N + 1][W + 1];
        boolean[][] sol = new boolean[N + 1][W + 1];

        for (int n = 1; n <= N; n++) {
            for (int w = 1; w <= W; w++) {

                // don't take item n
                int option1 = opt[n-1][w];

                // take item n
                int option2 = Integer.MIN_VALUE;
                if (weight[n] <= w) option2 = profit[n] + opt[n-1][w-weight[n]];

                // select better of two options
                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = (option2 > option1);

            }
        }

        // determine which items to take
        ArrayList<Integer> take = new ArrayList<Integer>();
        for (int n = N, w = W; n > 0; n--) {
            if (sol[n][w]) {
                take.add(itemName[n]);
                w = w - items.get(n - 1);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = take.size() - 1 ; i >= 0; i--) {
            sb.append(take.get(i) + " ");
        }
        return sb.toString().trim();
    }
}
