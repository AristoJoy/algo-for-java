package com.zh.algo.dp.state.compression;

/**
 * 体系学习班class42
 *
 * 状态压缩的动态规划
 *
 * 在"100 game"这个游戏中，两名玩家轮流选择从1到10的任意整数，累计整数和
 * 先使得累计整数和达到或超过100的玩家，即为胜者，如果我们将游戏规则改为 “玩家不能重复使用整数” 呢？
 * 例如，两个玩家可以轮流从公共整数池中抽取从1到15的整数（不放回），直到累计整数和 >= 100
 * 给定一个整数maxChoosableInteger（整数池中可选择的最大数）和另一个整数desiredTotal（累计和）
 * 判断先出手的玩家是否能稳赢（假设两位玩家游戏时都表现最佳）
 * 你可以假设 maxChoosableInteger 不会大于 20，desiredTotal不会大于 300。
 * Leetcode题目：https://leetcode.com/problems/can-i-win/
 */
public class CanIWin {
    public static boolean canIWin1(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1)  < total) {
            return false;
        }
        // array[i] != -1 还没有被取出
        // array[i] = -1 被取出
        int[] array = new int[choose];
        for (int i = 0; i < choose; i++) {
            array[i] = i + 1;
        }
        return process1(array, total);
    }

    private static boolean process1(int[] array, int rest) {
        if (rest <= 0) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] != -1) {
                int cur = array[i];
                array[i] = -1;
                boolean result = process1(array, rest - cur);
                array[i] = cur;
                if (!result) {
                    return true;
                }
            }
        }
        return false;
    }

    // 由于题目choose小于等于20，则可以利用位信息来表示第i个数是否被使用

    public static boolean canIWin2(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1)  < total) {
            return false;
        }

        return process2(choose, 0, total);
    }

    private static boolean process2(int choose, int status, int rest) {
        if (rest <= 0) {
            return false;
        }

        for (int i = 1; i <= choose; i++) {
            if (((1 << i) & status) == 0) {
                if (!process2(choose, status | (1 << i), rest - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canIWin3(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1)  < total) {
            return false;
        }
        // dp[status] == 1  true
        // dp[status] == -1  false
        // dp[status] == 0  process(status) 没算过！去算！
        int[] dp = new int[1 << (choose + 1)];

        return process3(choose, 0, total, dp);
    }

    // 为什么明明status和rest是两个可变参数，却只用status来代表状态(也就是dp)
    // 因为选了一批数字之后，得到的和一定是一样的，所以rest是由status决定的，所以rest不需要参与记忆化搜索
    private static boolean process3(int choose, int status, int rest, int[] dp) {
        if (dp[status] != 0) {
            return dp[status] == 1;
        }
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= choose; i++) {
                if (((1 << i) & status) == 0) {
                    if (!process3(choose, status | (1 << i), rest - i, dp)) {
                        ans = true;
                        break;
                    }
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }
}
