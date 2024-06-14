package com.zh.algo.dp.extrainfo.simplification;

/**
 * 体系学习班class46
 *
 * 动态规划外部信息简化class2
 *
 * 整型数组arr长度为n(3 <= n <= 10^4)，最初每个数字是<=200的正数且满足如下条件：
 * 1. 0位置的要求：arr[0]<=arr[1]
 * 2. n-1位置的要求：arr[n-1]<=arr[n-2]
 * 3. 中间i位置的要求：arr[i]<=max(arr[i-1],arr[i+1])
 * 但是在arr有些数字丢失了，比如k位置的数字之前是正数，丢失之后k位置的数字为0
 * 请你根据上述条件，计算可能有多少种不同的arr可以满足以上条件
 * 比如 [6,0,9] 只有还原成 [6,9,9]满足全部三个条件，所以返回1，即[6,9,9]达标
 */
public class RestoreWays {
    public static int ways0(int[] arr) {
        return process0(arr, 0);
    }

    private static int process0(int[] arr, int index) {
        if (index == arr.length) {
            return isValid(arr) ? 1 : 0;
        }
        if (arr[index] != 0) {
            return process0(arr, index + 1);
        } else {
            int way = 0;
            for (int i = 1; i < 201; i++) {
                arr[index] = i;
                way += process0(arr, index + 1);
            }
            arr[index] = 0;
            return way;
        }
    }

    private static boolean isValid(int[] arr) {
        if (arr.length <= 1) {
            return true;
        }
        if (arr[0] > arr[1]) {
            return false;
        }
        if (arr[arr.length - 1] > arr[arr.length - 2]) {
            return false;
        }
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] > Math.max(arr[i - 1], arr[i + 1])) {
                return false;
            }
        }
        return true;
    }

    public static int ways1(int[] arr) {
        int N = arr.length;
        if (arr[N - 1] != 0) {
            return process1(arr, N - 1, arr[N - 1], 2);
        } else {
            int way = 0;
            for (int i = 1; i < 201; i++) {
                way += process1(arr, N - 1, i, 2);
            }
            return way;
        }
    }
    // 如果i位置的数字变成了v,
    // 并且arr[i]和arr[i+1]的关系为s，
    // s==0，代表arr[i] < arr[i+1] 右大
    // s==1，代表arr[i] == arr[i+1] 右=当前
    // s==2，代表arr[i] > arr[i+1] 右小
    // 返回0...i范围上有多少种有效的转化方式？
    private static int process1(int[] arr, int index, int v, int s) {
        if (index == 0) {
            return (s == 0 || s == 1) && (arr[0] == 0 || arr[0] == v) ? 1 : 0;
        }
        if (arr[index] != 0 && arr[index] != v) {
            return 0;
        }
        int way = 0;
        if (s == 0 || s == 1) {
            for (int pre = 1; pre < 201; pre++) {
                way += process1(arr, index - 1, pre, pre < v ? 0 : (pre == v ? 1 : 2));
            }
        } else {
            for (int pre = v; pre < 201; pre++) {
                way += process1(arr, index - 1, pre, pre == v ? 1 : 2);
            }
        }
        return way;
    }

    private static int processBetter(int[] arr, int index, int v, int s) {
        if (index == 0) {
            return (s == 0 || s == 1) && (arr[0] == 1 || arr[0] == v) ? 1 : 0;
        }
        if (arr[index] != 0 && arr[index] != v) {
            return 0;
        }
        int way = 0;
        if (s == 0 || s == 1) {
            for (int pre = 1; pre < v; pre++) {
                way += processBetter(arr, index - 1, pre, 0);
            }
        }
        way += process1(arr, index - 1, v, 1);
        for (int pre = v + 1; pre < 201; pre++) {
            way += processBetter(arr, index - 1, pre, 2);
        }
        return way;
    }

    public static int ways2(int[] arr) {
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        // 第一个数需要符合要求
        if (arr[0] != 0) {
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            for (int i = 1; i < 201; i++) {
                dp[0][i][0] = 1;
                dp[0][i][1] = 1;
            }
        }

        for (int i = 1; i < N; i++) {
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    if (arr[i] == 0 || arr[i] == v) {
                        int way = 0;
                        if (s == 0 || s == 1) {
                            for (int pre = 1; pre < v; pre++) {
                                way += dp[i - 1][pre][0];
                            }
                        }
                        way += dp[i - 1][v][1];
                        for (int pre = v + 1; pre < 201; pre++) {
                            way += dp[i - 1][pre][2];
                        }
                        dp[i][v][s] = way;
                    }
                }
            }
        }

        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            int way = 0;
            for (int i = 1; i < 201; i++) {
                way += dp[N - 1][i][2];
            }
            return way;
        }
    }

    public static int ways3(int[] arr) {
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        // 第一个数需要符合要求
        if (arr[0] != 0) {
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            for (int i = 1; i < 201; i++) {
                dp[0][i][0] = 1;
                dp[0][i][1] = 1;
            }
        }

        int[][] preSum = new int[201][3];
        for (int v = 1; v < 201; v++) {
            for (int s = 0; s < 3; s++) {
                preSum[v][s] = preSum[v - 1][s] + dp[0][v][s];
            }
        }

        for (int i = 1; i < N; i++) {
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    if (arr[i] == 0 || arr[i] == v) {
                        int way = 0;
                        if (s == 0 || s == 1) {
                            way += sum(1, v - 1, 0, preSum);
                        }
                        way += dp[i - 1][v][1];
                        way += sum(v + 1, 200, 2, preSum);
                        dp[i][v][s] = way;
                    }
                }
            }
            for (int v = 1; v < 201; v++) {
                for (int s = 0; s < 3; s++) {
                    preSum[v][s] = preSum[v - 1][s] + dp[i][v][s];
                }
            }
        }

        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            return sum(1, 200, 2, preSum);
        }
    }

    private static int sum(int start, int end, int relation, int[][] preSum) {
        return preSum[end][relation] - preSum[start - 1][relation];
    }


    // for test
    public static int[] generateRandomArray(int len) {
        int[] ans = new int[len];
        for (int i = 0; i < ans.length; i++) {
            if (Math.random() < 0.5) {
                ans[i] = 0;
            } else {
                ans[i] = (int) (Math.random() * 200) + 1;
            }
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.println("arr size : " + arr.length);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 4;
        int testTime = 15;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * len) + 2;
            int[] arr = generateRandomArray(N);
            int ans0 = ways0(arr);
            int ans1 = ways1(arr);
            int ans2 = ways2(arr);
            int ans3 = ways3(arr);
            if (ans0 != ans1 || ans2 != ans3 || ans0 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("功能测试结束");
        System.out.println("===========");
        int N = 100000;
        int[] arr = generateRandomArray(N);
        long begin = System.currentTimeMillis();
        ways3(arr);
        long end = System.currentTimeMillis();
        System.out.println("run time : " + (end - begin) + " ms");
    }
}
