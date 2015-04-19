package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

/**
 * Created by hang on 17.11.14.
 */
public class Queen {
    int board[];
    Stack<Integer> columns;
    int queensLeft;
    int N;
    int currentQueenRow;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Queen q = new Queen();
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            int n = Integer.parseInt(br.readLine());
            int[] board = new int[n];
            for (int i = 0; i < n; i++) {
                board[i] = -1;
            }
            int count = 0;
            boolean notPossible = false;
            for (int i = 0; i < n; i++) {
                char[] row = br.readLine().toCharArray();
                int times = 0;
                for (int j = 0; j < row.length; j++) {
                    if (row[j] == 'x') {
                        times++;
                        count++;
                        board[i] = j;
                    }
                }
                if (times > 1) notPossible = true;
            }
            q.board = board;
            q.queensLeft = n - count;
            q.N = n;
            q.columns = new Stack<Integer>();
            q.currentQueenRow = 0;
            sb.append("Case #" + tc + ":\n");
            if (notPossible)
                sb.append("not possible\n");
            else
                sb.append(q.placeQueenOnBoard(0));
            if (tc < t) br.readLine();
        }
        System.out.print(sb.toString());
    }

    boolean checkInputNotPossible() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] != -1) {
                if (!isSafePlace2(board[i], i)) {
                    return true;
                }
            }
        }
        return false;
    }

    String placeQueenOnBoard(int start) {
//        System.out.println(columns.toString());
        if (checkInputNotPossible()) {
            return "not possible\n";
        }
        if (currentQueenRow < 0) {
            return "not possible\n";
        } else if (columns.size() == N) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (j == columns.get(i)) {
                        sb.append("x");
                    } else {
                        sb.append(".");
                    }
                }
                sb.append("\n");
            }
            return sb.toString();
        } else {
            if (board[currentQueenRow] != -1) {
                columns.push(board[currentQueenRow]);
                currentQueenRow++;
                return placeQueenOnBoard(0);
            } else {
                for (int col = start; col < N; col++) {
                    if (isSafePlace(col, currentQueenRow)) {
                        columns.push(col);
                        currentQueenRow++;
                        return placeQueenOnBoard(0);
                    }

                }

                int lastCol;
                int lastRow;
                do {
                    if (columns.size() == 0)
                        return "not possible\n";
                    lastRow = columns.size() - 1;
                    lastCol = columns.pop();
                    currentQueenRow--;
                } while (board[lastRow] != -1);
                return placeQueenOnBoard(lastCol + 1);
            }

        }
    }

    //check if the column is safe place to put Qi (ith Queen)
    boolean isSafePlace(int col, int row) {

        //check for all previously placed queens
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i) == col) return false;
            if (Math.abs(columns.get(i) - col) == Math.abs(i - row)) {
                return false;
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i] == -1)
                continue;
            if (board[i] == col) { // the ith Queen(previous) is in same column
                return false;
            }
            if (Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    boolean isSafePlace2(int col, int row) {
        for (int i = row + 1; i < board.length; i++) {
            if (board[i] == -1)
                continue;
            if (board[i] == col) { // the ith Queen(previous) is in same column
                return false;
            }
            if (Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

}
