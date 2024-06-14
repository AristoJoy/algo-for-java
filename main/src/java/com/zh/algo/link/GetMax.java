package com.zh.algo.link;

/**
 * 体系班class3
 * 用递归行为得到数组中的最大值
 */
public class GetMax {
    public static int getMax(int[] array) {
        return process(array, 0, array.length - 1);
    }

    private static int process(int[] array, int left, int right) {
        if (left == right) {
            return array[left];
        }
        int mid = left + ((right - left) >> 1);
        return Math.max(process(array, left, mid), process(array, mid + 1, right));
    }

}
