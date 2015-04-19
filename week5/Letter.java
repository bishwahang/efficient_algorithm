package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by hang on 12.11.14.
 */
public class Letter {
    int[] distance;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Letter l = new Letter();
        int t = Integer.parseInt(br.readLine());
        List<Edge> edges;
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            String[] pattern = new String[3];
            String[] firstLine = br.readLine().split(" ");
            int n = Integer.parseInt(firstLine[0]);
            int m = Integer.parseInt(firstLine[1]);
            pattern[0] = firstLine[2];
            pattern[1] = firstLine[3];
            pattern[2] = firstLine[4];
            String w = br.readLine();
            edges = new ArrayList<Edge>();
            for (int i = 0; i < m; i++) {
                String[] edgeString = br.readLine().split(" ");
                edges.add(new Edge(Integer.parseInt(edgeString[0]) - 1, Integer.parseInt(edgeString[1]) - 1, Integer.parseInt(edgeString[2])));
            }
            String res = l.getResult(n, pattern, w, edges);
            sb.append("Case #" + tc + ": " + res + "\n");
            if(tc < t){
                br.readLine();
            }
        }
        System.out.print(sb.toString());
    }

    private String getResult(int n, String[] pattern, String w, List<Edge> edges) {
        if (edges.size() == 0)
            return "";
        calculateDistance(n, edges);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n; i++) {
            int dist = distance[i];
            if (dist <= 0) {
                continue;
            }
            char[] text = getText(dist, w).toCharArray();
            SuffixArrayX s = new SuffixArrayX(text);
            ArrayList<Integer> index = new ArrayList<Integer>();
            for (int j = 0; j < pattern.length; j++) {
                int pos = s.rank(pattern[j]);
                if (pos >= 0) {
                    index.add(pos);
                }
            }
            Collections.sort(index);
            if(index.size() > 0){
                int start = index.get(0);
                int last = index.get(index.size() - 1);
                if (last - start >= 1) {
                    for (int j = start; j <= last ; j++) {
                        sb.append(text[j]);
                    }
                } else {
                    sb.append(text[start]);
                }
            }
        }
        return sb.toString();
    }

    private String getText(int step, String w) {
        StringBuilder sb = new StringBuilder();
        char[] ww = w.toCharArray();
        for (int i = step - 1; i < ww.length; i += step) {
            sb.append(ww[i]);
        }
        return sb.toString();
    }

    private void calculateDistance(int n, List<Edge> edges) {
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(edges.size(),
                new Comparator<Edge>() {
                    @Override
                    public int compare(Edge e1, Edge e2) {
                        return e1.weight - e2.weight;
                    }
                });
        boolean[] visited = new boolean[n];
        distance = new int[n];
        int start = 0;

        for (int i = 0; i < n; i++) {
            if (i == start) {
                visited[i] = true;
                distance[i] = 0;
            } else {
                visited[i] = false;
                distance[i] = -1;
            }
        }

        for (Edge e : edges) {
            if (e.v1 == start) {
                pq.add(e);
            }
        }
        while (!pq.isEmpty()) {
            Edge min = pq.poll();
            visited[min.v2] = true;
            distance[min.v2] = min.weight;
            for (Edge e : edges) {
                if (visited[e.v1] && !visited[e.v2]) {
                    int w = distance[e.v1] + e.weight;
                    pq.remove(e);
                    pq.add(new Edge(e.v1, e.v2, w));
                }
            }
        }
    }

    static class Edge {
        int v1;
        int v2;
        int weight;

        public Edge(int v1, int v2, int w) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = w;
        }
    }


    // source: http://algs4.cs.princeton.edu/63suffix/SuffixArrayX.java

    static class SuffixArrayX {
        private static final int CUTOFF = 5;   // cutoff to insertion sort (any value between 0 and 12)

        private final char[] text;
        private final int[] index;   // index[i] = j means text.substring(j) is ith largest suffix
        private final int N;         // number of characters in text


        public SuffixArrayX(char[] t) {
            N = t.length;
//            text = text + '\0';
            this.text = t;
            this.index = new int[N];
            for (int i = 0; i < N; i++)
                index[i] = i;
            sort(0, N - 1, 0);
        }

        // 3-way string quicksort lo..hi starting at dth character
        private void sort(int lo, int hi, int d) {

            // cutoff to insertion sort for small subarrays
            if (hi <= lo + CUTOFF) {
                insertion(lo, hi, d);
                return;
            }

            int lt = lo, gt = hi;
            char v = text[index[lo] + d];
            int i = lo + 1;
            while (i <= gt) {
                int t = text[index[i] + d];
                if (t < v) exch(lt++, i++);
                else if (t > v) exch(i, gt--);
                else i++;
            }

            // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
            sort(lo, lt - 1, d);
            if (v > 0) sort(lt, gt, d + 1);
            sort(gt + 1, hi, d);
        }

        // sort from a[lo] to a[hi], starting at the dth character
        private void insertion(int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++)
                for (int j = i; j > lo && less(index[j], index[j - 1], d); j--)
                    exch(j, j - 1);
        }

        // is text[i+d..N) < text[j+d..N) ?
        private boolean less(int i, int j, int d) {
            if (i == j) return false;
            i = i + d;
            j = j + d;
            while (i < N && j < N) {
                if (text[i] < text[j]) return true;
                if (text[i] > text[j]) return false;
                i++;
                j++;
            }
            return i > j;
        }

        // exchange index[i] and index[j]
        private void exch(int i, int j) {
            int swap = index[i];
            index[i] = index[j];
            index[j] = swap;
        }

        // return index of matched query in original string
        public int rank(String query) {
            int lo = 0, hi = N - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = compare(query, index[mid]);
                if (cmp < 0) hi = mid - 1;
                else if (cmp > 0) lo = mid + 1;
                else return mid;
            }
            return -1;
        }

        // is query < text[i..N) ?
        private int compare(String query, int i) {
            int M = query.length();
            int j = 0;
            while (i < N && j < M) {
                if (query.charAt(j) != text[i]) return query.charAt(j) - text[i];
                i++;
                j++;
            }
            if (i < N || j == M) return 0;
            return +1;
        }
    }
}
