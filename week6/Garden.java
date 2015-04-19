package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hang on 16.11.14.
 */
public class Garden {
    int maxHeightAllowed;
    int fountainHeight;
    Node[][] gardenGrid;
    Node fountain;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Garden g = new Garden();
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            String[] firstLine = br.readLine().split(" ");
            int maxHeight = Integer.parseInt(firstLine[0]);
            int fountainHeight = Integer.parseInt(firstLine[1]);

            Node[][] gardenGrid = new Node[4][4];
            Node root;
            for (int i = 0; i < 4; i++) {
                char[] row = br.readLine().toCharArray();
                for (int j = 0; j < 4; j++) {
                    char c = row[j];
                    switch (c){
                        case '_':
                            gardenGrid[i][j] = new Node(-2,-2,c);
                            break;
                        case '*':
                            root =  new Node(-2,-2,c);
                            g.fountain = root;
                            gardenGrid[i][j] = root;
                            break;
                        case '?':
                            gardenGrid[i][j] = new Node(-2,-2,c);
                            break;
                        default:
                            int h = Integer.parseInt(String.valueOf(c));
                            gardenGrid[i][j] = new Node(h,h,c);
                            break;
                    }
                }
            }
            g.maxHeightAllowed = maxHeight;
            g.fountainHeight = fountainHeight;
            g.gardenGrid = gardenGrid;
            sb.append("Case #"+tc+": "+g.findPossibleWays());
            if(tc < t) br.readLine();
        }

    }

    public String findPossibleWays() {

        return "";
    }
    static class Node{
        char type;
        int maxHeight;
        int minHeight;
        public Node(int max,int min, char t) {
            this.maxHeight = max;
            this.minHeight = min;
            this.type = t;
        }
    }
}

