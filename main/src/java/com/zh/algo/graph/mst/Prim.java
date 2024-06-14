package com.zh.algo.graph.mst;

import com.zh.algo.graph.model.Edge;
import com.zh.algo.graph.model.Graph;
import com.zh.algo.graph.model.Node;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 由点及边，再由边及点，选当前未解锁的边
 */
public class Prim {

    public static Set<Edge> primMST(Graph graph) {
        Set<Edge> minMST = new HashSet<>();
        if (graph == null) {
            return minMST;
        }

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        Set<Node> nodes = new HashSet<>();
        for (Node node : graph.getNodes().values()) {
            if (nodes.contains(node)) {
                continue;
            }
            nodes.add(node);
            priorityQueue.addAll(node.getEdges());
            while (!priorityQueue.isEmpty()) {
                Edge edge = priorityQueue.poll();
                Node to = edge.getTo();
                if (!nodes.contains(to)) {
                    nodes.add(to);
                    minMST.add(edge);
                    priorityQueue.addAll(node.getEdges());
                }
            }
        }
        return minMST;
    }

    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int sum = 0;
        int size = graph.length;
        // 当前点到i的最小距离
        int[] distance = new int[size];
        boolean[] visited = new boolean[size];
        visited[0] = true;
        for (int i = 0; i < size; i++) {
            distance[i] = graph[0][i];
        }

        for (int i = 1; i < size; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                // 找到当前点的最小距离的点
                if (!visited[j] && distance[j] < minPath) {
                    minIndex = j;
                    minPath = distance[j];
                }

                if (minIndex == -1) {
                    return sum;
                }
                // 标记最小点被访问过，累加最小连通图路径
                visited[minIndex] = true;
                sum += minPath;
                // 更新下一个点为起点的距离
                for (int k = 0; k < size; k++) {
                    if (!visited[k] && distance[k] > graph[minIndex][k]) {
                        distance[k] = graph[minIndex][k];
                    }
                }
            }
        }

        return sum;
    }
}
