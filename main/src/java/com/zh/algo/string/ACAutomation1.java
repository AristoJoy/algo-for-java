package com.zh.algo.string;

import java.util.*;

/**
 * 体系学习班class32
 *
 * AC自动机
 *
 * 解决在一个大字符串中，找到多个候选字符串的问题
 * 1）把所有匹配串生成一棵前缀树
 * 2）前缀树节点增加fail指针
 * 3）fail指针的含义：如果必须以当前字符结尾，当前形成的路径是str，剩下哪一个字符串的前缀和str的后缀
 *    拥有最大的匹配长度。fail指针就指向那个字符串的最后一个字符所对应的节点（迷不迷？听讲述！）
 */
public class ACAutomation1 {
    static class Node {
        private String end;
        private boolean endUse;

        /**
         * 匹配当前字符失败后，以这个字符为结尾和哪一个必须从开头开始的字符串相等前缀最长
         */
        private Node fail;
        private Node[] next;

        public Node() {
            endUse = false;
            end = null;
            fail = null;
            next = new Node[26];
        }
    }

    static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        public void insert(String s) {
            if (s == null || s.length() == 0) {
                return;
            }
            char[] str = s.toCharArray();
            Node cur = root;
            int ch;
            for (int i = 0; i < str.length; i++) {
                ch = str[i] - 'a';
                if (cur.next[ch] == null) {
                    cur.next[ch] = new Node();
                }
                cur = cur.next[ch];
            }
            cur.end = s;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node cFail = null;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.next[i] == null) {
                        continue;
                    }
                    // cur -> 父亲  i号儿子，必须把i号儿子的fail指针设置好！
                    cur.next[i].fail = root;
                    cFail = cur.fail;
                    while (cFail != null) {
                        if (cFail.next[i] != null) {
                            cur.next[i].fail = cFail.next[i];
                            break;
                        }
                        cFail = cFail.fail;
                    }
                    queue.add(cur.next[i]);
                }
            }
        }

        public List<String> containWords(String content) {
            if (content == null || content.length() == 0) {
                return Collections.emptyList();
            }
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index;
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                // 如果当前字符在这条路上没配出来，就随着fail方向走向下条路径
                while (cur.next[index] == null && cur != root) {
                    cur = cur.fail;
                }
                // 1) 现在来到的路径，是可以继续匹配的
                // 2) 现在来到的节点，就是前缀树的根节点
                cur = cur.next[index] != null ? cur.next[index] : root;
                follow = cur;
                // 每来到一个节点，就顺着fail指针收集一次答案
                while (follow != root) {
                    if (follow.endUse) {
                        break;
                    }
                    // 不同的需求，在这一段之间修改
                    if (follow.end != null) {
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    // 不同的需求，在这一段之间修改
                    follow = follow.fail;
                }
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }
    }
}
