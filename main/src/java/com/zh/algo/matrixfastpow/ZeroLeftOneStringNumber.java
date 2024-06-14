package com.zh.algo.matrixfastpow;

import com.zh.algo.utils.MatrixUtils;

/**
 * 体系学习班class26
 *
 * 矩阵快速幂乘法
 *
 * 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
 * 如果某个字符串，任何0字符的左边都有1紧挨着，认为这个字符串达标
 * 返回有多少达标的字符串
 *
 * 思路：
 * int f(i)
 * 前面填1的情况下，还剩i个格子，打表的填法
 * 1)填1，剩下f(i-1)
 * 2)填0，下一位要填1，还剩下f(i-2)
 *
 * 用1*2的瓷砖，把N*2的区域填满，返回铺瓷砖的方法数(斐波那契）
 */
public class ZeroLeftOneStringNumber {
    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }

    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
    }

    public static int getNum2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int pre = 1;
        int cur = 1;
        int tmp = 0;
        for (int i = 2; i < n + 1; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }

    public static int getNum3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = { { 1, 1 }, { 1, 0 } };
        int[][] res = MatrixUtils.matrixPow(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static void main(String[] args) {
        for (int i = 0; i != 20; i++) {
            System.out.println(getNum1(i));
            System.out.println(getNum2(i));
            System.out.println(getNum3(i));
            System.out.println("===================");
        }

    }
}
