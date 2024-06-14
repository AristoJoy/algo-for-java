package com.zh.algo.sort.mergesort;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系班class4
 * 在一个数组中，对于任何一个数num，求有多少个(后面的数*2)依然<num，返回总个数
 */
public class BiggerThanRightTwice {
    public static int reversePairs(int[] array) {
        if (array == null || array.length <= 1) {
            return 0;
        }
        return process(array, 0, array.length - 1);
    }
    private static int process(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        return process(array, left, mid)
                + process(array, mid + 1, right)
                + merge3(array, left, mid, right);
    }


//    private static int merge1(int[] array, int left, int mid, int right) {
//        int[] help = new int[right - left + 1];
//        int p1 = mid;
//        int p2 = right;
//        int i = help.length - 1;
//        int sum = 0;
//          todo-zh 这里不能使用这种方式更新，因为这样排序不是有序的，所以统计和排序必须分开
//        while (p1 >= left && p2 > mid) {
//            sum += array[p1] > 2 * array[p2] ? (p2 - mid) : 0;
//            help[i--] = array[p1] >= 2 * array[p2] ? array[p1--] : array[p2--];
//        }
//        while (p1 >= left) {
//            help[i--] = array[p1--];
//        }
//        while (p2 > mid) {
//            help[i--] = array[p2--];
//        }
//
//        for (i = 0; i < help.length; i++) {
//            array[left + i] = help[i];
//        }
//        return sum;
//    }
//
//    private static int merge2(int[] array, int left, int mid, int right) {
//        int[] help = new int[right - left + 1];
//        int p1 = left;
//        int p2 = mid + 1;
//        int i = 0;
//        int sum = 0;
//        while (p1 <= mid && p2 <= right) {
//            sum += array[p1] > 2 * array[p2] ? (mid - p1 + 1) : 0;
//            help[i++] = array[p1] > 2 * array[p2] ? array[p2++] : array[p1++];
//        }
//        while (p1 <= mid) {
//            help[i++] = array[p1++];
//        }
//        while (p2 <= right) {
//            help[i++] = array[p2++];
//        }
//
//        for (i = 0; i < help.length; i++) {
//            array[left + i] = help[i];
//        }
//        return sum;
//    }

    public static int merge3(int[] array, int left, int mid, int right) {
        int sum = 0;
        // 右组囊括的数[mid+1, windowR)
        int windowR = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (windowR <= right && ((long) array[i]) > ((long) array[windowR] << 1)) {
                windowR++;
            }
            sum += (windowR - mid - 1);
        }
        int[] help = new int[right - left + 1];
        int p1 = left;
        int p2 = mid + 1;
        int i = 0;
        while (p1 <= mid && p2 <= right) {
            help[i++] = array[p1] <= array[p2] ? array[p1++] : array[p2++];
        }
        while (p1 <= mid) {
            help[i++] = array[p1++];
        }
        while (p2 <= right) {
            help[i++] = array[p2++];
        }

        for (i = 0; i < help.length; i++) {
            array[left + i] = help[i];
        }
        return sum;
    }

    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            int count1 = reversePairs(arr1);
            int count2 = comparator(arr2);
            if (count1 != count2) {
                System.out.println("Oops!");
                System.out.println(count1);
                System.out.println(count2);
                System.out.println("---------------------------------");
                ArrayUtils.printArray(arr1);
                ArrayUtils.printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
