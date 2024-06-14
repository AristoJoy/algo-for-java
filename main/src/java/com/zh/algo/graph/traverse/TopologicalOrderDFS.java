package com.zh.algo.graph.traverse;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * OJ链接：https://www.lintcode.com/problem/topological-sorting
 */
public class TopologicalOrderDFS {
    // 不要提交这个类
    static class DirectedGraphNode {
        public int label;
        public List<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }

    static class Record {
        private DirectedGraphNode node;
        private int metric;

        public Record(DirectedGraphNode node, int metric) {
            this.node = node;
            this.metric = metric;
        }

        public DirectedGraphNode getNode() {
            return node;
        }

        public void setNode(DirectedGraphNode node) {
            this.node = node;
        }

        public int getMetric() {
            return metric;
        }

        public void setMetric(int metric) {
            this.metric = metric;
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph,
        Comparator<Record> comparator, BiConsumer<DirectedGraphNode, Map<DirectedGraphNode, Record>> f) {
        Map<DirectedGraphNode, Record> recordMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f.accept(cur, recordMap);
        }
        List<Record> records = new ArrayList<>(recordMap.values());

        records.sort(comparator);
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record record : records) {
            ans.add(record.getNode());
        }
        return ans;
    }
}
