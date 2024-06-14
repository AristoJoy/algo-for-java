package com.zh.algo.search;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 体系班class1 有序数组中找到<=num最右的位置
 */
public class BinarySearchNearRight {
    public static int nearestRight(int[] sortedArray, int value) {
        if (sortedArray == null || sortedArray.length == 0) {
            return -1;
        }
        int left = 0;
        int right = sortedArray.length - 1;
        int mid;
        int index = -1;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (sortedArray[mid] <= value) {
                left = mid + 1;
                index = mid;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }

    public static int test(int[] arr, int value) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] <= value) {
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
            if (test(arr, value) != nearestRight(arr, value)) {
                ArrayUtils.printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(nearestRight(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
