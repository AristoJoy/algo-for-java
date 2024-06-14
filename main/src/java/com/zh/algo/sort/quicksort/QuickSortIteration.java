package com.zh.algo.sort.quicksort;

import com.zh.algo.utils.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 体系班class5
 * 快排基础班、荷兰国旗版快排、随机快排的迭代版
 */
public class QuickSortIteration {
    static class Area {
        private int left;
        private int right;

        public Area(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static int[] netherlandsFlag(int[] array, int left, int right) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        if (left == right) {
            return new int[]{left, right};
        }
        int less = left - 1;
        int more = right;
        int index = left;
        while (index < more) {
            // 相等时，只移动index
            if (array[index] == array[right]) {
                index++;
            } else if (array[index] < array[right]) {
                // 小于时，将当前数与<区的下一个位置交换，index向右移动
                ArrayUtils.swap(array, ++less, index++);
            } else {
                // 大于时，将当前数与>去的前一个位置交换，但是index不移动（todo-zh 因为这个数还未比较过）
                ArrayUtils.swap(array, index, --more);
            }
        }
        // 将其与>区的第一个数交换
        ArrayUtils.swap(array, more, right);
        return new int[]{less + 1, more};
    }

    public static void quickSort1(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int len = array.length;
        ArrayUtils.swap(array, (int) (Math.random() * len), len - 1);
        int[] equalArea = netherlandsFlag(array, 0, len - 1);
        Stack<Area> stack = new Stack<>();
        stack.push(new Area(0, equalArea[0] - 1));
        stack.push(new Area(equalArea[1] + 1, len - 1));
        while (!stack.isEmpty()) {
            Area area = stack.pop();
            if (area.left < area.right) {
                ArrayUtils.swap(array, area.left + (int) (Math.random() * (area.right - area.left + 1)), area.right);
                int[] equalAreaTemp = netherlandsFlag(array, area.left, area.right);
                stack.push(new Area(area.left, equalAreaTemp[0] - 1));
                stack.push(new Area(equalAreaTemp[1] + 1, area.right));
            }
        }
    }

    public static void quickSort2(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int len = array.length;
        ArrayUtils.swap(array, (int) (Math.random() * len), len - 1);
        int[] equalArea = netherlandsFlag(array, 0, len - 1);
        Queue<Area> stack = new LinkedList<>();
        stack.offer(new Area(0, equalArea[0] - 1));
        stack.offer(new Area(equalArea[1] + 1, len - 1));
        while (!stack.isEmpty()) {
            Area area = stack.poll();
            if (area.left < area.right) {
                ArrayUtils.swap(array, area.left + (int) (Math.random() * (area.right - area.left + 1)), area.right);
                int[] equalAreaTemp = netherlandsFlag(array, area.left, area.right);
                stack.offer(new Area(area.left, equalAreaTemp[0] - 1));
                stack.offer(new Area(equalAreaTemp[1] + 1, area.right));
            }
        }
    }

    public static void sort(int[] array) {
        Arrays.sort(array);
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = ArrayUtils.generateRandomArray(maxSize, maxValue);
            int[] arr2 = ArrayUtils.copyArray(arr1);
            int[] arr3 = ArrayUtils.copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            sort(arr3);
            if (!ArrayUtils.isEqual(arr1, arr2) || !ArrayUtils.isEqual(arr1, arr3)) {
                succeed = false;
                break;
            }
        }
        System.out.println("test end");
        System.out.println("测试" + testTime + "组是否全部通过：" + (succeed ? "是" : "否"));
    }


}
