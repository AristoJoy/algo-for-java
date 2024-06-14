package com.zh.algo.graph.traverse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class TopologicalOrderDFS1 extends TopologicalOrderDFS {
    // 当前来到cur点，请返回cur点所到之处，最大深度！
    // 返回（cur，深度）
    // 缓存！！！！！order
    //  key : 某一个点的深度，之前算过了！
    //  value : 深度是多少
    public static Record f1(DirectedGraphNode cur, Map<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        int follower = 0;
        for (DirectedGraphNode neighbor : cur.neighbors) {
            follower += Math.max(follower, f1(neighbor, order).getMetric());
        }
        Record record = new Record(cur, follower + 1);
        order.put(cur, record);
        return record;
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        return topSort(graph, Comparator.comparingInt(Record::getMetric), TopologicalOrderDFS1::f1);
    }
}
