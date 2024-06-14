package com.zh.algo.dp.quadrilateral.inequality;

/**
 * 体系学习班class41
 *
 * 动态规划四边形不等式class1
 *
 * 给定一个非负数组arr，长度为N，
 * 那么有N-1种方案可以把arr切成左右两部分
 * 每一种方案都有，min{左部分累加和，右部分累加和}
 * 求这么多方案中，min{左部分累加和，右部分累加和}的最大值是多少？
 * 整个过程要求时间复杂度O(N)
 */
public class BestSplitForAll {

    public static int bestSplit1(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        int N = array.length;
        int ans = 0;
        int sumL;
        int sumR;
        for (int mid = 0; mid < N; mid++) {
            sumL = 0;
            for (int left = 0; left <= mid; left++) {
                sumL += array[left];
            }
            sumR = 0;
            for (int right = mid + 1; right < N; right++) {
                sumR += array[right];
            }
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }

    public static int bestSplit2(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        int N = array.length;
        int sumAll = 0;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            sumAll += array[i];
        }

        int sumL = 0;
        int sumR;
        for (int i = 0; i < N; i++) {
            sumL += array[i];
            sumR = sumAll - sumL;
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int ans1 = bestSplit1(arr);
            int ans2 = bestSplit2(arr);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

}
