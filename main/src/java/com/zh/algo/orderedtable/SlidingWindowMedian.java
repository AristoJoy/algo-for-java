package com.zh.algo.orderedtable;

/**
 * 体系学习班class36
 * <p>
 * 有序表
 * <p>
 * 有一个滑动窗口：
 * 1）L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
 * 2）任何一步都可能R往右动，表示某个数进了窗口
 * 3）任何一步都可能L往右动，表示某个数出了窗口
 * 想知道每一个窗口状态的中位数
 */
public class SlidingWindowMedian {
    static class SizeBalancedTreeMap<K extends Comparable<K>> {

        private SBTNode<K> root;

        public SizeBalancedTreeMap() {
            this.root = null;
        }

        private SBTNode<K> leftRotate(SBTNode<K> cur) {
            SBTNode<K> head = cur.right;
            cur.right = head.left;
            head.left = cur;
            return reCalcSize(cur, head);
        }

        private SBTNode<K> rightRotate(SBTNode<K> cur) {
            SBTNode<K> head = cur.left;
            cur.left = head.right;
            head.right = cur;
            return reCalcSize(cur, head);
        }

        private SBTNode<K> reCalcSize(SBTNode<K> cur, SBTNode<K> head) {
            head.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return head;
        }

        private SBTNode<K> maintain(SBTNode<K> cur) {
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


        private SBTNode<K> findLastIndex(K key) {
            SBTNode<K> pre = root;
            SBTNode<K> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return pre;
        }

        private SBTNode<K> add(SBTNode<K> cur, K k) {
            if (cur == null) {
                return new SBTNode<>(k);
            } else {
                cur.size++;
                if (k.compareTo(cur.key) < 0) {
                    cur.left = add(cur.left, k);
                } else {
                    cur.right = add(cur.right, k);
                }
                return maintain(cur);
            }
        }

        private SBTNode<K> delete(SBTNode<K> cur, K k) {
            cur.size--;
            if (k.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, k);
            } else if (k.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, k);
            } else {
                if (cur.left == null && cur.right == null) {
                    cur = null;
                } else if (cur.left == null) {
                    cur = cur.right;
                } else if (cur.right == null) {
                    cur = cur.left;
                } else {
                    // 右树最左节点的父节点
                    SBTNode<K> pre = null;
                    SBTNode<K> des = cur.right;
                    // 途径的点的大小都有减1
                    des.size--;
                    while (des.left != null) {
                        pre = des;
                        des = des.left;
                        des.size--;
                    }

                    // 删除des
                    if (pre != null) {
                        pre.left = des.right;
                        des.right = cur.right;
                    }

                    // 把des替换cur上的值
                    des.left = cur.left;
                    des.size = des.left.size + (des.right == null ? 0 : des.right.size) + 1;
                    cur = des;
                }
            }
            // cur = maintain(cur); 可写可不写
            return cur;
        }

        private SBTNode<K> getIndex(SBTNode<K> cur, int kth) {
            if (kth == (cur.left != null ? cur.left.size : 0) + 1) {
                return cur;
            } else if (kth <= (cur.left != null ? cur.left.size : 0)) {
                return getIndex(cur.left, kth);
            } else {
                return getIndex(cur.right, kth - (cur.left != null ? cur.left.size : 0) - 1);
            }
        }


        public int size() {
            return this.root == null ? 0 : this.root.size;
        }


        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SBTNode<K> lastIndex = findLastIndex(key);
            return lastIndex != null && key.compareTo(lastIndex.key) == 0;
        }

        public void add(K key) {
            if (key == null) {
                return;
            }
            SBTNode<K> lastIndex = findLastIndex(key);
            if (lastIndex == null || key.compareTo(lastIndex.key) != 0) {
                this.root = add(this.root, key);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                this.root = delete(this.root, key);
            }
        }

        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            return getIndex(root, index + 1).key;
        }

    }

    static class SBTNode<K extends Comparable<K>> {
        private K key;
        private SBTNode<K> left;
        private SBTNode<K> right;
        private int size;

        public SBTNode(K key) {
            this.key = key;
            this.size = 1;
        }
    }

    static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int i, int v) {
            index = i;
            value = v;
        }

        @Override
        public int compareTo(Node o) {
            return value != o.value ? Integer.valueOf(value).compareTo(o.value)
                    : Integer.valueOf(index).compareTo(o.index);
        }
    }

    public static double[] medianSlidingWindow(int[] nums, int k) {
        SizeBalancedTreeMap<Node> treeMap = new SizeBalancedTreeMap();
        for (int i = 0; i < k - 1; i++) {
            treeMap.add(new Node(i, nums[i]));
        }

        double[] ans = new double[nums.length - k + 1];
        int index = 0;
        for (int i = k - 1; i < nums.length; i++) {
            treeMap.add(new Node(i, nums[i]));
            if (treeMap.size() % 2 == 0) {
                Node upMid = treeMap.getIndexKey(treeMap.size() / 2 - 1);
                Node mid = treeMap.getIndexKey(treeMap.size() / 2);
                ans[index++] = ((double) upMid.value + (double) mid.value) / 2;
            } else {
                ans[index++] = treeMap.getIndexKey(treeMap.size() / 2).value;
            }
            treeMap.remove(new Node(i - k + 1, nums[i - k + 1]));
        }
        return ans;
    }
}
