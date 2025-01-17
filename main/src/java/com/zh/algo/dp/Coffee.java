package com.zh.algo.dp;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 体系学习班class20
 *
 * 动态规划class3
 *
 * 给定一个数组arr，arr[i]代表第i号咖啡机泡一杯咖啡的时间
 * 给定一个正数N，表示N个人等着咖啡机泡咖啡，每台咖啡机只能轮流泡咖啡
 * 只有一台咖啡机，一次只能洗一个杯子，时间耗费a，洗完才能洗下一杯
 * 每个咖啡杯也可以自己挥发干净，时间耗费b，咖啡杯可以并行挥发
 * 假设所有人拿到咖啡之后立刻喝干净，
 * 返回从开始等到所有咖啡机变干净的最短时间
 * 四个参数：int[] arr、int N，int a、int b
 */
public class Coffee {

    static class CoffeeMachineWork {
        /**
         * 当前时间点
         */
        private int timePoint;

        /**
         * 咖啡机泡一杯咖啡的时间
         */
        private int workTime;

        public CoffeeMachineWork(int timePoint, int workTime) {
            this.timePoint = timePoint;
            this.workTime = workTime;
        }
    }

    public static int minTime1(int[] arr, int n, int a, int b) {
        PriorityQueue<CoffeeMachineWork> priorityQueue = new PriorityQueue<>((w1, w2) -> (w1.timePoint + w1.workTime) - (w1.timePoint + w2.workTime));
        for (int workTime : arr) {
            priorityQueue.add(new CoffeeMachineWork(0, workTime));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            CoffeeMachineWork machineWork = priorityQueue.poll();
            machineWork.timePoint += machineWork.workTime;
            drinks[i] = machineWork.timePoint;
            priorityQueue.add(machineWork);
        }

        return process(drinks, a, b, 0, 0);
    }

    // drinks 所有杯子可以开始洗的时间
    // wash 单杯洗干净的时间（串行）
    // air 挥发干净的时间(并行)
    // freeTime 洗的机器什么时候可用
    // drinks[index.....]都变干净，最早的结束时间（返回）
    private static int process(int[] drinks, int wash, int air, int index, int freeTime) {
        if (index == drinks.length) {
            return 0;
        }
        // 使用机器洗
        int machineClean = Math.max(drinks[index], freeTime) + wash;
        int nextClean = process(drinks, wash, air, index + 1, machineClean);
        int washClean = Math.max(machineClean, nextClean);

        // 挥发
        int airClean = drinks[index] + air;
        int nextAirClean = process(drinks, wash, air, index + 1, freeTime);

        return Math.min(washClean, Math.max(airClean, nextAirClean));
    }

    public static int minTime2(int[] arr, int n, int a, int b) {
        PriorityQueue<CoffeeMachineWork> priorityQueue =
                new PriorityQueue<>((w1, w2) -> (w1.timePoint + w1.workTime) - (w1.timePoint + w2.workTime));
        for (int workTime : arr) {
            priorityQueue.add(new CoffeeMachineWork(0, workTime));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            CoffeeMachineWork machineWork = priorityQueue.poll();
            machineWork.timePoint += machineWork.workTime;
            drinks[i] = machineWork.timePoint;
            priorityQueue.add(machineWork);
        }

        return dp(drinks, a, b);
    }

    // drinks 所有杯子可以开始洗的时间
    // wash 单杯洗干净的时间（串行）
    // air 挥发干净的时间(并行)
    // freeTime 洗的机器什么时候可用
    // drinks[index.....]都变干净，最早的结束时间（返回）
    private static int dp(int[] drinks, int wash, int air) {
        int N = drinks.length;
        int maxFree = 0;
        for (int drink : drinks) {
            maxFree = Math.max(maxFree, drink) + wash;
        }
        int[][] dp = new int[N + 1][maxFree + 1];

        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                int machineClean = Math.max(drinks[index], free) + wash;
                if (machineClean > maxFree) {
                    continue;
                }
                int nextClean = dp[index + 1][machineClean];
                int washClean = Math.max(machineClean, nextClean);

                // 挥发
                int airClean = drinks[index] + air;
                int nextAirClean = dp[index + 1][free];
                dp[index][free] = Math.min(washClean, Math.max(airClean, nextAirClean));
            }
        }

        return dp[0][0];
    }

    // 验证的方法
    // 彻底的暴力
    // 很慢但是绝对正确
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        // times[i]，第i台咖啡机的可使用的时间,drink[kth]第kth个人泡完咖啡的时间，第kth个人，有arr.length，可以选择等待i台咖啡机泡完咖啡
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            // 这一一定要恢复现场，因为后面的人会修改time[i]，如果不改回去，就像越来越多人排在第i台咖啡机后面一样
            times[i] = pre;
        }
        return time;
    }

    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        // 这里需要传递Math.max(wash, time)，而不是wash，因为当前时间是time，而咖啡杯干净的时间是wash，必须取最大值，才是下一次的时间
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }

    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }
}
