package com.zh.algo.range.indextree;

/**
 * 体系学习班class32
 * <p>
 * 2维IndexTree
 */
public class IndexTree2D {
    private int[][] tree;

    private int[][] num;
    private int N;

    private int M;

    public IndexTree2D(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        N = matrix.length;
        M = matrix[0].length;

        tree = new int[N + 1][M + 1];
        num = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }
    // 1~index 累加和是多少？
    public int sum(int row, int col) {
        if (N == 0 || M == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = row + 1; i > 0; i -= i & (-i)) {
            for (int j = col + 1; j > 0; j -= j & (-j)) {
                sum += tree[i][j];
            }
        }
        return sum;
    }

    public void update(int row, int col, int value) {
        if (N == 0 || M == 0) {
            return;
        }
        int add = value - num[row][col];
        num[row][col] = value;
        for (int i = row + 1; i <= N; i += i & (-i)) {
            for (int j = col + 1; j <= M; j += j & (-j)) {
                tree[i][j] += add;
            }
        }
    }

    // 1为左上，2位右下
    public int sumRegion(int r1, int c1, int r2, int c2) {
        if (N == 0 || M == 0) {
            return 0;
        }
        return sum(r2, c2)  + sum(r1 - 1, c1 - 1) - sum(r2, c1 - 1) - sum(r1 - 1, c2);
    }
}
