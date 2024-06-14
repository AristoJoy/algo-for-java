package com.zh.algo.link;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 体系班class3
 * 两个队列实现栈
 */
public class TwoQueueImplStack {
    static class TwoQueueStack<T> {
        private Queue<T> queue;
        private Queue<T> help;

        public TwoQueueStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        public void push(T value) {
            queue.offer(value);
        }

        public T poll() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T value = queue.poll();
            Queue<T> temp = queue;
            queue = help;
            help = temp;
            return value;
        }

        public T peek() {
            while (queue.size() > 1) {
                help.add(queue.poll());
            }
            T value = queue.poll();
            help.offer(value);
            Queue<T> temp = queue;
            queue = help;
            help = temp;
            return value;
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
        java.util.Stack<Integer> test = new java.util.Stack<>();
        int testTime = 1000000;
        int max = 1000000;
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Oops");
                }
                int num = (int) (Math.random() * max);
                myStack.push(num);
                test.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * max);
                    myStack.push(num);
                    test.push(num);
                } else if (Math.random() < 0.5) {
                    if (!myStack.peek().equals(test.peek())) {
                        System.out.println("Oops");
                    }
                } else if (Math.random() < 0.75) {
                    if (!myStack.poll().equals(test.pop())) {
                        System.out.println("Oops");
                    }
                } else {
                    if (myStack.isEmpty() != test.isEmpty()) {
                        System.out.println("Oops");
                    }
                }
            }
        }

        System.out.println("test finish!");

    }
}
