package com.zh.algo.string;

/**
 * 体系学习班class45
 *
 * DC3算法应用
 *
 * 给定两个字符串str1和str2，想把str2整体插入到str1中的某个位置，形成最大的字典序，返回字典序最大的结果
 */
public class InsertS2MakeMostAlphabeticalOrder {
    // 暴力方法
    public static String right(String s1, String s2) {
        if (s1 == null || s1.length() == 0) {
            return s2;
        }
        if (s2 == null || s2.length() == 0) {
            return s1;
        }
        String p1 = s1 + s2;
        String p2 = s2 + s1;
        String ans = p1.compareTo(p2) > 0 ? p1 : p2;
        for (int end = 1; end < s1.length(); end++) {
            String cur = s1.substring(0, end) + s2 + s1.substring(end);
            if (cur.compareTo(ans) > 0) {
                ans = cur;
            }
        }
        return ans;
    }

    // 正式方法 O(N+M) + O(M^2)
    // N : s1长度
    // M : s2长度
    public static String maxCombine(String s1, String s2) {
        if (s1 == null || s1.length() == 0) {
            return s2;
        }
        if (s2 == null || s2.length() == 0) {
            return s1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int min = str1[0];
        int max = str1[0];
        for (int i = 1; i < N; i++) {
            min = Math.min(min, str1[i]);
            max = Math.max(max, str1[i]);
        }

        for (int i = 0; i < M; i++) {
            min = Math.min(min, str2[i]);
            max = Math.max(max, str2[i]);
        }

        int[] all = new int[N + M + 1];
        int i = 0;
        for (; i < N; i++) {
            all[i] = str1[i] - min + 2;
        }
        all[i++] = 1;
        for (int j = 0; i < M; i++) {
            all[i] = str2[j] - min + 2;
        }
        DC3 dc3 = new DC3(all, max - min + 2);
        int[] rank = dc3.getRank();
        int comp = N + 1;
        for (int j = 0; j < N; j++) {
            if (rank[j] < rank[comp]) {
                int best = bestSplit(s1, s2, i);
                return s1.substring(0, best) + s2 + s1.substring(best);
            }
        }
        return s1 + s2;
    }

    private static int bestSplit(String s1, String s2, int first) {
        int N = s1.length();
        int M = s2.length();
        int end = N;
        for (int i = first, j = 0; i < N && j < M; i++, j++) {
            if (s1.charAt(i) < s2.charAt(j)) {
                end = i;
                break;
            }
        }
        // 这个就相当于从first-1开始
        String bestPrefix = s2;
        int bestSplit = first;
        for (int i = first + 1, j = M - 1; i <= end; i++, j--) {
            String curPrefix = s1.substring(first, i) + s2.substring(0, j);
            if (curPrefix.compareTo(bestPrefix) >= 0) {
                bestPrefix = curPrefix;
                bestSplit = i;
            }
        }
        return bestSplit;
    }
}
