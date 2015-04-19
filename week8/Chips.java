package week8;

import java.util.Arrays;
import java.util.Scanner;

/**
* Created by hang on 03.12.14.
*/
public class Chips {
    double[][] adj;
    int N;
    int twoN;
    int[] visited;
    int tc;

    public static void main(String[] args) {
        Chips chips = new Chips();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            Node[] nodes = new Node[n];
            for (int i = 0; i < n; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                nodes[i] = new Node(x, y);
            }
            double[][] adj = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    double dist = Math.sqrt(Math.pow(nodes[i].x - nodes[j].x, 2) + Math.pow(nodes[i].y - nodes[j].y, 2));
                    adj[i][j] = dist;
                    adj[j][i] = dist;
                }
            }
            chips.adj = adj;
            chips.N = n;
            chips.tc = tc;
            System.out.format("Case #%d: %s\n", tc, chips.calculateRoundTrip());
        }
    }

    private String calculateRoundTrip() {
        twoN = 2 * N;
        visited = new int[N];
        int[] route = new int[twoN];
        double bestDistance = Double.MAX_VALUE;
        int[] bestRoute = new int[twoN];
        Arrays.fill(bestRoute,-1);
        for (int i = 0; i < N; i++) {
            int value = i;
            for (int k = 0; k < N; k++) {
                visited[k] = 0;
                if (k == value) visited[k]++;
            }
            Arrays.fill(route, -1);
            int curNode = value;
            int index = 0;
            route[index++] = curNode;
            boolean wrongStart = false;
            while (notAllVisited()) {
                double min = Double.MAX_VALUE;
                int newCurNode = -1;
                for (int j = 0; j < N; j++) {
                    if (j != curNode && (route[(index+1) % twoN] != j ) && (visited[j] < 2) && adj[curNode][j] < min) {
                        min = adj[curNode][j];
                        newCurNode = j;
                    }
                }
                if(newCurNode == -1) {
                    wrongStart = true;
                    break;
                }
                route[index++] = newCurNode;
                visited[newCurNode]++;
                curNode = newCurNode;
            }
            if(wrongStart){ // we hit the wrong start start with another point
                continue;
            }else {
                double newDistance = calculateTotalDistance(route); // if new route is better exchange
                if (newDistance < bestDistance) {
                    bestDistance = newDistance;
                    for (int j = 0; j < bestRoute.length; j++) {
                        bestRoute[j] = route[j];
                    }
                }
            }
        }
        route = bestRoute;
        if(notFoundSolution(route)) return "impossible";
        boolean loop;
        do {
            loop = false;
            bestDistance = calculateTotalDistance(route);
            for (int i = 0; i < twoN - 1; i++) {
                boolean breakLoop = false;
                for (int k = i + 1; k < twoN; k++) {
                    int[] newRoute = twoOptSwap(route, i, k);
                    if(!checkSatisfiedCondition(newRoute)) continue; // if swapping does not give right conditioned route
                    double newDistance = calculateTotalDistance(newRoute);
                    if (newDistance < bestDistance) {
//                        System.out.println(newDistance + ", " + bestDistance);
                        route = newRoute;
                        loop = true;
                        // since new shortest route found break all loop and start again
                        breakLoop = true;
                        break;
                    }
                }
                if (breakLoop) break;
            }
        } while (loop);
//
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bestRoute.length; i++) {
            sb.append(route[i] + 1 + " ");
        }
        return sb.toString().trim();
    }
    // check if proper start solution was not found
    private boolean notFoundSolution(int[] route) {
        for (int i = 0; i < route.length; i++) {
            if(route[i] == -1) return true;
        }
        return false;
    }

    // calculate total distance
    double calculateTotalDistance(int[] tour) {
        double dist = 0;
        for (int i = 0; i < tour.length; i++) {
            dist += adj[tour[i]][tour[(i+1) % twoN]];
        }
        return dist;
    }

    boolean checkSatisfiedCondition(int[] list) {
        int[] visited = new int[N];
        for (int i = 0; i < twoN; i++) {
            if (list[i] == list[(i + 1) % twoN]) return false;
            visited[list[i]]++;
        }
        for (int i = 0; i < visited.length; i++) {
            if(visited[i] > 2) return false;
        }
        return true;
    }
    // two opt swap implementation
    int[] twoOptSwap(int[] tour, int i, int k) {
        int[] newRoute = new int[twoN];
        int index = 0;
        for (int j = 0; j < i; j++) {
            newRoute[index++] = tour[j];
        }
        for (int j = k; j >= i; j--) {
            newRoute[index++] = tour[j];
        }
        for (int j = k + 1; j < twoN; j++) {
            newRoute[index++] = tour[j];
        }
        return newRoute;
    }
    // function to check if all node was visited
    private boolean notAllVisited() {
        for (int i = 0; i < N; i++) {
            if (visited[i] < 2) return true;
        }
        return false;
    }
    // to hold data for adj matrix
    static class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
