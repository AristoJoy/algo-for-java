package com.zh.algo.orderedtable;

import java.util.HashSet;
import java.util.Set;

/**
 * 体系学习班class36
 * <p>
 * 有序表
 * <p>
 * 给定一个数组arr，和两个整数a和b（a<=b）。求arr中有多少个子数组，累加和在[a,b]这个范围上。返回达标的子数组数量
 * <p>
 * 思路：
 * 解法1，class5的归并排序
 * 解法2，有序表
 */
public class CountOfRangeSum {

    static class SBTNode {
        private long key;
        private SBTNode left;
        private SBTNode right;
        private long size;

        public long all;

        public SBTNode(long key) {
            this.key = key;
            this.size = 1;
            this.all = 1;
        }
    }

    static class SizeBalancedTreeSet {
        private SBTNode root;

        private Set<Long> set = new HashSet<>();

        public SizeBalancedTreeSet() {
            this.root = null;
        }

        private SBTNode leftRotate(SBTNode cur) {
            long same = cur.all - (cur.left != null ? cur.left.all : 0) - (cur.right != null ? cur.right.all : 0);
            SBTNode head = cur.right;
            cur.right = head.left;
            head.left = cur;
            return reCalcSize(cur, head, same);
        }

        private SBTNode rightRotate(SBTNode cur) {
            long same = cur.all - (cur.left != null ? cur.left.all : 0) - (cur.right != null ? cur.right.all : 0);
            SBTNode head = cur.left;
            cur.left = head.right;
            head.right = cur;
            return reCalcSize(cur, head, same);
        }

        private SBTNode reCalcSize(SBTNode cur, SBTNode head, long same) {
            head.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            head.all = cur.all;
            cur.all = (cur.left != null ? cur.left.all : 0) + (cur.right != null ? cur.right.all : 0) + same;
            return head;
        }

        private SBTNode maintain(SBTNode cur) {
            if (cur == null) {
                return null;
            }
            long leftSize = cur.left == null ? 0 : cur.left.size;
            long rightSize = cur.right == null ? 0 : cur.right.size;
            long leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            long leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            long rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            long rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;
            // 谁的孩子改变了，就递归调用检查调整
            if (leftLeftSize > rightSize) {
                cur = rightRotate(cur);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode add(SBTNode cur, long k, boolean contains) {
            if (cur == null) {
                return new SBTNode(k);
            } else {
                cur.all++;
                if (k == cur.key) {
                    return cur;
                } else {
                    if (!contains) {
                        cur.size++;
                    }
                    if (k< cur.key) {
                        cur.left = add(cur.left, k, contains);
                    } else {
                        cur.right = add(cur.right, k, contains);
                    }
                    return maintain(cur);
                }

            }
        }

        public void add(long sum) {
            boolean contains = set.contains(sum);
            root = add(root, sum, contains);
            set.add(sum);
        }

        public long lessKeySize(long key) {
            SBTNode cur = root;
            long ans = 0;
            while (cur != null) {
                if (key == cur.key) {
                    return ans + (cur.left != null ? cur.left.all : 0);
                } else if (key < cur.key) {
                    cur = cur.left;
                } else {
                    ans += cur.all - (cur.right != null ? cur.right.all : 0);
                    cur = cur.right;
                }
            }
            return ans;
        }
    }

    public static int countRangeSum2(int[] num, int lower, int upper) {
        // 黑盒，加入数字（前缀和），不去重，可以接受重复数字
        // < num , 有几个数？
        SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
        long sum = 0;
        int ans = 0;
        // 一个数都没有的时候，就已经有一个前缀和累加和为0，
        treeSet.add(0);
        for (int i = 0; i < num.length; i++) {
            sum += num[i];
            long a = treeSet.lessKeySize(sum - lower + 1);
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;
            treeSet.add(sum);
        }
        return ans;
    }

    public static int countRangeSum1(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i)
            sums[i + 1] = sums[i] + nums[i];
        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (end - start <= 1)
            return 0;
        int mid = (start + end) / 2;
        int count = countWhileMergeSort(sums, start, mid, lower, upper)
                + countWhileMergeSort(sums, mid, end, lower, upper);
        int j = mid, k = mid, t = mid;
        long[] cache = new long[end - start];
        for (int i = start, r = 0; i < mid; ++i, ++r) {
            while (k < end && sums[k] - sums[i] < lower)
                k++;
            while (j < end && sums[j] - sums[i] <= upper)
                j++;
            while (t < end && sums[t] < sums[i])
                cache[r++] = sums[t++];
            cache[r] = sums[i];
            count += j - k;
        }
        System.arraycopy(cache, 0, sums, start, t - start);
        return count;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * varible);
        }
        return arr;
    }

    public static void main(String[] args) {
        int len = 200;
        int varible = 50;
        for (int i = 0; i < 10000; i++) {
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            int ans1 = countRangeSum1(test, lower, upper);
            int ans2 = countRangeSum2(test, lower, upper);
            if (ans1 != ans2) {
                System.out.println("Oops");
                printArray(test);
                System.out.println(lower);
                System.out.println(upper);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }

    }
}
