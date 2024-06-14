package com.zh.algo.dp.extrainfo.simplification;

/**
 * 体系学习班class46
 *
 * 动态规划外部信息简化class2
 *
 * 有台奇怪的打印机有以下两个特殊要求：
 * 打印机每次只能打印由同一个字符组成的序列。
 * 每次可以在任意起始和结束位置打印新字符，并且会覆盖掉原来已有的字符。
 * 给你一个字符串s，你的任务是计算这个打印机打印它需要的最少打印次数。
 * Leetcode题目：https://leetcode.com/problems/strange-printer/
 *
 * 思路：
 * [L,R]上最少转数
 * 划分可能性：
 * 潜台词：先刷出array[L]还是后刷出，不影响结果；划分了[L,k-1]和[k,R]后，两边就再也没有交集
 */
public class StrangePrinter {

    public static int strangePrinter1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process1(str, 0, str.length - 1);
    }

    private static int process1(char[] str, int L, int R) {
        if (L == R) {
            return 1;
        }
        int ans = R - L + 1;
        for (int k = L + 1; k <= R; k++) {
            // [L,k-1]和[k,R]上分别求转数，但是如果L和k位置相同字符，则L和k位置在同一转刷出来
            ans = Math.min(ans, process1(str, L, k - 1) + process1(str, k, R) - (str[k] == str[L] ? 1 : 0));
        }
        return ans;
    }

    public static int strangePrinter2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        return process2(str, 0, N - 1, dp);
    }

    private static int process2(char[] str, int L, int R, int[][] dp) {
        if (dp[L][R] != 0) {
            return dp[L][R];
        }
        int ans;
        if (L == R) {
            ans = 1;
        } else {
            ans = R - L + 1;
            for (int k = L + 1; k <= R; k++) {
                // [L,k-1]和[k,R]上分别求转数，但是如果L和k位置相同字符，则L和k位置在同一转刷出来
                ans = Math.min(ans, process2(str, L, k - 1, dp) + process2(str, k, R, dp) - (str[k] == str[L] ? 1 : 0));
            }
        }
        dp[L][R] = ans;
        return ans;
    }

    public static int strangePrinter3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = (str[i] == str[i + 1] ? 1 : 2);
        }
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                dp[L][R] = R - L + 1;
                for (int k = L + 1; k <= R; k++) {
                    dp[L][R] = Math.min(dp[L][R], dp[L][k - 1] + dp[k][R] - (str[k] == str[L] ? 1 : 0));
                }
            }
        }
        return dp[0][N - 1];
    }
}
