package com.zh.algo.recursion;

import java.util.Stack;

/**
 * 给定一个栈，请逆序这个栈，不能申请额外的数据结构，只能使用递归函数
 */
public class ReverseStackUsingRecursive {

    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        // 弹出栈底 d
        int bottom = fun(stack);
        // 逆序剩余栈元素
        reverse(stack);
        // 将栈底元素push到栈顶
        stack.push(bottom);
    }

    /**
     * 移除栈底元素
     * 栈中保持原来顺序
     * 返回栈底元素
     * @param stack
     * @return
     */
    public static int fun(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int bottom = fun(stack);
            stack.push(result);
            return bottom;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }
        System.out.println(stack);
        reverse(stack);
        System.out.println(stack);
    }
}
