package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hang on 09.11.14.
 */
public class Shakespeare {
    SuffixArrayX suffix;
    public Shakespeare(SuffixArrayX suffix) {
        this.suffix = suffix;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SuffixArrayX suffix = new SuffixArrayX(br.readLine());

        Shakespeare s = new Shakespeare(suffix);
//        s.printSuffix();
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            String res =  s.findSonnet(br.readLine());
            String st = "Case #"+tc+": "+res+"\n";
            sb.append(st);
        }
        System.out.println(sb.toString());
    }
    void printSuffix(){
        for (int i = 0; i < suffix.length(); i++) {
            System.out.println(suffix.select(i));
        }
    }
    private String findSonnet(String query) {
//        for (int i = 0; i < suffix.length(); i++) {
//            System.out.println(suffix.select(i) + " "+suffix.index[i]);
//        }
        return suffix.rank(query);
    }

    // source: http://algs4.cs.princeton.edu/63suffix/SuffixArrayX.java

    static class SuffixArrayX {
        private static final int CUTOFF =  5;   // cutoff to insertion sort (any value between 0 and 12)

        private final char[] text;
        private final int[] index;   // index[i] = j means text.substring(j) is ith largest suffix
        private final int N;         // number of characters in text


        public SuffixArrayX(String text) {
            N = text.length();
            text = text+'\0';
            this.text = text.toCharArray();
            this.index = new int[N];
            for (int i = 0; i < N; i++)
                index[i] = i;
            sort(0, N-1, 0);
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
                if      (t < v) exch(lt++, i++);
                else if (t > v) exch(i, gt--);
                else            i++;
            }

            // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
            sort(lo, lt-1, d);
            if (v > 0) sort(lt, gt, d+1);
            sort(gt+1, hi, d);
        }

        // sort from a[lo] to a[hi], starting at the dth character
        private void insertion(int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++)
                for (int j = i; j > lo && less(index[j], index[j-1], d); j--)
                    exch(j, j-1);
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

        /**
         * Returns the length of the input string.
         * @return the length of the input string
         */
        public int length() {
            return N;
        }

        public String select(int i) {
            if (i < 0 || i >= N) return "%";
            return new String(text, index[i], N - index[i]);
        }

        /**
         * Returns the number of suffixes strictly less than the <tt>query</tt> string.
         * We note that <tt>rank(select(i))</tt> equals <tt>i</tt> for each <tt>i</tt>
         * between 0 and <em>N</em>-1.
         * @param query the query string
         * @return the number of suffixes strictly less than <tt>query</tt>
         */
        public String rank(String query) {
            int lo = 0, hi = N - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = compare(query, index[mid]);
                if      (cmp < 0) hi = mid - 1;
                else if (cmp > 0) lo = mid + 1;
                else return "yes";
            }
            return "no";
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
//            if (j < M) return +1;
            return +1;
        }

    }
}

