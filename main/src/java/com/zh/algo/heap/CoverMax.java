package com.zh.algo.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 体系班class7
 * 给定很多线段，每个线段都有两个数[start, end]，
 * 表示线段开始位置和结束位置，左右都是闭区间
 * 规定：
 * 1）线段的开始和结束位置一定都是整数值
 * 2）线段重合区域的长度必须>=1
 * 返回线段最多重合区域中，包含了几条线段
 */
public class CoverMax {
    public static int coverMax1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p += 1) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (p > lines[i][0] && p < lines[i][1]) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    public static int coverMax2(int[][] lines) {
        Line[] lineArr = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            lineArr[i] = new Line(lines[i][0], lines[i][1]);
        }

        Arrays.sort(lineArr, Comparator.comparingInt(Line::getStart));

        int max = 0;
        // 小根堆，将线段的右端点放入堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < lineArr.length; i++) {
            // 小于等于当前线段start的end值都弹出，因为这样的线段其start必然是小于当前线段的start，所以与当前线段没有重回部分（端点不算）
            while (!queue.isEmpty() && queue.peek() <= lineArr[i].start) {
                queue.poll();
            }
            queue.add(lineArr[i].end);
            max = Math.max(max, queue.size());
        }

        return max;
    }

    public static int coverMax3(int[][] lines) {

        Arrays.sort(lines, Comparator.comparingInt(line -> line[0]));

        int max = 0;
        // 小根堆，将线段的右端点放入堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < lines.length; i++) {
            // 小于等于当前线段start的end值都弹出，因为这样的线段其start必然是小于当前线段的start，所以与当前线段没有重回部分（端点不算）
            while (!queue.isEmpty() && queue.peek() <= lines[i][0]) {
                queue.poll();
            }
            queue.add(lines[i][1]);
            max = Math.max(max, queue.size());
        }

        return max;
    }



    static class Line {
        private int start;
        private int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void main(String[] args) {

        Line l1 = new Line(4, 9);
        Line l2 = new Line(1, 4);
        Line l3 = new Line(7, 15);
        Line l4 = new Line(2, 4);
        Line l5 = new Line(4, 6);
        Line l6 = new Line(3, 7);

        // 底层堆结构，heap
        PriorityQueue<Line> heap = new PriorityQueue<>(Comparator.comparingInt(Line::getStart));
        heap.add(l1);
        heap.add(l2);
        heap.add(l3);
        heap.add(l4);
        heap.add(l5);
        heap.add(l6);

        while (!heap.isEmpty()) {
            Line cur = heap.poll();
            System.out.println(cur.start + "," + cur.end);
        }

        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = coverMax1(lines);
            int ans2 = coverMax2(lines);
            int ans3 = coverMax3(lines);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }
}
