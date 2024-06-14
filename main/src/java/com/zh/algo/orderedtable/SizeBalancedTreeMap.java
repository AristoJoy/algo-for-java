package com.zh.algo.orderedtable;

/**
 * 体系学习班class35
 * <p>
 * SizeBalanced树
 */
public class SizeBalancedTreeMap<K extends Comparable<K>, V> {

    private SBTNode<K, V> root;

    public SizeBalancedTreeMap() {
        this.root = null;
    }

    private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
        SBTNode<K, V> head = cur.right;
        cur.right = head.left;
        head.left = cur;
        return reCalcSize(cur, head);
    }

    private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
        SBTNode<K, V> head = cur.left;
        cur.left = head.right;
        head.right = cur;
        return reCalcSize(cur, head);
    }

    private SBTNode<K, V> reCalcSize(SBTNode<K, V> cur, SBTNode<K, V> head) {
        head.size = cur.size;
        cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
        return head;
    }

    private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
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

    private SBTNode<K, V> add(SBTNode<K, V> cur, K k, V v) {
        if (cur == null) {
            return new SBTNode<>(k, v);
        } else {
            cur.size++;
            if (k.compareTo(cur.key) < 0) {
                cur.left = add(cur.left, k, v);
            } else {
                cur.right = add(cur.right, k, v);
            }
            return maintain(cur);
        }
    }

    private SBTNode<K, V> delete(SBTNode<K, V> cur, K k) {
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
                SBTNode<K, V> pre = null;
                SBTNode<K, V> des = cur.right;
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

    private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
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

    private SBTNode<K, V> findLastIndex(K key) {
        SBTNode<K, V> pre = root;
        SBTNode<K, V> cur = root;
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

    private SBTNode<K, V> findLastNoSmallIndex(K key) {
        SBTNode<K, V> ans = null;
        SBTNode<K, V> cur = this.root;
        while (cur != null) {
            if (key.compareTo(cur.key) == 0) {
                ans = cur;
                break;
            } else if (key.compareTo(cur.key) < 0) {
                ans = cur;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return ans;
    }

    private SBTNode<K, V> findLastNoBigIndex(K key) {
        SBTNode<K, V> ans = null;
        SBTNode<K, V> cur = this.root;
        while (cur != null) {
            if (key.compareTo(cur.key) == 0) {
                ans = cur;
                break;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                ans = cur;
                cur = cur.right;
            }
        }
        return ans;
    }

    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        SBTNode<K, V> lastIndex = findLastIndex(key);
        return lastIndex != null && key.compareTo(lastIndex.key) == 0;
    }

    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        SBTNode<K, V> lastIndex = findLastIndex(key);
        if (lastIndex != null && key.compareTo(lastIndex.key) == 0) {
            lastIndex.value = value;
        } else {
            this.root = add(this.root, key, value);
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

    public V getIndexValue(int index) {
        if (index < 0 || index >= this.size()) {
            throw new RuntimeException("invalid parameter.");
        }
        return getIndex(root, index + 1).value;
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }
        SBTNode<K, V> lastIndex = findLastIndex(key);
        if (lastIndex != null && key.compareTo(lastIndex.key) == 0) {
            return lastIndex.value;
        }
        return null;
    }

    public K firstKey() {
        if (this.root == null) {
            return null;
        }
        SBTNode<K, V> cur = this.root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }

    public K lastKey() {
        if (this.root == null) {
            return null;
        }
        SBTNode<K, V> cur = this.root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.key;
    }

    public K floorKey(K key) {
        if (key == null) {
            return null;
        }
        SBTNode<K, V> lastNoBigIndex = findLastNoBigIndex(key);
        return lastNoBigIndex == null ? null : lastNoBigIndex.key;
    }

    public K ceilingKey(K key) {
        if (key == null) {
            return null;
        }
        SBTNode<K, V> lastNoSmallIndex = findLastNoSmallIndex(key);
        return lastNoSmallIndex == null ? null : lastNoSmallIndex.key;
    }

    static class SBTNode<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private SBTNode<K, V> left;
        private SBTNode<K, V> right;
        private int size;

        public SBTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }

    // for test
    public static void printAll(SBTNode<String, Integer> head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    // for test
    public static void printInOrder(SBTNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + "(" + head.key + "," + head.value + ")" + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    // for test
    public static String getSpace(int num) {
        String space = " ";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        SizeBalancedTreeMap<String, Integer> sbt = new SizeBalancedTreeMap<>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
        }
        printAll(sbt.root);
        System.out.println(sbt.firstKey());
        System.out.println(sbt.lastKey());
        System.out.println(sbt.floorKey("g"));
        System.out.println(sbt.ceilingKey("g"));
        System.out.println(sbt.floorKey("e"));
        System.out.println(sbt.ceilingKey("e"));
        System.out.println(sbt.floorKey(""));
        System.out.println(sbt.ceilingKey(""));
        System.out.println(sbt.floorKey("j"));
        System.out.println(sbt.ceilingKey("j"));
        sbt.remove("d");
        printAll(sbt.root);
        sbt.remove("f");
        printAll(sbt.root);

    }
}
