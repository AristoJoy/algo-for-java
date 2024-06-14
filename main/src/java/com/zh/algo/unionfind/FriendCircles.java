package com.zh.algo.unionfind;

/**
 * 体系学习班class15
 * 本题为leetcode原题
 * 测试链接：
 * https://leetcode.com/problems/friend-circles/
 * https://leetcode.com/problems/number-of-provinces/
 */
public class FriendCircles {
    public static int findCircleNum(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix.length != matrix[0].length) {
            return 0;
        }
        int m = matrix.length;
        Union union = new Union(m);
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                if (matrix[i][j] == 1) {
                    union.union(i, j);
                }
            }
        }
        return union.sets();
    }

    static class Union {
        private int[] parents;
        private int[] size;
        private int[] help;
        private int sets;

        public Union(int m) {
            parents = new int[m];
            size = new int[m];
            help = new int[m];
            sets = m;

            for (int i = 0; i < m; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public int find(int index) {
            if (index < 0 || index >= parents.length) {
                return -1;
            }
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

        public void union(int i, int j) {
            if (i < 0 || i >= parents.length || j < 0 || j >= parents.length) {
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

        public int sets() {
            return sets;
        }
    }


}
