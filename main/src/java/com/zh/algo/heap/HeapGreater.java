package com.zh.algo.heap;

import java.util.*;

/**
 * 体系班class7 加强小根堆
 * @param <T>
 */
public class HeapGreater<T> {
    private List<T> heap;
    private Map<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<? super T> comp) {
        this.comp = comp;
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    public void remove(T obj) {
        T replace = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if (replace != obj) {
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    public void resign(T obj) {
        int index = indexMap.get(obj);
        heapInsert(index);
        heapify(index);
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = (left + 1) < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(index, best);
            index = best;
            left = index * 2 + 1;
        }
    }



    private void swap(int i, int j) {
        T a = heap.get(i);
        T b = heap.get(j);
        heap.set(i, b);
        heap.set(j, a);
        indexMap.put(a, j);
        indexMap.put(b, i);
    }
}
