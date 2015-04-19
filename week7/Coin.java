package week7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by hang on 22.11.14.
 */
public class Coin {
    int OPT[][];

    ArrayList<Integer> optimalChange[][]; // string representation of optimal solutions.
    int[] denoms;
    int[] lastcoins;
    int target;
    String[][] sequence;

    public static void main(String[] args) throws IOException {
        Coin coin = new Coin();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            int c = sc.nextInt();
            int[] denoms = new int[n];
            for (int j = 0; j < n; j++) {
                denoms[j] = sc.nextInt();
            }
            coin.denoms = denoms;
            coin.target = c;
            sb.append("Case #" + tc + ": " + coin.findOptimalChange()+"\n");
        }
        System.out.print(sb.toString());
    }
    // source : http://www.codeproject.com/Articles/31002/Coin-Change-Problem-Using-Dynamic-Programming

    String findOptimalChange() {
        StringBuilder sb = new StringBuilder();
        OPT = new int[denoms.length][target + 1];
        optimalChange = new ArrayList[denoms.length][target + 1];
        int actualAmount;
        int m = denoms.length + 1;
        int n = target + 1;
        int inf = Integer.MAX_VALUE - 1;
        lastcoins = new int[target + 1];
        int[] coinsUsed = new int[target + 1];
        int[] lastCoin = new int[target + 1];
        coinsUsed[0] = 0;
        lastCoin[0] = 1;

        for (int cents = 1; cents <= target; cents++) {
            int minCoins = cents;
            int newCoin = 1;

            for (int j = 0; j < denoms.length; j++) {
                if (denoms[j] > cents)   // Cannot use coin j
                    continue;
                if (coinsUsed[cents - denoms[j]] + 1 < minCoins) {
                    minCoins = coinsUsed[cents - denoms[j]] + 1;
                    newCoin = denoms[j];
                }
            }
            coinsUsed[cents] = minCoins;
            lastCoin[cents] = newCoin;
        }
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        int current = lastCoin[target];
        int count = 1;
        int pointer = target - lastCoin[target];
        while (pointer>0){
            if (current == lastCoin[pointer]) {
                count++;
            } else {
                hashMap.put(current, count);
                current = lastCoin[pointer];
                count = 1;
            }
            pointer -= lastCoin[pointer];
        }
        hashMap.put(current, count);
        sb = new StringBuilder();
        for (int i = 0; i < denoms.length; i++) {
            if (hashMap.containsKey(denoms[i])) {
                sb.append(hashMap.get(denoms[i]) + " ");
            } else {
                sb.append(0 + " ");
            }
        }
        return  sb.toString();
    }
}
