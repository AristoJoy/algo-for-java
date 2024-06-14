package com.zh.algo.string;

/**
 * 体系学习班class28
 *
 * Manacher算法：求最大回文子串长度
 */
public class Manacher {
    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int[] pArray = new int[str.length];
        int max = Integer.MIN_VALUE;
        int C = -1; // 最大回文半径对应的中心点
        int R = -1;// 表示最大回文半径的下一个位置
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
        }

        return max - 1;
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

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
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
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
