package com.zh.algo.guessbyquantity;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 体系学习班class39
 *
 * 根据数据量猜解法
 *
 * 给定一个非负数组arr，和一个正数m，返回arr的所有子序列中累加和%m之后的最大值
 */
public class SubSequenceMaxModM {
    public static int max1(int[] arr, int m) {
        Set<Integer> set = new HashSet<>();
        process(arr, 0, 0, set);
        int max = 0;
        for (Integer sum : set) {
            max = Math.max(max, sum % m);
        }
        return max;
    }

    private static void process(int[] arr, int index, int sum, Set<Integer> set) {
        if (index == arr.length) {
            set.add(sum);
        } else {
            process(arr, index + 1, sum, set);
            process(arr, index + 1, sum + arr[index], set);
        }
    }

    public static int max2(int[] arr, int m) {
        int sum = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }

        // 0...i达到累加和j
        boolean[][] dp = new boolean[N][sum + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }

        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }
        int ans = 0;
        for (int i = 0; i <= sum; i++) {
            if (dp[N - 1][i]) {
                ans = Math.max(ans, i % m);
            }
        }
        return ans;
    }

    public static int max3(int[] arr, int m) {
        int N = arr.length;
        boolean[][] dp = new boolean[N][m];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }

        dp[0][arr[0] % m] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = dp[i - 1][j];
                int cur = arr[i] % m;
                if (cur <= j) {
                    dp[i][j] |= dp[i - 1][j - cur];
                } else {
                    dp[i][j] |= dp[i - 1][m + j - cur];
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            if (dp[N - 1][i]) {
                ans = i;
            }
        }
        return ans;
    }

    // 如果arr的累加和很大，m也很大
    // 但是arr的长度相对不大

    public static int max4(int[] arr, int m) {
        if (arr.length == 1) {
            return arr[0] % m;
        }
        int mid = (arr.length - 1) / 2;
        TreeSet<Integer> sortedSet1 = new TreeSet<>();
        process4(arr, 0, 0, mid, m, sortedSet1);
        TreeSet<Integer> sortedSet2 = new TreeSet<>();
        process4(arr, mid + 1, 0, arr.length - 1, m, sortedSet2);
        int ans = 0;
        for (Integer leftMod : sortedSet1) {
            ans = Math.max(ans, leftMod + sortedSet2.floor(m - 1 - leftMod));
        }
        return ans;
    }

    private static void process4(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> set) {
        if (index == end + 1) {
            set.add(sum % m);
        } else {
            process4(arr, index + 1, sum, end, m, set);
            process4(arr, index + 1, sum + arr[index], end, m, set);
        }
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        int m = 76;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int ans1 = max1(arr, m);
            int ans2 = max2(arr, m);
            int ans3 = max3(arr, m);
            int ans4 = max4(arr, m);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("test finish!");

    }
}
