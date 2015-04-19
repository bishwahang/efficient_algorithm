package week8;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hang on 30.11.14.
 */
public class Sightseeing {
    int[][] adj;
    boolean[] visited;
    int N;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Sightseeing ss = new Sightseeing();
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            int[][] adj = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    adj[i][j] = sc.nextInt();
                }
            }
            ss.adj = adj;
            ss.N = n;
            System.out.format("Case #%d: %s\n",tc,ss.calculateRoundTrip());
        }
    }

    private String calculateRoundTrip() {
        int value = N - 1; // choose a starting value
        visited = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (i == value) visited[i] = true;
            else visited[i] = false;
        }
        int curCity = value;
        ArrayList<Integer> tour = new ArrayList<Integer>();
        while (notAllVisited()) {
            int min = Integer.MAX_VALUE;
            int newCurCity = -1;
            for (int i = 0; i < N; i++) {
                if (i != curCity && !visited[i] && adj[curCity][i] < min) {
                    min = adj[curCity][i];
                    newCurCity = i;
                }
            }
            tour.add(newCurCity);
            visited[newCurCity] = true;
            curCity = newCurCity;
        }
        tour.add(value);
        boolean loop;
        do {
            loop = false;
            int bestDistance = calculateTotalDistance(tour);
            for (int i = 0; i < N - 1; i++) {
                boolean breakLoop = false;
                for (int k = i + 1; k < N; k++) {
                    ArrayList<Integer> newRoute = twoOptSwap(tour, i, k);
                    int newDistance = calculateTotalDistance(newRoute);
                    if (newDistance < bestDistance) {
//                        System.out.println(newDistance + ", " + bestDistance);
//                        System.out.println(newRoute.toString());
                        tour = newRoute;
//                        System.out.println(tour.toString());
                        loop = true;
                        // since new shortest route found break all loop and start again
                        breakLoop = true;
                        break;
                    }
                }
                if (breakLoop) break;
            }
        } while (loop);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tour.size(); i++) {
            sb.append(tour.get(i) + 1 + " ");
        }
        return sb.toString().trim();
    }

    private int calculateTotalDistance(ArrayList<Integer> tour) {
        int dist = 0;
        for (int i = 0; i < tour.size(); i++) {
            dist += adj[tour.get(i)][tour.get((i + 1) % N)];
        }
        return dist;
    }
    private ArrayList<Integer> twoOptSwap(ArrayList<Integer> tour, int i ,int k) {
        ArrayList<Integer> newRoute = new ArrayList<Integer>();
        for (int j = 0; j < i; j++) {
            newRoute.add(tour.get(j));
        }
        for (int j = k; j >= i; j--) {
            newRoute.add(tour.get(j));
        }
        for (int j = k+1 ; j < N; j++) {
            newRoute.add(tour.get(j));
        }
        return newRoute;
    }

    private boolean notAllVisited() {
        for (int i = 0; i < N; i++) {
            if(visited[i] == false) return true;
        }
        return false;
    }
}
