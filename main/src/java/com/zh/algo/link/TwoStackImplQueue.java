package com.zh.algo.link;

import java.util.Stack;

/**
 * 体系班class3
 * 两个栈实现队列
 */
public class TwoStackImplQueue {
    static class TwoStacksQueue {
        private Stack<Integer> pushStack;
        private Stack<Integer> popStack;

        public TwoStacksQueue() {
            popStack = new Stack<>();
            pushStack = new Stack<>();
        }

        public void push(int value) {
            pushStack.push(value);
            pushToPop();
        }

        public int poll() {
            if (pushStack.isEmpty() && popStack.isEmpty()) {
                throw new RuntimeException("queue empty");
            }
            pushToPop();
            return popStack.pop();
        }

        public int peek() {
            if (pushStack.isEmpty() && popStack.isEmpty()) {
                throw new RuntimeException("queue empty");
            }
            pushToPop();
            return popStack.peek();
        }

        public boolean isEmpty() {
            return pushStack.isEmpty() && popStack.isEmpty();
        }

        private void pushToPop() {
            while (!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
    }

    public static void main(String[] args) {
        TwoStacksQueue test = new TwoStacksQueue();
        test.push(1);
        test.push(2);
        test.push(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }
}
