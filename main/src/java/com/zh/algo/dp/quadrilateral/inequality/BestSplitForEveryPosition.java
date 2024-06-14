package com.zh.algo.dp.quadrilateral.inequality;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系学习班class41
 *
 * 动态规划四边形不等式class1
 *
 * 把题目一中提到的，min{左部分累加和，右部分累加和}，定义为S(N-1)，也就是说：
 * S(N-1)：在arr[0…N-1]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 现在要求返回一个长度为N的s数组，
 * s[i] =在arr[0…i]范围上，做最优划分所得到的min{左部分累加和，右部分累加和}的最大值
 * 得到整个s数组的过程，做到时间复杂度O(N)
 */
public class BestSplitForEveryPosition {

    public static int[] bestSplit1(int[] array) {
        if (array == null || array.length == 0) {
            return new int[0];
        }
        int N = array.length;
        int[] ans = new int[N];
        ans[0] = 0;

        int sumL;
        int sumR;
        for (int range = 1; range < N; range++) {
            for (int end = 0; end < range; end++) {
                sumL = 0;
                for (int left = 0; left <= end; left++) {
                    sumL += array[left];
                }
                sumR = 0;
                for (int right = end + 1; right <= range; right++) {
                    sumR += array[right];
                }
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }

        return ans;
    }


    public static int[] bestSplit2(int[] array) {
        if (array == null || array.length == 0) {
            return new int[0];
        }
        int N = array.length;
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + array[i];
        }
        int[] ans = new int[N];
        for (int range = 1; range < N; range++) {
            for (int i = 0; i < range; i++) {
                int sumL = sum(sum, 0, i);
                int sumR = sum(sum, i + 1, range);
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    /**
     * 证明后面的划分不会比前一个划分的位置回退
     *
     * 假设0-17上的划分是9，10之间
     * 1. 0 - 9 是min，则0-18上的划分必能回退，否则左部分更小了，min变得更小
     * 2. 10 - 17 是min：
     *      a) 18进来后，左部分变成了min，则原来的右部分变大了，左部分如果回退，则左部分比之前小了，则min比之前更小
     *      b) 18进来后，右部分还是min，右部分原来就是min，
     *         如果0-17划分在9和10中间就说明，如果划分是在9前面，则左部分不如右部分，左部分是min
     *         所以0-18划分仍然不能回退，否则左部分就是min了
     */

    public static int[] bestSplit3(int[] array) {
        if (array == null || array.length == 0) {
            return new int[0];
        }
        int N = array.length;
        int[] ans = new int[N];

        // arr =   {5, 3, 1, 3}
        //          0  1  2  3
        // sum ={0, 5, 8, 9, 12}
        //       0  1  2  3   4
        // 0~2 ->  sum[3] - sum[0]
        // 1~3 ->  sum[4] - sum[1]
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + array[i];
        }
        // 最优划分
        // 0~range-1上，最优划分是左部分[0~best]  右部分[best+1~range-1]
        int best = 0;
        for (int range = 1; range < N; range++) {
            while (best + 1 < range) {
                int before = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
                int after = Math.min(sum(sum, 0, best + 1), sum(sum, best + 2, range));
                if (after >= before) {
                    best++;
                } else {
                    break;
                }
            }
            ans[range] = Math.min(sum(sum, 0, best), sum(sum, best + 1, range));
        }
        return ans;
    }

    public static int sum(int[] sum, int L, int R) {
        return sum[R + 1] - sum[L];
    }

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int[] ans1 = bestSplit1(arr);
            int[] ans2 = bestSplit2(arr);
            int[] ans3 = bestSplit3(arr);
            if (!isSameArray(ans1, ans2) || !isSameArray(ans1, ans3)) {
                System.out.println("Oops!");
                ArrayUtils.printArray(ans1);
                ArrayUtils.printArray(ans2);
                ArrayUtils.printArray(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
