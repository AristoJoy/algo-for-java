package com.zh.algo.sort.heapsort;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 体系班class6
 * 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离一定不超过k
 * k相对于数组长度来说是比较小的。请选择一个合适的排序策略，对这个数组进行排序。
 */
public class SortArrayDistanceLessK {
    public static void sortArrayDistanceLessK(int[] array, int k) {
        if (k <= 0) {
            return;
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int index = 0;
        // 0 位置的数，只有[0,k]中最小的那个，而1位置的数，由于0位置被占了，所以只有[1,k+1]位置中最小的那个数
        for (; index <= Math.min(array.length - 1, k - 1); index++) {
            minHeap.add(array[index]);
        }
        int i = 0;
        for (; index < array.length; i++, index++) {
            minHeap.add(array[index]);
            array[i] = minHeap.poll();
        }

        while (!minHeap.isEmpty()) {
            array[i++] = minHeap.poll();
        }
    }

    public static void comparator(int[] arr, int k) {
        Arrays.sort(arr);
    }

    public static int[] randomArrayNoMoveMoreK(int maxSize, int maxValue, int K) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        // 先排个序
        Arrays.sort(arr);
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
            int[] arr1 = ArrayUtils.copyArray(arr);
            int[] arr2 = ArrayUtils.copyArray(arr);
            sortArrayDistanceLessK(arr1, k);
            comparator(arr2, k);
            if (!ArrayUtils.isEqual(arr1, arr2)) {
                succeed = false;
                System.out.println("K : " + k);
                ArrayUtils.printArray(arr);
                ArrayUtils.printArray(arr1);
                ArrayUtils.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
