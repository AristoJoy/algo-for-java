package com.zh.algo.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * 体系班class8
 * 前缀树
 */
public class TrieTree {
    static class Node1 {
        private int pass;
        private int end;
        private Node1[] next;

        public Node1() {
            pass = 0;
            end = 0;
            next = new Node1[26];
        }
    }

    static class Trie1 {
        private Node1 root;

        public Trie1() {
            root = new Node1();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chars = word.toCharArray();
            Node1 node = root;
            node.pass++;
            int path = 0;
            for (char ch : chars) {
                path = ch - 'a';
                if (node.next[path] == null) {
                    node.next[path] = new Node1();
                }
                node = node.next[path];
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) == 0) {
                return;
            }
            char[] chars = word.toCharArray();
            Node1 node = root;
            node.pass--;
            int path = 0;
            for (char ch : chars) {
                path = ch - 'a';
                if (--node.next[path].pass == 0) {
                    node.next[path] = null;
                    return;
                }
                node = node.next[path];
            }
            node.end--;
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chars = word.toCharArray();
            Node1 node = root;
            int path = 0;
            for (char ch : chars) {
                path = ch - 'a';
                if (node.next[path] == null) {
                    return 0;
                }
                node = node.next[path];
            }
            return node.end;
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chars = pre.toCharArray();
            Node1 node = root;
            int path = 0;
            for (char ch : chars) {
                path = ch - 'a';
                if (node.next[path] == null) {
                    return 0;
                }
                node = node.next[path];
            }
            return node.pass;
        }
    }

    static class Node2 {
        private int pass;
        private int end;
        private Map<Integer, Node2> next;

        public Node2() {
            pass = 0;
            end = 0;
            next = new HashMap<>();
        }
    }

    static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chars = word.toCharArray();
            Node2 node = root;
            node.pass++;
            for (int path : chars) {
                if (node.next.get(path) == null) {
                    node.next.put(path, new Node2());
                }
                node = node.next.get(path);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) == 0) {
                return;
            }
            char[] chars = word.toCharArray();
            Node2 node = root;
            node.pass--;
            for (int path : chars) {
                if (--node.next.get(path).pass == 0) {
                    node.next.remove(path);
                    return;
                }
                node = node.next.get(path);
            }
            node.end--;
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chars = word.toCharArray();
            Node2 node = root;
            for (int path : chars) {
                if (node.next.get(path) == null) {
                    return 0;
                }
                node = node.next.get(path);
            }
            return node.end;
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chars = pre.toCharArray();
            Node2 node = root;
            for (int path : chars) {
                if (node.next.get(path) == null) {
                    return 0;
                }
                node = node.next.get(path);
            }
            return node.pass;
        }
    }

    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");

    }

}
