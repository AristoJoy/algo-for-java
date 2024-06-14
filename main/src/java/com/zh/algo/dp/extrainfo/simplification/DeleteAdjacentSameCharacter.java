package com.zh.algo.dp.extrainfo.simplification;

/**
 * 体系学习班class45
 *
 * 动态规划外部信息简化class1
 *
 *如果一个字符相邻的位置没有相同字符，那么这个位置的字符出现不能被消掉
 * 比如:"ab"，其中a和b都不能被消掉
 * 如果一个字符相邻的位置有相同字符，就可以一起消掉
 * 比如:"abbbc"，中间一串的b是可以被消掉的，消除之后剩下"ac"
 * 某些字符如果消掉了，剩下的字符认为重新靠在一起
 * 给定一个字符串，你可以决定每一步消除的顺序，目标是请尽可能多的消掉字符，返回最少的剩余字符数量
 * 比如："aacca", 如果先消掉最左侧的"aa"，那么将剩下"cca"，然后把"cc"消掉，剩下的"a"将无法再消除，返回1
 * 但是如果先消掉中间的"cc"，那么将剩下"aaa"，最后都消掉就一个字符也不剩了，返回0，这才是最优解。
 * 再比如："baaccabb"，
 * 如果先消除最左侧的两个a，剩下"bccabb"，如果再消除最左侧的两个c，剩下"babb"，最后消除最右侧的两个b，剩下"ba"无法再消除，返回2
 * 而最优策略是：
 * 如果先消除中间的两个c，剩下"baaabb"，如果再消除中间的三个a，剩下"bbb"，最后消除三个b，不留下任何字符，返回0，这才是最优解
 */
public class DeleteAdjacentSameCharacter {
    // 暴力解
    public static int restMin1(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() < 2) {
            return s.length();
        }
        int min = s.length();
        for (int L = 0; L < s.length(); L++) {
            for (int R = L + 1; R < s.length(); R++) {
                if (canDelete(s.substring(L, R + 1))) {
                    min = Math.min(min, restMin1(s.substring(0, L) + s.substring(R + 1)));
                }
            }
        }
        return min;
    }

    private static boolean canDelete(String str) {
        char[] chs = str.toCharArray();
        for (int i = 1; i < chs.length; i++) {
            if (chs[i - 1] != chs[i]) {
                return false;
            }
        }
        return true;
    }

    // 优良尝试的暴力递归版本
    public static int restMin2(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() < 2) {
            return s.length();
        }
        char[] str = s.toCharArray();
        return process(str, 0, str.length - 1, false);
    }

    private static int process(char[] str, int L, int R, boolean has) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return has ? 0 : 1;
        }
        int index = L;
        int K = has ? 1 : 0;
        while (index <= R && str[index] == str[L]) {
            K++;
            index++;
        }
        // index来到第一个不是str[L]的位置
        int way1 = (K > 1 ? 0 : 1) + process(str, index, R, false);
        int way2 = Integer.MAX_VALUE;
        for (int split = index; split <= R; split++) {
            // 和index后面第一个相同的位置一起消
            if (str[split] == str[L] && str[split - 1] != str[L]) {
                // 只有中间位置消除为0，才能一起消除
                if (process(str, index, split - 1, false) == 0) {
                    // [L, index - 1]和[split,R]的数一起消除
                    way2 = Math.min(way2, process(str, split, R, K != 0));
                }
            }
        }

        return Math.min(way1, way2);
    }

    // // 优良尝试的动态规划版本
    public static int restMin3(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() < 2) {
            return s.length();
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][][] dp = new int[N][N][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 2; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        return dpProcess(str, 0, str.length - 1, false, dp);
    }

    private static int dpProcess(char[] str, int L, int R, boolean has, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        int K = has ? 1 : 0;
        if (dp[L][R][K] != -1) {
            return dp[L][R][K];
        }
        int ans = 0;
        if (L == R) {
            ans = has ? 0 : 1;
        } else {
            int index = L;
            int all = K;
            while (index <= R && str[index] == str[L]) {
                all++;
                index++;
            }
            // index来到第一个不是str[L]的位置
            int ways1 = (all > 1 ? 0 : 1) + dpProcess(str, index, R, false, dp);
            int ways2 = Integer.MAX_VALUE;
            for (int i = index; i <= R; i++) {
                // 和index后面第一个相同的位置一起消
                if (str[i] == str[L] && str[i - 1] != str[L]) {
                    // 只有中间位置消除为0，才能一起消除
                    if (dpProcess(str, index, i - 1, false, dp) == 0) {
                        // [L, index - 1]和[i,R]的数一起消除
                        ways2 = Math.min(ways2, dpProcess(str, i, R, all != 0, dp));
                    }
                }
            }

            ans = Math.min(ways1, ways2);
        }
        dp[L][R][K] = ans;
        return ans;
    }

    public static String randomString(int len, int variety) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * variety) + 'a');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int maxLen = 16;
        int variety = 3;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            String str = randomString(len, variety);
            int ans1 = restMin1(str);
            int ans2 = restMin2(str);
            int ans3 = restMin3(str);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(str);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }


}
