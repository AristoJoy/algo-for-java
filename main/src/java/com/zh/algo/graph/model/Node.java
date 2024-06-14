package com.zh.algo.graph.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int value;
    private int in;
    private int out;
    private List<Node> next;
    private List<Edge> edges;

    public Node(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        this.next = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public List<Node> getNext() {
        return next;
    }

    public void setNext(List<Node> next) {
        this.next = next;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void addIn() {
        this.in++;
    }

    public void subIn() {
        this.in--;
    }

    public void addOut() {
        this.out++;
    }

    public void subOut() {
        this.out--;
    }

    public void addNext(Node node) {
        this.next.add(node);
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }
}
