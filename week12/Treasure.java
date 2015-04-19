package week12;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hang on 21.01.15.
 */
public class Treasure {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        Treasure util = new Treasure();
        for (int tc = 1; tc <= t; tc++) {
            ArrayList<Point> vertices = new ArrayList<Point>();
            for (int i = 0; i < 9; i++) {
                Point p = new Point(sc.nextInt(), sc.nextInt(), 1.0);
                vertices.add(p);
            }
            Matrix a = new Matrix(8, 8);
            a.insertInputs(vertices);
            Matrix adj = util.inverse(a);
            Matrix b = new Matrix(8, 1);
            b.makeVector(vertices);
            Matrix H = util.multiply(adj, b);
            double det = util.determinant(a);
            int x_1 = (int) (H.data[0][0] * vertices.get(4).x + H.data[1][0] * vertices.get(4).y + H.data[2][0]);
            int y_1 = (int) (H.data[3][0] * vertices.get(4).x + H.data[4][0] * vertices.get(4).y + H.data[5][0]);
            int z_1 = (int) ((int) H.data[6][0] * vertices.get(4).x + H.data[7][0] * vertices.get(4).y + det);
            int div = (int) util.calcGCD(Math.abs(x_1), Math.abs(z_1));
            System.out.println("Case #" + tc + ": ");
            if (z_1 < 0) {
                x_1 = -x_1;
                y_1 = -y_1;
                z_1 = -z_1;
            }
            if (z_1 / div == 1) {
                System.out.format("%d ", x_1 / z_1);
            } else {
                System.out.format("%d/%d ", x_1 / div, z_1 / div);
            }

            div = (int) util.calcGCD(Math.abs(y_1), Math.abs(z_1));
            if (z_1 / div == 1) {
                System.out.format("%d", y_1 / div);
            } else {
                System.out.format("%d/%d", y_1 / div, z_1 / div);
            }
            System.out.println();
        }
    }

    long calcGCD(long a, long b) {
        long temp;
        while (b != 0 && a != 0) {
            temp = a;
            a = b;
            b = temp % b;
        }
        return a + b;
    }

    public Matrix transpose(Matrix matrix) {
        Matrix transposedMatrix = new Matrix(matrix.getNcols(), matrix.getNrows());
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                transposedMatrix.setValueAt(j, i, matrix.getValueAt(i, j));
            }
        }
        return transposedMatrix;
    }

    public Matrix inverse(Matrix matrix) {
        return (transpose(cofactor(matrix)));
    }

    public double determinant(Matrix matrix) {
        if (matrix.size() == 1) {
            return matrix.getValueAt(0, 0);
        }
        if (matrix.size() == 2) {
            return (matrix.getValueAt(0, 0) * matrix.getValueAt(1, 1)) - (matrix.getValueAt(0, 1) * matrix.getValueAt(1, 0));
        }
        double sum = 0.0;
        for (int i = 0; i < matrix.getNcols(); i++) {
            sum += changeSign(i) * matrix.getValueAt(0, i) * determinant(createSubMatrix(matrix, 0, i));
        }
        return sum;
    }

    private int changeSign(int i) {
        if (i % 2 == 0)
            return 1;
        return -1;
    }

    public Matrix createSubMatrix(Matrix matrix, int excluding_row, int excluding_col) {
        Matrix mat = new Matrix(matrix.getNrows() - 1, matrix.getNcols() - 1);
        int r = -1;
        for (int i = 0; i < matrix.getNrows(); i++) {
            if (i == excluding_row)
                continue;
            r++;
            int c = -1;
            for (int j = 0; j < matrix.getNcols(); j++) {
                if (j == excluding_col)
                    continue;
                mat.setValueAt(r, ++c, matrix.getValueAt(i, j));
            }
        }
        return mat;
    }

    public Matrix cofactor(Matrix matrix) {
        Matrix mat = new Matrix(matrix.getNrows(), matrix.getNcols());
        for (int i = 0; i < matrix.getNrows(); i++) {
            for (int j = 0; j < matrix.getNcols(); j++) {
                mat.setValueAt(i, j, changeSign(i) * changeSign(j) * determinant(createSubMatrix(matrix, i, j)));
            }
        }
        return mat;
    }

    public Matrix add(Matrix matrix1, Matrix matrix2) {
        Matrix sumMatrix = new Matrix(matrix1.getNrows(), matrix1.getNcols());
        for (int i = 0; i < matrix1.getNrows(); i++) {
            for (int j = 0; j < matrix1.getNcols(); j++)
                sumMatrix.setValueAt(i, j, matrix1.getValueAt(i, j) + matrix2.getValueAt(i, j));
        }
        return sumMatrix;
    }

    public Matrix multiply(Matrix matrix1, Matrix matrix2) {
        Matrix multipliedMatrix = new Matrix(matrix1.getNrows(), matrix2.getNcols());

        for (int i = 0; i < multipliedMatrix.getNrows(); i++) {
            for (int j = 0; j < multipliedMatrix.getNcols(); j++) {
                double sum = 0.0;
                for (int k = 0; k < matrix1.getNcols(); k++) {
                    sum += matrix1.getValueAt(i, k) * matrix2.getValueAt(k, j);
                }
                multipliedMatrix.setValueAt(i, j, sum);
            }
        }
        return multipliedMatrix;
    }

    static class Point {
        double x;
        double y;
        double z;

        Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        Point() {
        }
    }

    public static class Matrix {
        public double[][] data;
        int nrows;
        int ncols;

        public Matrix(int nrow, int ncol) {
            this.nrows = nrow;
            this.ncols = ncol;
            data = new double[nrow][ncol];
        }

        public int getNrows() {
            return nrows;
        }

        public int getNcols() {
            return ncols;
        }


        public void setValueAt(int row, int col, double value) {
            data[row][col] = value;
        }

        public double getValueAt(int row, int col) {
            return data[row][col];
        }

        public boolean isSquare() {
            return nrows == ncols;
        }

        public int size() {
            if (isSquare())
                return nrows;
            return -1;
        }

        public void makeVector(ArrayList<Point> p) {
            data[0][0] = p.get(5).x;
            data[1][0] = p.get(5).y;

            data[2][0] = p.get(6).x;
            data[3][0] = p.get(6).y;

            data[4][0] = p.get(7).x;
            data[5][0] = p.get(7).y;

            data[6][0] = p.get(8).x;
            data[7][0] = p.get(8).y;
        }

        public void insertInputs(ArrayList<Point> p) {
            int c = 0;
            for (int i = 0; i <= 6; i = i + 2, ++c) {
                data[i][0] = p.get(c).x;
                data[i][1] = p.get(c).y;
                data[i][2] = 1;
                data[i][3] = 0;
                data[i][4] = 0;
                data[i][5] = 0;
                data[i][6] = -(p.get(c).x * p.get(c + 5).x);
                data[i][7] = -(p.get(c).y * p.get(c + 5).x);

                data[i + 1][0] = 0;
                data[i + 1][1] = 0;
                data[i + 1][2] = 0;
                data[i + 1][3] = p.get(c).x;
                data[i + 1][4] = p.get(c).y;
                data[i + 1][5] = 1;
                data[i + 1][6] = -(p.get(c).x * p.get(c + 5).y);
                data[i + 1][7] = -(p.get(c).y * p.get(c + 5).y);
            }
        }

    }
}
