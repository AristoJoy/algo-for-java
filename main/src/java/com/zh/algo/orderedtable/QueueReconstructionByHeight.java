package com.zh.algo.orderedtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 体系学习班class36
 * <p>
 * 有序表
 *
 * 假设有打乱顺序的一群人站成一个队列，数组people表示队列中一些人的属性（不一定按顺序）
 * 每个people[i]=[hi, ki]表示第i个人的身高为hi，前面正好有ki个身高大于或等于hi的人
 * 请你重新构造并返回输入数组people所表示的队列，返回的队列应该格式化为数组queue
 * 其中queue[j]=[hj, kj]是队列中第j个人的属性（queue[0] 是排在队列前面的人）。
 * Leetcode题目：https://leetcode.com/problems/queue-reconstruction-by-height/
 */
public class QueueReconstructionByHeight {
    public static int[][] reconstructQueue1(int[][] people) {
        int N = people.length;
        Unit[] units = new Unit[N];
        for (int i = 0; i < N; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, (u1, u2) -> u1.h != u2.h ? u2.h - u1.h : u1.k - u2.k);
        ArrayList<Unit> list = new ArrayList<>();
        for (Unit unit : units) {
            list.add(unit.k, unit);
        }
        int[][] ans = new int[N][2];
        int index = 0;
        for (Unit unit : list) {
            ans[index][0] = unit.h;
            ans[index++][1] = unit.k;
        }
        return ans;
    }

    public static int[][] reconstructQueue2(int[][] people) {
        int N = people.length;
        Unit[] units = new Unit[N];
        for (int i = 0; i < N; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, (u1, u2) -> u1.h != u2.h ? u2.h - u1.h : u1.k - u2.k);
        SBTree tree = new SBTree();
        // 此时value存放的是原数组的索引
        for (int i = 0; i < N; i++) {
            tree.insert(units[i].k, i);
        }
        int[][] ans = new int[N][2];
        int index = 0;
        List<Integer> list = tree.allIndexes();
        for (Integer arrIndex : list) {
            ans[index][0] = units[arrIndex].h;
            ans[index++][1] = units[arrIndex].k;
        }
        return ans;
    }

    static class Unit {
        private int h;
        private int k;

        public Unit(int height, int greater) {
            this.h = height;
            this.k = greater;
        }
    }

    static class SBTNode {
        private int value;
        private SBTNode left;
        private SBTNode right;
        private int size;

        public SBTNode(int  value) {
            this.value = value;
            this.size = 1;
        }
    }

    static class SBTree {
        private SBTNode root;

        public SBTree() {
            this.root = null;
        }

        private SBTNode leftRotate(SBTNode cur) {
            SBTNode head = cur.right;
            cur.right = head.left;
            head.left = cur;
            return reCalcSize(cur, head);
        }

        private SBTNode rightRotate(SBTNode cur) {
            SBTNode head = cur.left;
            cur.left = head.right;
            head.right = cur;
            return reCalcSize(cur, head);
        }

        private SBTNode reCalcSize(SBTNode cur, SBTNode head) {
            head.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return head;
        }

        private SBTNode maintain(SBTNode cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.left == null ? 0 : cur.left.size;
            int rightSize = cur.right == null ? 0 : cur.right.size;
            int leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            int leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            int rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            int rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;
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

        private SBTNode insert(SBTNode root, int index, SBTNode cur) {
            if (root == null) {
                return cur;
            }
            root.size++;
            int leftAndHeadSize = (root.left != null ? root.left.size : 0) + 1;
            if (index < leftAndHeadSize) {
                root.left = insert(root.left, index, cur);
            } else {
                root.right = insert(root.right, index - leftAndHeadSize, cur);
            }
            return maintain(root);
        }

        public void insert(int index, int value) {
            SBTNode cur = new SBTNode(value);
            if (root == null) {
                root = cur;
            } else {
                if (index <= root.size) {
                    root = insert(root, index, cur);
                }
            }
        }

        private SBTNode get(SBTNode root, int index) {
            int leftSize = root.left != null ? root.left.size : 0;
            if (index < leftSize) {
                return get(root.left, index);
            } else if (index == leftSize) {
                return root;
            } else {
                return get(root.right, index - leftSize - 1);
            }
        }

        public int get(int index) {
            SBTNode sbtNode = get(root, index);
            return sbtNode.value;
        }

        private void process(SBTNode head, List<Integer> indexes) {
            if (head == null) {
                return;
            }
            process(head.left, indexes);
            indexes.add(head.value);
            process(head.right, indexes);
        }

        public List<Integer> allIndexes() {
            List<Integer> list = new LinkedList<>();
            process(root, list);
            return list;
        }

        public int size() {
            return this.root == null ? 0 : this.root.size;
        }
    }

    public static void main(String[] args) {
        int[][] people = new int[][] {{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};
        int[][] ints = reconstructQueue2(people);
        for (int[] anInt : ints) {
            System.out.println(anInt[0] + " " + anInt[1]);
        }
    }
}
