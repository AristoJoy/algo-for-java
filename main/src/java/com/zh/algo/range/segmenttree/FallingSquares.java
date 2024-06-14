package com.zh.algo.range.segmenttree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * 体系学习班class31
 *
 * 线段树
 *
 * 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
 * 下面是这个游戏的简化版：
 * 1）只会下落正方形积木
 * 2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
 * 3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
 * 4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
 * 给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，
 * 返回每一次掉落之后的最大高度
 * Leetcode题目：https://leetcode.com/problems/falling-squares/
 */
public class FallingSquares {
    static class SegmentTree {
        private int[] max;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int size) {
            int N = size + 1;
            max = new int[N << 2];
            change = new int[N << 2];
            update = new boolean[N << 2];
        }

        public void pushUp(int root) {
            max[root] = Math.max(max[root << 1], max[root << 1 | 1]);
        }

        public void pushDown(int root) {
            if (update[root]) {
                update[root << 1] = true;
                update[root << 1 | 1] = true;
                change[root << 1] = change[root];
                change[root << 1 | 1] = change[root];
                max[root << 1] = change[root];
                max[root << 1 | 1] = change[root];
                update[root] = false;
            }
        }

        // L~R, C 任务！
        // rt，l~r
        public void update(int L, int R, int M, int l, int r, int root) {
            // 任务如果把此时的范围全包了！
            if (L <= l && r <= R) {
                update[root] = true;
                change[root] = M;
                max[root] = M * (r - l + 1);
                return;
            }
            // 任务没有把你全包！
            // l  r  mid = (l+r)/2
            int mid = (l + r) >> 1;
            pushDown(root);
            if (L <= mid) {
                update(L, R, M, l, mid, root << 1);
            }
            if (R > mid) {
                update(L, R, M, mid + 1, r, root << 1 | 1);
            }
            pushUp(root);
        }

        public int query(int L, int R, int l, int r, int root) {
            // 任务如果把此时的范围全包了！
            if (L <= l && r <= R) {
                return max[root];
            }
            // 任务没有把你全包！
            // l  r  mid = (l+r)/2
            int mid = (l + r) >> 1;
            pushDown(root);
            int left = 0;
            int right = 0;
            if (L <= mid) {
                left = query(L, R, l, mid, root << 1);
            }
            if (R > mid) {
                right = query(L, R, mid + 1, r, root << 1 | 1);
            }
            return Math.max(left, right);
        }
    }

    // todo-zh 还没理解
    public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            pos.add(arr[0]);
            pos.add(arr[0] + arr[1] - 1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size();
        SegmentTree segmentTree = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = segmentTree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(L, R, height, 1, N, 1);
        }
        return res;
    }
}
