package com.zh.algo.string;

/**
 * 体系学习班class27
 *
 * KMP算法应用
 *
 * 判断str1和str2是否互为旋转字符串
 */
public class IsRotation {
    public static boolean isRotation(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        String b2 = b + b;
        return KMP.getIndexOf(b2, a) != -1;
    }

    public static void main(String[] args) {
        String str1 = "yunzuocheng";
        String str2 = "zuochengyun";
        System.out.println(isRotation(str1, str2));

    }
}
