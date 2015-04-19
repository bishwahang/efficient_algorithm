package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hang on 12.11.14.
 */
public class Computer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        Computer c = new Computer();
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            String contents = br.readLine();
            String[] secondLine = br.readLine().split(" ");
            int m = Integer.parseInt(secondLine[0]);
            int n = Integer.parseInt(secondLine[1]);
            String[] parts = new String[n];
            for (int i = 0; i < n; i++) {
                parts[i] = br.readLine();
            }
            if (tc < t) {
                br.readLine();
            }
            String res = c.getResult(contents, m, parts);
            String st = "Case #" + tc + ": " + res + "\n";
            sb.append(st);

        }
        System.out.println(sb.toString());
    }

    private String getResult(String contents, int m, String[] parts) {
        int curMinPos = 0;
        int curMaxCount = 0;
        int start = 0;
        for (int i = 0; i < contents.length()/m; i ++) {
            int end = start + m;
            if(end > contents.length()) continue;
            String text = contents.substring(start, end);
            SuffixArrayX s = new SuffixArrayX(text);
            int count = 0;
            for (int j = 0; j < parts.length; j++) {
                int L = s.getL(parts[j]); // get min left bound
                int R = s.getR(parts[j]); // get max right bound
                count += (R - L + 1); // number of occurrence of pattern
            }
            if (count > 0 && count > curMaxCount) { // if new count is greater then old change the curMinPos
                curMaxCount = count;
                curMinPos = start;
            }
            start = end;
        }
        return curMinPos + "";
    }

    static class SuffixArrayX {
        // source: http://algs4.cs.princeton.edu/63suffix/SuffixArrayX.java
        private static final int CUTOFF = 5;   // cutoff to insertion sort (any value between 0 and 12)

        private final char[] text;
        private final int[] index;   // index[i] = j means text.substring(j) is ith largest suffix
        private final int N;         // number of characters in text


        public SuffixArrayX(String text) {
            N = text.length();
            text = text + '\0';
            this.text = text.toCharArray();
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

        // return the highest index of prefix match
        public int getR(String query) {
            int lo = 0, hi = N - 1;
            while (hi - lo > 1) {
                int mid = lo + (hi - lo) / 2;
                int cmp = compare(query, index[mid]);
                if (cmp < 0) hi = mid;
                else lo = mid;
            }
            return lo;
        }

        // return the lowest index of prefix match
        public int getL(String query) {
            int lo = 0, hi = N - 1;
            while (hi - lo > 1) {
                int mid = lo + (hi - lo) / 2;
                int cmp = compare(query, index[mid]);
                if (cmp > 0) lo = mid;
                else hi = mid;
            }
            return hi;
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
