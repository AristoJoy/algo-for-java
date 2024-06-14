package com.zh.algo.binarytree.tricks;

import java.util.*;

public class LowestAncestor {
    static class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        public Node(T value) {
            this.value = value;
        }
    }

    public static <T> Node<T> lowestAncestor1(Node<T> head, Node<T> a, Node<T> b) {
        if (head == null) {
            return null;
        }
        Map<Node<T>, Node<T>> parentMap = new HashMap<>();
        fillParentMap(parentMap, head);

        Set<Node<T>> parents = new HashSet<>();
        Node<T> node = a;
        while ((node = parentMap.get(node)) != null) {
            parents.add(node);
        }

        node = b;
        while (!parents.contains(node)) {
            node = parentMap.get(node);
        }
        return node;
    }

    private static <T> void fillParentMap(Map<Node<T>, Node<T>> parentMap, Node<T> head) {
        Stack<Node<T>> stack = new Stack<>();
        stack.push(head);
        Node<T> node;
        parentMap.put(head, null);
        while (!stack.isEmpty()) {
            node = stack.pop();
            if (node.right != null) {
                parentMap.put(node.right, node);
                stack.push(node.right);
            }
            if (node.left != null) {
                parentMap.put(node.left, node);
                stack.push(node.left);
            }
        }
    }

    static class Info {
        private boolean findA;
        private boolean findB;
        private Node convergence;

        public Info(boolean findA, boolean findB, Node convergence) {
            this.findA = findA;
            this.findB = findB;
            this.convergence = convergence;
        }
    }

    public static <T> Node lowestAncestor2(Node<T> head, Node<T> a, Node<T> b) {
        return process(head, a, b).convergence;
    }

    public static <T> Info process(Node<T> x, Node<T> a, Node<T> b) {
        if (x == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);
        boolean findA = (x == a) || leftInfo.findA || rightInfo.findA;
        boolean findB = (x == b) || leftInfo.findB || rightInfo.findB;
        Node convergence = null;

        if (leftInfo.convergence != null) {
            convergence = leftInfo.convergence;
        } else if (rightInfo.convergence != null) {
            convergence = rightInfo.convergence;
        } else if (findA && findB) {
            convergence = x;
        }
        return new Info(findA, findB, convergence);
    }

    // todo-zh 找网上最简化的写法
}
