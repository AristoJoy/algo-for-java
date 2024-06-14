package com.zh.algo.binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * 体系学习班class11
 * N叉树如何通过二叉树来序列化、并完成反序列化
 * Leetcode题目：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree/
 */
public class EncodeNaryTreeToBinaryTree {
    static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    // 提交时不要提交这个类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Codec {
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }
            TreeNode head = new TreeNode(root.val);
            head.left = en(root.children);
            return head;
        }

        private TreeNode en(List<Node> children) {
            if (children == null || children.isEmpty()) {
                return null;
            }
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child : children) {
                TreeNode node = new TreeNode(child.val);
                if (head == null) {
                    head = node;
                } else {
                    cur.right = node;
                }
                cur = node;
                cur.left = en(child.children);
            }
            return head;
        }

        public Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }
            return new Node(root.val, de(root.left));
        }

        private List<Node> de(TreeNode root) {
            if (root == null) {
                return null;
            }
            List<Node> nodes = new ArrayList<>();
            while (root != null) {
                Node cur = new Node(root.val, de(root.left));
                nodes.add(cur);
                root = root.right;
            }
            return nodes;
        }
    }
}
