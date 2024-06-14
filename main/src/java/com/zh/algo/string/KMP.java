package com.zh.algo.string;

/**
 * 体系学习班class27
 * <p>
 * KMP算法
 */
public class KMP {
    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < 1 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str = s1.toCharArray();
        char[] pat = s2.toCharArray();
        int x = 0;
        int y = 0;
        int[] next = getNextArray(pat);
        while (x < str.length && y < pat.length) {
            if (str[x] == pat[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }

        return y == pat.length ? x - y : -1;
    }

    private static int[] getNextArray(char[] pat) {
        if (pat.length == 1) {
            return new int[]{-1};
        }
        int N = pat.length;
        int[] next = new int[N];
        next[0] = -1;
        next[1] = 0;
        int i = 2; // 目前在哪个位置上求next数组的值
        int cn = 0; // 当前是哪个位置的值再和i-1位置的字符比较
        while (i < N) {
            if (pat[i - 1] == pat[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 10;
        int strSize = 200;
        int matchSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
