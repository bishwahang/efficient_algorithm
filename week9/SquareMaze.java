package week9;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by hang on 12.12.14.
 */
public class SquareMaze {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            System.out.println("Case #" + tc + ":");
            Stack<Integer> history = new Stack<Integer>();
            int n = sc.nextInt();
            int m = sc.nextInt();
            int x = sc.nextInt();
            int y = sc.nextInt();
            int[] startPoint = new int[]{x, y};
            int[] curPoint = new int[]{x, y};
            int[][] grid = new int[m+2][n+2];
            grid[y][x] = 1;
            int s = sc.nextInt();
            String moved = "";
            int curDirection = -1; // 0,1,2,3 is north, east, south, west resp.
            boolean found = false;
            boolean backTrack = false;
            do { // until out of maze safely
                boolean startPosition = (curPoint[0] == startPoint[0] && curPoint[1] == startPoint[1]);
                while (true) { // until correct move is made
                    switch (s) {
                        case 0:
                            break;
                        case 1: // west
                            backTrack = false;
                            if (grid[curPoint[1]][curPoint[0] - 1] == 0) {
                                curPoint[0]--;
                                moved = "west";
                                curDirection = 3;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 2: // south
                            backTrack = false;
                            if (grid[curPoint[1] + 1][curPoint[0]] == 0) {
                                curPoint[1]++;
                                moved = "south";
                                curDirection = 2;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 3: // south west
                            backTrack = false;
                            if (grid[curPoint[1] + 1][curPoint[0]] == 0) {
                                curPoint[1]++;
                                moved = "south";
                                curDirection = 2;
                            } else if (grid[curPoint[1]][curPoint[0] - 1] == 0) {
                                curPoint[0]--;
                                moved = "west";
                                curDirection = 3;
                            } else {
                                backTrack = true;
                            }

                            break;
                        case 4: // east
                            backTrack = false;
                            if (grid[curPoint[1]][curPoint[0] + 1] == 0) {
                                curPoint[0]++;
                                moved = "east";
                                curDirection = 1;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 5: // east and west
                            backTrack = false;
                            if (grid[curPoint[1]][curPoint[0] + 1] == 0) {
                                curPoint[0]++;
                                moved = "east";
                                curDirection = 1;
                            } else if (grid[curPoint[1]][curPoint[0] - 1] == 0) {
                                curPoint[0]--;
                                moved = "west";
                                curDirection = 3;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 6: // east and south
                            backTrack = false;
                            if (grid[curPoint[1]][curPoint[0] + 1] == 0) {
                                curPoint[0]++;
                                moved = "east";
                                curDirection = 1;
                            } else if (grid[curPoint[1] + 1][curPoint[0]] == 0) {
                                curPoint[1]++;
                                moved = "south";
                                curDirection = 2;
                            } else {
                                backTrack = true;
                            }

                            break;
                        case 7: // east and south and west
                            backTrack = false;
                            if (grid[curPoint[1]][curPoint[0] + 1] == 0) {
                                curPoint[0]++;
                                moved = "east";
                                curDirection = 1;
                            } else if (grid[curPoint[1] + 1][curPoint[0]] == 0) {
                                curPoint[1]++;
                                moved = "south";
                                curDirection = 2;
                            } else if (grid[curPoint[1]][curPoint[0] - 1] == 0) {
                                curPoint[0]--;
                                moved = "west";
                                curDirection = 3;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 8: // north
                            backTrack = false;
                            if (grid[curPoint[1] - 1][curPoint[0]] == 0) {
                                curPoint[1]--;
                                moved = "north";
                                curDirection = 0;
                            } else {
                                backTrack = true;
                            }

                            break;
                        case 9: // north and west
                            backTrack = false;
                            if (grid[curPoint[1] - 1][curPoint[0]] == 0) {
                                curPoint[1]--;
                                moved = "north";
                                curDirection = 0;
                            } else if (grid[curPoint[1]][curPoint[0] - 1] == 0) {
                                curPoint[0]--;
                                moved = "west";
                                curDirection = 3;
                            } else {
                                backTrack = true;
                            }

                            break;
                        case 10: // north and south
                            backTrack = false;
                            if (grid[curPoint[1] - 1][curPoint[0]] == 0) {
                                curPoint[1]--;
                                moved = "north";
                                curDirection = 0;
                            } else if (grid[curPoint[1] + 1][curPoint[0]] == 0) {
                                curPoint[1]++;
                                moved = "south";
                                curDirection = 2;
                            } else {
                                backTrack = true;
                            }

                            break;
                        case 11: // north and south and west
                            backTrack = false;
                            if (grid[curPoint[1] - 1][curPoint[0]] == 0) {
                                curPoint[1]--;
                                moved = "north";
                                curDirection = 0;
                            } else if (grid[curPoint[1] + 1][curPoint[0]] == 0) {
                                curPoint[1]++;
                                moved = "south";
                                curDirection = 2;
                            } else if (grid[curPoint[1]][curPoint[0] - 1] == 0) {
                                curPoint[0]--;
                                moved = "west";
                                curDirection = 3;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 12: // north and east
                            backTrack = false;
                            if (grid[curPoint[1] - 1][curPoint[0]] == 0) {
                                curPoint[1]--;
                                moved = "north";
                                curDirection = 0;
                            } else if (grid[curPoint[1]][curPoint[0] + 1] == 0) {
                                curPoint[0]++;
                                moved = "east";
                                curDirection = 1;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 13: // north east west
                            backTrack = false;
                            if (grid[curPoint[1] - 1][curPoint[0]] == 0) {
                                curPoint[1]--;
                                moved = "north";
                                curDirection = 0;
                            } else if (grid[curPoint[1]][curPoint[0] + 1] == 0) {
                                curPoint[0]++;
                                moved = "east";
                                curDirection = 1;
                            } else if (grid[curPoint[1]][curPoint[0] - 1] == 0) {
                                curPoint[0]--;
                                moved = "west";
                                curDirection = 3;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 14: // north east south
                            backTrack = false;
                            if (grid[curPoint[1] - 1][curPoint[0]] == 0) {
                                curPoint[1]--;
                                moved = "north";
                                curDirection = 0;
                            } else if (grid[curPoint[1] + 1][curPoint[0]] == 0) {
                                curPoint[1]++;
                                moved = "south";
                                curDirection = 2;
                            } else if (grid[curPoint[1]][curPoint[0] + 1] == 0) {
                                curPoint[0]++;
                                moved = "east";
                                curDirection = 1;
                            } else {
                                backTrack = true;
                            }
                            break;
                        case 15: // all four
                            backTrack = false;
                            if (grid[curPoint[1]][curPoint[0] - 1] == 0) {
                                curPoint[0]--;
                                moved = "west";
                                curDirection = 3;
                            } else if (grid[curPoint[1] - 1][curPoint[0]] == 0) {
                                curPoint[1]--;
                                moved = "north";
                                curDirection = 0;
                            } else if (grid[curPoint[1]][curPoint[0] + 1] == 0) {
                                curPoint[0]++;
                                moved = "east";
                                curDirection = 1;
                            } else if (grid[curPoint[1] + 1][curPoint[0]] == 0) {
                                curPoint[1]++;
                                moved = "south";
                                curDirection = 2;
                            }else {
                                backTrack = true;
                            }

                    }
                    // possible exit point hit
                    if ((!backTrack) && (curPoint[0] > n || curPoint[1] > m || curPoint[0] < 1 || curPoint[1] < 1)) {
                        if (startPosition) {
                            // we moved out of grid on start position so backtrack
                            switch (curDirection) { // go reverse of current direction
                                case 0:
                                    curPoint[1]++; // backtrack south
                                    curDirection = 2;
                                    break;
                                case 1:
                                    curPoint[0]--; // backtrack west
                                    curDirection = 3;
                                    break;
                                case 2:
                                    curPoint[1]--; // backtrack north
                                    curDirection = 0;
                                    break;
                                case 3:
                                    curPoint[0]++; // backtrack east
                                    curDirection = 1;
                                    break;

                            }
                            // choose another direction and try it
                            continue;
                        } else {
                            // Hurray moved out of maze
                            found = true;
                            break;
                        }
                    }
                    if(backTrack) {
                        int lastDirection = history.pop();
                        // returned to same place
                        switch (lastDirection) { // go reverse of current direction
                            case 0:
                                curPoint[1]++; // backtrack south
                                curDirection = 2;
                                moved = "south";
                                break;
                            case 1:
                                curPoint[0]--; // backtrack west
                                curDirection = 3;
                                moved = "west";
                                break;
                            case 2:
                                curPoint[1]--; // backtrack north
                                curDirection = 0;
                                moved = "north";
                                break;
                            case 3:
                                curPoint[0]++; // backtrack east
                                curDirection = 1;
                                moved = "east";
                                break;

                        }
                    }
                    if(!backTrack){
                        history.push(curDirection);
                    }
                    grid[curPoint[1]][curPoint[0]] = 1;
                    System.out.println("Move " + moved);
                    break;
                }
                if(found) break; // if out of grid successfully break loop
                // else read next possible input
                s = sc.nextInt();
            } while (true);
            System.out.println("Finished!");
        }
    }
}
