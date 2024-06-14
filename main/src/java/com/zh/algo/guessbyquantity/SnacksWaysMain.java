package com.zh.algo.guessbyquantity;

import java.util.Map;
import java.util.TreeMap;

/**
 * 体系学习班class39
 *
 * 根据数据量猜解法
 *
 * 牛牛家里一共有n袋零食, 第i袋零食体积为v[i]，背包容量为w，牛牛想知道在总体积不超过背包容量的情况下,
 * 一共有多少种零食放法，体积为0也算一种放法
 * 1 <= n <= 30, 1 <= w <= 2 * 10^9，v[I] (0 <= v[i] <= 10^9）
 *
 * 但是用的分治的方法
 * 这是牛客的测试链接：
 * https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
 */
public class SnacksWaysMain {
    public static long ways(int[] arr, int bag) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] <= bag ? 2 : 1;
        }
        int mid = (arr.length - 1) >> 1;
        TreeMap<Long, Long> left = new TreeMap<>();
        long ways = process(arr, 0, 0, mid, bag, left);
        TreeMap<Long, Long> right = new TreeMap<>();
        ways += process(arr, mid + 1, 0, arr.length - 1, bag, right);
        TreeMap<Long, Long> rPre = new TreeMap<>();
        long pre = 0;
        for (Map.Entry<Long, Long> entry : right.entrySet()) {
            pre += entry.getValue();
            rPre.put(entry.getKey(), pre);
        }
        for (Map.Entry<Long, Long> entry : left.entrySet()) {
            long lWeight = entry.getKey();
            long lWays = entry.getValue();
            Long floorKey = rPre.floorKey(bag - lWeight);
            if (floorKey != null) {
                long rWays = rPre.get(floorKey);
                ways += lWays * rWays;
            }
        }
        return ways + 1;
    }

    // arr 30
    // func(arr, 0, 14, 0, bag, map)

    // func(arr, 15, 29, 0, bag, map)

    // 从index出发，到end结束
    // 之前的选择，已经形成的累加和sum
    // 零食[index....end]自由选择，出来的所有累加和，不能超过bag，每一种累加和对应的方法数，填在map里
    // 最后不能什么货都没选
    // [3,3,3,3] bag = 6
    // 0 1 2 3
    // - - - - 0 -> （0 : 1）
    // - - - $ 3 -> （0 : 1）(3, 1)
    // - - $ - 3 -> （0 : 1）(3, 2)
    private static long process(int[] arr, int index, int end, long sum, long bag, TreeMap<Long, Long> map) {
        if (sum > bag) {
            return 0;
        }
        // 所有商品自由选择完了！
        if (index > end) {
            if (sum != 0) {
                if (!map.containsKey(sum)) {
                    map.put(sum, 1L);
                } else {
                    map.put(sum, map.get(sum) + 1);
                }
                return 1;
            } else {
                return 0;
            }
        } else {
            // sum <= bag 并且 index <= end(还有货)
            // 1) 不要当前index位置的货
            long ways = process(arr, index + 1, end, sum, bag, map);
            // 2) 要当前index位置的货
            ways += process(arr, index + 1, end, sum + arr[index], bag, map);
            return ways;
        }
    }
}
