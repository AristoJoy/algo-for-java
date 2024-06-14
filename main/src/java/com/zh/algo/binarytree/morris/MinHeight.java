package com.zh.algo.binarytree.morris;

/**
 * 体系学习班class30
 *
 * 二叉树之前的遍历方式有空间浪费的问题
 *
 * Morris遍历
 *
 * 给定一棵二叉树的头节点head，求以head为头的树中，最小深度是多少？
 */
public class MinHeight {
    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int x) {
            val = x;
        }
    }

    public static int minHeight1(Node head) {
        if (head == null) {
            return 0;
        }
        return p(head);
    }

    // 返回x为头的树，最小深度是多少
    public static int p(Node x) {
        if (x.left == null && x.right == null) {
            return 1;
        }
        int leftH = Integer.MAX_VALUE;
        if (x.left != null) {
            leftH = p(x.left);
        }
        int rightH = Integer.MAX_VALUE;
        if (x.right != null) {
            rightH = p(x.right);
        }
        return Math.min(leftH, rightH) + 1;
    }

    // morris遍历
    // 两个问题：
    // 1.如果正确更新level（x的左树最右节点不是pre，正常；否则，减去左树右边的高）
    // 2.如何判断叶节点（第二次来到节点，恢复后，判断叶节点）
    public static int minHeight2(Node head) {
        if (head == null) {
            return 0;
        }
        Node cur = head;
        Node mostRight = null;
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                int rightBoardSize = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                    rightBoardSize++;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    // 第一次来到cur，往左遍历
                    cur = cur.left;
                    curLevel++;
                    continue;
                } else {
                    // 左子树为为，说明为叶节点
                    if (mostRight.left == null) {
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    // 第二次来到cur，恢复链接并正确计算当前层数
                    curLevel -= rightBoardSize;
                    mostRight.right = null;
                }
            } else {
                // 只有一次到达
                curLevel++;
            }
            cur = cur.right;
        }
        // 整棵树的右边界没有遍历
        int finalRightH = 1;
        cur = head;
        while (cur.right != null) {
            finalRightH++;
            cur = cur.right;
        }
        if (cur.left == null) {
            minHeight = Math.min(minHeight, finalRightH);
        }
        return minHeight;
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
        int treeLevel = 7;
        int nodeMaxValue = 5;
        int testTimes = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(treeLevel, nodeMaxValue);
            int ans1 = minHeight1(head);
            int ans2 = minHeight2(head);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }

}
