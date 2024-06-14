package com.zh.algo.maxlengthoffixedsum;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * 体系学习班class40
 *
 * 子数组达到规定累加和的最大长度系列问题
 *
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0，给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的，返回其长度
 *
 * 思路：
 *     利用前缀和数组，求子数组累加和等于k，就是求前缀和数组为sum - k。
 *     从左往右遍历中，找到第一个满足该要求的位置，求子数组的长度。
 *     遍历过程可以一边求前缀和，一边求满足要求的子数组。
 */
public class LongestSumSubArrayLength {
    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = 0;
        int sum = 0;
        Map<Integer, Integer> preSumMap = new HashMap<>();
        // 十分重要
        preSumMap.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (preSumMap.containsKey(sum - k)) {
                len = Math.max(len, i - preSumMap.get(sum - k));
            }
            if (!preSumMap.containsKey(sum)) {
                preSumMap.put(sum, i);
            }
        }
        return len;
    }

    public static int right(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (isValid(arr, i, j, K)) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }
        return len;
    }

    private static boolean isValid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
    }

    // for test
    public static int[] generateRandomArray(int size, int value) {
        int[] ans = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 500000;

        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int K = (int) (Math.random() * value) - (int) (Math.random() * value);
            int ans1 = maxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println("K : " + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test end");

    }
}
