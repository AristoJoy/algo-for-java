package com.zh.algo.binarytree.tricks;

import java.util.ArrayList;
import java.util.List;

/**
 * 体系学习班class12
 * 判断一棵二叉树是不是搜索二叉树
 */
public class IsSearchBinaryTree {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 中序遍历，将其放到容器中，比较前后的大小
    public static boolean isSearchBinaryTree1(Node head) {
        if (head == null) {
            return true;
        }
        List<Node> traverse = new ArrayList<>();
        in(head, traverse);
        for (int i = 1; i < traverse.size(); i++) {
            if (traverse.get(i - 1).value > traverse.get(i).value) {
                return false;
            }
        }
        return true;
    }

    public static void in(Node head, List<Node> traverse) {
        if (head == null) {
            return;
        }
        in(head.left, traverse);
        traverse.add(head);
        in(head.right, traverse);
    }

    static class Info {
        boolean isSBT;
        int max;
        int min;

        public Info(boolean isSBT, int max, int min) {
            this.isSBT = isSBT;
            this.max = max;
            this.min = min;
        }
    }
    public static boolean isSearchBinaryTree2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isSBT;
    }

    public static Info process(Node head) {
        if (head == null) {
            return null;
        }
        boolean isSBT = true;
        int max = head.value;
        int min = head.value;

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }

        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        // 两棵子树不是平衡二叉树
        if (leftInfo != null && !leftInfo.isSBT) {
            isSBT = false;
        }
        if (rightInfo != null && !rightInfo.isSBT) {
            isSBT = false;
        }

        // 不符合中序遍历非递减的规律
        if (leftInfo != null && head.value < leftInfo.max) {
            isSBT = false;
        }
        if (rightInfo != null && head.value > rightInfo.min) {
            isSBT = false;
        }

        return new Info(isSBT, max, min);
    }

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
            if (isSearchBinaryTree1(head) != isSearchBinaryTree2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


}
