package com.zh.algo.range.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 体系学习班class24
 *
 * 滑动窗口应用
 *
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 *
 * 发现：
 * 当[L,R]达标时，[L,R]内所有子数组都达标
 * 当[L,R]不达标事，L往左扩或R往右扩都不达标
 */
public class AllLessNumSubArray {
    public static int num(int[] num, int sum) {
        if (num == null || num.length == 0) {
            return 0;
        }
        int count = 0;
        int N = num.length;
        Deque<Integer> maxWindow = new LinkedList<>();
        Deque<Integer> minWindow = new LinkedList<>();
        int R = 0;
        for (int L = 0; L < N; L++) {
            // R扩到不达标为止，[L,R)
            while (R < N) {
                while (!maxWindow.isEmpty() && num[maxWindow.peekLast()] <= num[R]) {
                    maxWindow.pollLast();
                }
                maxWindow.addLast(R);
                while (!minWindow.isEmpty() && num[minWindow.pollLast()] >= num[R]) {
                    minWindow.pollLast();
                }
                minWindow.addLast(R);
                if (num[maxWindow.peekFirst()] - num[minWindow.peekFirst()] > sum) {
                    break;
                } else {
                    R++;
                }
            }
            count += R - L;
            if (minWindow.peekFirst() == L) {
                minWindow.pollFirst();
            }
            if (minWindow.peekFirst() == L) {
                maxWindow.pollFirst();
            }
        }
        return count;
    }
}
