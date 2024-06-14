package com.zh.algo.binarytree.tricks;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 体系学习班class12
 * 判断二叉树是不是完全二叉树
 */
public class IsCompleteBinaryTree {
    static class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        public Node(T value) {
            this.value = value;
        }
    }

    public static <T> boolean isComplete1(Node<T> head) {
        if (head == null) {
            return true;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        // 是否遇到过左右孩子不全的节点
        boolean isLeaf = false;
        queue.add(head);
        Node<T> cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            // 1. 遇到过不全的节点，并且当前节点不是叶子节点
            // 2. 当前节点的左孩子为空，右孩子不为空
            if ((isLeaf && (cur.left != null || cur.right != null))
                    || (cur.left == null && cur.right != null)) {
                return false;
            }
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
            if (cur.left == null || cur.right == null) {
                isLeaf = true;
            }
        }
        return true;
    }

    static class Info {
        private boolean isFull;
        private boolean isComplete;
        private int height;

        public Info(boolean isFull, boolean isComplete, int height) {
            this.isFull = isFull;
            this.isComplete = isComplete;
            this.height = height;
        }
    }


    public static <T> boolean isComplete2(Node<T> head) {
        return process(head).isComplete;
    }

    public static <T> Info process(Node<T> head) {
        if (head == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 第一种情况 左满 右满 左右高度一样
        boolean isComplete = isFull;
        if (leftInfo.isComplete && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isComplete = true;
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isComplete = true;
        } else if (leftInfo.isFull && rightInfo.isComplete && leftInfo.height == rightInfo.height) {
            isComplete = true;
        }

        return new Info(isFull, isComplete, height);
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isComplete1(head) != isComplete2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
