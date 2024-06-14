package com.zh.algo.string;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;

/**
 * 基于诱导排序的SA-IS 算法
 * <p>
 * 算法介绍链接：https://riteme.site/blog/2016-6-19/sais.html
 * <p>
 * 用于计算后缀数组
 */
public class SAIS {
    /**
     * 判断字符是否是LMS字符
     * @param type 类型数组，true为S型，false为L型
     * @param x 位置
     * @return 是否是LMS字符串
     */
    private boolean isLMSChar(boolean[] type, int x) {
        return x > 0 && !type[x - 1] && type[x];
    }

    /**
     * 判断两个LMS子串是否相同
     * @param str 字符串
     * @param x LMS子串1起始位置
     * @param y LMS子串2起始位置
     * @param type 字符串类型数组
     * @return LMS子串是否相同
     */
    private boolean equalSub(int[] str, int x, int y, boolean[] type) {
        do {
            if (str[x] != str[y]) {
                return false;
            }
            x++;
            y++;
        } while (!isLMSChar(type, x) && !isLMSChar(type, y));

        return str[x] == str[y];
    }

    private void inducedSort(int[] str, int[] sa, boolean[] type,
        int[] bucket, int[] lBucket, int[] sBucket, int n, int max) {
        for (int i = 0; i <= n; i++) {
            if (sa[i] > 0 && !type[sa[i] - 1]) {
                sa[lBucket[str[sa[i] - 1]]++] = sa[i] - 1;
            }
        }
        for (int i = 1; i <= max; i++) {
            sBucket[i] = bucket[i] - 1;
        }
        for (int i = n; i >= 0; i--) {
            if (sa[i] > 0 && type[sa[i] - 1]) {
                sa[sBucket[str[sa[i] - 1]]--] = sa[i] - 1;
            }
        }
    }

    public int[] sais(int[] str, int max) {
        int n = str.length - 1;
        boolean[] type = new boolean[n + 1];    // 后缀类型
        int[] position = new int[n + 1]; // 记录LMS子串的起始位置
        int[] name = new int[n + 1]; // 记录每个LMS子串的新名称
        int[] sa = new int[n + 1];  // 后缀数组

        int[] bucket = new int[max + 1];
        int[] lBucket = new int[max + 1];
        int[] sBucket = new int[max + 1];

        // 初始化桶
        for (int i = 0; i <= n; i++) {
            bucket[str[i]]++;
        }
        lBucket[0] = sBucket[0] = 0;
        for (int i = 1; i <= max; i++) {
            bucket[i] += bucket[i - 1];
            lBucket[i] = bucket[i - 1];
            sBucket[i] = bucket[i] - 1;
        }
        type[n] = true;
        for (int i = n - 1; i >= 0; i--) {
            if (str[i] < str[i + 1]) {
                type[i] = true;
            } else if (str[i] > str[i + 1]) {
                type[i] = false;
            } else {
                type[i] = type[i + 1];
            }
        }

        // 寻找每个LMS子串
        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            if (!type[i - 1] && type[i]) {
                position[cnt++] = i;
            }
        }

        // sa数组初始化为-1
        Arrays.fill(sa, -1);

        // 先填充*型后缀
        for (int i = 0; i < cnt; i++) {
            sa[sBucket[str[position[i]]]--] = position[i];
        }
        inducedSort(str, sa, type, bucket, lBucket, sBucket, n, max);

        // 填充每个LMS子串名称为-1
        Arrays.fill(name, -1);
        // 为每个LMS子串命名
        int lastX = -1;
        int nameCnt = 1;

        boolean flag = false;
        // todo-zh 这里舍弃了第一个字符
        for (int i = 1; i <= n; i++) {
            int x = sa[i];
            if (isLMSChar(type, x)) {
                if (lastX >= 0 && !equalSub(str, x, lastX, type)) {
                    nameCnt++;
                }
                if (lastX >= 0 && nameCnt == name[lastX]) {
                    flag = true;
                }
                name[x] = nameCnt;
                lastX = x;
            }
        }
        name[n] = 0;

        // 生成s1
        int[] s1 = new int[cnt];
        for (int i = 0, pos = 0; i <= n; i++) {
            if (name[i] >= 0) {
                s1[pos++] = name[i];
            }
        }
        int[] sa1;
        if (!flag) {
            sa1 = new int[cnt + 1];
            for (int i = 0; i < cnt; i++) {
                sa1[s1[i]] = i;
            }
        } else {
            sa1 = sais(s1, nameCnt);
        }

        // 从sa1引诱到sa
        lBucket[0] = sBucket[0] = 0;
        for (int i = 1; i <= max; i++) {
            lBucket[i] = bucket[i - 1];
            sBucket[i] = bucket[i] - 1;
        }

        Arrays.fill(sa, -1);
        for (int i = cnt - 1; i >= 0; i--) {
            sa[sBucket[str[position[sa1[i]]]]--] = position[sa1[i]];
        }
        inducedSort(str, sa, type, bucket, lBucket, sBucket, n, max);

        return sa;
    }

    // 为了测试
    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
//        int[] str = new int[]{1, 1, 2, 1, 1, 1, 1, 2, 0};
//        int[] sais1 = sais.sais(str, 2);
        int len = 100000000;
        int maxValue = 100;
        int[] array = randomArray(len, maxValue);
        int[] array1 = Arrays.copyOf(array, array.length + 1);

        long start = System.currentTimeMillis();
        DC3 dc3 = new DC3(array, maxValue);
        int[] dc3SA = dc3.sa();
        long end = System.currentTimeMillis();
        System.out.println("数据量 " + len + ", 运行时间 " + (end - start) + " ms");
        start = System.currentTimeMillis();
        SAIS sais = new SAIS();
        int[] saisSa = sais.sais(array1, maxValue);
        int[] sa = Arrays.copyOfRange(saisSa, 1, saisSa.length);
        if (!ArrayUtils.isEqual(dc3SA, sa)) {
            System.out.println("Oops!");
        }
        end = System.currentTimeMillis();
        System.out.println("数据量 " + len + ", 运行时间 " + (end - start) + " ms");
    }
}
