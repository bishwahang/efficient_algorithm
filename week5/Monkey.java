package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by hang on 08.11.14.
 */
public class Monkey {
    public static void main(String[] args) throws IOException {
        Monkey m = new Monkey();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SuffixArray suffix = new SuffixArray(br.readLine());
//        SuffixArray suffix = new SuffixArray("hellolo");
        suffix.printSuffix();
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= t; tc++) {
//            String query = br.readLine();
            String res =  suffix.getResult(br.readLine());
            String s = "Case #"+tc+": "+res+"\n";
            sb.append(s);
        }
        System.out.println(sb.toString());
    }

    static class SuffixArray {
        String masterWord = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char[] masterChar = masterWord.toCharArray();
        String text;
        int N;
        char[] characters;
        HashMap<Character, Integer> bucket;
        boolean[] bucketStart, bucketStart2;
        int[] pos, bucketSize, suf;

        public SuffixArray(String t) {
            this.text = t;
            this.characters = text.toCharArray();
            N = characters.length;

            bucketStart = new boolean[N];
            bucketStart2 = new boolean[N];

            pos = new int[N];
            bucketSize = new int[N];
            suf = new int[N];
            initialize();
            int x = 0;
        }

        private void initialize() {
            bucket = new HashMap<Character, Integer>(masterChar.length);
            // Radix short w.r.t first character
            for (char c : masterChar) {
                bucket.put(c, -1);
            }
            for (int i = 0; i < N; i++) {
                int b = bucket.get(characters[i]);
                bucket.put(characters[i], i);
                pos[i] = b;
            }
            int c = 0;
            for (char d : masterChar) {
                int i = bucket.get(d);
                while (i != -1) {
                    int j = pos[i];
                    suf[i] = c;
                    if (i == bucket.get(d)) {
                        bucketStart[c] = true;
                    } else {
                        bucketStart[c] = false;
                    }
                    c++;
                    i = j;
                }
            }
//            initialize arrays
            for (int i = 0; i < N; i++) {
                pos[suf[i]] = i;
            }
            int log = (int) (Math.log(N) / Math.log(2));
            for (int h = 0; h <= log; h++) {
                int l = 0;
                do {
                    int r = getR(l);
                    bucketSize[l] = 0;
                    for (int i = l; i < r; i++) {
                        suf[pos[i]] = l;
                    }
                    l = r;
                } while (l < N);
                l = 0;
                do {
                    int r = getR(l);
                    for (int i = l; i < r; i++) {
                        int d = pos[i] - (int) Math.pow(2, h);
                        if (d < 0 || d >= N) continue;
                        int k = suf[d];
                        suf[d] = k + bucketSize[k];
                        bucketSize[k]++;
                        // TODO: here gets the null pointer sometimes as suf[d] value is bigger then size of bucketStart2 array
//                        System.out.println(d);
//                        System.out.println(suf[d]);
                        bucketStart2[suf[d]] = true;

                    }
                    for (int i = l; i < r; i++) {
                        int d = pos[i] - (int) Math.pow(2, h);
                        if (d < 0 || d >= N || !bucketStart2[suf[d]]) continue;
                        int k = getMinJ(d);
                        for (int j = suf[d] + 1; j < k; j++) {
                            bucketStart2[j] = false;
                        }
                    }
                    l = r;
                } while (l < N);
                for (int i = 0; i < N; i++) {
                    pos[suf[i]] = i;
                    bucketStart[i] = bucketStart[i] || bucketStart2[i];
                }
            }
        }

        private int getMinJ(int d) {
            for (int j = suf[d] + 1; j < N; j++) {
                if (bucketStart[j] || !bucketStart2[j])
                    return j;
            }
            return d;
        }

        int getR(int l) {
            for (int i = l + 1; i < bucketStart.length; i++) {
                if (bucketStart[i])
                    return i;
            }
            return N;
        }

        public void printSuffix() {
            for (int i = 0; i < pos.length; i++) {
                int n = pos[i];
                System.out.println(text.substring(n, N) + ": "+n);
            }
        }

        public String getResult(String query) {
            int lo = 0, hi = N - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = compare(query, pos[mid]);
                if (cmp < 0) hi = mid - 1;
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
                if (query.charAt(j) != characters[i]) return query.charAt(j) - characters[i];
                i++;
                j++;
            }
//
//            if (j == M) return 0;
            if (i < N) return 0;
            if (j < M) return +1;
            return 0;
        }
    }
}
