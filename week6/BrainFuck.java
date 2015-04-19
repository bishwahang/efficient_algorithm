package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hang on 15.11.14.
 */
public class BrainFuck {
    int[][] memory;
    char[][] instructions;
    int counter;
    char currentDirection;
    boolean currentSlash;
    int curX, curY;
    int pointX, pointY;

    public static void main(String[] args) throws IOException {
        BrainFuck bf = new BrainFuck();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= t; tc++) {
            String[] firsLine = br.readLine().split(" ");
            int n = Integer.parseInt(firsLine[0]);
            int m = Integer.parseInt(firsLine[1]);
            char[][] instructions = new char[n][m];
            for (int i = 0; i < n; i++) {
                instructions[i] = br.readLine().toCharArray();
            }
            bf.instructions = instructions;
            System.out.format("Case #%d: %s\n", tc, bf.computeResult(n, m));
            if (tc < t) br.readLine();
        }
    }

    private String computeResult(int n, int m) {
        initialize();
        StringBuilder sb = new StringBuilder();
        boolean output = false;
        counter = 0;
        while (true) {
            if (curX < 0 || curX >= m || curY < 0 || curY >= n) {
                break;
            }
            if(counter == 50000000){
                return "endless loop";
            }
            char ins = instructions[curY][curX];
            switch (ins) {
                case '^':
                    counter++;
                    pointY++;
                    moveForward();
                    break;
                case 'v':
                    counter++;
                    pointY--;
                    moveForward();
                    break;
                case '>':
                    counter++;
                    pointX++;
                    moveForward();
                    break;
                case '<':
                    counter++;
                    pointX--;
                    moveForward();
                    break;
                case 'U':
                    counter++;
                    currentDirection = 'U';
                    moveForward();
                    break;
                case 'D':
                    counter++;
                    currentDirection = 'D';
                    moveForward();
                    break;
                case 'R':
                    counter++;
                    currentDirection = 'R';
                    moveForward();
                    break;
                case 'L':
                    counter++;
                    currentDirection = 'L';
                    moveForward();
                    break;
                case '+':
                    counter++;
                    memory[pointY][pointX] = (memory[pointY][pointX] + 1) % 256; // over flow
                    moveForward();
                    break;
                case '-':
                    counter++;
                    int t = (memory[pointY][pointX] - 1) % 256;
                    if(t < 0){
                        memory[pointY][pointX] = t + 256; // underflow
                    }else{
                        memory[pointY][pointX] = t;
                    }
                    moveForward();
                    break;
                case '.':
                    counter++;
                    output = true;
                    moveForward();
                    break;
                case '/':
                    counter++;
                    currentSlash = true;
                    moveForward();
                    break;
                case ' ':
                    moveForward();
                    break;
                default:
                    break;
            }
            if (output) { // set output back to false and append the output
                output = false;
                sb.append(Character.toString((char) memory[pointY][pointX]));
            }
        }
        return sb.toString();
    }

    private void initialize() {
        memory = new int[16][16];
        currentDirection = 'R';
        currentSlash = false;
        curX = 0;
        curY = 0;
        pointX = 0;
        pointY = 0;
    }

    private void moveForward() {
        if (currentDirection == 'U') {
            curY--;
            if (currentSlash && memory[pointY][pointX] == 0){
                counter--;
                curY--; // go one more step forward
            }

        } else if (currentDirection == 'D') {
            curY++;
            if (currentSlash &&  memory[pointY][pointX] == 0) {
                counter--;
                curY++; // go one more step forward
            }
        } else if (currentDirection == 'R') {
            curX++;
            if (currentSlash &&  memory[pointY][pointX] == 0) {
                counter--;
                curX++; // go one more step forward
            }
        } else {
            curX--;
            if (currentSlash &&  memory[pointY][pointX] == 0) {
                counter--;
                curX--; // go one more step forward
            }
        }
        currentSlash = false; // skip character read so set back to false
    }
}
