package com.zh.algo.orderedtable;

/**
 * 体系学习班class35
 * <p>
 * AVL树
 */
public class AVLTreeMap<K extends Comparable<K>, V> {

    private AVLNode<K, V> root;
    private int size;

    public AVLTreeMap() {
        this.root = null;
        this.size = 0;
    }

    private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
        AVLNode<K, V> head = cur.right;
        cur.right = head.left;
        head.left = cur;
        return reCalcHeight(cur, head);
    }

    private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
        AVLNode<K, V> head = cur.left;
        cur.left = head.right;
        head.right = cur;
        return reCalcHeight(cur, head);
    }

    private AVLNode<K, V> reCalcHeight(AVLNode<K, V> cur, AVLNode<K, V> head) {
        cur.height = Math.max(cur.left != null ? cur.left.height : 0, cur.right != null ? cur.right.height : 0) + 1;
        head.height = Math.max(head.left.height, head.right != null ? head.right.height : 0) + 1;
        return head;
    }

    private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
        if (cur == null) {
            return null;
        }
        int leftHeight = cur.left == null ? 0 : cur.left.height;
        int rightHeight = cur.right == null ? 0 : cur.right.height;
        if (Math.abs(leftHeight - rightHeight) > 1) {
            if (leftHeight > rightHeight) {
                int leftLeftHeight = cur.left != null && cur.left.left != null ? cur.left.left.height : 0;
                int leftRightHeight = cur.left != null && cur.left.right != null ? cur.left.right.height : 0;
                // LL和LR同时出现
                if (leftLeftHeight >= leftRightHeight) {
                    cur = rightRotate(cur);
                } else {
                    cur.left = leftRotate(cur.left);
                    cur = rightRotate(cur);
                }
            } else {
                int rightLeftHeight = cur.right != null && cur.right.left != null ? cur.right.left.height : 0;
                int rightRightHeight = cur.right != null && cur.right.right != null ? cur.right.right.height : 0;
                // RR和RL同时出现
                if (rightRightHeight >= rightLeftHeight) {
                    cur = leftRotate(cur);
                } else {
                    cur.right = rightRotate(cur.right);
                    cur = leftRotate(cur);
                }
            }
        }
        return cur;
    }

    private AVLNode<K, V> add(AVLNode<K, V> cur, K k, V v) {
        if (cur == null) {
            return new AVLNode<>(k, v);
        } else {
            if (k.compareTo(cur.key) < 0) {
                cur.left = add(cur.left, k, v);
            } else {
                cur.right = add(cur.right, k, v);
            }
            cur.height = Math.max(cur.left != null ? cur.left.height : 0, cur.right != null ? cur.right.height : 0) + 1;
            return maintain(cur);
        }
    }

    private AVLNode<K, V> delete(AVLNode<K, V> cur, K k) {
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
                AVLNode<K, V> des = cur.right;
                while (des.left != null) {
                    des = des.left;
                }
                cur.right = delete(cur.right, des.key);
                // 把des替换cur上的值
                des.left = cur.left;
                des.right = cur.right;
                cur = des;
            }
        }
        if (cur != null) {
            cur.height = Math.max(cur.left != null ? cur.left.height : 0, cur.right != null ? cur.right.height : 0) + 1;
        }
        return maintain(cur);
    }

    public int size() {
        return size;
    }

    private AVLNode<K, V> findLastIndex(K key) {
        AVLNode<K, V> pre = root;
        AVLNode<K, V> cur = root;
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

    private AVLNode<K, V> findLastNoSmallIndex(K key) {
        AVLNode<K, V> ans = null;
        AVLNode<K, V> cur = this.root;
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

    private AVLNode<K, V> findLastNoBigIndex(K key) {
        AVLNode<K, V> ans = null;
        AVLNode<K, V> cur = this.root;
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
        AVLNode<K, V> lastIndex = findLastIndex(key);
        return lastIndex != null && key.compareTo(lastIndex.key) == 0;
    }

    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        AVLNode<K, V> lastIndex = findLastIndex(key);
        if (lastIndex != null && key.compareTo(lastIndex.key) == 0) {
            lastIndex.value = value;
        } else {
            this.size++;
            this.root = add(this.root, key, value);
        }
    }

    public void remove(K key) {
        if (key == null) {
            return;
        }
        if (containsKey(key)) {
            this.size--;
            this.root = delete(this.root, key);
        }
    }

    public V get(K key) {
        if (key == null) {
            return null;
        }
        AVLNode<K, V> lastIndex = findLastIndex(key);
        if (lastIndex != null && key.compareTo(lastIndex.key) == 0) {
            return lastIndex.value;
        }
        return null;
    }

    public K firstKey() {
        if (this.root == null) {
            return null;
        }
        AVLNode<K, V> cur = this.root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }

    public K lastKey() {
        if (this.root == null) {
            return null;
        }
        AVLNode<K, V> cur = this.root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.key;
    }

    public K floorKey(K key) {
        if (key == null) {
            return null;
        }
        AVLNode<K, V> lastNoBigIndex = findLastNoBigIndex(key);
        return lastNoBigIndex == null ? null : lastNoBigIndex.key;
    }

    public K ceilingKey(K key) {
        if (key == null) {
            return null;
        }
        AVLNode<K, V> lastNoSmallIndex = findLastNoSmallIndex(key);
        return lastNoSmallIndex == null ? null : lastNoSmallIndex.key;
    }

    static class AVLNode<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private AVLNode<K, V> left;
        private AVLNode<K, V> right;
        private int height;

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }
}
