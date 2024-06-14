package com.zh.algo.search;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 体系班class1
 */
public class BinarySearch {
    public static boolean exist(int[] sortedArray, int ele) {
        if (sortedArray == null || sortedArray.length == 0) {
            return false;
        }
        int left = 0;
        int right = sortedArray.length - 1;
        int mid = 0;
        while (left < right) {
            mid = left + ((right - left) >> 1);
            if (sortedArray[mid] == ele) {
                return true;
            } else if (sortedArray[mid] > ele) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return sortedArray[left] == ele;
    }

    public static boolean test(int[] sortedArray, int ele) {
        if (sortedArray == null || sortedArray.length == 0) {
            return false;
        }
        for (int num : sortedArray) {
            if (num == ele) {
                return true;
            }
        }
        return false;
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
            if (test(arr, value) != exist(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
