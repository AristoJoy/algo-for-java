package com.zh.algo.dp.quadrilateral.inequality;

import java.awt.image.Kernel;

/**
 * 体系学习班class41
 *
 * 动态规划四边形不等式class1
 *
 * 给定一个整型数组 arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，再给定一个整数num
 * 表示画匠的数量，每个画匠只能画连在一起的画作
 * 所有的画家并行工作，返回完成所有的画作需要的最少时间
 * arr=[3,1,4]，num=2。
 * 最好的分配方式为第一个画匠画3和1，所需时间为4
 * 第二个画匠画4，所需时间为4
 * 所以返回4
 * arr=[1,1,1,4,3]，num=3
 * 最好的分配方式为第一个画匠画前三个1，所需时间为3
 * 第二个画匠画4，所需时间为4
 * 第三个画匠画3，所需时间为3
 * 返回4
 *
 * 测试链接：https://leetcode.com/problems/split-array-largest-sum/
 *
 * min{max{所有画家}}
 *
 * 尝试模型：
 * dp[i][j]
 *
 * [0-i]幅画，j个画家，最短时间
 *
 * 枚举：最后一个画家负责哪个范围内的画
 *
 * 
 */
public class SplitArrayLargestSum {
    public static int sum(int[] sum, int L, int R) {
        return sum[R + 1] - sum[L];
    }

    public static int bestSplit1(int[] nums, int m) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int[][] dp = new int[N][m + 1];

        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        for (int j = 1; j <= m; j++) {
            dp[0][j] = nums[0];
        }

        for (int i = 1; i < N; i++) {
            dp[i][1] = sum(sum, 0, i);
        }

        for (int i = 1; i < N; i++) {
            for (int j = 2; j <= m; j++) {
                int ans = Integer.MAX_VALUE;
                for (int leftEnd = -1; leftEnd <= i; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    ans = Math.min(ans, Math.max(leftCost, sum(sum, leftEnd + 1, i)));
                }
                dp[i][j] = ans;
            }
        }

        return dp[N - 1][m];
    }

    public static int bestSplit2(int[] nums, int m) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;

        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        int[][] dp = new int[N][m + 1];
        int[][] best = new int[N][m + 1];
        for (int j = 1; j <= m; j++) {
            dp[0][j] = nums[0];
            // 前面的画家不负责画
            best[0][j] = -1;
        }

        for (int i = 1; i < N; i++) {
            dp[i][1] = sum(sum, 0, i);
            // 前面的画家不负责画
            best[i][1] = -1;
        }

        for (int j = 2; j <= m; j++) {
            for (int i = N - 1; i >= 1; i--) {
                int down = best[i][j - 1];
                // 如果i==N-1，则不优化上限
                int up = i == N - 1 ? N - 1 : best[i + 1][j];
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : sum(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);

                    // 注意下面的if一定是 < 课上的错误就是此处！当时写的 <= ！
                    // 也就是说，只有取得明显的好处才移动！
                    // 举个例子来说明，比如[2,6,4,4]，3个画匠时候，如下两种方案都是最优:
                    // (2,6) (4) 两个画匠负责 | (4) 最后一个画匠负责
                    // (2,6) (4,4)两个画匠负责 | 最后一个画匠什么也不负责
                    // 第一种方案划分为，[0~2] [3~3]
                    // 第二种方案划分为，[0~3] [无]
                    // 两种方案的答案都是8，但是划分点位置一定不要移动!
                    // 只有明显取得好处时(<)，划分点位置才移动!
                    // 也就是说后面的方案如果==前面的最优，不要移动！只有优于前面的最优，才移动
                    // 比如上面的两个方案，如果你移动到了方案二，你会得到:
                    // [2,6,4,4] 三个画匠时，最优为[0~3](前两个画家) [无](最后一个画家)，
                    // 最优划分点为3位置(best[3][3])
                    // 那么当4个画匠时，也就是求解dp[3][4]时
                    // 因为best[3][3] = 3，这个值提供了dp[3][4]的下限
                    // 而事实上dp[3][4]的最优划分为:
                    // [0~2]（三个画家处理） [3~3] (一个画家处理)，此时最优解为6
                    // 所以，你就得不到dp[3][4]的最优解了，因为划分点已经越过2了
                    // 提供了对数器验证，你可以改成<=，对数器和leetcode都过不了
                    // 这里是<，对数器和leetcode都能通过
                    // 这里面会让同学们感到困惑的点：
                    // 为啥==的时候，不移动，只有<的时候，才移动呢？例子懂了，但是道理何在？
                    // 哈哈哈哈哈，看了邮局选址问题，你更懵，请看42节！
                    if (cur < ans) {
                        ans = cur;
                        bestChoose = leftEnd;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = bestChoose;
            }
        }

        return dp[N - 1][m];
    }

    // 最优尝试：总共画画时间是k时，至少需要几个画家
    // 取k/2，如果结果小于m，则画家给多了，画画的时间可以更小，所以往左二分

    public static int bestSplit3(int[] nums, int m) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        long left = 0;
        long right = sum;
        long ans = 0;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            long cur = getNeedParts(nums, mid);
            if (cur <= m) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }

        return (int) ans;
    }

    // 从左往右扩，如果累加和超过目标，则起一个新的区域，重新累加，统计数量
    private static long getNeedParts(int[] nums, long aim) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > aim) {
                return Integer.MAX_VALUE;
            }
        }
        int parts = 1;
        long all = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (all + nums[i] > aim) {
                parts++;
                all = nums[i];
            } else {
                all += nums[i];
            }
        }
        return parts;
    }
}
