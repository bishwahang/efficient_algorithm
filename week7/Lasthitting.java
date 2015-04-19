package week7;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by hang on 24.11.14.
 */
public class Lasthitting {
    int[] health;
    int[] G;
    int N;
    HashMap<Integer, Integer>[][] memo;
    int towerPower;
    int flowerPower;

    public static void main(String[] args) {
        Lasthitting lh = new Lasthitting();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int k = sc.nextInt();
            int[] health = new int[n];
            int[] G = new int[n];
            int maxH = 0;
            for (int i = 0; i < n; i++) {
                int h = sc.nextInt();
                int g = sc.nextInt();
                if (h > maxH) maxH = h;
                health[i] = h;
                G[i] = g;
            }
            if (m == 0) {
                System.out.format("Case #%d: %d\n", tc, 0);
            } else {
                lh.memo = new HashMap[n][maxH + 1];
                lh.N = n;
                lh.towerPower = k;
                lh.flowerPower = m;
                lh.health = health;
                lh.G = G;
                System.out.format("Case #%d: %d\n", tc, lh.calcMaxGold(0, health[0], 1));
            }

        }
    }

    int calcMaxGold(int i, int remHP, int extraShots) {
        if (remHP <= 0 && i + 1 == N) return 0;
        if (remHP <= 0) return calcMaxGold(i + 1, health[i + 1], extraShots);
        System.out.println("here");
        if (memo[i][remHP] != null && memo[i][remHP].containsKey(extraShots)) {
            return memo[i][remHP].get(extraShots);
        }
        int ret = calcMaxGold(i, remHP - towerPower, extraShots + 1);
        if (extraShots > 0) {
            int gold = (remHP <= flowerPower) ? G[i] : 0;
            ret = Math.max(ret, gold + calcMaxGold(i, remHP - flowerPower, extraShots - 1));
        }
        if (memo[i][remHP] == null) {
            memo[i][remHP] = new HashMap<Integer, Integer>();
        }
        System.out.println("last");
        memo[i][remHP].put(extraShots, ret);
        return ret;
    }
}
