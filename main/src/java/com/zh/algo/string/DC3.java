package com.zh.algo.string;

import java.util.Arrays;

/**
 * 体系学习班class44
 *
 * DC3生成后缀数组
 *
 *
 */
public class DC3 {
    private int[] sa;
    private int[] rank;

    /**
     * lcp(sa[i],sa[i-1])，最长公共前缀
     */
    private int[] height;

    public DC3(int[] num, int max) {
        this.sa = sa(num, max);
        this.rank = rank();
        this.height = height(num);
    }

    private int[] height(int[] num) {
        int[] array = new int[num.length];
        // 逻辑概念h数组，其索引i的位置值含义是，以i位置字符开头的后缀，与其上一排名的字符最长公共前缀
        // 这里k是上一次的结果
        for (int i = 0, k = 0; i < num.length; i++) {
            if (rank[i] == 0) {
                continue;
            }

            if (k > 0) {
                k--;
            }
            // 上一名次后缀的索引
            int j = sa[rank[i] - 1];
            while (i + k < num.length && j + k < num.length && num[i + k] == num[j + k]) {
                k++;
            }
            array[rank[i]] = k;
        }
        return array;
    }

    private int[] rank() {
        int[] array = new int[sa.length];
        for (int i = 0; i < sa.length; i++) {
            array[sa[i]] = i;
        }
        return array;
    }

    private int[] sa(int[] num, int max) {
        int[] array = Arrays.copyOf(num, num.length + 3);

        return skew(array, num.length, max);
    }

    private int[] skew(int[] num, int n, int max) {
        int n0 = (n + 2) / 3;
        int n1 = (n + 1) / 3;
        int n2 = n / 3;
        int n02 = n0 + n2;
        int[] s12 = new int[n02 + 3];
        int[] sa12 = new int[n02 + 3];
        for (int i = 0, j = 0; i < n + (n0 - n1); i++) {
            if (i % 3 != 0) {
                s12[j++] = i;
            }
        }
        // s12基数排序
        radixSort(num, s12, sa12, n02, 2, max);
        radixSort(num, sa12, s12, n02, 1, max);
        radixSort(num, s12, sa12, n02, 0, max);
        int name = 0;
        // 上次的字符
        int c0 = -1;
        int c1 = -1;
        int c2 = -1;
        // 对
        for (int i = 0; i < n02; i++) {
            if (c0 != num[sa12[i]] || c1 != num[sa12[i] + 1] || c2 != num[sa12[i] + 2]) {
                name++;
                c0 = num[sa12[i]];
                c1 = num[sa12[i] + 1];
                c2 = num[sa12[i] + 2];
            }
            if (sa12[i] % 3 == 1) {
                s12[sa12[i] / 3] = name;
            } else {
                s12[sa12[i] / 3 + n0] = name;
            }
        }
        if (name < n02) {
            // 如果有重复排名，递归利用新字符串获取sa数组
            sa12 = skew(s12, n02, name);
            for (int i = 0; i < n02; i++) {
                // s12根据新sa构造（大于0)
                s12[sa12[i]] = i + 1;
            }
        } else {
            // 由s12构造sa12
            for (int i = 0; i < n02; i++) {
                sa12[s12[i] - 1] = i;
            }
        }
        int[] s0 = new int[n0];
        int[] sa0 = new int[n0];
        for (int i = 0, j = 0; i < n02; i++) {
            // 由s1在有序的sa12中索引，乘以3，即前一个字符的后缀s0的第一次基数排序结果(s0,s1中s1基数排序后的结果)
            if (sa12[i] < n0) {
                s0[j++] = 3 * sa12[i];
            }
        }
        // s0,s1中s0基数排序
        radixSort(num, s0, sa0, n0, 0, max);
        int[] sa = new int[n];
        // 注意这里t从n0 - n1开始，就是为了抛弃补0的那一个后缀
        for (int p = 0, t = n0 - n1, k = 0; k < n; k++) {
            // 注意这里是sa12里面的值是s12的索引，不是原数组索引
            int i = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
            int j = sa0[p];
            // 当小于n0时，s0后缀和s1后缀对比，将其s0,s1排名和s1，s2排名对比 sa12[t] + n0为s2索引，s12[sa12[t] + n0]为s12排名
            // j / 3为s1的组数，s12[j/3]为s1排名
            if (sa12[t] < n0 ? leq(num[i], s12[sa12[t] + n0], num[j], s12[j / 3])
                    : leq(num[i], num[i + 1], s12[sa12[t] - n0 + 1], num[j], num[j + 1], s12[j / 3 + n0])) {
                sa[k] = i;
                t++;
                if (t == n02) {
                    for (k++; p < n0; p++, k++) {
                        sa[k] = sa0[p];
                    }
                }
            } else {
                sa[k] = j;
                p++;
                if (p == n0) {
                    for (k++; t < n02; k++, t++) {
                        // 注意这里是sa12里面的值是s12的索引，不是原数组索引
                        sa[k] = sa12[t] < n0 ? sa12[t] * 3 + 1 : (sa12[t] - n0) * 3 + 2;
                    }
                }
            }
        }

        return sa;
    }

    private void radixSort(int[] num, int[] input, int[] output, int n02, int offset, int max) {
        int[] cnt = new int[max + 1];
        for (int i = 0; i < n02; i++) {
            cnt[num[input[i] + offset]]++;
        }
        for (int i = 0, sum = 0; i < cnt.length; i++) {
            int t = cnt[i];
            cnt[i] = sum;
            sum += t;
        }
        for (int i = 0; i < n02; i++) {
            output[cnt[num[input[i] + offset]]++] = input[i];
        }
    }

    private boolean leq(int a1, int a2, int b1, int b2) {
        return a1 < b1 || (a1 == b1 && a2 <= b2);
    }

    public boolean leq(int a1, int a2, int a3, int b1, int b2, int b3) {
        return a1 < b1 || (a1 == b1 && leq(a2, a3, b2, b3));
    }

    public int[] sa() {
        return sa;
    }

    public int[] getRank() {
        return rank;
    }

    public int[] getHeight() {
        return height;
    }

    // 为了测试
    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void main(String[] args) {
        int len = 100000;
        int maxValue = 100;
        long start = System.currentTimeMillis();
        new DC3(randomArray(len, maxValue), maxValue);
        long end = System.currentTimeMillis();
        System.out.println("数据量 " + len + ", 运行时间 " + (end - start) + " ms");
    }
}
