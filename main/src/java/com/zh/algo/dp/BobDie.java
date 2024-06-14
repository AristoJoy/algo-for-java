package com.zh.algo.dp;

/**
 * 体系学习班class21
 * <p>
 * 动态规划class4
 * <p>
 * 给定5个参数，N，M，row，col，k
 * 表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候Bob只要离开N*M的区域，就直接死亡
 * 返回k步之后，Bob还在N*M的区域的概率
 */
public class BobDie {

    public static double liveProbability1(int row, int col, int k, int N, int M) {
        return (double) process(row, col, k, N, M) / Math.pow(4, k);
    }

    private static long process(int row, int col, int rest, int n, int m) {
        if (row < 0 || row == n || col < 0 || col == m) {
            return 0;
        }
        if (rest == 0) {
            return 1;
        }
        int ways = 0;
        ways += process(row - 1, col, rest - 1, n, m);
        ways += process(row + 1, col, rest - 1, n, m);
        ways += process(row, col - 1, rest - 1, n, m);
        ways += process(row, col + 1, rest - 1, n, m);

        return ways;
    }

    public static double liveProbability2(int row, int col, int k, int N, int M) {
        long[][][] dp = new long[N][M][k + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    long ways = 0;
                    ways += pick(dp, r - 1, c, rest - 1, N, M);
                    ways += pick(dp, r + 1, c, rest - 1, N, M);
                    ways += pick(dp, r, c - 1, rest - 1, N, M);
                    ways += pick(dp, r, c + 1, rest - 1, N, M);
                    dp[r][c][rest] = ways;
                }
            }
        }

        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    private static long pick(long[][][] dp, int row, int col, int rest, int n, int m) {
        if (row < 0 || row == n || col < 0 || col == m) {
            return 0;
        }
        return dp[row][col][rest];
    }


    public static void main(String[] args) {
        System.out.println(liveProbability1(6, 6, 10, 50, 50));
        System.out.println(liveProbability2(6, 6, 10, 50, 50));
    }
}
