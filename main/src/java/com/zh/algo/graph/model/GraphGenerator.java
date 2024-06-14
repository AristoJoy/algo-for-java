package com.zh.algo.graph.model;

public class GraphGenerator {
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length < 3) {
            return graph;
        }
        for (int i = 0; i < matrix.length; i++) {
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];

            if (!graph.containsNode(from)) {
                graph.addNode(from, new Node(from));
            }
            if (!graph.containsNode(to)) {
                graph.addNode(to, new Node(to));
            }
            Node fromNode = graph.getNode(from);
            Node toNode = graph.getNode(to);
            Edge edge = new Edge(weight, fromNode, toNode);
            fromNode.addNext(toNode);
            fromNode.addEdge(edge);
            fromNode.addOut();
            toNode.addIn();
            graph.addEdge(edge);
        }

        return graph;
    }
}
