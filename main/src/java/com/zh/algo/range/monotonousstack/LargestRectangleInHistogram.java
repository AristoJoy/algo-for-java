package com.zh.algo.range.monotonousstack;

import java.util.Stack;

/**
 * 体系学习班class25
 *
 * 单调栈
 *
 * 给定一个非负数组arr，代表直方图，返回直方图的最大长方形面积
 *
 * https://leetcode.com/problems/largest-rectangle-in-histogram
 *
 */
public class LargestRectangleInHistogram {
    public static int right(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            max = Math.max(max, (rightLessIndex - leftLessIndex - 1) * cur);
        }
        return max;
    }

    public static int largestRectangleArea1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int N = array.length;
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            // 注意这里相等也要弹出单调栈
            while (!stack.isEmpty() && array[stack.peek()] >= array[i]) {
                int cur = stack.pop();
                max = Math.max(max, (stack.isEmpty() ? i : i - 1 - stack.peek()) * array[cur]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? N : N - 1 - stack.peek()) * array[cur]);
        }

        return max;
    }

    public static int largestRectangleArea2(int[] array) {
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
