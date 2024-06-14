package com.zh.algo.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 体系学习班class19
 *
 * 动态规划class2
 *
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
 * 返回需要至少多少张贴纸可以完成这个任务
 * 例子：str= "babac"，arr = {"ba","c","abcd"}
 * ba + ba + c  3  abcd + abcd 2  abcd+ba 2
 * 所以返回2
 *
 * 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
 */
public class StickersToSpellWord {
    public static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        // 不算first的最小值
        int min = Integer.MAX_VALUE;
        for (String first : stickers) {
            String rest = minus(target, first);
            if (rest.length() != target.length()) {
                min = Math.min(min, process1(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    private static String minus(String target, String sticker) {
        char[] str1 = target.toCharArray();
        char[] str2 = sticker.toCharArray();
        int[] strCount = new int[26];
        for (char ch : str1) {
            strCount[ch - 'a']++;
        }

        for (char ch : str2) {
            strCount[ch - 'a']--;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strCount.length; i++) {
            char ch = (char) ('a' + i);
            while (strCount[i]-- > 0) {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        int[][] count = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char ch : str) {
                count[i][ch - 'a']++;
            }
        }
        int ans = process2(count, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process2(int[][] stickers, String t) {
        if (t.length() == 0) {
            return 0;
        }
        char[] target = t.toCharArray();
        int[] count = new int[26];
        for (char ch : target) {
            count[ch - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (count[j] > 0) {
                        char ch = (char) (j + 'a');
                        int num = count[j] - sticker[j];
                        for (int k = 0; k < num; k++) {
                            builder.append(ch);
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    public static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        int[][] count = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char ch : str) {
                count[i][ch - 'a']++;
            }
        }
        Map<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process3(count, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process3(int[][] stickers, String t, Map<String, Integer> dp) {
        if (dp.containsKey(t)) {
            return dp.get(t);
        }
        if (t.length() == 0) {
            return 0;
        }
        char[] target = t.toCharArray();
        int[] count = new int[26];
        for (char ch : target) {
            count[ch - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (count[j] > 0) {
                        char ch = (char) (j + 'a');
                        int num = count[j] - sticker[j];
                        for (int k = 0; k < num; k++) {
                            builder.append(ch);
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t, ans);
        return ans;
    }


    public static void main(String[] args) {
        String[] arr = new String[]{"with", "example", "science"};
        String t = "thehat";
        int ans = minStickers2(arr, t);
        System.out.println(ans);
    }

}
