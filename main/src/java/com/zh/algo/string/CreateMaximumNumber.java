package com.zh.algo.string;

/**
 * 体系学习班class45
 *
 * DC3算法应用
 *
 * 给两个长度分别为M和N的整型数组nums1和nums2，其中每个值都不大于9，再给定一个正数K。 你可以在nums1和nums2中挑选数字，要求一共挑选K个，并且要从左到右挑。返回所有可能的结果中，代表最大数字的结果
 *
 * 测试链接: https://leetcode.com/problems/create-maximum-number/
 */
public class CreateMaximumNumber {

    public static int[] maxNumber1(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        if (k < 0 || k > len1 + len2) {
            return null;
        }
        int[] res = new int[k];
        int[][] dp1 = getDp(nums1); // 生成dp1这个表，以后从nums1中，只要固定拿N个数，
        int[][] dp2 = getDp(nums2);
        // get1 从arr1里拿的数量
        // K - get1 从arr2里拿的数量
        for (int get1 = Math.max(0, k - len2); get1 <= Math.min(k, len1); get1++) {
            // arr1 挑 get1个，怎么得到一个最优结果
            int[] pick1 = maxPick(nums1, dp1, get1);
            int[] pick2 = maxPick(nums2, dp2, k - get1);
            int[] merge = merge(pick1, pick2);
            res = preMoreThanLast(res, 0, merge, 0) ? res : merge;
        }
        return res;
    }

    public static int[] merge(int[] nums1, int[] nums2) {
        int k = nums1.length + nums2.length;
        int[] ans = new int[k];
        for (int i = 0, j = 0, r = 0; r < k; ++r) {
            ans[r] = preMoreThanLast(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }
        return ans;
    }

    public static boolean preMoreThanLast(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }

    public static int[] maxNumber2(int[] nums1, int[] nums2, int k) {
        int N = nums1.length;
        int M = nums2.length;

        if (k < 0 || k > N + M) {
            return null;
        }

        int[] ans = new int[k];
        // dp[i][j]表示以i...的数，选择j个最大的，第一个位置在哪
        int[][] dp1 = getDp(nums1);
        int[][] dp2 = getDp(nums1);
        // get1 从arr1里拿的数量
        // K - get1 从arr2里拿的数量
        for (int get1 = Math.max(0, k - M); get1 <= Math.min(k, N); get1++) {
            // arr1 挑 get1个，怎么得到一个最优结果
            int[] picks1 = maxPick(nums1, dp1, get1);
            int[] picks2 = maxPick(nums1, dp2, k - get1);
            int[] merge = mergeBySuffixArray(picks1, picks2);
            ans = moreThan(ans, merge) ? merge : ans;
        }
        return ans;
    }


    private static int[][] getDp(int[] arr) {
        int size = arr.length; // 0 ~ N
        int pick = size + 1; // 1 ~ N
        int[][] dp = new int[size][pick];
        // get 不从0开始，因为拿0个无意义
        for (int get = 1; get < pick; get++) {
            int maxIndex = size - get;
            for (int i = size - get; i >= 0; i--) {
                if (arr[i] >= arr[maxIndex]) {
                    maxIndex = i;
                }
                dp[i][get] = maxIndex;
            }
        }
        return dp;
    }

    private static int[] maxPick(int[] nums, int[][] dp, int pick) {
        int[] ans = new int[pick];
        for (int index = 0, dpRow = 0; pick > 0; pick--, index++) {
            ans[index] = nums[dp[dpRow][pick]];
            dpRow = dp[dpRow][pick] + 1;
        }
        return ans;
    }

    private static int[] mergeBySuffixArray(int[] picks1, int[] picks2) {
        int N = picks1.length;
        int M = picks2.length;
        int[] all = new int[N + M + 1];
        int i = 0;
        for (; i < N; i++) {
            all[i] = picks1[i] + 2;
        }
        all[i++] = 1;
        for (int j = 0; i < M; i++) {
            all[i] = picks2[j] + 2;
        }
        DC3 dc3 = new DC3(all, 11);
        int[] rank = dc3.getRank();
        int[] ans = new int[N + M];
        i = 0;
        int j = 0;
        int r = 0;
        while (i < N && j < M) {
            ans[r++] = rank[i] > rank[j + M + 1] ? picks1[i++] : picks2[j++];
        }
        while (i < N) {
            ans[r++] = picks1[i++];
        }
        while (j < M) {
            ans[r++] = picks2[j++];
        }
        return ans;
    }

    private static boolean moreThan(int[] ans, int[] merge) {
        int i = 0;
        int j = 0;
        while (i < ans.length && j < merge.length && ans[i] == merge[j]) {
            i++;
            j++;
        }
        return j == merge.length || (i < ans.length && ans[i] > merge[j]);
    }

}
