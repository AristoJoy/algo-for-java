package com.zh.algo.heap;

import com.zh.algo.utils.ArrayUtils;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 体系班class6
 */
public class Heap {
    static class MaxHeap {
        private int[] heap;
        private int heapSize;

        public MaxHeap(int limit) {
            heap = new int[limit];
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == heap.length;
        }

        public void push(int value) {
            if (heapSize == heap.length) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heapSize++);
        }

        public int pop() {
            if (heapSize == 0) {
                throw new RuntimeException("heap is empty");
            }
            int value = heap[0];
            ArrayUtils.swap(heap, 0, --heapSize);
            heapify(0, heapSize);
            return value;
        }

        private void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int largest = (left + 1) < heapSize && heap[left + 1] > heap[left] ? left + 1 : left;
                largest = heap[largest] > heap[index] ? largest : index;
                if (largest == index) {
                    break;
                }
                ArrayUtils.swap(heap, index, largest);
                index = largest;
                left = index * 2 + 1;
            }
        }

        private void heapInsert(int index) {
            while (heap[index] > heap[(index - 1) / 2]) {
                ArrayUtils.swap(heap, (index - 1) / 2, index);
                index = (index - 1) / 2;
            }
        }

    }

    static class RightMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }

    static class MyComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }

    }

    public static void main(String[] args) {

        // 小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
        heap.add(5);
        heap.add(5);
        heap.add(5);
        heap.add(3);
        // 5 , 3
        System.out.println(heap.peek());
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        System.out.println(heap.peek());
        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }

        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MaxHeap my = new MaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
