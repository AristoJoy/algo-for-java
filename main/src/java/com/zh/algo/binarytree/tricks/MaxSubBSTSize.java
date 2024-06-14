package com.zh.algo.binarytree.tricks;

import java.util.ArrayList;
import java.util.List;

/**
 * 体系学习班class12
 * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
 */
public class MaxSubBSTSize {
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

    // 先获取搜索二叉树大小，如果当前树不是搜索二叉树，则递归获取左右子树中最大的
    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }

    public static void in(Node head, List<Node> list) {
        if (head == null) {
            return;
        }
        in(head.left, list);
        list.add(head);
        in(head.right, list);
    }

    // 是否是搜索二叉树，可以有maxBSTSize是否等于allSize判断
    static class Info {
        // 最大搜索二叉树大小
        int maxBSTSize;
        // 二叉树的节点数量
        int allSize;
        int max;
        int min;

        public Info(int maxBSTSize, int allSize, int max, int min) {
            this.maxBSTSize = maxBSTSize;
            this.allSize = allSize;
            this.max = max;
            this.min = min;
        }
    }

    public static int maxSubBSTSize2(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSize;
    }

    public static Info process(Node head) {
        if (head == null) {
            return null;
        }
        Info left = process(head.left);
        Info right = process(head.right);
        int max = head.value;
        int min = head.value;
        int allSize = 1;
        int maxBSTSize = 0;
        if (left != null) {
            max = Math.max(max, left.max);
            min = Math.min(min, left.min);
            allSize += left.allSize;
            maxBSTSize = Math.max(maxBSTSize, left.maxBSTSize);
        }
        if (right != null) {
            max = Math.max(max, right.max);
            min = Math.min(min, right.min);
            allSize += right.allSize;
            maxBSTSize = Math.max(maxBSTSize, right.maxBSTSize);
        }

        // 左右子树是否是搜索二叉树
        boolean leftBST = left == null || left.maxBSTSize == left.allSize;
        boolean rightBST = right == null || right.maxBSTSize == right.allSize;
        if (leftBST && rightBST) {
            // 当前树是否是搜索二叉树
            boolean leftMaxLess = left == null || left.max <= head.value;
            boolean rightMinLarge = right == null || head.value <= right.min;
            if (leftMaxLess && rightMinLarge) {
                maxBSTSize = Math.max(maxBSTSize, allSize);
            }
        }

        return new Info(maxBSTSize, allSize, max, min);
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
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
