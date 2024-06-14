package com.zh.algo.sort.mergesort;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系班class4
 * 在一个数组中，任何一个前面的数a，和任何一个后面的数b，如果(a,b)是降序的，就称为降序对
 * 给定一个数组arr，求数组的降序对总数量
 */
public class ReversePair {
    public static int reversePairNum(int[] array) {
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
                + merge2(array, left, mid, right);
    }

    private static int merge1(int[] array, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int p1 = mid;
        int p2 = right;
        int i = help.length - 1;
        int sum = 0;
        while (p1 >= left && p2 > mid) {
            sum += array[p1] <= array[p2] ? 0 : (p2 - mid);
            help[i--] = array[p1] <= array[p2] ? array[p2--] : array[p1--];
        }
        while (p1 >= left) {
            help[i--] = array[p1--];
        }
        while (p2 > mid) {
            help[i--] = array[p2--];
        }

        for (i = 0; i < help.length; i++) {
            array[left + i] = help[i];
        }
        return sum;
    }

    private static int merge2(int[] array, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int p1 = left;
        int p2 = mid + 1;
        int i = 0;
        int sum = 0;
        while (p1 <= mid && p2 <= right) {
            sum += array[p1] <= array[p2] ? 0 : (mid - p1 + 1);
            help[i++] = array[p1] <= array[p2] ? array[p1++] : array[p2++];
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

    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    ans++;
                }
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            int[] arr3 = ArrayUtils.copyArray(arr1);
            if (reversePairNum(arr1) != comparator(arr2)) {
                System.out.println("Oops!");
                ArrayUtils.printArray(arr1);
                ArrayUtils.printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
