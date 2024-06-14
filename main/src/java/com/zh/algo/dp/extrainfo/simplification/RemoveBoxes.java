package com.zh.algo.dp.extrainfo.simplification;

/**
 * 体系学习班class45
 *
 * 动态规划外部信息简化class1
 *
 * 给出一些不同颜色的盒子，盒子的颜色由数字表示，即不同的数字表示不同的颜色，你将经过若干轮操作去去掉盒子
 * 直到所有的盒子都去掉为止，每一轮你可以移除具有相同颜色的连续k个盒子（k >= 1）
 * 这样一轮之后你将得到 k * k 个积分，当你将所有盒子都去掉之后，求你能获得的最大积分和
 * Leetcode题目：https://leetcode.com/problems/remove-boxes/
 *
 * 思路：
 * 潜台词：前面K个数跟L位置的数相同
 *
 */
public class RemoveBoxes {
    public static int removeBoxes0(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        return process0(array, 0, array.length - 1, 0);
    }

    private static int process0(int[] array, int L, int R, int K) {
        if (L > R) {
            return 0;
        }
        // 前K个array[L]和array[L]消除
        int ans = process0(array, L + 1, R, 0) + (K + 1) * (K + 1);
        for (int i = L + 1; i <= R; i++) {
            // array[L]和array[i]一起消掉
            if (array[i] == array[L]) {
                ans = Math.max(ans, process0(array, L + 1, i - 1, 0) + process0(array, i + 1, R, K + 1));
            }
        }
        return ans;
    }

    public static int removeBoxes1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int N = array.length;
        int[][][] dp = new int[N][N][N];
        return process1(array, 0, N - 1, 0, dp);
    }

    private static int process1(int[] array, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        if (dp[L][R][K] > 0) {
            return dp[L][R][K];
        }
        // 前K个array[L]和array[L]消除
        int ans = process1(array, L + 1, R, 0, dp) + (K + 1) * (K + 1);
        for (int i = L + 1; i <= R; i++) {
            // array[L]和array[i]一起消掉
            if (array[i] == array[L]) {
                ans = Math.max(ans, process1(array, L + 1, i - 1, 0, dp) + process1(array, i, R, K + 1, dp));
            }
        }
        dp[L][R][K] = ans;
        return ans;
    }


    public static int removeBoxes2(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int N = array.length;
        int[][][] dp = new int[N][N][N];
        return process2(array, 0, N - 1, 0, dp);
    }

    private static int process2(int[] array, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        if (dp[L][R][K] > 0) {
            return dp[L][R][K];
        }
        // 找到开头，
        // 1,1,1,1,1,5
        // 3 4 5 6 7 8
        //         !
        int last = L;
        while (last + 1 <= R && array[last + 1] == array[L]) {
            last++;
        }
        int pre = K + last - L;
        // 前pre个array[L]和array[last]消除
        int ans = process2(array, last + 1, R, 0, dp) + (pre + 1) * (pre + 1);
        for (int i = last + 2; i <= R; i++) {
            // array[last]和array[i]一起消掉(开始第一个array[L]
            if (array[i] == array[L] && array[i - 1] != array[L]) {
                ans = Math.max(ans, process2(array, last + 1, i - 1, 0, dp) + process2(array, i, R, pre + 1, dp));
            }
        }
        dp[L][R][K] = ans;
        return ans;
    }
}
