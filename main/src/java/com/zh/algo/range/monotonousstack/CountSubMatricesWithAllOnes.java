package com.zh.algo.range.monotonousstack;

/**
 * 体系学习班class25
 *
 * 单调栈
 *
 * 给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的子矩形数量
 *
 * i（高度h）左边比你小的位置x,高度a，右边比你小的位置y，高度b
 * 则矩阵数量 (h - max{a, b}) * (y - x - 1) * (y - x) / 2
 */
public class CountSubMatricesWithAllOnes {
    public static int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }
        int N = mat.length;
        int M = mat[0].length;
        int num = 0;
        int[] height = new int[M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
            }
            num += countFromBottom(height);
        }
        
        return num;
    }

    // 比如
    //              1
    //              1
    //              1         1
    //    1         1         1
    //    1         1         1
    //    1         1         1
    //
    //    2  ....   6   ....  9
    // 如上图，假设在6位置，1的高度为6
    // 在6位置的左边，离6位置最近、且小于高度6的位置是2，2位置的高度是3
    // 在6位置的右边，离6位置最近、且小于高度6的位置是9，9位置的高度是4
    // 此时我们求什么？
    // 1) 求在3~8范围上，必须以高度6作为高的矩形，有几个？
    // 2) 求在3~8范围上，必须以高度5作为高的矩形，有几个？
    // 也就是说，<=4的高度，一律不求
    // 那么，1) 求必须以位置6的高度6作为高的矩形，有几个？
    // 3..3  3..4  3..5  3..6  3..7  3..8
    // 4..4  4..5  4..6  4..7  4..8
    // 5..5  5..6  5..7  5..8
    // 6..6  6..7  6..8
    // 7..7  7..8
    // 8..8
    // 这么多！= 21 = (9 - 2 - 1) * (9 - 2) / 2
    // 这就是任何一个数字从栈里弹出的时候，计算矩形数量的方式

    private static int countFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int N = height.length;
        int num = 0;
        int[] stack = new int[N];
        int si = -1;
        for (int i = 0; i < N; i++) {
            while (si != -1 && height[stack[si]] >= height[i]) {
                int cur = stack[si--];
                if (height[cur] > height[i]) {
                    int leftMost = si == -1 ? -1 : stack[si];
                    int n = i - leftMost - 1;
                    int down = Math.max(leftMost == -1 ? 0 : height[leftMost], height[i]);
                    num += (height[cur] - down) * (n * (n + 1) / 2);
                }
            }
            stack[++si] = i;
        }
        while (si != -1) {
            int cur = stack[si--];
            int leftMost = si == -1 ? -1 : stack[si];
            int n = N - leftMost - 1;
            int down = leftMost == -1 ? 0 : height[leftMost];
            num += (height[cur] - down) * (n * (n + 1) / 2);

        }
        return num;
    }
}
