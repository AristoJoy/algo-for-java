package com.zh.algo.dp.state.compression;

import java.util.Arrays;

/**
 * 体系学习班class42
 *
 * 状态压缩的动态规划
 *
 * 铺砖问题（最优解其实是轮廓线dp，但是这个解法对大厂刷题来说比较难，掌握课上的解法即可）
 * 你有无限的1*2的砖块，要铺满M*N的区域，
 * 不同的铺法有多少种?
 */
public class PavingTile {
    // 试法：潜台词，前n-2行以上都摆好了，当前瓷砖只能往往上摆，或者往右摆
    public static int ways1(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int[] pre = new int[M];
        Arrays.fill(pre, 1);

        return process1(pre, 0, N);
    }

    private static int process1(int[] pre, int level, int N) {
        if (level == N) {
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }

        int[] op = getOp1(pre);
        return dfs1(op, 0, level, N);
    }

    private static int dfs1(int[] op, int col, int level, int N) {
        // 这一行已经做完决策了，往下一行做
        if (col == op.length) {
            return process1(op,level + 1, N);
        }
        // 这一列不往右铺瓷砖
        int ans = dfs1(op, col + 1, level, N);
        // 往右铺瓷砖
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            op[col] = op[col + 1] = 1;
            // 后面的位置继续深度优先遍历
            ans += dfs1(op, col + 2, level, N);
            op[col] = op[col + 1] = 0;
        }
        return ans;
    }

    private static int[] getOp1(int[] pre) {
        int[] op = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            op[i] = pre[i] ^ 1;
        }
        return op;
    }

    public static int ways2(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << min) - 1;
        return process2(pre, 0, max, min);
    }

    private static int process2(int pre, int level, int N, int M) {
        if (level == N) {
            return pre == (1 << M) - 1 ? 1 : 0;
        }
        int op = (~pre) & ((1 << M) - 1);
        // 注意这里从高位往低位走
        return dfs2(op, M - 1, level, N, M);
    }

    private static int dfs2(int op, int col, int level, int N, int M) {
        if (col == -1) {
            return process2(op, level + 1, N, M);
        }
        // 这一列不往右铺瓷砖
        int ans = dfs2(op, col - 1, level, N, M);
        // 往右铺瓷砖
        if (col > 0 && (op & (3 << (col - 1))) == 0) {
            ans += dfs2(op | (3 << (col - 1)), col - 2, level, N, M);
        }
        return ans;
    }

    public static int ways3(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << min) - 1;
        int[][] dp = new int[1 << min][max];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < max; j++) {
                dp[i][j] = -1;
            }
        }
        return process3(pre, 0, max, min, dp);
    }

    private static int process3(int pre, int level, int N, int M, int[][] dp) {
        if (dp[pre][level] != -1) {
            return dp[pre][level];
        }
        int ans = 0;
        if (level == N) {
            ans = pre == (1 << M) - 1 ? 1 : 0;
        } else {
            int op = (~pre) & ((1 << M) - 1);
            // 注意这里从高位往低位走
            ans += dfs3(op, M - 1, level, N, M, dp);
        }
        dp[pre][level] = ans;
        return ans;
    }

    private static int dfs3(int op, int col, int level, int N, int M, int[][] dp) {
        if (col == -1) {
            return process3(op, level + 1, N, M, dp);
        }
        // 这一列不往右铺瓷砖
        int ans = dfs3(op, col - 1, level, N, M, dp);
        // 往右铺瓷砖
        if (col > 0 && (op & (3 << (col - 1))) == 0) {
            ans += dfs3(op | (3 << (col - 1)), col - 2, level, N, M, dp);
        }
        return ans;
    }

    // 严格位置依赖的动态规划解
    public static int ways4(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int big = N > M ? N : M;
        int small = big == N ? M : N;
        int sn = 1 << small;
        int limit = sn - 1;
        int[] dp = new int[sn];
        dp[limit] = 1;
        // todo-zh cur和dp
        int[] cur = new int[sn];
        for (int level = 0; level < big; level++) {
            for (int status = 0; status < sn; status++) {
                if (dp[status] != 0) {
                    int op = (~status) & limit;
                    dfs4(dp[status], op, 0, small - 1, cur);
                }
            }
            for (int i = 0; i < sn; i++) {
                dp[i] = 0;
            }
            int[] tmp = dp;
            dp = cur;
            cur = tmp;
        }
        return dp[limit];
    }

    public static void dfs4(int way, int op, int index, int end, int[] cur) {
        if (index == end) {
            cur[op] += way;
        } else {
            dfs4(way, op, index + 1, end, cur);
            if (((3 << index) & op) == 0) { // 11 << index 可以放砖
                // todo-zh 有时间研究为啥是index + 1
                dfs4(way, op | (3 << index), index + 1, end, cur);
            }
        }
    }

    public static void main(String[] args) {
        int N = 8;
        int M = 6;
        System.out.println(ways1(N, M));
        System.out.println(ways2(N, M));
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));

        N = 10;
        M = 10;
        System.out.println("=========");
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));
    }
}
