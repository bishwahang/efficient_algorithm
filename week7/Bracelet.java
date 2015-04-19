package week7;

import java.util.Scanner;

/**
 * Created by hang on 23.11.14.
 */
public class Bracelet {
    int c[][];
    String a,b;
    public static void main(String[] args) {
        Bracelet bracelet = new Bracelet();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            sc.nextLine();
            String first = sc.nextLine();
            String second = sc.nextLine();
//            bracelet.a = first;
//            bracelet.b = second;
//            int lenFirst = first.length();
//            int lenSecond = second.length();
//            bracelet.c = new int[lenFirst+1][lenSecond+1];
            System.out.format("Case #%d: %d\n", tc, bracelet.getMaxLength(first, second));


        }
    }

    private int getMaxLength(String first, String second) {
        // initialize
        a = first;
        b = second;
        c = new int[first.length()+1][second.length()+1];
        int currLen, maxLen = 0;

        for ( int i = 0 ; i<=a.length() ; i++)
        {
            c[i][0] = 0;
        }
        for ( int j = 0 ; j<=b.length() ; j++)
        {
            c[0][j] = 0;
        }

        for ( int i = 0 ; i< a.length() ; i ++)
        {
            StringBuilder sb = new StringBuilder();
            String temp = a.substring(i);
            sb.append(temp);
            sb.append(a.substring(0,i));
            currLen =  computeLcs(sb.toString());
            if(maxLen < currLen )
            {
                maxLen = currLen;
            }
            currLen = computeLcs(sb.reverse().toString());
            if(maxLen < currLen )
            {
                maxLen = currLen;
            }
        }
        return maxLen;
    }
    // source: http://en.wikipedia.org/wiki/Longest_common_subsequence_problem#Code_for_the_dynamic_programming_solution
    int computeLcs(String currSubString)
    {
        char[] a = currSubString.toCharArray();
        char[] b = this.b.toCharArray();
        int m = a.length;
        int n = b.length;
        for ( int i = 1;i<= m ; i ++)
        {
            for ( int j = 1;j<= n;j ++)
            {
                if ( a[i - 1] == b[j - 1])
                {
                    c[i][j] = c[i - 1][j - 1] + 1;
                }
                else
                {
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);

                }

            }

        }
        return c[m][n];

    }
}
