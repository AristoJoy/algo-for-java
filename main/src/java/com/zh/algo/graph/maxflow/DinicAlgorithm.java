package com.zh.algo.graph.maxflow;

import java.util.*;

/**
 * 体系学习班class46
 *
 * 网络最大流算法
 *
 * todo-zh总结
 *
 *
 */
public class DinicAlgorithm {

    static class Edge {
        private int from;
        private int to;
        private int available;

        public Edge(int from, int to, int available) {
            this.from = from;
            this.to = to;
            this.available = available;
        }
    }

    static class Dinic {
        private int N;

        /**
         * 邻接矩阵，第一维存放节点i的连接边的顶点
         */
        private List<List<Integer>> next;

        /**
         * 所有边 0 1存放两条互补的边
         */
        private List<Edge> edges;

        /**
         * 顶点i的高度（用于优化1）
         */
        private int[] depth;

        /**
         * 用于统计当前节点i所未用过的边的顶点（优化2）
         */
        private int[] cur;

        public Dinic(int n) {
            N = n + 1;
            next = new ArrayList<>();
            for (int i = 0; i <= N; i++) {
                next.add(new ArrayList<>());
            }
            edges = new ArrayList<>();
            depth = new int[N];
            cur = new int[N];
        }

        public void addEdge(int u, int v, int r) {
            int m = edges.size();
            edges.add(new Edge(u, v, r));
            next.get(u).add(m);
            edges.add(new Edge(v, u, 0));
            next.get(v).add(m + 1);
        }

        public int maxFlow(int src, int dst) {
            int flow = 0;
            while (bfs(src, dst)) {
                Arrays.fill(cur, 0);
                flow += dfs(src, dst, Integer.MAX_VALUE);
                Arrays.fill(depth, 0);
            }
            return flow;
        }

        private boolean bfs(int src, int dst) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(src);
            boolean[] visited = new boolean[N];
            visited[src] = true;
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int i = 0; i < next.get(cur).size(); i++) {
                    Edge edge = edges.get(next.get(cur).get(i));
                    int to = edge.to;
                    if (!visited[to] && edge.available > 0) {
                        visited[to] = true;
                        depth[to] = depth[cur] + 1;
                        if (to == dst) {
                            break;
                        }
                        queue.add(to);
                    }
                }
            }
            return visited[dst];
        }

        // 当前来到了s点，s可变
        // 最终目标是t，t固定参数
        // r，收到的任务
        // 收集到的流，作为结果返回，ans <= r
        private int dfs(int src, int dst, int task) {
            if (src == dst || task == 0) {
                return task;
            }
            int flow = 0;
            int f;
            // 主要这里cur自增，用过的边就去掉了
            for (; cur[src] < next.get(src).size(); cur[src]++) {
                int ei = next.get(src).get(cur[src]);
                Edge e = edges.get(ei);
                Edge o = edges.get(ei ^ 1);
                // 当下一个深度的节点调用dfs，任务是瓶颈
                if (depth[e.to] == depth[src] + 1 && (f = dfs(e.to, dst, Math.min(task, e.available))) != 0) {
                    e.available -= f;
                    o.available += f;
                    // 网络流增加
                    flow += f;
                    // 任务减少
                    task -= f;
                    if (e.to == dst) {
                        break;
                    }
                }
            }
            return flow;
        }
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cases = cin.nextInt();
        for (int i = 1; i <= cases; i++) {
            int n = cin.nextInt();
            int s = cin.nextInt();
            int t = cin.nextInt();
            int m = cin.nextInt();
            Dinic dinic = new Dinic(n);
            for (int j = 0; j < m; j++) {
                int from = cin.nextInt();
                int to = cin.nextInt();
                int weight = cin.nextInt();
                dinic.addEdge(from, to, weight);
                dinic.addEdge(to, from, weight);
            }
            int ans = dinic.maxFlow(s, t);
            System.out.println("Case " + i + ": " + ans);
        }
        cin.close();
    }
}
