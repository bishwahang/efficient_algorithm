package week8;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by hang on 07.12.14.
 */
public class TimeTravel {
    int N, M, K;
    ArrayList<Integer> items;
    int[] glueWeight;
    int[][] shops;
    boolean[] itemTaken;
    Random rand;

    public static void main(String[] args) {
        TimeTravel tt = new TimeTravel();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int k = sc.nextInt();
            ArrayList<Integer> items = new ArrayList<Integer>();
            for (int i = 0; i < k; i++) {
                items.add(sc.nextInt() - 1);
            }
            int[] glueWeight = new int[n];
            int[][] shops = new int[n][m];
            for (int i = 0; i < n; i++) {
                glueWeight[i] = sc.nextInt();
                for (int j = 0; j < m; j++) {
                    shops[i][j] = sc.nextInt();
                }
            }
            tt.N = n;
            tt.M = m;
            tt.K = k;
            tt.items = items;
            tt.glueWeight = glueWeight;
            tt.shops = shops;
            System.out.format("Case #%d: %s\n", tc, tt.goShopping());

        }
    }

    private String goShopping() {
        // some short circuit
        // if shopping list is zero
        if (K == 0) return "nothing";
        // also if number of items to be sold is zero but K is not zero
//        if (M == 0) return "impossible";

        int[] chosenShopIndex = new int[K];
        for (int i = 0; i < K; i++) {
            chosenShopIndex[i] = -1;
        }
        itemTaken = new boolean[K];

        // get the first heuristic solution
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (shops[i][j] != 0 && items.contains(j)) {
                    int itemIndex = items.indexOf(j);
                    if (!itemTaken[itemIndex]) {
                        chosenShopIndex[itemIndex] = i;
                        itemTaken[itemIndex] = true;
                    }
                }
            }
            if (!notAllShopped()) {
                break;
            }
        }
        // if cannot find even a simple solution its not possible to find further
        if (notAllShopped()) {
            return "impossible";
        }
//
//        int iteration = 0;
//        int[] bestShopIndex = new int[K];
//        for (int i = 0; i < K; i++) {
//            bestShopIndex[i] = chosenShopIndex[i];
//        }
        rand = new Random();
        double temperature = 100000.0;
        double deltaWeight = 0;
        double coolingRate = 0.99;
        double absoluteTemperature = 1;
        double curWeight = calculateCurrentWeight(chosenShopIndex);
        int kOpt = 0;
        int l = 0;
        while (temperature > absoluteTemperature) {
            for (int i = 0; i < K; i++) {
                int curShop = chosenShopIndex[i];
                int[] newShopIndex = new int[K];
                for (int k = 0; k < K; k++) {
                    newShopIndex[k] = chosenShopIndex[k];
                }
                for (int j = 0; j < N ; j++) {
                    curShop = (curShop + 1) % N;
                    newShopIndex[i] = curShop;
                    if(!checkSatisfiedCondition(newShopIndex)) continue;
                    deltaWeight = calculateCurrentWeight(newShopIndex) - curWeight;
//                    System.out.println(Math.exp(-deltaWeight / temperature));
                    if ((deltaWeight <= 0) || (curWeight > 0 &&
                            Math.exp(-deltaWeight / temperature) > rand.nextDouble())) {
                        for (int k = 0; k < K; k++){
                            chosenShopIndex[k] = newShopIndex[k];
                        }
                        curWeight = deltaWeight + curWeight;
                        l++;
                        if(l > N){
                            l = 0;
                            temperature *= coolingRate;
                        }
                        break;
                    }
                }
            }
            kOpt++;
            if(kOpt > N){
                kOpt = 0;
                temperature *= coolingRate;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < K; i++) {
            sb.append(chosenShopIndex[i] + 1 + " ");
        }
        return sb.toString().trim();
    }

    private int[] kOptSwap(int[] chosenShopIndex) {
        int[] newChoice = new int[K];
        for (int i = 0; i < K; i++) { // change all the choices
            boolean found = false;
            int curShop = chosenShopIndex[i];
            int checkIndex = curShop;
            for (int j = 0; j < N - 1; j++) { // loop through all possible shops
                checkIndex = (checkIndex + 1) % N;
                if (shops[checkIndex][items.get(i)] != 0) {
//                    System.out.println(shops[curShop][items.get(i)]);
                    int newWeight = shops[j][items.get(i)];
                    int oldWeight = shops[curShop][items.get(i)];
                    if (newWeight < oldWeight || glueWeight[j] < glueWeight[curShop]) { // if new shop has lower weight of item or glue try it
                        found = true;
                        newChoice[i] = checkIndex;
                        break;
                    }
                }
            }
            if (!found) { // if none optimal condition meet, just try next possible solution
                while (true) {
                    curShop = (curShop + 1) % N;
                    if(shops[curShop][items.get(i)] > 0 ) break;
                }
                newChoice[i] = curShop;
            }
        }
        return newChoice;
    }

    double calculateCurrentWeight(int[] shopsChosen) {
        double sum = 0;
        boolean[] glueWtAdded = new boolean[N];
        for (int i = 0; i < K; i++) {
            int shopIndex = shopsChosen[i];
            int itemWeight = shops[shopIndex][items.get(i)];
            sum += itemWeight;
            if (!glueWtAdded[shopIndex]) {
                sum += glueWeight[shopIndex];
                glueWtAdded[shopIndex] = true;
            }
        }
        return sum;
    }

    boolean notAllShopped() {
        for (int i = 0; i < K; i++) {
            if (itemTaken[i] == false) return true;
        }
        return false;
    }

    boolean checkSatisfiedCondition(int[] chosenShops) {
        for (int i = 0; i < K; i++) {
            int shopIndex = chosenShops[i];
            if (shops[shopIndex][items.get(i)] == 0) return false;
        }
        return true;
    }
}
