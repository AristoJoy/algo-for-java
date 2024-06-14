package com.zh.algo.sort.mergesort;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系班class4
 * 在一个数组中，一个数左边比它小的数的总和，叫该数的小和
 * 所有数的小和累加起来，叫数组小和
 */
public class SmallSum {
    public static int smallSum(int[] array) {
        if (array == null || array.length <= 1) {
            return 0;
        }
        return process(array, 0, array.length - 1);
    }

    private static int process(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        return process(array, left, mid)
                + process(array, mid + 1, right)
                + merge(array, left, mid, right);
    }

    private static int merge(int[] array, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int p1 = left;
        int p2 = mid + 1;
        int i = 0;
        int sum = 0;
        while (p1 <= mid && p2 <= right) {
            sum += array[p1] < array[p2] ? array[p1] * (right - p2 + 1) : 0;
            help[i++] = array[p1] < array[p2] ? array[p1++] : array[p2++];
        }
        while (p1 <= mid) {
            help[i++] = array[p1++];
        }
        while (p2 <= right) {
            help[i++] = array[p2++];
        }

        for (i = 0; i < help.length; i++) {
            array[left + i] = help[i];
        }
        return sum;
    }

    public static int comparator(int[] array) {
        if (array == null || array.length <= 1) {
            return 0;
        }
        int sum = 0;
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                sum += array[j] < array[i] ? array[j] : 0;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            if (smallSum(arr1) != comparator(arr2)) {
                succeed = false;
                ArrayUtils.printArray(arr1);
                ArrayUtils.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
