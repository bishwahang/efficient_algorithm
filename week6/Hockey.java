package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by hang on 15.11.14.
 */
public class Hockey {
    public static void main(String[] args) throws IOException {
        Hockey h = new Hockey();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Integer>[] gp;
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t ; tc++) {
            String[] firstLine = br.readLine().split(" ");
            int n = Integer.parseInt(firstLine[0])+1;
            int m = Integer.parseInt(firstLine[1]);
            gp = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                gp[i] = new ArrayList<Integer>(Arrays.asList(i));
            }
            for (int i = 0; i < m; i++) {
                String[] match = br.readLine().split(" ");
                int x = Integer.parseInt(match[0]);
                int y = Integer.parseInt(match[1]);
                gp[x].add(y);
                gp[y].add(x);
            }
            sb.append("Case #"+tc+": ");
            sb.append((h.getResult(n,gp)));
            sb.append("\n");
            if(tc < t){
                br.readLine();
            }
        }
        System.out.print(sb.toString());
    }

    private String getResult(int n, ArrayList<Integer>[] gp) {
        Arrays.sort(gp, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return o2.size() - o1.size();
            }
        });
        for (int i = 0; i < gp.length; i++) {
            System.out.println(gp[i].toString());
        }
        ArrayList<Integer> contender = null;
        for (int i = 0; i < n -1; i++) {
            boolean max = false;
            int count = 1;
            contender = gp[i];
            for (int j = 0; j < n-1; j++) {
                ArrayList<Integer> compareTo = gp[j];
                if(i ==j || (compareTo.size() < contender.size())){
                    continue;
                }
                boolean flag = true;
                for (int e: contender){
                    if(!compareTo.contains(e)){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    count ++;
                }
                if(count == contender.size()){
                    max = true;
                    break;
                }

            }
            if(max) break;
        }
        Collections.sort(contender);
        StringBuilder sb = new StringBuilder();
        for (int e : contender) sb.append(e+" ");
        return sb.toString().trim();
    }
}
