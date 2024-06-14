package com.zh.algo.graph.traverse;

import java.util.*;

/**
 * OJ链接：https://www.lintcode.com/problem/topological-sorting
 */
public class TopologicalOrderBFS {
    // 不要提交这个类
    static class DirectedGraphNode {
        public int label;
        public List<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }


    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        Map<DirectedGraphNode, Integer> inDegreeMap = new HashMap<>();
        for (DirectedGraphNode directedGraphNode : graph) {
            inDegreeMap.put(directedGraphNode, 0);
        }

        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) + 1);
            }
        }
        Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
        for (DirectedGraphNode node : graph) {
            if (inDegreeMap.get(node) == 0) {
                zeroQueue.add(node);
            }
        }

        ArrayList<DirectedGraphNode> ans = new ArrayList<>();

        while (!zeroQueue.isEmpty()) {
            DirectedGraphNode cur = zeroQueue.poll();
            ans.add(cur);
            for (DirectedGraphNode neighbor : cur.neighbors) {
                inDegreeMap.put(neighbor, inDegreeMap.get(neighbor) - 1);
                if (inDegreeMap.get(neighbor) == 0) {
                    zeroQueue.add(neighbor);
                }
            }
        }

        return ans;
    }
}
