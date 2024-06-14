package com.zh.algo.recursion;

import java.util.Stack;

public class Hanoi {
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to right");
            return;
        }
        leftToMid(n - 1);
        System.out.println("Move " + n + " from left to right");
        midToRight(n - 1);
    }

    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("Move " + n + " from mid to right");
        leftToRight(n - 1);
    }

    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        midToRight(n - 1);
        System.out.println("Move " + n + " from mid to left");
        rightToLeft(n - 1);
    }

    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n + " from right to mid");
        leftToMid(n - 1);
    }

    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("Move " + n + " from left to mid");
        rightToMid(n - 1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        rightToMid(n - 1);
        System.out.println("Move " + n + " from mid to left");
        midToLeft(n - 1);
    }


    public static void hanoi2(int n) {
        process(n, "left", "right", "mid");
    }

    public static void process(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
            return;
        }
        process(n - 1, from, other, to);
        System.out.println("Move " + n + " from " + from + " to " + to);
        process(n - 1, other, to, from);
    }

    static class Frame {
        private boolean finish;
        private int base;
        private String from;
        private String to;
        private String other;

        public Frame(boolean finish, int base, String from, String to, String other) {
            this.finish = finish;
            this.base = base;
            this.from = from;
            this.to = to;
            this.other = other;
        }

        @Override
        public String toString() {
            return "Frame{" +
                    "finish=" + finish +
                    ", base=" + base +
                    ", from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", other='" + other + '\'' +
                    '}';
        }
    }

    public static void hanoi3(int n) {
        if (n < 1) {
            return;
        }
        Stack<Frame> stack = new Stack<>();
        stack.push(new Frame(false, n, "left", "to", "mid"));
        while (!stack.isEmpty()) {
            Frame cur = stack.pop();
            if (cur.base == 1) {
                System.out.println("Move 1 from " + cur.from + " to " + cur.to);
                if (!stack.isEmpty()) {
                    stack.peek().finish = true;
                }
            } else {
                if (!cur.finish) {
                    stack.push(cur);
                    stack.push(new Frame(false, cur.base - 1, cur.from, cur.other, cur.to));
                } else {
                    System.out.println("Move " + cur.base + " from " + cur.from + " to " + cur.to);
                    stack.push(new Frame(false, cur.base - 1, cur.other, cur.to, cur.from));
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;
        hanoi1(n);
        System.out.println("============");
        hanoi2(n);
		System.out.println("============");
		hanoi3(n);
    }

}
