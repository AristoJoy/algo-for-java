package com.zh.algo.bits;

/**
 * 体系班class2
 * 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
 * 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 */
public class EvenTimesOddTimes {
    public static int evenTimes(int[] array) {
        int xor = 0;
        for (int i = 0; i < array.length; i++) {
            xor ^= array[i];
        }
        return xor;
    }

    public static int[] oddTimes(int[] array) {
        int xor = 0;
        // xor = a ^ b
        for (int i = 0; i < array.length; i++) {
            xor ^= array[i];
        }
        // 最右侧的1
        int rightBit = xor & (-xor);
        int xor2 = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & rightBit) != 0) {
                xor2 ^= xor2;
            }
        }
        // xor2是a或b，xor ^ xor2是b或a
        return new int[]{xor ^ xor2, xor2};
    }
}
