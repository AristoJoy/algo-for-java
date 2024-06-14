package com.zh.algo.range.monotonousstack;

/**
 * 体系学习班class26
 *
 * 单调栈
 *
 * 给定一个数组arr，返回所有子数组最小值的累加和
 *
 * subArrayMinSum1是暴力解
 * subArrayMinSum2是最优解的思路
 * sumSubarrayMins是最优解思路下的单调栈优化
 * Leetcode上不要提交subArrayMinSum1、subArrayMinSum2方法，因为没有考虑取摸
 * Leetcode上只提交sumSubarrayMins方法，时间复杂度O(N)，可以直接通过
 *
 * 思路：
 * 以i为划分了，则子数组一定要跨过i（i为最小值），求子数组数量
 *      2 4 5 3 6 6 6 1
 *      x	  i		  y
 *
 *  以i为最小值的子数组的结果 = (y - i) * ( - x) * 3
 *
 *  2 4 5 3 6 6 6 3 7 8 6 3 5 3 2
 *  |	  | 	  |
 *   第一组，右边遇到相同的数停止，然后统计子数组的数量
 *  |			  |		 |
 *  第二组，右边遇到相同的数停止，然后统计子数组的数量
 *  |                    |   |
 *  第三组，右边遇到相同的数停止，然后统计子数组的数量
 *  这样即不会多，也不会少
 *
 */
public class SumOfSubArrayMinimums {
    public static int subArrayMinSum1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int min = arr[i];
                for (int k = i + 1; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                }
                sum += min;
            }
        }
        return sum;
    }

    public static int subArrayMinSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // left[i] = x : arr[i]左边，离arr[i]最近，<=arr[i]，位置在x
        int[] left = leftNearLessEqual(arr);
        // right[i] = y : arr[i]右边，离arr[i]最近，< arr[i],的数，位置在y
        int[] right = rightNearLess(arr);
        int sum = 0;
        for (int i = 0; i < N; i++) {
            int start = i - left[i];
            int end = right[i] - i;
            sum += start * end * arr[i];
        }
        return sum;
    }

    private static int[] leftNearLessEqual(int[] arr) {
        int N = arr.length;
        int[] left = new int[N];
        for (int i = 0; i < N; i++) {
            int ans = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] <= arr[i]) {
                    ans = j;
                    break;
                }
            }
            left[i] = ans;
        }
        return left;
    }

    private static int[] rightNearLess(int[] arr) {
        int N = arr.length;
        int[] right = new int[N];
        for (int i = 0; i < N; i++) {
            int ans = N;
            for (int j = i + 1; j < N; j++) {
                if (arr[j] < arr[i]) {
                    ans = j;
                    break;
                }
            }
            right[i] = ans;
        }
        return right;
    }

    public static int sumSubarrayMins(int[] arr) {
        int N = arr.length;
        int[] stack = new int[N];
        int[] left = nearLessEqualLeft(arr, stack);
        int[] right = nearLessRight(arr, stack);
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += start * end * (long) arr[i];
            ans %= 1000000007;
        }

        return (int) ans;
    }

    private static int[] nearLessEqualLeft(int[] arr, int[] stack) {
        int N = arr.length;
        int[] left = new int[N];
        int size = 0;
        // 从右往左遍历
        for (int i = N - 1; i >= 0; i--) {
            while (size != 0 && arr[stack[size - 1]] >= arr[i]) {
                left[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        while (size != 0) {
            left[stack[--size]] = -1;
        }
        return left;
    }

    private static int[] nearLessRight(int[] arr, int[] stack) {
        int N = arr.length;
        int[] right = new int[N];
        int size = 0;
        for (int i = 0; i < N; i++) {
            while (size != 0 && arr[stack[size - 1]] > arr[i]) {
                right[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        while (size != 0) {
            right[stack[--size]] = N;
        }
        return right;
    }


    public static int[] randomArray(int len, int maxValue) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 50;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = subArrayMinSum1(arr);
            int ans2 = subArrayMinSum2(arr);
            int ans3 = sumSubarrayMins(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
