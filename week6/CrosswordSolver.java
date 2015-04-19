package week6;

/**
 * Created by hang on 19.11.14.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CrosswordSolver {

    // INSTANCE VARIABLES:

    // CONSTANTS: characters used for blank and solid squares
    //            in the puzzle:
    public static final char BLANK = '_';
    public static final char FILLED = '#';
    // The current state of the crossword puzzle, including
    // blank squares, solid squares, and any letters we've added:
    private char[][] puzzle;
    // A collection of CrosswordSpace objects, each of which
    // represents a "slot" in the puzzle (like "1 Across" or
    // "2 Down"):
    private ArrayList<CrosswordSpace> slots;
    // The words we're trying to put in the crossword puzzle.
    // Each word is encapsulated within a CrosswordWord object,
    // which also keeps track of whether or not the word has
    // already been used:
    private String[] words;
    // This array keeps track of how many words are currently
    // using each letter in the puzzle -- this is needed so
    // that we can know which letters to actually remove from
    // the puzzle when we remove a word:
    private int[][] letterUsage;
    // Counts the number of backtracks the algorithm performs:

    /**
     * CrosswordSolver()
     * <p/>
     * The constructor simply stores references to the puzzle
     * array, slots array, and words array in the instance
     * variables.
     */
    public CrosswordSolver(char[][] puzzle, ArrayList<CrosswordSpace> slots,
                           String[] words) {
        this.puzzle = puzzle;
        this.slots = slots;
        this.words = words;
    }
    // Pass all of the above into a new CrosswordSolver object:

    /**
     * main()
     * <p/>
     * Creates a crossword puzzle and passes it in to our solver
     * to solve!
     */
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        CrosswordSolver s;
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            String[] firstLine = br.readLine().split(" ");
            int x = Integer.parseInt(firstLine[0]);
            int y = Integer.parseInt(firstLine[1]);
            int amount = Integer.parseInt(firstLine[2]);
            char[][] smallPuzzle = new char[y][x];
            ArrayList<CrosswordSpace> slots = new ArrayList<CrosswordSpace>();
            // Horizontal
            for (int i = 0; i < y; i++) {
                boolean previousFound = false;
                int count = 0;
                int start = 0;
                char[] row = br.readLine().toCharArray();
                for (int j = 0; j < x; j++) {
                    char input = row[j];
                    smallPuzzle[i][j] = input;
                    if (input == BLANK) {
                        if (previousFound) {
                            count++;
                        } else {
                            previousFound = true;
                            count++;
                            start = j;
                        }

                    } else {
                        if (count >= 2) {
                            slots.add(new CrosswordSpace(i, start, 0, 1, count));
                        }
                        count = 0;
                        previousFound = false;
                    }
                }
                if (count >= 2) {
                    slots.add(new CrosswordSpace(i, start, 0, 1, count));
                }
            }
            // Vertical
            for (int i = 0; i < smallPuzzle[0].length; i++) {
                boolean previousFound = false;
                int count = 0;
                int start = 0;
                for (int j = 0; j < smallPuzzle.length; j++) {
                    char input = smallPuzzle[j][i];
                    if (input == BLANK) {
                        if (previousFound) {
                            count++;
                        } else {
                            previousFound = true;
                            count++;
                            start = j;
                        }

                    } else {
                        if (count >= 2) {
                            slots.add(new CrosswordSpace(start, i, 1, 0, count));
                        }
                        count = 0;
                        previousFound = false;
                    }
                }
                if (count >= 2) {
                    slots.add(new CrosswordSpace(start, i, 1, 0, count));
                }
            }
            String[] words = new String[amount];
            for (int i = 0; i < amount; i++)
                words[i] = br.readLine();
            s = new CrosswordSolver(smallPuzzle, slots, words);
            s.solve();
            sb.append("Case #" + tc + ":\n");
            sb.append(s.printPuzzle());
            if (tc < t){
                br.readLine();
                sb.append("\n");
            }
        }
        System.out.print(sb.toString());
    }

    /**
     * reinitialize()
     * <p/>
     * Resets the solver to prepare for a fresh call to solve()
     * by zeroing out the letterUsage array and numBacktracks
     * variable.
     */
    private void reinitialize() {
        letterUsage = new int[puzzle.length][puzzle[0].length];
    }

    /**
     * solve()
     * <p/>
     * A public "wrapper" method for the actual recursive method,
     * fillPuzzle(). Resets the state of the solver by calling
     * reinitialize(), then makes the first call to fillPuzzle(),
     * passing in 0 to tell it to start at the first slot (slot
     * number 0). Once the recursion is finished, checks the
     * return value to determine whether or not a solution was
     * found.
     */
    public void solve() {
        reinitialize();
        fillPuzzle(0);
    }

    /**
     * fillPuzzle()
     * <p/>
     * Our recursive backtracking algorithm for solving the crossword
     * puzzle. For each slot in the slots array, tries to find an
     * UNUSED word from the words array that fits. If no such word
     * is found, or all the words it's tried lead to backtracks,
     * returns false to backtrack to the previous call and tell it
     * to choose a different word. If we've filled every slot, we
     * print the solution and return true, which causes every other
     * call to also return true, bringing us back to the solve()
     * wrapper method.
     */
    private boolean fillPuzzle(int slot) {

        // If we've filled every slot, slot will be equal to
        // the length of the slots array. In that case, we are
        // done, so we print the solution and return true to
        // tell all the previous calls to also return true:

        if (slot == slots.size()) {
            return true;
        }

        // Consider each word from the words array as a potential
        // fit for the current slot:

        for (String word : words) {

            // If the word is UNUSED and fits in the slot given
            // its length and the letters already there from other
            // words, we place the word in the slot (marking it
            // USED in the process), and make a recursive call to
            // fill the next slot. If that call backtracks (returns
            // false), we remove the word from the slot and choose
            // a different one:

            if (wordFitsInSlot(word, slots.get(slot))) {
                putWordInSlot(word,  slots.get(slot));

                if (fillPuzzle(slot + 1)) {
                    return true;
                }

                removeWordFromSlot( slots.get(slot));
            }
        }
        return false;
    }

    /**
     * wordFitsInSlot()
     * <p/>
     * Returns true if the word passed to it fits into the slot
     * passed to it AND is unused, otherwise returns false.
     */
    private boolean wordFitsInSlot(String w, CrosswordSpace slot) {

        // If the length of the word doesn't match the length of the
        // slot, or the word is already used, we can't put this word
        // here and so we return false:

//        if (w.getWord().length() != slot.getLength() || w.isUsed()) {
        if (w.length() != slot.length ) {
            return false;
        }

        // Otherwise we examine each position in the slot. If
        // there are letters in the slot already, and those letters
        // DON'T match the letters at the corresponding positions
        // in our word, the word won't fit and we return false:

        int[] position = new int[]{slot.x,slot.y};

        for (int i = 0; i < slot.length; i++) {

            if (puzzle[position[0]][position[1]] != BLANK &&
                    puzzle[position[0]][position[1]] != w.charAt(i)) {
                return false;
            }

            // Advance to the next position in the slot:

            position[0] += slot.xDirection;
            position[1] += slot.yDirection;
        }

        // If we get here, it means the word is unused, the right
        // length for the current slot, and its letters match any
        // letters already in the slot:

        return true;
    }

    /**
     * putWordInSlot()
     * <p/>
     * Puts each letter from the word it's passed into the slot
     * it's passed, and marks the word USED. Also increments the
     * positions in the letterUsage array corresponding to the
     * slot to indicate that one more word is now using these
     * letters.
     */
    private void putWordInSlot(String w, CrosswordSpace slot) {
        int[] position = new int[]{slot.x,slot.y};

        for (int i = 0; i < slot.length; i++) {

            // Put each letter from the word into this slot of
            // the puzzle:

            puzzle[position[0]][position[1]] = w.charAt(i);

            // Record the fact that one more word is now using the
            // letter at this position:

            letterUsage[position[0]][position[1]]++;

            // Advance to the next position in the slot:

            position[0] += slot.xDirection;
            position[1] += slot.yDirection;
        }

    }

    /**
     * removeWordFromSlot()
     * <p/>
     * Clears each position in the slot it's passed, but ONLY
     * those positions containing letters NOT used by any other
     * words according to the letterUsage array. Also marks
     * the word that used to be in the slot as UNUSED.
     */
    private void removeWordFromSlot(CrosswordSpace slot) {
//        Point position = new Point(slot.getStart());
        int[] position = new int[]{slot.x,slot.y};

        for (int i = 0; i < slot.length; i++) {

            // One fewer word is now using the letter at this position:

            letterUsage[position[0]][position[1]]--;

            // If no words are now using this letter, clear it:

            if (letterUsage[position[0]][position[1]] == 0) {
                puzzle[position[0]][position[1]] = BLANK;
            }

            // Advance to the next position in the slot:

            position[0] += slot.xDirection;
            position[1] += slot.yDirection;
        }

    }

    /**
     * printPuzzle()
     * <p/>
     * Outputs the current state of the crossword puzzle.
     */
    public String printPuzzle() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[row].length; col++) {
                sb.append(puzzle[row][col]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

// HELPER CLASSES:

/**
 * CrosswordSpace
 * <p/>
 * A helper class to represent a slot in the Crossword puzzle. Contains
 * instance variables describing the start location, direction, and
 * length of the slot:
 */
class CrosswordSpace {
    public int x,y,xDirection,yDirection, length;

    public CrosswordSpace(int x,int y,int xDirection,int yDirection,int length) {
        this.x = x;
        this.y = y;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.length = length;
    }
}