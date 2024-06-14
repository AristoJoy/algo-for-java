package com.zh.algo.orderedtable;

import java.util.ArrayList;

/**
 * 体系学习班class36
 * <p>
 * 有序表
 * <p>
 * 设计一个结构包含如下两个方法：
 * void add(int index, int num)：把num加入到index位置
 * int get(int index) ：取出index位置的值
 * void remove(int index) ：把index位置上的值删除
 * 要求三个方法时间复杂度O(logN)
 */
public class AddRemoveGetIndexGreat {
    static class SBTNode< V> {
        private V value;
        private SBTNode<V> left;
        private SBTNode<V> right;
        private int size;

        public SBTNode(V value) {
            this.value = value;
            this.size = 1;
        }
    }

    static class SbtList<V> {
        private SBTNode<V> root;

        public SbtList() {
            this.root = null;
        }

        private SBTNode<V> rightRotate(SBTNode<V> cur) {
            SBTNode<V> head = cur.left;
            cur.left = head.right;
            head.right = cur;
            return reCalcSize(cur, head);
        }

        private SBTNode<V> leftRotate(SBTNode<V> cur) {
            SBTNode<V> head = cur.right;
            cur.right = head.left;
            head.left = cur;
            return reCalcSize(cur, head);
        }

        private SBTNode<V> reCalcSize(SBTNode<V> cur, SBTNode<V> head) {
            head.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return head;
        }

        private SBTNode<V> maintain(SBTNode<V> cur) {
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

        private SBTNode<V> add(SBTNode<V> root, int index, SBTNode<V> cur) {
            if (root == null) {
                return cur;
            } else {
                root.size++;
                int leftAndHeadSize = (root.left != null ? root.left.size : 0) + 1;
                if (index < leftAndHeadSize) {
                    root.left = add(root.left, index, cur);
                } else {
                    root.right = add(root.right, index - leftAndHeadSize, cur);
                }
                return maintain(root);
            }
        }

        private SBTNode<V> remove(SBTNode<V> root, int index) {
            root.size--;
            int rootIndex = root.left != null ? root.left.size : 0;
            if (index != rootIndex) {
                if (index < rootIndex) {
                    root.left = remove(root.left, index);
                } else {
                    root.right = remove(root.right, index - rootIndex - 1);
                }
                return root;
            }

            if (root.left == null && root.right == null) {
                return null;
            }
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }

            // 右树最左节点的父节点
            SBTNode<V> pre = null;
            SBTNode<V> suc = root.right;
            // 途径的点的大小都有减1
            suc.size--;
            while (suc.left != null) {
                pre = suc;
                suc = suc.left;
                suc.size--;
            }

            // 删除des
            if (pre != null) {
                pre.left = suc.right;
                suc.right = root.right;
            }

            // 把des替换cur上的值
            suc.left = root.left;
            suc.size = suc.left.size + (suc.right == null ? 0 : suc.right.size) + 1;
            return suc;
        }

        private SBTNode<V> get(SBTNode<V> root, int index) {
            int leftSize = root.left != null ? root.left.size : 0;
            if (index < leftSize) {
                return get(root.left, index);
            } else if (index == leftSize) {
                return root;
            } else {
                return get(root.right, index - leftSize - 1);
            }
        }

        public void add(int index, V value) {
            SBTNode<V> cur = new SBTNode<>(value);
            if (root == null) {
                root = cur;
            } else {
                if (index <= size()) {
                    root = add(root, index, cur);
                }
            }
        }

        public V get(int index) {
            SBTNode<V> ans = get(root, index);
            return ans.value;
        }

        public void remove(int index) {
            if (index >= 0 && index < size()) {
                root = remove(root, index);
            }
        }

        public int size() {
            return this.root == null ? 0 : this.root.size;
        }
    }
    // 通过以下这个测试，
    // 可以很明显的看到LinkedList的插入、删除、get效率不如SbtList
    // LinkedList需要找到index所在的位置之后才能插入或者读取，时间复杂度O(N)
    // SbtList是平衡搜索二叉树，所以插入或者读取时间复杂度都是O(logN)
    public static void main(String[] args) {
        // 功能测试
        int test = 50000;
        int max = 1000000;
        boolean pass = true;
        ArrayList<Integer> list = new ArrayList<>();
        SbtList<Integer> sbtList = new SbtList<>();
        for (int i = 0; i < test; i++) {
            if (list.size() != sbtList.size()) {
                pass = false;
                break;
            }
            if (list.size() > 1 && Math.random() < 0.5) {
                int removeIndex = (int) (Math.random() * list.size());
                list.remove(removeIndex);
                sbtList.remove(removeIndex);
            } else {
                int randomIndex = (int) (Math.random() * (list.size() + 1));
                int randomValue = (int) (Math.random() * (max + 1));
                list.add(randomIndex, randomValue);
                sbtList.add(randomIndex, randomValue);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(sbtList.get(i))) {
                pass = false;
                break;
            }
        }
        System.out.println("功能测试是否通过 : " + pass);

        // 性能测试
        test = 500000;
        list = new ArrayList<>();
        sbtList = new SbtList<>();
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (list.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList插入总时长(毫秒) ： " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            list.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList读取总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            list.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList删除总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sbtList.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList插入总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            sbtList.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList读取总时长(毫秒) :  " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * sbtList.size());
            sbtList.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList删除总时长(毫秒) :  " + (end - start));

    }




}
