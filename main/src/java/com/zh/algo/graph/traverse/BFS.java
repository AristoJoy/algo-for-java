package com.zh.algo.graph.traverse;

import com.zh.algo.graph.model.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFS {
    public static void bfs(Node start) {
        if (start == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            start = queue.poll();
            System.out.println(start.getValue());
            for (Node node : start.getNext()) {
                if (!visited.contains(node)) {
                    queue.add(node);
                    visited.add(node);
                }
            }
        }
    }
}
