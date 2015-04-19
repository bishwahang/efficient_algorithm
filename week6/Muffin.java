package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by hang on 15.11.14.
 */
public class Muffin {
    ArrayList<Integer>[] possibles; // judge recipes
    //    int currentJudge; // judge row number
    Stack<Integer> columns;// last column chosen
    Stack<Integer> muffinsSelected; // last muffing chosen
    int N, M;

    public static void main(String[] args) throws IOException {
        Muffin mf = new Muffin();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            String[] firstLine = br.readLine().split(" ");
            int m = Integer.parseInt(firstLine[0]);
            int n = Integer.parseInt(firstLine[1]);
            ArrayList<Integer>[] possibles = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                possibles[i] = new ArrayList<Integer>();
                String[] judge = br.readLine().split(" ");
                // not considering last element as it is always zero
                for (int j = 0; j < judge.length - 1; j++) {
                    possibles[i].add(Integer.parseInt(judge[j]));
                }
            }
            mf.possibles = possibles;
            mf.N = n;
            mf.M = m;
            sb.append("Case #" + tc + ": ");
            sb.append(mf.getResult(0) + "\n");
            if (tc < t) br.readLine();
        }
        System.out.print(sb.toString());
    }

    public String getResult(int start) {
        int begin = 0;
        int curRow = 0;
        columns = new Stack<Integer>();
        muffinsSelected = new Stack<Integer>();
//        for (int i = 0; i < possibles.length; i++) {
//            Collections.sort(possibles[i]);
//        }
        Arrays.sort(possibles, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o1.size() - o2.size();
            }
        });
        while (true) {
            if (columns.size() == N)
                return "yes";
            boolean found = false;
            for (int col = begin; col < possibles[curRow].size(); col++) {
                int muffin = possibles[curRow].get(col);
                if (!muffinsSelected.contains(-muffin)) {
                    columns.push(col);
                    muffinsSelected.push(muffin);
                    curRow++;
                    begin = 0;
                    found = true;
                    break;
                }
            }
            if (found)
                continue;
            if (columns.size() == 0 || muffinsSelected.size() == 0)
                return "no";
            int lastCol = columns.pop();
            muffinsSelected.pop();
            curRow--;
            begin = lastCol + 1;
        }
    }
    
}
