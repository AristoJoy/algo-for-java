package com.zh.algo.string;

/**
 * 体系学习班class28
 *
 * Manacher算法应用
 *
 * 给定一个字符串str，只能在str的后面添加字符，想让str整体变成回文串，返回至少要添加几个字符
 */
public class AddShortestEnd {
    public static String shortestEnd(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = manacherString(s);
        int[] pArray = new int[str.length];
        int max = Integer.MIN_VALUE;
        int C = -1; // 最大回文半径对应的中心点
        int R = -1;// 表示最大回文半径的下一个位置
        int containsEnd = -1;
        for (int i = 0; i < str.length; i++) {
            pArray[i] = R > i ? Math.min(pArray[2 * C - i], R - i) : 1;
            while (i + pArray[i] < str.length && i - pArray[i] > -1) {
                if (str[i + pArray[i]] == str[i - pArray[i]]) {
                    pArray[i]++;
                } else {
                    break;
                }
            }
            if (i + pArray[i] > R) {
                R = i + pArray[i];
                C = i;
            }
            max = Math.max(max, pArray[i]);
            if (R == str.length) {
                containsEnd = pArray[i];
                break;
            }
        }

        char[] ans = new char[s.length() - containsEnd + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[ans.length - 1 - i] = str[2 * i + 1];
        }
        return String.valueOf(ans);
    }

    public static char[] manacherString(String s) {
        char[] charArray = s.toCharArray();
        char[] str = new char[s.length() * 2 + 1];
        for (int i = 0; i < charArray.length; i++) {
            str[2 * i] = '#';
            str[2 * i + 1] = charArray[i];
        }
        str[str.length - 1] = '#';
        return str;
    }

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }
}
