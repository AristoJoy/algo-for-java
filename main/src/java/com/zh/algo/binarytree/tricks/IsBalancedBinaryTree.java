package com.zh.algo.binarytree.tricks;

/**
 * 体系学习班class12
 * 判断二叉树是不是平衡二叉树
 */
public class IsBalancedBinaryTree {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isBalanced1(Node head) {
        if (head == null) {
            return true;
        }
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int left = process1(head.left, ans);
        int right = process1(head.right, ans);
        // 只有false才能修改，否则子树是false的，但是整棵树的根是高度相差1之内
        if (Math.abs(left - right) > 1) {
            ans[0] = false;
        }
        return Math.max(left, right) + 1;
    }

    static class Info {
        boolean isBalanced;
        int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static boolean isBalanced2(Node head) {
        if (head == null) {
            return true;
        }
        return process2(head).isBalanced;
    }

    public static Info process2(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process2(head.left);
        Info rightInfo = process2(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = leftInfo.isBalanced && rightInfo.isBalanced && Math.abs(leftInfo.height - rightInfo.height) < 2;
        return new Info(isBalanced, height);
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
