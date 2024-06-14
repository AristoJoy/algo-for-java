package com.zh.algo.graph.traverse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class TopologicalOrderDFS2 extends TopologicalOrderDFS {
    // 当前来到cur点，请返回cur点所到之处，所有的点次！
    // 返回（cur，点次）
    // 缓存！！！！！order
    //  key : 某一个点的点次，之前算过了！
    //  value : 点次是多少
    public static Record f2(DirectedGraphNode cur, Map<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        int follower = 0;
        for (DirectedGraphNode neighbor : cur.neighbors) {
            follower += f2(neighbor, order).getMetric();
        }
        Record record = new Record(cur, follower + 1);
        order.put(cur, record);
        return record;
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        return topSort(graph, Comparator.comparingInt(Record::getMetric), TopologicalOrderDFS2::f2);
    }

}
