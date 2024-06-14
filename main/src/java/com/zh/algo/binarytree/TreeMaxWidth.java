package com.zh.algo.binarytree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 体系学习班class11
 * 求二叉树的最大宽度
 */
public class TreeMaxWidth {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Integer> levelMap = new HashMap<>();
        queue.add(head);
        levelMap.put(head, 1);
        int maxWidth = 0;
        Node cur = null;
        int curLevel = 1;
        int curLevelNodes = 0;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (curLevel == levelMap.get(cur)) {
                curLevelNodes++;
            } else {
                maxWidth = Math.max(maxWidth, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
            if (cur.left != null) {
                queue.add(cur.left);
                levelMap.put(cur.left, curLevel + 1);
            }
            if (cur.right != null) {
                queue.add(cur.right);
                levelMap.put(cur.right, curLevel + 1);
            }
        }

        return Math.max(maxWidth, curLevelNodes);
    }

    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node cur = null;
        Node curEnd = head;
        Node nextEnd = null;
        int maxWidth = 0;
        int curLevelNodes = 0;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            curLevelNodes++;
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            if (cur == curEnd) {
                maxWidth = Math.max(maxWidth, curLevelNodes);
                curLevelNodes = 0;
                curEnd = nextEnd;
            }
        }

        return maxWidth;
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
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println(maxWidthUseMap(head));
                System.out.println(maxWidthNoMap(head));
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }
}
