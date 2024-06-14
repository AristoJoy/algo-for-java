package com.zh.algo.macrodispatch.print;

/**
 * 体系学习班class40
 *
 * 矩阵处理技巧题
 *
 * 给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
 */
public class RotateMatrix {
    public static void rotate(int[][] matrix) {
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix.length - 1;
        while (a < c) {
            rotateEdge(matrix, a++, b++, c--, d--);
        }
    }

    private static void rotateEdge(int[][] matrix, int a, int b, int c, int d) {
        int tmp = 0;
        for (int i = 0; i < d - b; i++) {
            tmp = matrix[a][b + i];
            matrix[a][b + i] = matrix[c - i][b];
            matrix[c - i][b] = matrix[c][d - i];
            matrix[c][d - i] = matrix[a + i][d];
            matrix[a + i][d] = tmp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);

    }
}
