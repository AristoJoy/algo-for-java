package com.zh.algo.graph.traverse;

import com.zh.algo.graph.model.Graph;
import com.zh.algo.graph.model.Node;

import java.util.*;

public class TopologySort {
    // directed graph and no loop
    public static List<Node> sortedTopology(Graph graph) {
        Map<Node, Integer> inDegree = new HashMap<>();
        Queue<Node> zeroQueue = new LinkedList<>();
        for (Node node : graph.getNodes().values()) {
            inDegree.put(node, node.getIn());
            if (node.getIn() == 0) {
                zeroQueue.add(node);
            }
        }
        List<Node> ans = new ArrayList<>();

        while (!zeroQueue.isEmpty()) {
            Node cur = zeroQueue.poll();
            ans.add(cur);
            for (Node next : cur.getNext()) {
                inDegree.put(next, inDegree.get(next) - 1);
                if (inDegree.get(next) == 0) {
                    zeroQueue.add(next);
                }
            }
        }

        return ans;
    }
}
