package com.zh.algo.link;

/**
 * 体系班class3
 * 双端队列
 * @param <T>
 */
public class DoubleEndsQueue<T> {

    private Node<T> head;
    private Node<T> tail;

    public void addFromHead(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    public void addFromBottom(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    public T popFromHead() {
        if (head == null) {
            return null;
        }
        Node<T> cur = head;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            cur.next = null;
            head.prev = null;
        }
        return cur.value;
    }

    public T popFromBottom() {
        if (head == null) {
            return null;
        }
        Node<T> cur = tail;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            cur.prev = null;
            tail.next = null;
        }
        return cur.value;
    }

    public boolean isEmpty() {
        return head == null;
    }


    static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}
