package com.zh.algo.linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * 体系学习班class9
 * 一种特殊的单链表节点类描述如下
 * class Node {
 * int value;
 * Node next;
 * Node rand;
 * Node(int val) { value = val; }
 * }
 * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null
 * 给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个函数完成这个链表的复制
 * 返回复制的新链表的头节点，要求时间复杂度O(N)，额外空间复杂度O(1)
 */
public class CopyListWithRandom {
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList1(Node head) {
        Map<Node, Node> nodeMap = new HashMap<>();
        Node cur = head;
        while (cur != null) {
            nodeMap.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            Node node = nodeMap.get(cur);
            node.next = nodeMap.get(cur.next);
            node.random = nodeMap.get(cur.random);
            cur = cur.next;
        }
        return nodeMap.get(head);
    }

    public static Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        while (cur != null) {
            Node node = new Node(cur.val);
            node.next = cur.next;
            cur.next = node;
            cur = cur.next.next;
        }
        cur = head;
        while (cur != null) {
            cur.next.random = cur.random == null ? null : cur.random.next;
            cur = cur.next.next;
        }
        cur = head;
        Node head2 = cur.next;
        Node next;
        while (cur != null) {
            next = cur.next.next;
            cur.next.next = next == null ? null : next.next;
            cur.next = next;
            cur = next;
        }
        return head2;
    }


}
