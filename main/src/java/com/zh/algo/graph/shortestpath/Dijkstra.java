package com.zh.algo.graph.shortestpath;


import com.zh.algo.graph.model.Edge;
import com.zh.algo.graph.model.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dijkstra {
    public static Map<Node, Integer> dijkstra1(Node from) {
        Map<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);

        Set<Node> visited = new HashSet<>();
        Node minPathNode = getMinDistanceFromUnselected(distanceMap, visited);
        while (minPathNode != null) {
            int distance = distanceMap.get(minPathNode);
            for (Edge edge : minPathNode.getEdges()) {
                Node to = edge.getTo();
                if (!visited.contains(to)) {
                    distanceMap.put(to, distance + edge.getWeight());
                } else {
                    distanceMap.put(to, Math.min(distanceMap.get(to), distance + edge.getWeight()));
                }
            }
            visited.add(minPathNode);
            minPathNode = getMinDistanceFromUnselected(distanceMap, visited);
        }

        return distanceMap;
    }

    private static Node getMinDistanceFromUnselected(Map<Node, Integer> distanceMap, Set<Node> visited) {
        Node minNode = null;
        int minPath = Integer.MAX_VALUE;
        for (Node node : distanceMap.keySet()) {
            if (visited.contains(node)) {
                continue;
            }
            if (minPath > distanceMap.get(node)) {
                minNode = node;
                minPath = distanceMap.get(node);
            }
        }
        return minNode;
    }

    static class NodeRecord {
        private Node node;
        private int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    static class NodeHeap {
        private Node[] heap;
        private Map<Node, Integer> heapIndexMap;
        private Map<Node, Integer> distanceMap;
        private int size;

        public NodeHeap(int size) {
            this.size = 0;
            heap = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
        }

        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                heapInsert(heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                heap[size] = node;
                distanceMap.put(node, distance);
                heapIndexMap.put(node, size);
                heapInsert(size++);
            }
        }

        public NodeRecord pop() {
            Node top = heap[0];
            int distance = distanceMap.get(top);
            // 堆顶和堆底交换
            swap(0, size - 1);
            // 清除堆底记录
            heapIndexMap.put(top, -1);
            distanceMap.remove(top);
            heap[size - 1] = null;
            // 从堆顶向下调整堆
            heapify(0, --size);
            return new NodeRecord(top, distance);
        }

        private void heapInsert(int index) {
            while (distanceMap.get(heap[index]) < distanceMap.get(heap[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = (left + 1) < size && distanceMap.get(heap[left + 1]) < distanceMap.get(heap[left]) ? left + 1 : left;
                smallest = distanceMap.get(heap[index]) < distanceMap.get(heap[smallest]) ? index : smallest;
                if (smallest == index) {
                    return;
                }
                swap(index, smallest);
                index = smallest;
                left = smallest * 2 + 1;
            }
        }


        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        public boolean inHeap(Node node) {
            return heapIndexMap.containsKey(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int i, int j) {
            heapIndexMap.put(heap[i], j);
            heapIndexMap.put(heap[j], i);
            Node temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }
    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static Map<Node, Integer> dijkstra2(Node start, int size) {
        Map<Node, Integer> distance = new HashMap<>();
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(start, 0);

        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            distance.put(record.node, record.distance);
            for (Edge edge : record.node.getEdges()) {
                nodeHeap.addOrUpdateOrIgnore(edge.getTo(), edge.getWeight());
            }
        }
        return distance;
    }
}

