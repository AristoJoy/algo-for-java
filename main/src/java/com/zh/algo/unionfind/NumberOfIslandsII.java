package com.zh.algo.unionfind;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberOfIslandsII {

    static class UnionFind1 {
        private Map<String, String> parents;
        private Map<String, Integer> sizeMap;

        private List<String> help;

        private int sets;

        public UnionFind1() {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }

        public String findFather(String cur) {
            // stack用来做路径压缩

            while (cur != parents.get(cur)) {
                help.add(cur);
                cur = parents.get(cur);
            }

            for (String s : help) {
                parents.put(s, cur);
            }
            help.clear();
            return cur;
        }

        public void union(String a, String b) {
            if (!parents.containsKey(a) || !parents.containsKey(b)) {
                return;
            }
            String aFather = findFather(a);
            String bFather = findFather(b);
            if (!aFather.equals(bFather)) {
                int aSize = sizeMap.get(aFather);
                int bSize = sizeMap.get(bFather);
                String big = aSize >= bSize ? aFather : bFather;
                String small = big == aFather ? bFather : aFather;
                parents.put(small, big);
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
                sets--;
            }
        }

        public int connect(int r, int c) {
            String index = index(r, c);
            if (!parents.containsKey(index)) {
                parents.put(index, index);
                sizeMap.put(index, 1);
                sets++;
                union(index, index(r - 1, c));
                union(index, index(r, c - 1));
                union(index, index(r, c + 1));
                union(index, index(r + 1, c));
            }
            return sets;
        }

        private String index(int row, int col) {
            return String.valueOf(row) + "_" + String.valueOf(col);
        }

        public int sets() {
            return sizeMap.size();
        }

    }

    static class UnionFind2 {
        private int[] parents;
        private int[] size;
        private int[] help;

        private int row;
        private int col;
        private int sets;

        public UnionFind2(int m, int n)  {
            row = m;
            col = n;
            sets = 0;
            int len = m * n;
            parents = new int[len];
            size = new int[len];
            help = new int[len];
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
            if (r1 < 0 || r1 >= row || c1 < 0 || c1 >= col || r2 < 0 || r2 >= row || c2 < 0 || c2 >= col) {
                return;
            }
            int i = index(r1, c1);
            int j = index(r2, c2);
            if (size[i] == 0 || size[j] == 0) {
                return;
            }
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

        public int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) {
                parents[index] = index;
                size[index] = 1;
                sets++;
                union(r, c, r - 1, c);
                union(r, c, r, c - 1);
                union(r, c, r, c + 1);
                union(r, c, r + 1, c);
            }
            return sets;
        }

        public int sets() {
            return sets;
        }
    }

    public List<Integer> numIslands21(int m, int n, int[][] positions) {
        UnionFind1 unionFind1 = new UnionFind1();
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            ans.add(unionFind1.connect(positions[i][0], positions[i][1]));
        }
        return ans;
    }

    public List<Integer> numIslands22(int m, int n, int[][] positions) {
        UnionFind2 unionFind2 = new UnionFind2(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            ans.add(unionFind2.connect(positions[i][0], positions[i][1]));
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] test = new int[4][2];
        test[0] = new int[]{0, 0};
        test[1] = new int[]{0, 1};
        test[2] = new int[]{1, 2};
        test[3] = new int[]{2, 1};
        int m = 3;
        NumberOfIslandsII numberOfIslandsII = new NumberOfIslandsII();
        List<Integer> integers = numberOfIslandsII.numIslands22(m, m, test);
        System.out.println(integers);
    }
}
