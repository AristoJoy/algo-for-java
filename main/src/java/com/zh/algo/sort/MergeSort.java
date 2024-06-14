package com.zh.algo.sort;

public class MergeSort {
    public static void sort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        mergeSort(array, 0, array.length - 1);
    }

    private static void mergeSort(int[] array, int l, int r) {
        if (l == r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(array, l, mid);
        mergeSort(array, mid + 1, r);
        merge(array, l, mid, r);
    }

    private static void merge(int[] array, int l, int m, int r) {
        int[] mergeRes = new int[r - l + 1];
        int l1 = l;
        int l2 = m + 1;
        int i = 0;
        while (l1 <= m && l2 <= r) {
            if (array[l1] <= array[l2]) {
                mergeRes[i++] = array[l1++];
            } else {
                mergeRes[i++] = array[l2++];
            }
        }
        while (l1 <= m) {
            mergeRes[i++] = array[l1++];
        }
        while (l2 <= r) {
            mergeRes[i++] = array[l2++];
        }
        for (int j = 0; j < mergeRes.length; j++) {
            array[l1 + j] = mergeRes[j];
        }
    }

    private static void mergeSort2(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int mergeSize = 1;
        while (mergeSize < array.length) {
            int l = 0;
            while (l < array.length) {
                int m = l + mergeSize - 1;
                if (m >= array.length) {
                    break;
                }
                int r = Math.min(m + mergeSize, array.length - 1);
                merge(array, l, m, r);
                l = r + 1;
            }
            // 防止溢出
            if (mergeSize > array.length / 2) {
                break;
            }
            mergeSize <<= 1;
        }
    }
}
