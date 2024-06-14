package com.zh.algo.binarytree.tricks;

import java.util.ArrayList;
import java.util.List;

/**
 * 体系学习班class13
 * 给定一棵二叉树的头节点head，返回这颗二叉树中最大的二叉搜索子树的头节点
 */
public class MaxSubBSTHead {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 获取搜索二叉树的大小
    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        List<Node> list = new ArrayList<>();
        in(head, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).value > list.get(i).value) {
                return 0;
            }
        }
        return list.size();
    }

    public static void in(Node head, List<Node> list) {
        if (head == null) {
            return;
        }
        in(head.left, list);
        list.add(head);
        in(head.right, list);
    }

    public static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return head;
        }
        Node left = maxSubBSTHead1(head.left);
        Node right = maxSubBSTHead1(head.right);
        return getBSTSize(left) >= getBSTSize(right) ? left : right;
    }

    // 是否是搜索二叉树，可以有maxBSTSize是否等于allSize判断
    static class Info {
        // 最大搜索二叉树大小
        Node maxBSTHead;
        // 最大搜索二叉树大小
        int maxBSTSize;
        int max;
        int min;

        public Info(Node maxBSTHead, int maxBSTSize, int max, int min) {
            this.maxBSTHead = maxBSTHead;
            this.maxBSTSize = maxBSTSize;
            this.max = max;
            this.min = min;
        }
    }

    public static Node maxSubBSTHead2(Node head) {
        if (head == null) {
            return null;
        }
        return process(head).maxBSTHead;
    }

    public static Info process(Node head) {
        if (head == null) {
            return null;
        }
        Info left = process(head.left);
        Info right = process(head.right);
        int max = head.value;
        int min = head.value;
        int maxBSTSize = 0;
        Node maxBSTHead = null;
        if (left != null) {
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
            maxBSTSize = left.maxBSTSize;
            maxBSTHead = left.maxBSTHead;
        }
        if (right != null) {
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
            if (right.maxBSTSize > maxBSTSize) {
                maxBSTSize = right.maxBSTSize;
                maxBSTHead = right.maxBSTHead;
            }
        }

        // 左右子树是否是搜索二叉树
        boolean leftBST = left == null || left.maxBSTHead == head.left;
        boolean rightBST = right == null || right.maxBSTHead == head.right;
        if (leftBST && rightBST) {
            // 当前树是否是搜索二叉树
            boolean leftMaxLess = left == null || left.max <= head.value;
            boolean rightMinLarge = right == null || head.value <= right.min;
            if (leftMaxLess && rightMinLarge) {
                maxBSTHead = head;
                maxBSTSize = (left == null ? 0 : left.maxBSTSize) + (right == null ? 0 : right.maxBSTSize) + 1;
            }
        }

        return new Info(maxBSTHead, maxBSTSize, max, min);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
