package com.zh.algo.graph.traverse;

import com.zh.algo.graph.model.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFS {
    public static void dfs(Node start) {
        if (start == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        stack.push(start);
        visited.add(start);
        System.out.println(start.getValue());
        while (!stack.isEmpty()) {
            start = stack.pop();
            for (Node node : start.getNext()) {
                if (!visited.contains(node)) {
                    stack.push(start);
                    stack.push(node);
                    visited.add(node);
                    System.out.println(node.getValue());
                    break;
                }
            }
        }
    }
}
