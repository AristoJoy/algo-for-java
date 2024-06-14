package com.zh.algo.sort;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系班class1
 */
public class BubbleSort {
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        boolean sorted = false;
        for (int i = array.length - 1; i > 0 && (sorted = !sorted); i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    ArrayUtils.swap(array, j, j + 1);
                    sorted = false;
                }
            }
        }
    }

    public static void sortOpt(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int l = 0;
        int r = array.length - 1;
        int last;
        for (; r > 0; r = last) {
            last = 0;
            for (int i = 0; i < r; i++) {
                if (array[i] > array[i + 1]) {  // 逆序对仅存在于[0, last)
                    ArrayUtils.swap(array, i, i + 1);
                    last = i;
                }
            }
        }
    }

    public static void main(String[] args) {
        testSort();
        System.out.println("********************************* sort ***************************************");
        testSortOpt();
    }

    private static void testSortOpt() {
        int testTime = 500;
        int maxSize = 100;
        int maxValue = 1000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] array1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] array2 = ArrayUtils.copyArray(array1);
            sortOpt(array1);
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
        sortOpt(array);
        ArrayUtils.printArray(array);
    }

    private static void testSort() {
        int testTime = 500;
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
