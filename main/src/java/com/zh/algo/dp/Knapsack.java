package com.zh.algo.dp;

/**
 * 体系学习班class19
 *
 * 动态规划class2
 *
 * 背包问题
 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表 i号物品的重量和价值
 * 给定一个正数bag，表示一个载重bag的袋子，装的物品不能超过这个重量
 * 返回能装下的最大价值
 */
public class Knapsack {
    public static int maxValue(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || weight.length != value.length || weight.length == 0 || bag <= 0) {
            return 0;
        }

        return process1(weight, value, 0, bag);
    }

    private static int process1(int[] weight, int[] value, int index, int rest) {
        if (rest < 0) {
            return -1;
        }
        if (index == weight.length) {
            return 0;
        }
        int p1 = process1(weight, value, index + 1, rest);
        int p2 = 0;
        int next = process1(weight, value, index + 1, rest - weight[index]);
        if (next != -1) {
            p2 = value[index] + next;
        }
        return Math.max(p1, p2);
    }

    public static int dp(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || weight.length != value.length || weight.length == 0 || bag <= 0) {
            return 0;
        }
        int N = weight.length;
        int[][] dp = new int[N + 1][bag + 1];
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= bag; rest++) {
                int p1 = dp[i + 1][rest];
                int p2 = 0;
                if (rest - weight[i] >= 0) {
                    p2 = value[i] + dp[i + 1][rest - weight[i]];
                }
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }
}
