package com.zh.algo.recursion;

import com.zh.algo.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class PrintAllPermutations {
    public static List<String> permutation1(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = s.toCharArray();
        List<Character> rest = new ArrayList<>();
        for (char c : str) {
            rest.add(c);
        }
        List<String> ans = new ArrayList<>();
        String path = "";
        process1(rest, path, ans);
        return ans;
    }

    private static void process1(List<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            for (int i = 0; i < rest.size(); i++) {
                char ch = rest.get(i);
                rest.remove(i);
                process1(rest, path + ch, ans);
                rest.add(i, ch);
            }
        }
    }

    public static List<String> permutation2(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = s.toCharArray();
        List<String> ans = new ArrayList<>();
        process2(str, 0, ans);
        return ans;
    }

    private static void process2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            for (int i = index; i < str.length; i++) {
                ArrayUtils.swap(str, index, i);
                process2(str, index + 1, ans);
                ArrayUtils.swap(str, index, i);
            }
        }
    }

    public static List<String> permutation3(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = s.toCharArray();
        List<String> ans = new ArrayList<>();
        process3(str, 0, ans);
        return ans;
    }

    private static void process3(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            boolean[] visited = new boolean[256];
            for (int i = index; i < str.length; i++) {
                if (visited[str[i]]) {
                    continue;
                }
                visited[str[i]] = true;
                ArrayUtils.swap(str, index, i);
                process3(str, index + 1, ans);
                ArrayUtils.swap(str, index, i);
            }
        }
    }

    public static void main(String[] args) {
        String s = "acc";
        List<String> ans1 = permutation1(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutation2(s);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }

    }
}
