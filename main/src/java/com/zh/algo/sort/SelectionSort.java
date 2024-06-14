package com.zh.algo.sort;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系班class1
 */
public class SelectionSort {
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int minIndex;
        for (int i = 0; i < array.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[minIndex] > array[j]) {
                    minIndex = j;
                }
            }
            ArrayUtils.swap(array, minIndex, i);
        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 1000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] array1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] array2 = ArrayUtils.copyArray(array1);
            sort(array1);
            ArrayUtils.comparator(array2);
            if (!ArrayUtils.isEqual(array1, array2)) {
                success = false;
                ArrayUtils.printArray(array1);
                System.out.println("-------------------------diff----------------------------");
                ArrayUtils.printArray(array2);
                break;
            }
        }
        System.out.println(success ? "Nice!" : "Fucking fucked!");
        int[] array = ArrayUtils.generateRandomArray(maxSize, maxValue);
        ArrayUtils.printArray(array);
        sort(array);
        ArrayUtils.printArray(array);
    }
}
