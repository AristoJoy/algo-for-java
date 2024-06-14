package com.zh.algo.unionfind;

import java.util.*;

/**
 *
 */
public class NumberOfIslands {

    static class Dot {
    }

    static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    static class UnionFind1<V> {
        private Map<V, Node<V>> nodes;
        private Map<Node<V>, Node<V>> parents;
        private Map<Node<V>, Integer> sizeMap;

        public UnionFind1(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V value : values) {
                Node<V> node = new Node<>(value);
                nodes.put(value, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findFather(Node<V> cur) {
            // stack用来做路径压缩
            Stack<Node<V>> stack = new Stack<>();
            while (cur != parents.get(cur)) {
                stack.push(cur);
                cur = parents.get(cur);
            }

            while (!stack.isEmpty()) {
                parents.put(stack.pop(), cur);
            }
            return cur;
        }

        public boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b)) {
                return;
            }
            Node<V> aFather = findFather(nodes.get(a));
            Node<V> bFather = findFather(nodes.get(b));
            if (aFather != bFather) {
                int aSize = sizeMap.get(aFather);
                int bSize = sizeMap.get(bFather);
                Node<V> big = aSize >= bSize ? aFather : bFather;
                Node<V> small = big == aFather ? bFather : aFather;
                parents.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }

    }

    static class UnionFind2 {
        private int[] parents;
        private int[] size;
        private int[] help;

        private int col;
        private int sets;

        public UnionFind2(char[][] board)  {
            col = board[0].length;
            int row = board.length;
            int m = row * col;
            parents = new int[m];
            size = new int[m];
            help = new int[m];
            sets = 0;

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '1') {
                        int index = index(i, j);
                        parents[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        private int index(int r, int c) {
            return r * col + c;
        }

        public int find(int index) {
            int k = 0;
            // help用来做路径压缩
            while (index != parents[index]) {
                help[k++] = index;
                index = parents[index];
            }

            while (k > 0) {
                parents[help[--k]] = index;
            }
            return index;
        }

        public void union(int r1, int c1, int r2, int c2) {
            int i = index(r1, c1);
            int j = index(r2, c2);
            int aFather = find(i);
            int bFather = find(j);
            if (aFather != bFather) {
                int aSize = size[aFather];
                int bSize = size[bFather];
                int big = aSize >= bSize ? aFather : bFather;
                int small = big == aFather ? bFather : aFather;
                parents[small] = big;
                size[big] = aSize + bSize;
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }

    public static int numIslands1(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '1') {
                    Dot dot = new Dot();
                    dots[i][j] = dot;
                    dotList.add(dot);
                }
            }
        }
        UnionFind1<Dot> unionFind = new UnionFind1<>(dotList);
        for (int i = 1; i < col; i++) {
            if (board[0][i - 1] == '1' && board[0][i] == '1') {
                unionFind.union(dots[0][i - 1], dots[0][i]);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                unionFind.union(dots[i - 1][0], dots[i][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i - 1][j] == '1') {
                        unionFind.union(dots[i][j], dots[i - 1][j]);
                    }
                    if (board[i][j - 1] == '1') {
                        unionFind.union(dots[i][j], dots[i][j - 1]);
                    }
                }
            }
        }
        return unionFind.sets();
    }

    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        UnionFind2 unionFind = new UnionFind2(board);
        for (int i = 1; i < col; i++) {
            if (board[0][i - 1] == '1' && board[0][i] == '1') {
                unionFind.union(0, i - 1, 0, i);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                unionFind.union(i - 1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i - 1][j] == '1') {
                        unionFind.union(i, j, i - 1, j);
                    }
                    if (board[i][j - 1] == '1') {
                        unionFind.union(i, j, i, j - 1);
                    }
                }
            }
        }
        return unionFind.sets();
    }

    public static int numIslands3(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        int island = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '1') {
                    island++;
                    infect(board, i, j);
                }
            }
        }
        return island;
    }

    private static void infect(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != '1') {
            return;
        }
        board[i][j] = '2';
        infect(board, i - 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);
        infect(board, i + 1, j);
    }

    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + numIslands1(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

    }


}
