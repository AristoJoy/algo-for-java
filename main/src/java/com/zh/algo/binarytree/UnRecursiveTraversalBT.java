package com.zh.algo.binarytree;

import java.util.Stack;

/**
 * 体系学习班class10
 * 二叉树先序、中序、后序的非递归遍历
 */
public class UnRecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            head = stack.pop();
            System.out.print(head.value + " ");
            if (head.right != null) {
                stack.push(head.right);
            }
            if (head.left != null) {
                stack.push(head.left);
            }
        }
        System.out.println();
    }

    public static void in(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            // 一直向左
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                System.out.print(head.value + " ");
                // 开始遍历右子树
                head = head.right;
            }
        }
        System.out.println();
    }
    public static void pos1(Node head) {
        if (head == null) {
            return;
        }
        // 左右中   中右左
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(head);
        while (!stack1.isEmpty()) {
            head = stack1.pop();
            stack2.push(head);
            if (head.left != null) {
                stack1.push(head.left);
            }
            if (head.right != null) {
                stack1.push(head.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().value + " ");
        }
        System.out.println();
    }

    public static void pos2(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node cur = null;
        // 所有的遍历过程，都经过左中右，但是后序遍历是左右中，遍历时，先不弹出栈，这就需要确定何时访问中点
        // 分为两种情况：
        // 1. 当前节点是叶子节点
        // 2. 上一个访问的节点是当前节点的右节点（如果没有右节点，则是左节点）
        while (!stack.isEmpty()) {
            // 先不弹出栈
            cur = stack.peek();
            // 当前不是叶子节点，并且上次访问的不是左节点和右节点（注意这里入栈的顺序是左，右，因为我们约定的访问顺序是左，右，中，所以这里必须按顺序入栈）

            // 左孩子非空，并且当前节点的孩子都未访问过，才将左孩子入栈
            if (cur.left != null && head != cur.left && head != cur.right) {
                // 一直向左遍历，直至左孩子为空或未访问过
                stack.push(cur.left);
            } else if (cur.right != null && head != cur.right) {
                // 右孩子非空，当前节点的右孩子未访问过，才入栈
                stack.push(cur.right);
            } else {
                // 1. 当前节点是叶子节点
                // 2. 上一个访问的节点是当前节点的右节点（如果没有右节点，则是左节点）
                System.out.print(cur.value + " ");
                stack.pop();
                head = cur;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos1(head);
        System.out.println("========");
        pos2(head);
        System.out.println("========");
    }
}
