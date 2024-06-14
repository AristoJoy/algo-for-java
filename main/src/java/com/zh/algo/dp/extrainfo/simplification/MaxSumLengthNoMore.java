package com.zh.algo.dp.extrainfo.simplification;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 体系学习班class45
 *
 * 动态规划外部信息简化class1(和外部信息简化没关系）
 *
 * 给定一个数组arr，和一个正数M，返回在arr的子数组在长度不超过M的情况下，最大的累加和
 */
public class MaxSumLengthNoMore {
    public static int test(int[] array, int M) {
        if (array == null || array.length == 0 || M < 1) {
            return 0;
        }
        int N = array.length;
        int max = 0;
        for (int L = 0; L < N; L++) {
            int sum = 0;
            for (int R = L; R < N; R++) {
                if (R - L + 1 > M) {
                    break;
                }
                sum += array[R];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    public static int maxSum(int[] array, int M) {
        if (array == null || array.length == 0 || M < 1) {
            return 0;
        }
        int N = array.length;
        int[] sum = new int[N];
        sum[0] = array[0];
        for (int i = 1; i < N; i++) {
            sum[i] = sum[i - 1] + array[i];
        }
        Deque<Integer> qMax = new LinkedList<>();
        int end = Math.min(N, M);
        int i = 0;
        for (; i < end; i++) {
            while (!qMax.isEmpty() && sum[qMax.pollLast()] <= sum[i]) {
                qMax.pollLast();
            }
            qMax.add(i);
        }
        int max = sum[qMax.peekFirst()];
        int L = 0;
        for (; i < N; L++, i++) {
            if (qMax.peekFirst() == L) {
                qMax.pollFirst();
            }
            while (!qMax.isEmpty() && sum[qMax.peekLast()] <= sum[i]) {
                qMax.pollLast();
            }
            qMax.add(i);
            max = Math.max(max, sum[qMax.peekFirst()] - sum[L]);
        }
        for (; L < N - 1; L++) {
            if (qMax.peekFirst() == L) {
                qMax.pollFirst();
            }
            max = Math.max(max, sum[qMax.peekFirst()] - sum[L]);
        }

        return max;
    }

    // 用作测试
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // 用作测试
    public static void main(String[] args) {
        int maxN = 50;
        int maxValue = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN);
            int M = (int) (Math.random() * maxN);
            int[] arr = randomArray(N, maxValue);
            int ans1 = test(arr, M);
            int ans2 = maxSum(arr, M);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
