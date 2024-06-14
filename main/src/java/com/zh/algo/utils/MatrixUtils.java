package com.zh.algo.utils;

/**
 * 矩阵运算基本算法（无优化）
 */
public class MatrixUtils {
    public static int[][] multiply(int[][] m1, int[][] m2) {
        if (m1 == null || m2 == null || m1.length == 0 || m2.length == 0 || m1[0].length != m2.length) {
            return null;
        }
        int[][] res = new int[m1.length][m2[0].length];
        int N = res.length;
        int M = res[0].length;
        int T = m1[0].length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int t = 0;
                for (int k = 0; k < T; k++) {
                    t += m1[i][k] * m2[k][j];
                }
                res[i][j] = t;
            }
        }
        return res;
    }

    public static int[][] unitMatrix(int M) {
        if (M <= 0) {
            return null;
        }
        int[][] matrix = new int[M][M];
        for (int i = 0; i < M; i++) {
            matrix[i][i] = 1;
        }
        return matrix;
    }

    public static int[][] matrixPow(int[][] base, int p) {
        int[][] res = MatrixUtils.unitMatrix(base.length);
        int[][] t = base;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = MatrixUtils.multiply(res, t);
            }
            t = MatrixUtils.multiply(t, t);
        }
        return res;
    }
}
