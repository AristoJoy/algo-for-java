package com.zh.algo.range.monotonousstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 体系学习班class25
 *
 * 单调栈：
 * 描述：在一个数组中，对于位置i的值，左边第一个比我小的数的位置，右边第一个比我小的数的位置
 * 例子：
 *  arr = [ 3, 1, 2, 3]
 * 	        0  1  2  3
 * 单调栈：[
 * 	    0 : [-1,  1]
 * 	    1 : [-1, -1]
 * 	    2 : [ 1, -1]
 * 	    3 : [ 2, -1]
 * 	 ]
 *
 * 	 思路：
 * 	 准备一个栈，从栈底到栈顶从小到大，当遍历到当前i位置的数，
 * 	 如果比栈顶大，就压栈，
 * 	 如果比栈顶小，就将栈内比当前数小的位置弹出，
 * 	 此时在栈下面一个位置就是左边比弹出位置的数小的位置，而当前让栈弹出的数的位置，就是比弹出位置的数小的位置。
 * 	 遇到重复的数，将栈的元素封装成链表，将后入栈的元素放到链表尾部，而弹出栈时，链表内所有元素的结果一样
 *
 */
public class MonotonousStack {
    public static int[][] getNearLessNoRepeat(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        int N = array.length;
        int[][] ans = new int[N][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && array[stack.peek()] > array[i]) {
                int cur = stack.pop();
                int leftMost = stack.isEmpty() ? -1 : stack.peek();
                ans[cur][0] = leftMost;
                ans[cur][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int leftMost = stack.isEmpty() ? -1 : stack.peek();
            ans[cur][0] = leftMost;
            ans[cur][1] = -1;
        }
        return ans;
    }

    public static int[][] getNearLess(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        int N = array.length;
        int[][] ans = new int[N][2];
        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && array[stack.peek().get(0)] > array[i]) {
                List<Integer> cur = stack.pop();
                int leftMost = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (int p : cur) {
                    ans[p][0] = leftMost;
                    ans[p][1] = i;
                }
            }
            if (!stack.isEmpty() && array[stack.peek().get(0)] == array[i]) {
                stack.peek().add(i);
            } else {
                stack.push(new ArrayList<>());
                stack.peek().add(i);
            }
        }
        while (!stack.isEmpty()) {
            List<Integer> cur = stack.pop();
            int leftMost = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (int p : cur) {
                ans[p][0] = leftMost;
                ans[p][1] = -1;
            }
        }

        return ans;
    }

    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
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
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops!");
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
