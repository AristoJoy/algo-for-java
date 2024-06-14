package com.zh.algo.kth;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 体系学习班class29
 *
 * 在无序数组中找到第K小的数（改写快排+bfprt）
 */
public class FindMinK {

    // 利用大根堆，时间复杂度O(N*logK)
    public static int minKth1(int[] arr, int k) {
        if (arr == null || k <= 0 || arr.length < k) {
            return Integer.MIN_VALUE;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    // 改写快排，时间复杂度O(N)
    // k >= 1
    public static int minKth2(int[] arr, int k) {
        if (arr == null || k <= 0 || arr.length < k) {
            return Integer.MIN_VALUE;
        }
        int[] nums = Arrays.copyOf(arr, arr.length);
        return process(nums, 0, nums.length - 1, k - 1);
    }

    private static int process(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        // 不止一个数  L +  [0, R -L]
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] equal = partition(arr, L, R, pivot);
        if (index >= equal[0] && index <= equal[1]) {
            return arr[index];
        } else if (index < equal[0]) {
            return process(arr, L, equal[0] - 1, index);
        } else {
            return process(arr, equal[1] + 1, R, index);
        }
    }

    private static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                ArrayUtils.swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                ArrayUtils.swap(arr, --more, cur);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static void insertSort(int[] arr, int L, int R) {
        //  [L,L + 1, L + 2,..., R - 1, R]
        //  [L,        i]
        //  [L,     i - 1]
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                ArrayUtils.swap(arr, j, j + 1);
            }
        }
    }

    // 改写快排，时间复杂度O(N)
    // k >= 1
    public static int minKth3(int[] arr, int k) {
        if (arr == null || k <= 0 || arr.length < k) {
            return Integer.MIN_VALUE;
        }
        int[] nums = Arrays.copyOf(arr, arr.length);
        return bfprt(nums, 0, nums.length - 1, k - 1);
    }

    private static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = getMedianOfMedians(arr, L, R);
        int[] equal = partition(arr, L, R, pivot);
        if (index >= equal[0] && index <= equal[1]) {
            return arr[index];
        } else if (index < equal[0]) {
            return bfprt(arr, L, equal[0] - 1, index);
        } else {
            return bfprt(arr, equal[1] + 1, R, index);
        }
    }

    private static int getMedianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int[] mArr = new int[(size + 4) / 5];
        // 1. 分组（5个一组）
        for (int team = 0; team < mArr.length; team++) {
            int first = L + team * 5;
            // 2. 5个数排序
            // 3. 获取中位数
            mArr[team] = getMedian(arr, first, Math.min(R, first + 4));
        }
        // 找到m数组的中位数
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    private static int getMedian(int[] arr, int L, int R) {
        insertSort(arr, L, R);
        return arr[(L + R) / 2];
    }

}
