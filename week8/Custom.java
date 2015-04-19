package week8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by hang on 30.11.14.
 */
public class Custom {
    ArrayList<Integer>[] vertices;
    int N;

    public static void main(String[] args) {
        Custom c = new Custom();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            ArrayList<Integer>[] vertices = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                int times = sc.nextInt();
                ArrayList<Integer> neighbours = new ArrayList<Integer>(times);
                for (int j = 0; j < times; j++) {
                    int v = sc.nextInt() - 1;
                    neighbours.add(v);
                }
                vertices[i] = neighbours;
            }
            c.vertices = vertices;
            c.N = n;
            System.out.format("Case #%d:\n%s\n", tc, c.calculateMaxCut());
        }
    }

    private String calculateMaxCut() {
        ArrayList<Integer> A = new ArrayList<Integer>(); // set A
        ArrayList<Integer> B = new ArrayList<Integer>(); // set B
        for (int i = 0; i < N; i++) { // arbitrarily put vertex in set A and B
            if (i % 2 == 0) A.add(i);
            else B.add(i);
        }
        while (true) {
            boolean flag = true;
            for (int i = 0; i < N; i++) {
                ArrayList<Integer> edges = vertices[i];
                int Cv = 0;
                int Dv = 0;
                boolean inA = A.contains(i);
                for (int j = 0; j < edges.size(); j++) { // count inside and outside edges of particular vertex
                    int incidentEdge = edges.get(j);
                    if (inA) {
                        if (A.contains(incidentEdge)) {
                            Dv++;
                        } else {
                            Cv++;
                        }
                    } else {
                        if (B.contains(incidentEdge)) {
                            Dv++;
                        } else {
                            Cv++;
                        }
                    }

                } // end for edge
                if (Dv > Cv) {
                    if (inA) {
                        int index = A.indexOf(i);
                        A.remove(index);
                        B.add(i);
                    } else {
                        int index = B.indexOf(i);
                        B.remove(index);
                        A.add(i);
                    }
                    flag = false; // at least one vertices found to swap
                    break;
                }
            } // end for vertices

            if (flag) { // if none was found we have somewhat optimal solution
                StringBuilder sb = new StringBuilder();
                Collections.sort(A);
                for (int i = 0; i < A.size(); i++) {
                    sb.append(A.get(i) + 1 + " ");
                }
                return sb.toString().trim();
            }
        }
    }
}
