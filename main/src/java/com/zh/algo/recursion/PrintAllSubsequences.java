package com.zh.algo.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PrintAllSubsequences {
    public static List<String> subs(String s) {
        char[] str = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        process1(str, 0, path, ans);
        return ans;
    }

    private static void process1(char[] str, int index, String path, List<String> ans) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        // 没要当前字符
        process1(str, index + 1, path, ans);
        // 要了当前字符
        process1(str, index + 1, path + String.valueOf(str[index]), ans);
    }

    public static List<String> subsNoRepeat(String s) {
        char[] str = s.toCharArray();
        String path = "";
        Set<String> ans = new HashSet<>();
        process1(str, 0, path, ans);
        return new ArrayList<>(ans);
    }

    private static void process1(char[] str, int index, String path, Set<String> ans) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        // 没要当前字符
        process1(str, index + 1, path, ans);
        // 要了当前字符
        process1(str, index + 1, path + String.valueOf(str[index]), ans);
    }

    public static void main(String[] args) {
        String test = "acccc";
        List<String> ans1 = subs(test);
        List<String> ans2 = subsNoRepeat(test);

        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=================");
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=================");

    }
}
