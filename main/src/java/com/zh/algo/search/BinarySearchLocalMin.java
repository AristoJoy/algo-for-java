package com.zh.algo.search;

/**
 * 体系班class1
 * 局部最小值问题
 * 定义何为局部最小值：
 * arr[0] < arr[1]，0位置是局部最小；
 * arr[N-1] < arr[N-2]，N-1位置是局部最小；
 * arr[i-1] > arr[i] < arr[i+1]，i位置是局部最小；
 * 给定一个数组arr，已知任何两个相邻的数都不相等，找到随便一个局部最小位置返回
 */
public class BinarySearchLocalMin {
    public static int localMin(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }
        if (array.length == 1 || array[0] < array[1]) {
            return 0;
        }
        if (array[array.length - 1] < array[array.length - 2]) {
            return array.length - 1;
        }
        int left = 1;
        int right = array.length - 2;
        int mid;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (array[mid] > array[mid - 1]) {
                right = mid - 1;
            } else if (array[mid] > array[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}
