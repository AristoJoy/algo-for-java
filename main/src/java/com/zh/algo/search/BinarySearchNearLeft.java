package com.zh.algo.search;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 体系班class1 有序数组中找到>=num最左的位置
 */
public class BinarySearchNearLeft {
    public static int nearestLeft(int[] sortedArray, int value) {
        if (sortedArray == null || sortedArray.length == 0) {
            return -1;
        }
        int left = 0;
        int right = sortedArray.length - 1;
        int mid;
        int index = -1;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (sortedArray[mid] >= value) {
                right = mid - 1;
                index = mid;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    public static int test(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= value) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayUtils.generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != nearestLeft(arr, value)) {
                ArrayUtils.printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(nearestLeft(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
