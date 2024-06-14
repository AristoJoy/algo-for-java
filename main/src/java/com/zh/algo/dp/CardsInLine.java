package com.zh.algo.dp;

/**
 * 体系学习班class18
 *
 * 动态规划class1
 *
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌
 * 玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数
 */
public class CardsInLine {
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        int first = f1(arr, 0, arr.length - 1);
        int second = g1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }


    private static int f1(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        }
        int leftScore = arr[left] + g1(arr, left + 1, right);
        int rightScore = arr[right] + g1(arr, left, right - 1);
        return Math.max(leftScore, rightScore);
    }

    private static int g1(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int leftScore = arr[left] + f1(arr, left + 1, right);
        int rightScore = arr[right] + f1(arr, left, right - 1);
        return Math.min(leftScore, rightScore);
    }

    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        int[][] fMap = new int[arr.length][arr.length];
        int[][] gMap = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                fMap[i][j] = -1;
                gMap[i][j] = -1;
            }
        }
        int first = f2(fMap, gMap, arr, 0, arr.length - 1);
        int second = g2(fMap, gMap, arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    private static int f2(int[][] fMap, int[][] gMap, int[] arr, int left, int right) {
        if (fMap[left][right] != -1) {
            return fMap[left][right];
        }
        int ans;
        if (left == right) {
            ans = arr[left];
        } else {
            int leftScore = arr[left] + g2(fMap, gMap, arr, left + 1, right);
            int rightScore = arr[right] + g2(fMap, gMap, arr, left, right - 1);
            ans =  Math.max(leftScore, rightScore);
        }
        fMap[left][right] = ans;
        return ans;
    }

    private static int g2(int[][] fMap, int[][] gMap, int[] arr, int left, int right) {
        if (gMap[left][right] != -1) {
            return gMap[left][right];
        }
        int ans = 0;
        if (left != right) {
            int leftScore = arr[left] + f2(fMap, gMap, arr, left + 1, right);
            int rightScore = arr[right] + f2(fMap, gMap, arr, left, right - 1);
            ans =  Math.min(leftScore, rightScore);
        }
        gMap[left][right] = ans;
        return ans;
    }

    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;

        if (N == 1) {
            return arr[0];
        }
        int[][] fMap = new int[N][N];
        int[][] gMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            fMap[i][i] = arr[i];
        }
        for (int startCol = 1; startCol < N; startCol++) {
            int left = 0;
            int right = startCol;
            while (right < N) {
                fMap[left][right] = Math.max(arr[left] + gMap[left + 1][right], arr[right] + gMap[left][right - 1]);
                fMap[left][right] = Math.min(arr[left] + fMap[left + 1][right], arr[right] + fMap[left][right - 1]);
                left++;
                right++;
            }
        }

        return Math.max(fMap[0][N - 1], gMap[0][N - 1]);
    }
    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));

    }
}
