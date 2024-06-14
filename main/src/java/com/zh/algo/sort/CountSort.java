package com.zh.algo.sort;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 体系班class8
 * 计数排序
 */
public class CountSort {
    // 只支持小于1000的非负数
    public static void countSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int max = Integer.MIN_VALUE;
        for (int num : array) {
            max = Math.max(max, num);
        }

        int[] bucket = new int[max + 1];
        for (int num : array) {
            bucket[num]++;
        }

        int j = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- > 0) {
                array[j++] = i;
            }
        }
    }

    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 150;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            countSort(arr1);
            comparator(arr2);
            if (!ArrayUtils.isEqual(arr1, arr2)) {
                succeed = false;
                ArrayUtils.printArray(arr1);
                ArrayUtils.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = ArrayUtils.generateRandomArray(maxSize, maxValue);
        ArrayUtils.printArray(arr);
        countSort(arr);
        ArrayUtils.printArray(arr);

    }
}
