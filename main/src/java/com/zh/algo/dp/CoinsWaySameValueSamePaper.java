package com.zh.algo.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 体系学习班class21
 *
 * 动态规划class4
 *
 * arr是货币数组，其中的值都是正数。再给定一个正数aim。
 * 每个值都认为是一张货币，
 * 认为值相同的货币没有任何不同，
 * 返回组成aim的方法数
 * 例如：arr = {1,2,1,1,2,1,2}，aim = 4
 * 方法：1+1+1+1、1+1+2、2+2
 * 一共就3种方法，所以返回3
 */
public class CoinsWaySameValueSamePaper {
    static class Info {
        private int[] values;
        private int[] nums;

        public Info(int[] values, int[] nums) {
            this.values = values;
            this.nums = nums;
        }
    }

    public static Info getInfo(int[] array) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int paper : array) {
            if (!counts.containsKey(paper)) {
                counts.put(paper, 1);
            } else {
                counts.put(paper, counts.get(paper) + 1);
            }
        }
        int[] values = new int[counts.size()];
        int[] nums = new int[counts.size()];
        int i = 0;
        for (Integer paper : counts.keySet()) {
            values[i] = paper;
            nums[i] = counts.get(paper);
        }

        return new Info(values, nums);
    }

    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        return process(info.values, info.nums, 0, aim);
    }

    private static int process(int[] values, int[] nums, int index, int rest) {
        if (index == values.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int num = 0; num <= nums[index] && num * values[index] <= rest; num++) {
            ways += process(values, nums, index + 1, rest - num * values[index]);
        }
        return ways;
    }

    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] values = info.values;
        int[] nums = info.nums;
        int N = values.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int num = 0; num <= nums[index] && num * values[index] <= rest; num++) {
                    ways += dp[index + 1][rest - num * values[index]];
                }
                dp[index][rest] = ways;
            }
        }

        return dp[0][aim];
    }

    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] values = info.values;
        int[] nums = info.nums;
        int N = values.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - values[index] >= 0) {
                    dp[index][rest] += dp[index][rest - values[index]];
                }

                if (rest - values[index] * (nums[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - values[index] * (nums[index] + 1)];
                }
            }
        }

        return dp[0][aim];
    }


    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
