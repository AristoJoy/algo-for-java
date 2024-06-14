package com.zh.algo.sort;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 体系班class6
 */
public class HeapSort {

    public static void heapSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // O(nlogn)
//        for (int i = 1; i < array.length; i++) {
//            heapInsert(array, i);
//        }

        // O(n)
        for (int i = (array.length - 2) / 2; i >= 0; i--) {
            heapify(array, i, array.length);
        }

        int heapSize = array.length;
        ArrayUtils.swap(array, 0, --heapSize);
        while (heapSize > 0) {
            heapify(array, 0, heapSize);
            ArrayUtils.swap(array, 0, --heapSize);
        }
    }

    private static void heapify(int[] array, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = (left + 1) < heapSize && array[left + 1] > array[left] ? left + 1 : left;
            largest = array[largest] > array[index] ? largest : index;
            if (largest == index) {
                break;
            }
            ArrayUtils.swap(array, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private static void heapInsert(int[] array, int index) {
        while (array[index] > array[(index - 1) / 2]) {
            ArrayUtils.swap(array, (index - 1) / 2, index);
            index = (index - 1) / 2;
        }
    }

    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static void main(String[] args) {

        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(6);
        heap.add(8);
        heap.add(0);
        heap.add(2);
        heap.add(9);
        heap.add(1);

        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!ArrayUtils.isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = ArrayUtils.generateRandomArray(maxSize, maxValue);
        ArrayUtils.printArray(arr);
        heapSort(arr);
        ArrayUtils.printArray(arr);
    }
}
