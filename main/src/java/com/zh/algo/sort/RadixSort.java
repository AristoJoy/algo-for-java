package com.zh.algo.sort;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 体系班class8
 * 基数排序
 */
public class RadixSort {
    public static void radixSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        radixSort(array, 0, array.length - 1, getMaxBit(array));
    }

    public static void radixSort(int[] array, int left, int right, int maxBits) {
        int radix = 10;
        int[] help = new int[(right - left + 1)];
        for (int d = 1; d <= maxBits; d++) {
            // count[0] 当前位(d位)是0的数字有多少个
            // count[1] 当前位(d位)是(0和1)的数字有多少个
            // count[2] 当前位(d位)是(0、1和2)的数字有多少个
            // count[i] 当前位(d位)是(0~i)的数字有多少个
            int[] count = new int[radix];

            for (int j = left; j <= right; j++) {
                int digit = getDigit(array[j], d);
                count[digit]++;
            }
            // todo-zh <= i的数有多少个
            for (int i = 1; i < radix; i++) {
                count[i] = count[i - 1] + count[i];
            }

            // todo-zh 从右往左遍历，则count[digit] - 1就是这个数该排的位置
            for (int i = right; i >= left; i--) {
                int digit = getDigit(array[i], d);
                help[--count[digit]] = array[i];
            }

            for (int i = 0; i < help.length; i++) {
                array[left + i] = help[i];
            }
        }
    }

    public static int getMaxBit(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int num : array) {
            max = Math.max(max, num);
        }
        int bits = 0;
        while (max != 0) {
            bits++;
            max /= 10;
        }
        return bits;
    }

    public static int getDigit(int num, int d) {
        return ((num / ((int) Math.pow(10, d - 1))) % 10);
    }

    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }


    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            radixSort(arr1);
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
        radixSort(arr);
        ArrayUtils.printArray(arr);

    }

}
