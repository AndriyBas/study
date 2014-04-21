package com.oyster.amo.lab05;

/**
 * @author bamboo
 * @since 4/17/14 1:12 AM
 */
public class GausSolver {

    private static final double EPS = 0.0000000000001D;
    double[][] a;
    int[] bIndexes;

    public GausSolver(double[][] data) {
        this.a = cloneMatrix(data);

        bIndexes = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            bIndexes[i] = i;
        }


    }

    public double[] solve() {
        goDown();
        goUp();

        int n = a.length;
        double[] res = new double[n];
        for (int i = 0; i < n; i++) {
            res[i] = a[bIndexes[i]][n];
        }

        return res;
    }

    private void goDown() {

        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i][i]) < EPS) {
                int rowIndex = indexOfRowWithMaxValueInColumn(i, i);
                swapRows(i, rowIndex);
            }

            for (int j = i + 1; j < a.length; j++) {
                addToRowAnotherMultipliedBy(j, i, -a[j][i] / a[i][i]);
            }

            multiplyRow(i, 1.0 / a[i][i]);
        }
    }


    private void goUp() {

        for (int i = a.length - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                addToRowAnotherMultipliedBy(j, i, -a[j][i]);
            }
        }

    }

    private void addToRowAnotherMultipliedBy(int destRow, int srcRow, double value) {

        int n = a[srcRow].length;

        for (int i = 0; i < n; i++) {
            a[destRow][i] += a[srcRow][i] * value;
        }
    }

    private void multiplyRow(int row, double value) {
        for (int i = 0; i < a[row].length; i++) {
            a[row][i] *= value;
        }
    }

    private int indexOfRowWithMaxValueInColumn(int startRow, int col) {
        double max = a[startRow][col];
        int index = startRow;
        for (int i = startRow + 1; i < a.length; i++) {
            if (a[i][col] > max) {
                max = a[i][col];
                index = i;
            }
        }
        return index;
    }

    private void swapRows(int i, int j) {

        double[] temp = a[i];
        a[i] = a[j];
        a[j] = temp;

        int t = bIndexes[i];
        bIndexes[i] = bIndexes[j];
        bIndexes[j] = t;
    }

    private double[][] cloneMatrix(double[][] x) {
        double[][] b = new double[x.length][x[0].length];
        for (int i = 0; i < x.length; i++) {
            System.arraycopy(x[i], 0, b[i], 0, x[i].length);
        }
        return b;
    }

}
