package com.zh.algo.greedy;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * 体系学习班class13
 * 给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有可能的拼接结果中字典序最小的结果
 */
public class LowestLexicography {

    public static String lowestString1(String[] strings) {
        if (strings == null || strings.length == 0) {
            return null;
        }
        TreeSet<String> ans = process1(strings);
        return ans.size() == 0 ? "" : ans.first();
    }

    // 返回所有字符串的全排序序列，然后取最小的
    private static TreeSet<String> process1(String[] strings) {
        TreeSet<String> ans = new TreeSet<>();
        if (strings.length == 0) {
            ans.add("");
            return ans;
        }

        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            String[] nexts = removeIndexString(strings, i);
            TreeSet<String> next = process1(nexts);
            for (String s : next) {
                ans.add(str + s);
            }
        }

        return ans;
    }

    private static String[] removeIndexString(String[] strings, int index) {
        String[] copy = new String[strings.length - 1];
        int j = 0;
        for (int i = 0; i < strings.length; i++) {
            if (i == index) {
                continue;
            }
            copy[j++] = strings[i];
        }
        return copy;
    }

    public static String lowestString2(String[] strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        Arrays.sort(strings, (a, b) -> (a + b).compareTo(b + a));
        String ans = "";
        for (String string : strings) {
            ans += string;
        }
        return ans;
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    // for test
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString1(arr1).equals(lowestString2(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
