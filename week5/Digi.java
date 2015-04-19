package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by hang on 30.11.14.
 */
public class Digi {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        Scanner sc = new Scanner(System.in);
        String[] nm = br.readLine().split(" ");
        BigInteger n = new BigInteger(nm[0]);
        int m = Integer.parseInt(nm[1]);
        String[] ins = new String[m + 1];
        int[] left = new int[m + 1];
        int[] right = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            String[] lines = br.readLine().split(" ");
            ins[i] = lines[0];
            left[i] = Integer.parseInt(lines[1]);
            right[i] = Integer.parseInt(lines[2]);
        }
        for (BigInteger bi = BigInteger.valueOf(0);
             bi.compareTo(n) < 0;
             bi = bi.add(BigInteger.ONE)) {
            int cur = 1;
            while (cur != 0) {
                String curIns = ins[cur];
                if (curIns.equalsIgnoreCase("R")) {
                    ins[cur] = "L";
                    cur = right[cur];

                } else {
                    ins[cur] = "R";
                    cur = left[cur];
                }
            }

        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < ins.length; i++) {
            sb.append(ins[i]);
        }
        System.out.println(sb.toString());

    }
}
