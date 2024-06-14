package com.zh.algo.binarytree;

/**
 * 体系学习班class11
 * 折纸问题
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开
 * 此时折痕是凹下去的，即折痕突起的方向指向纸条的背面
 * 如果从纸条的下边向上方连续对折2次，压出折痕后展开
 * 此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次
 * 请从上到下打印所有折痕的方向。
 * N=1时，打印: down
 * N=2时，打印: down down up
 */
public class PaperFolding {
    public static void printAllFolds(int times) {
        if (times == 0) {
            return;
        }
        process(1, times, true);
        System.out.println();
    }

    private static void process(int level, int times, boolean concave) {
        if (level > times) {
            return;
        }
        process(level + 1, times, true);
        System.out.print(concave ? "down " : "up ");
        process(level + 1, times, false);
    }

    public static void main(String[] args) {
        printAllFolds(3);
    }
}
