package com.zh.algo.link;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 体系班class3
 * 用双链表实现栈和队列
 */
public class DoubleEndsQueueToStackAndQueue {
    static class MyStack<T> {
        DoubleEndsQueue<T> queue;

        public MyStack() {
            queue = new DoubleEndsQueue<>();
        }

        public void push(T value) {
            queue.addFromHead(value);
        }

        public T pop() {
            return queue.popFromHead();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    static class MyQueue<T> {
        DoubleEndsQueue<T> queue;

        public MyQueue() {
            queue = new DoubleEndsQueue<>();
        }

        public void push(T value) {
            queue.addFromBottom(value);
        }

        public T poll() {
            return queue.popFromHead();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            MyStack<Integer> myStack = new MyStack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("oops!");
                        }
                    }
                }
                int numq = (int) (Math.random() * value);
                if (queue.isEmpty()) {
                    myQueue.push(numq);
                    queue.offer(numq);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(numq);
                        queue.offer(numq);
                    } else {
                        if (!isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }
}
