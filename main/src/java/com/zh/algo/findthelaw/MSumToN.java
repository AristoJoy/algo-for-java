package com.zh.algo.findthelaw;

/**
 * 体系学习班class38
 * <p>
 * 根据对数器找规律
 * <p>
 * 定义一种数：可以表示成若干（数量>1）连续正数和的数
 * 比如，5=2+3，5就是这样的数；12=3+4+5，12就是这样的数
 * 2=1+1，2不是这样的数，因为等号右边不是连续正数
 * 给定一个参数N，返回是不是可以表示成若干连续正数和的数
 */
public class MSumToN {
    public static boolean isMSum1(int num) {
        for (int start = 1; start <= num; start++) {
            int sum = start;
            for (int j = start + 1; j <= num; j++) {
                if (sum + j > num) {
                    break;
                }
                if (sum + j == num) {
                    return true;
                }
                sum += j;
            }
        }
        return false;
    }

    public static boolean isMSum2(int num) {
        return (num & (num - 1)) != 0;
    }

    public static void main(String[] args) {
        for (int num = 1; num < 200; num++) {
            System.out.println(num + " : " + isMSum1(num));
        }
        System.out.println("test begin");
        for (int num = 1; num < 5000; num++) {
            if (isMSum1(num) != isMSum2(num)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");

    }
}
