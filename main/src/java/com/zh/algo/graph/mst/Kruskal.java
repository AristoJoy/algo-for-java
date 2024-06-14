package com.zh.algo.graph.mst;


import com.zh.algo.graph.model.Edge;
import com.zh.algo.graph.model.Graph;
import com.zh.algo.graph.model.Node;

import java.util.*;

/**
 * 最小生成树算法，选择最小的n-1条边
 */
public class Kruskal {
    static class UnionFind {
        private Map<Node, Node> fatherMap;
        private Map<Node, Integer> sizeMap;

        public UnionFind() {
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node findFather(Node x) {
            Stack<Node> stack = new Stack<>();
            while (x != fatherMap.get(x)) {
                stack.push(x);
                x = fatherMap.get(x);
            }
            Node cur;
            while (!stack.isEmpty()) {
                cur = stack.pop();
                fatherMap.put(cur, x);
            }
            return x;
        }

        public boolean isSameSet(Node a, Node b) {
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node f1 = findFather(a);
            Node f2 = findFather(b);
            if (f1 != f2) {
                int s1 = sizeMap.get(f1);
                int s2 = sizeMap.get(f2);
                Node big = s1 >= s2 ? f1 : f2;
                Node small = big == f1 ? f2 : f1;
                fatherMap.put(small, big);
                sizeMap.put(big, s1 + s2);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    public static Set<Edge> kruskalMST(Graph graph) {
        Set<Edge> spanTreeEdges = new HashSet<>();
        if (graph == null) {
            return spanTreeEdges;
        }

        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.getNodes().values());
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        for (Edge edge : graph.getEdges()) {
            priorityQueue.add(edge);
        }

        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            if (!unionFind.isSameSet(edge.getFrom(), edge.getTo())) {
                spanTreeEdges.add(edge);
                unionFind.union(edge.getFrom(), edge.getTo());
            }
        }
        return spanTreeEdges;
    }
}
