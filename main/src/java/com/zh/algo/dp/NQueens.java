package com.zh.algo.dp;

/**
 * 体系学习班class23
 *
 * 动态规划class6
 *
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 */
public class NQueens {

    public static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] prePos = new int[n];
        return process(prePos, 0, n);
    }

    // 当前来到i行，一共是0~N-1行
    // 在i行上放皇后，所有列都尝试
    // 必须要保证跟之前所有的皇后不打架
    // int[] record record[x] = y 之前的第x行的皇后，放在了y列上
    // 返回：不关心i以上发生了什么，i.... 后续有多少合法的方法数
    private static int process(int[] prePos, int i, int n) {
        if (i == n) {
            return 1;
        }
        int ways = 0;

        for (int j = 0; j < n; j++) {
            if (isValid(prePos, i, j)) {
                prePos[i] = j;
                ways += process(prePos, i + 1, n);
            }
        }
        return ways;
    }

    private static boolean isValid(int[] prePos, int i, int j) {
        for (int k = 0; k < i; k++) {
            if (prePos[k] == j || Math.abs(prePos[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }

    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process1(limit, 0, 0, 0);
    }

    // 位运算加速计算过程
    //
    // 7皇后问题
    // limit : 0....0 1 1 1 1 1 1 1
    // 之前皇后的列影响：colLim
    // 之前皇后的左下对角线影响：leftDiaLim
    // 之前皇后的右下对角线影响：rightDiaLim
    private static int process1(int limit, int colLimit, int leftDiaLimit, int rightDiaLimit) {
        if (colLimit == limit) {
            return 1;
        }
        //
        int pos = limit & (~(colLimit | leftDiaLimit | rightDiaLimit));
        int rightMostOne = 0;
        int ans = 0;
        while (pos != 0) {
            rightMostOne = pos & (-pos);
            pos -= rightMostOne;
            ans += process1(limit, colLimit | rightMostOne, (leftDiaLimit | rightMostOne) << 1, (rightDiaLimit | rightMostOne) >>> 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 13;

        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }
}
