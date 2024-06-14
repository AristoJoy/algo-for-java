package com.zh.algo.sort.quicksort;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系班class5
 * 快排基础班、荷兰国旗版快排、随机快排的递归版
 */
public class PartitionAndQuickSort {
    // arr[L..R]上，以arr[R]位置的数做划分值
    // <= X > X
    public static int partition(int[] array, int left, int right) {
        if (left > right) {
            return -1;
        }
        if (left == right) {
            return left;
        }
        int lessEqual = left - 1;
        int index = left;
        // 因为使用array[R]当x，所以是小于
        while (index < right) {
            // 当前数大于等于x，则将其与<=区的下一个位置交换
            if (array[index] <= array[right]) {
                ArrayUtils.swap(array, index, ++lessEqual);
            }
            index++;
        }
        ArrayUtils.swap(array, ++lessEqual, right);
        return lessEqual;
    }

    // arr[L...R] 玩荷兰国旗问题的划分，以arr[R]做划分值
    // <arr[R] ==arr[R] > arr[R]
    public static int[] netherlandsFlag(int[] array, int left, int right) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        if (left == right) {
            return new int[]{left, right};
        }
        int less = left - 1;
        int more = right;
        int index = left;
        while (index < more) {
            // 相等时，只移动index
            if (array[index] == array[right]) {
                index++;
            } else if (array[index] < array[right]) {
                // 小于时，将当前数与<区的下一个位置交换，index向右移动
                ArrayUtils.swap(array, ++less, index++);
            } else {
                // 大于时，将当前数与>去的前一个位置交换，但是index不移动（todo-zh 因为这个数还未比较过）
                ArrayUtils.swap(array, index, --more);
            }
        }
        // 将其与>区的第一个数交换
        ArrayUtils.swap(array, more, right);
        return new int[]{less + 1, more};
    }

    public static void quickSort1(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        process1(array, 0, array.length - 1);
    }

    private static void process1(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        // L..R partition arr[R] [ <=arr[R] arr[R] >arr[R] ]
        int pivot = partition(array, left, right);
        process1(array, left, pivot - 1);
        process1(array, pivot + 1, right);
    }

    public static void quickSort2(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        process2(array, 0, array.length - 1);
    }

    private static void process2(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        // L..R partition arr[R] [ <=arr[R] arr[R] >arr[R] ]
        int[] equalArea = netherlandsFlag(array, left, right);
        process2(array, left, equalArea[0] - 1);
        process2(array, equalArea[1] + 1, right);
    }

    public static void quickSort3(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        process3(array, 0, array.length - 1);
    }

    private static void process3(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        ArrayUtils.swap(array, left + (int) (Math.random() * (right - left + 1)), right);
        // L..R partition arr[R] [ <=arr[R] arr[R] >arr[R] ]
        int[] equalArea = netherlandsFlag(array, left, right);
        process3(array, left, equalArea[0] - 1);
        process3(array, equalArea[1] + 1, right);
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 20;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            int[] arr3 = ArrayUtils.copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!ArrayUtils.isEqual(arr1, arr2) || !ArrayUtils.isEqual(arr2, arr3)) {
                succeed = false;
                ArrayUtils.printArray(arr1);
                System.out.println();
                ArrayUtils.printArray(arr2);
                System.out.println();
                ArrayUtils.printArray(arr3);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");

    }

}
