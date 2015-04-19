package week10;

/**
 * Created by hang on 06.01.15.
 */
public class Pascal {
    public static void main(String[] args) {
        Pascal p = new Pascal();
        p.generateTriagle2DArray(24);
    }
    public void generateTriagle2DArray(int maxRows)
    {
        int triangle[][] = new int[maxRows][maxRows];

        int[] previousRow;
        int[] currentRow = {1};
        int index = 0;
        triangle[index++] = currentRow;
        previousRow = currentRow;
        for (int i = 2; i <= maxRows; i++) {
            currentRow = new int[i];
            currentRow[0] = 1;
            currentRow[i - 1] = 1;
            for (int j = 0; j <= i - 3; j++) {
                currentRow[j + 1] = previousRow[j] + previousRow[j + 1];
            }
            triangle[index++] = currentRow;
            previousRow = currentRow;
        }
        printTriangle2DArray(triangle);
    }
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    public void printTriangle2DArray(int triangle[][])
    {
        for (int i = 0; i < triangle.length; i++)
        {
            for (int c = 0; c < triangle[i].length; c++)
            {
                if (triangle[i][c] == 0)
                {
                    System.out.print("-");
                } else
                {
                    System.out.print(triangle[i][c]+" ");
                }
            }
            System.out.println();
        }
    }

}
