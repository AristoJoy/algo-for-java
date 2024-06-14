package com.zh.algo.dp.extrainfo.simplification;

/**
 * 体系学习班class45
 *
 * 动态规划外部信息简化class1
 *
 * 有n个气球，编号为0到n-1，每个气球上都标有一个数字，这些数字存在数组nums中
 * 现在要求你戳破所有的气球。戳破第i个气球，你可以获得nums[i - 1] * nums[i] * nums[i + 1] 枚硬币
 * 这里的i-1和i+1代表和i相邻的、没有被戳爆的！两个气球的序号
 * 如果i-1或i+1超出了数组的边界，那么就当它是一个数字为1的气球
 * 求所能获得硬币的最大数量
 * Leetcode题目：https://leetcode.com/problems/burst-balloons/
 *
 * 思路：
 * [L,R]打爆所有气球的最大分数f(L,R)，但是由于L左侧和R右侧气球是否打爆，会影响最终的得分，所以当前函数信息不够
 * 如果涉及递归函数时，把左右两侧的气球状态传入，由于是线性结构，会很复杂。
 * 所以不妨设在调用时，增加潜台词：L-1和R+1位置的气球没爆。
 * 接下来是[L,R]内打爆气球的试法问题：
 *  如果i是先打爆的气球，对于[L,i-1]和[i+1,R]无法满足潜台词的要求
 *  如果i是最后打爆的气球，对于[L,i-1]和[i+1,R]就可以安心调用子过程了
 */
public class BurstBalloons {
    public static int maxCoins0(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        if (array.length == 1) {
            return array[0];
        }
        int N = array.length;
        // 增加两个虚拟气球，可以减少边界的讨论
        int[] balloons = new int[N + 2];
        for (int i = 0; i < N; i++) {
            balloons[i + 1] = array[i];
        }
        balloons[0] = 1;
        balloons[N + 1] = 1;
        return process0(balloons, 1, N);
    }

    // L - 1和R + 1的气球没爆
    private static int process0(int[] balloons, int L, int R) {
        if (L == R) {
            return balloons[L - 1] * balloons[L] * balloons[R + 1];
        }
        // L位置的气球最后打爆
        int max = balloons[L - 1] * balloons[L] * balloons[R + 1] + process0(balloons, L + 1, R);
        // R位置的气球最后打爆
        max = Math.max(max, balloons[L - 1] * balloons[R] * balloons[R + 1] + process0(balloons, L, R - 1));

        for (int i = L + 1; i < R; i++) {
            int leftCoins = process0(balloons, L, i - 1);
            int rightCoins = process0(balloons, i + 1, R);
            max = Math.max(max, leftCoins + rightCoins + balloons[L - 1] * balloons[i] * balloons[R + 1]);
        }

        return max;
    }

    public static int maxCoins1(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        if (array.length == 1) {
            return array[0];
        }
        int N = array.length;
        // 增加两个虚拟气球，可以减少边界的讨论
        int[] balloons = new int[N + 2];
        for (int i = 0; i < N; i++) {
            balloons[i + 1] = array[i];
        }
        balloons[0] = 1;
        balloons[N + 1] = 1;

        int[][] dp = new int[N + 2][N + 2];

        for (int i = 1; i <= N; i++) {
            dp[i][i] = balloons[i - 1] * balloons[i] * balloons[i + 1];
        }
        for (int L = N; L >= 1; L--) {
            for (int R = L + 1; R <= N; R++) {
                int max = balloons[L - 1] * balloons[L] * balloons[R + 1] + dp[L + 1][R];
                max = Math.max(max, balloons[L - 1] * balloons[R] * balloons[R + 1] + dp[L][R - 1]);
                for (int i = L + 1; i < R; i++) {
                    max = Math.max(max, dp[L][i - 1] + dp[i + 1][R] + balloons[L - 1] * balloons[i] * balloons[R + 1]);
                }
                dp[L][R] = max;
            }
        }


        return dp[1][N];
    }


}
