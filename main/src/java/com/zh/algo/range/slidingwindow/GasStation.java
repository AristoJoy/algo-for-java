package com.zh.algo.range.slidingwindow;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 体系学习班class24
 *
 * 滑动窗口应用
 *
 * 加油站的良好出发点问题
 *
 * https://leetcode.com/problems/gas-station
 */
public class GasStation {

    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodStations(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    // 求出全部解
    public static boolean[] goodStations(int[] gas, int[] cost) {
        int N = gas.length;
        int M = N << 1;
        int[] station = new int[M];
        // 经过当前站点，可以增加多少油
        for (int i = 0; i < N; i++) {
            station[i] = gas[i] - cost[i];
            station[i + N] = gas[i] - cost[i];
        }
        // 双倍累加和（用于循环数组求解）
        for (int i = 1; i < M; i++) {
            station[i] += station[i - 1];
        }
        Deque<Integer> qMin = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!qMin.isEmpty() && station[qMin.peekLast()] >= station[i]) {
                qMin.pollLast();
            }
            qMin.addLast(i);
        }
        boolean[] ans = new boolean[N];
        for (int i = N, j = 0, offset = 0; i < M; i++, j++) {
            // 如果中途最小值大于等于0，则其可以完成一圈(最小值减去上一次的值才是真正的最小值）
            if (station[qMin.peekFirst()] - offset >= 0) {
                ans[j] = true;
            }
            while (!qMin.isEmpty() && station[qMin.pollLast()] >= station[i]) {
                qMin.pollLast();
            }
            qMin.addLast(i);
            if (qMin.peekFirst() == i) {
                qMin.pollFirst();
            }
            // 记录上一次的数据
            offset = station[i];
        }
        return ans;
    }
 }
