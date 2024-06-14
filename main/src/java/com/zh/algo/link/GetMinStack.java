package com.zh.algo.link;

import java.util.Stack;

/**
 * 实现有getMin功能的栈
 */
public class GetMinStack {

    /**
     * 两个栈同时压栈
     * min栈重复压入栈顶值或者比栈顶小的值
     */
    static class MinStack1 {
        private Stack<Integer> stackData;

        public Stack<Integer> stackMin;

        public MinStack1() {
            stackData = new Stack<>();
            stackMin = new Stack<>();
        }

        public void push(int value) {
            if (stackData.isEmpty()) {
                stackMin.push(value);
            } else {
                if (stackMin.peek() >= value) {
                    stackMin.push(value);
                } else {
                    stackMin.push(stackMin.peek());
                }
            }
            stackData.push(value);
        }

        public int pop() {
            if (stackData.isEmpty()) {
                throw new RuntimeException("stack empty");
            }
            stackMin.pop();
            return stackData.pop();
        }

        public int peek() {
            return stackData.peek();
        }

        public int getMin() {
            return stackMin.peek();
        }

        public boolean isEmpty() {
            return stackData.isEmpty();
        }
    }

    /**
     * min栈只有在当前数小于等于栈顶元素时才压入栈
     * 弹出栈时，只有data的值等于min栈，min栈才会弹出
     */
    static class MinStack2 {
        private Stack<Integer> stackData;

        public Stack<Integer> stackMin;

        public MinStack2() {
            stackData = new Stack<>();
            stackMin = new Stack<>();
        }

        public void push(int value) {
            if (stackMin.isEmpty()) {
                stackMin.push(value);
            } else if (value <= stackMin.peek()) {
                stackMin.push(value);
            }
            stackData.push(value);
        }

        public int pop() {
            if (stackData.isEmpty()) {
                throw new RuntimeException("stack empty");
            }
            Integer value = stackData.pop();
            if (value == stackMin.peek()) {
                stackMin.pop();
            }
            return value;
        }

        public int peek() {
            if (stackData.isEmpty()) {
                throw new RuntimeException("stack empty");
            }
            return stackData.peek();
        }

        public int getMin() {
            if (stackData.isEmpty()) {
                throw new RuntimeException("stack empty");
            }
            return stackMin.peek();
        }

        public boolean isEmpty() {
            return stackData.isEmpty();
        }

    }

    public static void main(String[] args) {
        MinStack1 stack1 = new MinStack1();
        stack1.push(3);
        System.out.println(stack1.getMin());
        stack1.push(4);
        System.out.println(stack1.getMin());
        stack1.push(1);
        System.out.println(stack1.getMin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getMin());

        System.out.println("=============");

        MinStack2 stack2 = new MinStack2();
        stack2.push(3);
        System.out.println(stack2.getMin());
        stack2.push(4);
        System.out.println(stack2.getMin());
        stack2.push(1);
        System.out.println(stack2.getMin());
        System.out.println(stack2.pop());
        System.out.println(stack2.getMin());
    }
}
