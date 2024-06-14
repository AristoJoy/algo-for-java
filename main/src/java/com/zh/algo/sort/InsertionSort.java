package com.zh.algo.sort;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系班class1
 */
public class InsertionSort {
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0 && array[j - 1] > array[j]; j--) {
                ArrayUtils.swap(array, j - 1, j);
            }
        }
    }


}
