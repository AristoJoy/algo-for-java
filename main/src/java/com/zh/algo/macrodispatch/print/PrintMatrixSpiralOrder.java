package com.zh.algo.macrodispatch.print;

/**
 * 体系学习班class40
 *
 * 矩阵处理技巧题
 *
 * 给定一个正方形或者长方形矩阵matrix，实现转圈打印
 */
public class PrintMatrixSpiralOrder {
    public static void spiralOrderPrint(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    public static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
        if (tR == dR) {
            for (int i = tC; i <= dC; i++) {
                System.out.print(m[tR][i] + " ");
            }
        } else if (tC == dC) {
            for (int i = tR; i <= dR; i++) {
                System.out.print(m[i][tC] + " ");
            }
        } else {
            int curR = tR;
            int curC = tC;
            while (curC != dC) {
                System.out.print(m[curR][curC++] + " ");
            }
            while (curR != dR) {
                System.out.print(m[curR++][curC] + " ");
            }
            while (curC != tC) {
                System.out.print(m[curR][curC--] + " ");

            }
            while (curR != tR) {
                System.out.print(m[curR--][curC] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);

    }
}
