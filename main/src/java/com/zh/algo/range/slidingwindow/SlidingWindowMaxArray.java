package com.zh.algo.range.slidingwindow;

import com.zh.algo.utils.ArrayUtils;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 体系学习班class24
 *
 * 滑动窗口：当数弹出窗口时，哪些值依次称为最大值
 *
 * 窗口内最大值或最小值更新结构的实现
 * 假设一个固定大小为W的窗口，依次划过arr，
 * 返回每一次滑出状况的最大值
 * 例如，arr = [4,3,5,4,3,3,6,7], W = 3
 * 返回：[5,5,5,4,6,7]
 */
public class SlidingWindowMaxArray {
    public static int[] getMaxWindow(int[] array, int w) {
        if (array == null || w < 1 || array.length < w) {
            return null;
        }
        Deque<Integer> qMax = new LinkedList<>();
        int[] ans = new int[array.length - w + 1];
        int index = 0;
        for (int R = 0; R < array.length; R++) {
            // 窗口内的值小于等于当前数的弹出
            while (!qMax.isEmpty() && array[qMax.peekLast()] <= array[R]) {
                qMax.pollLast();
            }
            // 压入新值
            qMax.addLast(R);
            // 如果窗口第一值过期，将其弹出
            if (qMax.peekFirst() == R - w) {
                qMax.pollFirst();
            }
            // 如果窗口大小达到w，记录值
            if (R >= w - 1) {
                ans[index++] = array[qMax.peekFirst()];
            }
        }

        return ans;
    }

    // 暴力的对数器方法
    public static int[] right(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = right(arr, w);
            if (!ArrayUtils.isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
