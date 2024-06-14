package com.zh.algo.graph.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<Integer, Node> nodes;
    private Set<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

    public boolean containsNode(int no) {
        return nodes.containsKey(no);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    public Node getNode(int no) {
        return nodes.get(no);
    }

    public void addNode(int no, Node node) {
        nodes.put(no, node);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }
}
