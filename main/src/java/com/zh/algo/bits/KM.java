package com.zh.algo.bits;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 体系班class2
 * 一个数组中有一种数出现K次，其他数都出现了M次，
 * 已知M > 1，K < M，找到出现了K次的数
 * 要求额外空间复杂度O(1)，时间复杂度O(N)
 */
public class KM {

    public static int kTimes1(int[] array, int k, int m) {
        int[] help = new int[32];
        for (int d = 0; d < 32; d++) {
            for (int i = 0; i < array.length; i++) {
                help[d] += ((array[i] >> d) & 1);
            }
        }
        int ans = 0;
        for (int d = 0; d < 32; d++) {
            if (help[d] % m != 0) {
                ans += (1 << d);
            }
        }
        int real = 0;
        for (int num : array) {
            if (num == ans) {
                real++;
            }
        }
        return real == k ? ans : -1;
    }

    public static int kTimes2(int[] array, int k, int m) {
        Map<Integer, Integer> map = creatorMap();
        int[] counts = new int[32];
        int rightOne;
        for (int num : array) {
            while (num != 0) {
                rightOne = num & (-num);
                counts[map.get(rightOne)]++;
                num ^= rightOne;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (counts[i] % m != 0) {
                if (counts[i] % m == k) {
                    ans |= (1 << i);
                } else {
                    return -1;
                }
            }
        }

        // 0出现了k次
        if (ans == 0) {
            int count = 0;
            for (int num : array) {
                if (num == 0) {
                    count++;
                }
            }
            if (count != k) {
                return -1;
            }
        }
        return ans;
    }

    private static Map<Integer, Integer> creatorMap() {
        int value = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 32; i++) {
            map.put(value, i);
            value <<= 1;
        }
        return map;
    }

    public static int test(int[] array, int k, int m) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : array) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (Integer num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return -1;
    }

    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        // 真命天子出现的次数
        int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);
        // 2
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];
        int index = 0;
        for (; index < times; index++) {
            arr[index] = ktimeNum;
        }
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1)) - (int) (Math.random() * (range + 1));
    }

    public static void main(String[] args) {
        int kinds = 5;
        int range = 30;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = kTimes1(arr, k, m);
            int ans3 = kTimes2(arr, k, m);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("出错了！");
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
            }
        }
        System.out.println("测试结束");
    }
}
