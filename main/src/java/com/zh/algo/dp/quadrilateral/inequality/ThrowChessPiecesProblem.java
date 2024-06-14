package com.zh.algo.dp.quadrilateral.inequality;

/**
 * 体系学习班class42
 *
 * 动态规划四边形不等式class2
 *
 * K蛋问题：
 *
 * 一座大楼有0~N层，地面算作第0层，最高的一层为第N层
 * 已知棋子从第0层掉落肯定不会摔碎，从第i层掉落可能会摔碎，也可能不会摔碎(1≤i≤N)
 * 给定整数N作为楼层数，再给定整数K作为棋子数
 * 返回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔的最少次数
 * 一次只能扔一个棋子
 * N=10，K=1
 * 返回10
 * 因为只有1棵棋子，所以不得不从第1层开始一直试到第10层
 * 在最差的情况下，即第10层是不会摔坏的最高层，最少也要扔10次
 * N=3，K=2
 * 返回2
 * 先在2层扔1棵棋子，如果碎了试第1层，如果没碎试第3层
 * N=105，K=2
 * 返回14
 * 第一个棋子先在14层扔，碎了则用仅存的一个棋子试1~13
 * 若没碎，第一个棋子继续在27层扔，碎了则用仅存的一个棋子试15~26
 * 若没碎，第一个棋子继续在39层扔，碎了则用仅存的一个棋子试28~38
 * 若没碎，第一个棋子继续在50层扔，碎了则用仅存的一个棋子试40~49
 * 若没碎，第一个棋子继续在60层扔，碎了则用仅存的一个棋子试51~59
 * 若没碎，第一个棋子继续在69层扔，碎了则用仅存的一个棋子试61~68
 * 若没碎，第一个棋子继续在77层扔，碎了则用仅存的一个棋子试70~76
 * 若没碎，第一个棋子继续在84层扔，碎了则用仅存的一个棋子试78~83
 * 若没碎，第一个棋子继续在90层扔，碎了则用仅存的一个棋子试85~89
 * 若没碎，第一个棋子继续在95层扔，碎了则用仅存的一个棋子试91~94
 * 若没碎，第一个棋子继续在99层扔，碎了则用仅存的一个棋子试96~98
 * 若没碎，第一个棋子继续在102层扔，碎了则用仅存的一个棋子试100、101
 * 若没碎，第一个棋子继续在104层扔，碎了则用仅存的一个棋子试103
 * 若没碎，第一个棋子继续在105层扔，若到这一步还没碎，那么105便是结果
 */
public class ThrowChessPiecesProblem {

    // 试法1：假设仍在第i层，
    // 碎了，还剩i - 1层，k - 1个棋子
    // 没碎，还剩level - i，k个棋子

    public static int superEggDrop1(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        return Process1(nLevel, kChess);
    }

    // rest还剩多少层楼需要去验证
    // k还有多少颗棋子能够使用
    // 一定要验证出最高的不会碎的楼层！但是每次都是坏运气。
    // 返回至少需要扔几次？
    public static int Process1(int rest, int k) {
        if (rest == 0) {
            return 0;
        }
        if (k == 1) {
            return rest;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i != rest + 1; i++) { // 第一次扔的时候，仍在了i层
            min = Math.min(min, Math.max(Process1(i - 1, k - 1), Process1(rest - i, k)));
        }
        return min + 1;
    }

    public static int superEggDrop2(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        for (int i = 1; i <= nLevel; i++) {
            dp[i][1] = i;
        }

        for (int i = 1; i <= nLevel; i++) {
            for (int j = 2; j <= kChess; j++) {
                int ans = Integer.MAX_VALUE;
                for (int k = 1; k <= i; k++) {
                    ans = Math.min(ans, Math.max(dp[k - 1][j - 1], dp[i - k][j]));
                }
                dp[i][j] = ans + 1;
            }
        }

        return dp[nLevel][kChess];
    }
    public static int superEggDrop3(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kChess + 1];
        int[][] best = new int[nLevel + 1][kChess + 1];

        for (int i = 1; i <= nLevel; i++) {
            dp[i][1] = i;
        }

        for (int i = 1; i <= kChess; i++) {
            dp[1][i] = 1;
            best[1][i] = 1;
        }

        for (int i = 2; i <= nLevel; i++) {
            for (int j = kChess; j > 1; j--) {
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                int down = best[i - 1][j];
                int up = j == kChess ? i : best[i][j + 1];
                for (int k = down; k <= up; k++) {
                    int cur = Math.max(dp[k - 1][j - 1], dp[i - k][j]);
                    if (cur <= ans) {
                        ans = cur;
                        bestChoose = k;
                    }
                }

                dp[i][j] = ans + 1;
                best[i][j] = bestChoose;
            }
        }

        return dp[nLevel][kChess];
    }

    // 试法2：dp[i][j] 总共有i颗棋子，总共试j次，能达到的层数
    // 位置依赖：我试了一次，那么还剩j-1次
    // 碎了，还剩i-1个棋子，还能能搞定dp[i-1][j-1]层楼
    // 没碎，还剩i个棋子，还能搞定dp[i][j - 1]
    // 当前楼被试出来了，所以加1
    // dp[i][j= = dp[i - 1][j - 1] + dp[i][j - 1] + 1
    //

    public static int superEggDrop4(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        if (kChess == 1) {
            return nLevel;
        }

        // 由于不知道需要试多少次，也就是k的范围不知道，所以用数组压缩技巧
        int[] dp = new int[kChess];
        int times = 0;
        while (true) {
            times++;
            // 由于数组dp[i - 1][j-1]会由于上一次计算dp[i-1][j]被覆盖，所以需要保存
            int previous = 0;
            for (int i = 0; i < kChess; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= nLevel) {
                    return times;
                }
            }
        }
    }

    public static void main(String[] args) {
        int maxN = 500;
        int maxK = 30;
        int testTime = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN) + 1;
            int K = (int) (Math.random() * maxK) + 1;
            int ans2 = superEggDrop2(K, N);
            int ans3 = superEggDrop3(K, N);
            int ans4 = superEggDrop4(K, N);
            if (ans2 != ans3 || ans2 != ans4) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }


}
