package com.zh.algo.dp;

/**
 * 体系学习班class18
 *
 * 动态规划class1
 *
 * 假设有排成一行的N个位置记为1~N，N一定大于或等于2
 * 开始时机器人在其中的M位置上(M一定是1~N中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到N-1位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，返回方法数
 */
public class RobotWalk {

    /**
     * 机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
     * @param N 位置
     * @param start 起始位置
     * @param aim 目标位置
     * @param K 步数
     * @return 方法数
     */
    public static int ways1(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return 0;
        }
        return process1(N, aim, start, K);
    }

    /**
     * 还有rest步，从cur走到aim的方法数
     * @param n 位置
     * @param aim 目标位置
     * @param cur 当前位置
     * @param rest 剩余步数
     * @return 方法数
     */
    private static int process1(int n, int aim, int cur, int rest) {
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        // 还有rest步
        if (cur == 1) {
            return process1(n, aim, cur + 1, rest - 1);
        }
        if (cur == n) {
            return process1(n, aim, cur - 1, rest - 1);
        }
        return process1(n, aim, cur - 1, rest - 1) + process1(n, aim, cur + 1, rest - 1);
    }

    public static int ways2(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return 0;
        }
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        dp[aim][0] = 1;
        return process2(dp, N, aim, start, K);
    }

    private static int process2(int[][] dp, int n, int aim, int cur, int rest) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        int ans = 0;
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(dp, n, aim, cur + 1, rest - 1);
        } else if (cur == n) {
            ans = process2(dp, n, aim, cur - 1, rest - 1);
        } else {
            ans = process2(dp, n, aim, cur + 1, rest - 1) + process2(dp, n, aim, cur - 1, rest - 1);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    public static int ways3(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 0) {
            return 0;
        }
        int[][] dp = new int[N + 1][K + 1];
        dp[aim][0] = 1;
        for (int i = 1; i <= K; i++) {
            dp[1][i] = dp[2][i - 1];
            for (int j = 2; j < N; j++) {
                dp[j][i] = dp[j - 1][i - 1] + dp[j + 1][i - 1];
            }
            dp[N][i] = dp[N - 1][i - 1];
        }
        return dp[start][K];
    }

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }
}
