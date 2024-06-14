package com.zh.algo.linkedlist;

import com.zh.algo.utils.ArrayUtils;

/**
 * 体系学习班class9
 * 给定一个单链表的头节点head，给定一个整数n，将链表按n划分成左边<n、中间==n、右边>n
 */
public class SmallerEqualBigger {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        int i = 0;
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        Node[] nodeArray = new Node[i];
        cur = head;
        for (i = 0; i < nodeArray.length; i++) {
            nodeArray[i] = cur;
            cur = cur.next;
         }
        arrayPartition(nodeArray, pivot);
        for (i = 1; i < nodeArray.length; i++) {
            nodeArray[i - 1].next = nodeArray[i];
        }
        nodeArray[i].next = null;
        return nodeArray[0];
    }

    private static void arrayPartition(Node[] nodeArray, int pivot) {
        int small = -1;
        int big = nodeArray.length;
        int index = 0;
        while (index < big) {
            if (nodeArray[index].value < pivot) {
                ArrayUtils.swap(nodeArray, ++small, index);
            } else if (nodeArray[index].value == pivot) {
                index++;
            } else {
                ArrayUtils.swap(nodeArray, --big, index);
            }
        }
    }

    public static Node listPartition2(Node head, int pivot) {
        Node sH = null;
        Node sT = null;
        Node eH = null;
        Node eT = null;
        Node bH = null;
        Node bT = null;
        Node cur = head;
        Node next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = null;
            if (cur.value < pivot) {
                if (sH == null) {
                    sH = cur;
                    sT = cur;
                } else {
                    sT.next = cur;
                    sT = cur;
                }
            } else if (cur.value == pivot) {
                if (eH == null) {
                    eH = cur;
                    eT = cur;
                } else {
                    eT.next = cur;
                    eT = cur;
                }
            } else {
                if (bH == null) {
                    bH = cur;
                    bT = cur;
                } else {
                    bT.next = cur;
                    bT = cur;
                }
            }
            cur = next;
        }
        if (sT != null) {
            sT.next = eH;
            eT = eT == null ? sT : eT;
        }
        if (eT != null) {
            eT.next = bH;
        }
        return sH != null ? sH : eH != null ? eH : bH;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }
}
