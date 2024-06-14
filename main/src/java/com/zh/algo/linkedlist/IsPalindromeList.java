package com.zh.algo.linkedlist;

import java.util.Stack;

/**
 * 体系学习班class9
 * 给定一个单链表的头节点head，请判断该链表是否为回文结构
 */
public class IsPalindromeList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 需要O(N)空间
     * @param head
     * @return
     */
    public static boolean isPalindrome1(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        cur = head;
        while (!stack.isEmpty()) {
            if (stack.pop().value != cur.value) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    /**
     * 需要O(N/2)空间
     * @param head
     * @return
     */
    public static boolean isPalindrome2(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node left = head.next;
        Node right = head;
        while (right.next != null && right.next.next != null) {
            left = left.next;
            right = right.next.next;
        }
        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (stack.pop().value != head.value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 需要O(1)空间
     * todo-zh 再练
     * @param head
     * @return
     */
    public static boolean isPalindrome3(Node head) {
        if (head == null || head.next == null) {
            return true;
        }
        // 中点
        Node node1 = head;
        Node node2 = head;
        while (node2.next != null && node2.next.next != null) {
            node1 = node1.next;
            node2 = node2.next.next;
        }
        // 中点下一个
        node2 = node1.next;
        // 先将中点和下半部分断开
        node1.next = null;

        // node1 -> node2 -> node3
        Node node3;
        while (node2 != null) {
            node3 = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = node3;
        }
        // 保存反转链表head（原链表最后一个节点)
        node3 = node1;
        // 左边第一个节点
        node2 = head;
        boolean ans = true;
        while (node1 != null && node2 != null) {
            if (node1.value != node2.value) {
                ans = false;
                break;
            }
            node1 = node1.next;
            node2 = node2.next;
        }
        // 中点
        node1 = node3.next;
        node3.next = null;
        while (node1 != null) {
            node2 = node1.next;
            node1.next = node3;
            node3 = node1;
            node1 = node2;
        }
        return ans;
    }
}
