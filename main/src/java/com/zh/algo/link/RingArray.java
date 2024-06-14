package com.zh.algo.link;

/**
 * 体系班class3
 * 用环形数组实现队列
 */
public class RingArray {
    static class MyQueue<T> {
        Object[] elems;
        int pushIndex;
        int popIndex;
        int size;

        public MyQueue(int limit) {
            elems = new Object[limit];
            pushIndex = 0;
            popIndex = 0;
            size = 0;
        }

        public void push(T value) {
            if (size == elems.length) {
                throw new IndexOutOfBoundsException("queue full");
            }
            size++;
            elems[pushIndex] = value;
            pushIndex = nextIndex(pushIndex);
        }

        public T poll() {
            if (size == 0) {
                throw new IndexOutOfBoundsException("queue empty");
            }
            size--;
            T value = (T) elems[popIndex];
            popIndex = nextIndex(popIndex);
            return value;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private int nextIndex(int index) {
            return index == elems.length - 1 ? 0 : index + 1;
        }
    }
}
