package com.zh.algo.dp.quadrilateral.inequality;

/**
 * 体系学习班class41
 *
 * 动态规划四边形不等式class1
 *
 * 摆放着n堆石子。现要将石子有次序地合并成一堆，规定每次只能选相邻的2堆石子合并成新的一堆
 * 并将新的一堆石子数记为该次合并的得分，求出将n堆石子合并成一堆的最小得分（或最大得分）合并方案
 *
 * 范围上的尝试模型
 */
public class StoneMerge {
    public static int[] sum(int[] array) {
        int[] sum = new int[array.length + 1];
        for (int i = 0; i < array.length; i++) {
            sum[i + 1] = sum[i] + array[i];
        }
        return sum;
    }

    public static int w(int[] sum, int L, int R) {
        return sum[R + 1] - sum[L];
    }

    public static int min1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] sum = sum(array);
        return process1(0, array.length - 1, sum);
    }

    private static int process1(int L, int R, int[] sum) {
        if (L == R) {
            return 0;
        }
        int next = Integer.MAX_VALUE;
        for (int leftEnd = L; leftEnd < R; leftEnd++) {
            next = Math.min(next, process1(L, leftEnd, sum) + process1(leftEnd + 1, R, sum));
        }
        return next + w(sum, L, R);
    }

    public static int min2(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] sum = sum(array);
        int N = array.length;
        int[][] dp = new int[N][N];

        for (int L = N - 2; L >= 0; L--) {
            for (int R = L + 1; R < N; R++) {
                int next = Integer.MAX_VALUE;
                for (int leftEnd = L; leftEnd < R; leftEnd++) {
                    next = Math.min(next, dp[L][leftEnd] + dp[leftEnd + 1][R]);
                }
                dp[L][R] = next + w(sum, L, R);
            }
        }

        return dp[0][N - 1];
    }

    public static int min3(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int[] sum = sum(array);
        int N = array.length;
        int[][] dp = new int[N][N];
        int[][] best = new int[N][N];
        for (int i = 0; i < N - 1; i++) {
            // 只有两个位置，划分点就只有一个
            best[i][i + 1] = i;
            dp[i][i + 1] = w(sum, i, i + 1);
        }

        int choose;
        int next;
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                next = Integer.MAX_VALUE;
                choose = -1;
                for (int leftEnd = best[L][R - 1]; leftEnd <= best[L + 1][R]; leftEnd++) {
                    int cur = dp[L][leftEnd] + dp[leftEnd + 1][R];
                    if (cur < next) {
                        choose = leftEnd;
                        next = cur;
                    }
                }
                best[L][R] = choose;
                dp[L][R] = next + w(sum, L, R);
            }
        }

        return dp[0][N - 1];
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int N = 15;
        int maxValue = 100;
        int testTime = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, maxValue);
            int ans1 = min1(arr);
            int ans2 = min2(arr);
            int ans3 = min3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }


}
