package com.zh.algo.dp.state.compression;

import java.util.ArrayList;
import java.util.List;

/**
 * 体系学习班class42
 *
 * 状态压缩的动态规划
 *
 * TSP问题 旅行商问题
 *
 * 有N个城市，任何两个城市之间的都有距离，任何一座城市到自己的距离都为0
 * 所有点到点的距离都存在一个N*N的二维数组matrix里，也就是整张图由邻接矩阵表示
 * 现要求一旅行商从k城市出发必须经过每一个城市且只在一个城市逗留一次，最后回到出发的k城
 * 参数给定一个matrix，给定k。返回总距离最短的路的距离
 */
public class TSP {
    public static int tsp1(int[][] matrix) {
        int N = matrix.length;
        // set.get(i) != null i这座城市在集合里
        // set.get(i) == null i这座城市不在集合里
        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            set.add(1);
        }
        return process1(matrix, set, 0);
    }

    // 任何两座城市之间的距离，可以在matrix里面拿到
    // set中表示着哪些城市的集合，
    // start这座城一定在set里，
    // 从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
    private static int process1(int[][] matrix, List<Integer> set, int start) {
        int cityNum = 0;
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i) != null) {
                cityNum++;
            }
        }
        if (cityNum == 1) {
            return matrix[start][0];
        }
        set.set(start, null);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i) != null && i != start) {
                min = Math.min(min, matrix[start][i] + process1(matrix, set, i));
            }
        }
        set.set(start, 1);

        return min;
    }

    public static int tsp2(int[][] matrix) {
        int N = matrix.length;
        int cityStatus = (1 << N) - 1;
        return process2(matrix, cityStatus, 0);
    }

    private static int process2(int[][] matrix, int cityStatus, int start) {
        if (cityStatus == (cityStatus & (-cityStatus))) {
            return matrix[start][0];
        }
        cityStatus &= ~(1 << start);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if ((cityStatus & (1 << i)) != 0) {
                min = Math.min(min, matrix[start][i] + process2(matrix, cityStatus, i));
            }
        }

        cityStatus |= 1 << start;
        return min;
    }

    public static int tsp3(int[][] matrix) {
        int N = matrix.length;
        int cityStatus = (1 << N) - 1;
        int[][] dp = new int[1 << N][N];
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j] = -1;
            }
        }
        return process3(matrix, cityStatus, 0, dp);
    }

    private static int process3(int[][] matrix, int cityStatus, int start, int[][] dp) {
        if (dp[cityStatus][start] != -1) {
            return dp[cityStatus][start];
        }
        if (cityStatus == (cityStatus & (-cityStatus))) {
            dp[cityStatus][start] = matrix[start][0];
        } else {
            cityStatus &= ~(1 << start);
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < matrix.length; i++) {
                if ((cityStatus & (1 << i)) != 0) {
                    min = Math.min(min, matrix[start][i] + process3(matrix, cityStatus, i, dp));
                }
            }

            cityStatus |= 1 << start;
            dp[cityStatus][start] = min;
        }
        return dp[cityStatus][start];
    }
    public static int tsp4(int[][] matrix) {
        int N = matrix.length;
        int statusNums = 1 << N;
        int[][] dp = new int[statusNums][N];

        for (int status = 0; status < statusNums; status++) {
            for (int start = 0; start < N; start++) {
                if ((status & (1 << start)) != 0) {
                    if (status == (status & (-status))) {
                        dp[status][start] = matrix[start][0];
                    } else {
                        int min = Integer.MAX_VALUE;
                        int preStatus = status & (~(1 << start));
                        for (int i = 0; i < N; i++) {
                            if ((preStatus & (1 << i)) != 0) {
                                min = Math.min(min, matrix[start][i] + dp[preStatus][i]);
                            }
                        }
                        dp[status][start] = min;
                    }
                }
            }
        }


        return dp[statusNums - 1][0];
    }

    public static int[][] generateGraph(int maxSize, int maxValue) {
        int len = (int) (Math.random() * maxSize) + 1;
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue) + 1;
                matrix[j][i] = matrix[i][j];
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        System.out.println("功能测试开始");
        for (int i = 0; i < 20000; i++) {
            int[][] matrix = generateGraph(len, value);
//            int origin = (int) (Math.random() * matrix.length);
//            int ans1 = tsp1(matrix);
//            int ans2 = tsp2(matrix);
//            int ans3 = tsp3(matrix);
//            int ans4 = tsp4(matrix);
//            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
//                System.out.println("fuck");
//                System.out.println(ans1);
//                System.out.println(ans2);
//                System.out.println(ans3);
//                System.out.println(ans4);
//                break;
//            }
//            int ans1 = tsp1(matrix);
//            int ans2 = tsp2(matrix);
            int ans3 = tsp3(matrix);
            int ans4 = tsp4(matrix);
            if (ans3 != ans4) {
                System.out.println("fuck");
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("功能测试结束");

        len = 22;
        System.out.println("性能测试开始，数据规模 : " + len);
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * value) + 1;
                matrix[j][i] = matrix[i][j];
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        long start;
        long end;
        start = System.currentTimeMillis();
        tsp4(matrix);
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");
        System.out.println("性能测试结束");

    }
}
