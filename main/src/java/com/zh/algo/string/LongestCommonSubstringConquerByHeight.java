package com.zh.algo.string;

/**
 * 体系学习班class45
 * <p>
 * DC3算法应用
 * <p>
 * 最长公共子串问题是面试常见题目之一
 * 假设str1长度N，str2长度M
 * 因为最优解的难度所限，一般在面试场上回答出O(N*M)的解法已经是比较优秀了
 * 因为得到O(N*M)的解法，就已经需要用到动态规划了
 * 但其实这个问题的最优解是O(N+M)，为了达到这个复杂度可是不容易
 * 首先需要用到DC3算法得到后缀数组(sa)
 * 进而用sa数组去生成height数组
 * 而且在生成的时候，还有一个不回退的优化，都非常不容易理解
 * 这就是后缀数组在面试算法中的地位 : 德高望重的噩梦
 */
public class LongestCommonSubstringConquerByHeight {
    public static int lcs1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int row = 0;
        int col = str2.length - 1;
        int max = 0;
        while (row < str1.length) {
            int i = row;
            int j = col;
            int len = 0;
            while (i < str1.length && j < str2.length) {
                if (str1[i] != str2[j]) {
                    len = 0;
                } else {
                    len++;
                }
                if (len > max) {
                    max = len;
                }
                i++;
                j++;
            }
            if (col > 0) {
                col--;
            } else {
                row++;
            }
        }
        return max;
    }

    public static int lcs2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
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
        for (int i = 0; i < N; i++) {
            all[i] = str1[i] - min + 2;
        }
        all[N] = 1;
        for (int i = 0; i < M; i++) {
            all[i + N + 1] = str2[i] - min + 2;
        }
        DC3 dc3 = new DC3(all, max - min + 2);
        int[] sa = dc3.sa();
        int[] height = dc3.getHeight();
        int ans = 0;
        int n = all.length;
        for (int i = 1; i < n; i++) {
            int Y = sa[i - 1];
            int X = sa[i];
            if (Math.min(X, Y) < N && Math.max(X, Y) > N) {
                ans = Math.max(ans, height[i]);
            }
        }
        return ans;
    }

    // for test
    public static String randomNumberString(int len, int range) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int len = 30;
        int range = 5;
        int testTime = 100000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N1 = (int) (Math.random() * len);
            int N2 = (int) (Math.random() * len);
            String str1 = randomNumberString(N1, range);
            String str2 = randomNumberString(N2, range);
            int ans1 = lcs1(str1, str2);
            int ans2 = lcs2(str1, str2);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
        System.out.println("==========");

        System.out.println("性能测试开始");
        len = 80000;
        range = 26;
        long start;
        long end;

        String str1 = randomNumberString(len, range);
        String str2 = randomNumberString(len, range);

        start = System.currentTimeMillis();
        int ans1 = lcs1(str1, str2);
        end = System.currentTimeMillis();
        System.out.println("方法1结果 : " + ans1 + " , 运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        int ans2 = lcs2(str1, str2);
        end = System.currentTimeMillis();
        System.out.println("方法2结果 : " + ans2 + " , 运行时间 : " + (end - start) + " ms");

        System.out.println("性能测试结束");

    }
}
