package com.zh.algo.range.monotonousstack;

/**
 * 体系学习班class25
 *
 * 单调栈
 *
 * 给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的最大子矩形内部有多少个1（面积）
 *
 * 思路：
 * 以第i行为地基，全由1组成的最大的矩形有多少个1。
 * 数组压缩和累加上一行结果，当前行列为0，则清零
 * 求直方图的最大矩形面积
 *
 * 测试链接：https://leetcode.com/problems/maximal-rectangle/
 */
public class MaximalRectangle {

    public static int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        int[] sum = new int[M];
        int maxArea = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sum[j] = matrix[i][j] == '0' ? 0 : sum[j] + 1;
            }
            maxArea = Math.max(maxArea, largestRectangleArea(sum));
        }
        return maxArea;
    }


    public static int largestRectangleArea(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int N = array.length;
        int max = Integer.MIN_VALUE;
        int[] stack = new int[N];
        int top = 0;
        for (int i = 0; i < N; i++) {
            while (top > 0 && array[stack[top - 1]] >= array[i]) {
                int cur = stack[--top];
                max = Math.max(max, (top == 0 ? i : i - 1 - stack[top - 1]) * array[cur]);
            }
            stack[top++] = i;
        }
        while (top > 0) {
            int cur = stack[--top];
            max = Math.max(max, (top == 0 ? N : N - 1 - stack[top - 1]) * array[cur]);
        }

        return max;
    }
}
