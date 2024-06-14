package com.zh.algo.dp.quadrilateral.inequality;

import java.util.Arrays;

/**
 * 体系学习班class42
 *
 * 动态规划四边形不等式class2
 *
 * 邮局选址问题：
 *
 * 一条直线上有居民点，邮局只能建在居民点上
 * 给定一个有序正数数组arr，每个值表示 居民点的一维坐标，再给定一个正数 num，表示邮局数量
 * 选择num个居民点建立num个邮局，使所有的居民点到最近邮局的总距离最短，返回最短的总距离
 * arr=[1,2,3,4,5,1000]，num=2
 * 第一个邮局建立在3位置，第二个邮局建立在1000位置
 * 那么1位置到邮局的距离为2，2位置到邮局距离为1，3位置到邮局的距离为0，4位置到邮局的距离为1，5位置到邮局的距离为2
 * 1000位置到邮局的距离为0
 * 这种方案下的总距离为6，其他任何方案的总距离都不会比该方案的总距离更短，所以返回6
 */
public class PostOfficeProblem {
    // 划分问题，最后一个邮局负责范围
    // 所以最后一个邮局负责的问题，就转换为L到R上，一个邮局最短距离，可知中点是最好的地点，
    // 但是在居民点为偶数时，上下中点的总距离是一样的，所以可以认为邮局总是选在下中点，这样可以利用上一次的结果
    public static int min1(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 1) {
            return 0;
        }
        int N = arr.length;
        int[][] distance = new int[N + 1][N + 1];
        for (int L = 0; L < N; L++) {
            for (int R = L + 1; R < N; R++) {
                distance[L][R] = distance[L][R - 1] + arr[R] - arr[(L + R) >> 1];
            }
        }
        int[][] dp = new int[N][num + 1];
        // 一个邮局，上面已经求出
        for (int i = 0; i < N; i++) {
            dp[i][1] = distance[0][i];
        }

        // 0-i上j个邮局
        for (int i = 1; i < N; i++) {
            for (int j = 2; j <= Math.min(j, num); j++) {
                int ans = Integer.MAX_VALUE;
                // 0 - k j-1个邮局
                // k - i 第j个邮局
                for (int k = 0; k <= i; k++) {
                    ans = Math.min(ans, dp[k][j - 1] + distance[k + 1][i]);
                }
                dp[i][j] = ans;
            }
        }
        return dp[N - 1][num];
    }

    public static int min2(int[] arr, int num) {
        if (arr == null || arr.length == 0 || num < 1) {
            return 0;
        }
        int N = arr.length;
        int[][] distance = new int[N + 1][N + 1];
        for (int L = 0; L < N; L++) {
            for (int R = L + 1; R < N; R++) {
                distance[L][R] = distance[L][R - 1] + arr[R] - arr[(L + R) >> 1];
            }
        }
        int[][] dp = new int[N][num + 1];
        int[][] best = new int[N][num + 1];
        // 一个邮局，上面已经求出
        for (int i = 0; i < N; i++) {
            dp[i][1] = distance[0][i];
            best[i][1] = -1;
        }

        for (int j = 2; j <= num; j++) {
            for (int i = N - 1; i >= 1; i--) {
                int down = best[i][j - 1];
                int up = i == N - 1 ? N - 1 : best[i + 1][j];
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int k = down; k <= up; k++) {
                    int leftCost = k == -1 ? 0 : dp[k][j - 1];
                    int rightCost = k == i ? 0 : distance[k + 1][i];
                    if (leftCost + rightCost <= ans) {
                        ans = leftCost + rightCost;
                        bestChoose = k;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = bestChoose;
            }
        }
        return dp[N - 1][num];
    }

    // for test
    public static int[] randomSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int N = 30;
        int maxValue = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] arr = randomSortedArray(len, maxValue);
            int num = (int) (Math.random() * N) + 1;
            int ans1 = min1(arr, num);
            int ans2 = min2(arr, num);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(num);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");

    }
}
