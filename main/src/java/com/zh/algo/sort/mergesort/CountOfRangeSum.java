package com.zh.algo.sort.mergesort;

/**
 * 体系班class5
 * 给定一个数组arr，两个整数lower和upper，
 * 返回arr中有多少个子数组的累加和在[lower,upper]范围上
 * Leetcode题目：https://leetcode.com/problems/count-of-range-sum/
 */
public class CountOfRangeSum {
    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    // 将问题转化为以i为结尾的子数组，其累加和满足要求的个数是多少，将其累加起来
    // 可以转化为：如果[0,i]累加和为sum，则以[0,i-1]范围上的子数组满足要求的数组有多少个
    // （因为累加和是以0开头的，所以将以i结尾的子数组转化为以0开头的子数组求，可以利用前面求出来的前缀和数组)
    private static int process(long[] sum, int left, int right, int lower, int upper) {
        if (left == right) {
            return sum[left] >= lower && sum[left] <= upper ? 1 : 0;
        }
        int mid = left + ((right - left) >> 1);
        return process(sum, left, mid, lower, upper)
                + process(sum, mid + 1, right, lower, upper)
                + merge(sum, left, mid, right, lower, upper);
    }

    private static int merge(long[] sum, int left, int mid, int right, int lower, int upper) {
        int ans = 0;
        int windowL = left;
        int windowR = left;
        // 左组范围 [windowL, windowR)
        // 对于右组的i和i+1来说，由于组内有序，所以sum[i] <= sum[i+1]
        // 所以sum[i+1] - lower >= sum[i] - lower，sum[i+1] - upper >= sum[i] - upper
        // 所以右组往右移动时，左组符合要求的范围也是非递减的，所以windowL是不回退的
        for (int i = mid + 1; i <= right; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            // 注意这里windowR是左组，所以界限是mid
            while (windowR <= mid && sum[windowR] <= max) {
                windowR++;
            }
            while (windowL <= mid && sum[windowL] < min) {
                windowL++;
            }
            ans += windowR - windowL;
        }
        long[] help = new long[right - left + 1];
        int p1 = left;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= right) {
            help[i++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
        }
        while (p1 <= mid) {
            help[i++] = sum[p1++];
        }
        while (p2 <= right) {
            help[i++] = sum[p2++];
        }
        for (i = 0; i < help.length; i++) {
            sum[left + i] = help[i];
        }
        return ans;
    }
}
