package com.zh.algo.unionfind;

import java.util.*;

/**
 * 体系学习班class14
 * 并查集实现
 */
public class UnionFind<V> {
    static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    private Map<V, Node<V>> nodes;
    private Map<Node<V>, Node<V>> parents;
    private Map<Node<V>, Integer> sizeMap;

    public UnionFind() {
        nodes = new HashMap<>();
        parents = new HashMap<>();
        sizeMap = new HashMap<>();
    }

    public UnionFind(List<V> values) {
        nodes = new HashMap<>();
        parents = new HashMap<>();
        sizeMap = new HashMap<>();
        for (V value : values) {
            Node<V> node = new Node<>(value);
            nodes.put(value, node);
            parents.put(node, node);
            sizeMap.put(node, 1);
        }
    }

    public Node<V> findFather(Node<V> cur) {
        // stack用来做路径压缩
        Stack<Node<V>> stack = new Stack<>();
        while (cur != parents.get(cur)) {
            stack.push(cur);
            cur = parents.get(cur);
        }

        while (!stack.isEmpty()) {
            parents.put(stack.pop(), cur);
        }
        return cur;
    }

    public boolean isSameSet(V a, V b) {
        return findFather(nodes.get(a)) == findFather(nodes.get(b));
    }

    public void union(V a, V b) {
        if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
            return;
        }
        Node<V> aFather = findFather(nodes.get(a));
        Node<V> bFather = findFather(nodes.get(b));
        if (aFather != bFather) {
            int aSize = sizeMap.get(aFather);
            int bSize = sizeMap.get(bFather);
            Node<V> big = aSize >= bSize ? aFather : bFather;
            Node<V> small = big == aFather ? bFather : aFather;
            parents.put(small, big);
            sizeMap.put(big, aSize + bSize);
            sizeMap.remove(small);
        }
    }

}
