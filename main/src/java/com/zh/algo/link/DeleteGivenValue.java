package com.zh.algo.link;

/**
 * 体系班class3
 * 在链表中删除指定值的所有节点
 */
public class DeleteGivenValue {
    static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static Node removeValue(Node head, int value) {
        while (head != null) {
            if (head.value != value) {
                break;
            }
            head = head.next;
        }
        // head == null
        // head.value != value
        Node prev = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == value) {
                prev.next = cur.next;
            } else {
                prev = cur;
            }
            cur = cur.next;
        }
        return head;
    }
}
