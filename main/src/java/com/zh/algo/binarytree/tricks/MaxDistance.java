package com.zh.algo.binarytree.tricks;

import java.util.*;

/**
 * 体系学习班class12
 * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
 */
public class MaxDistance {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 获取任意遍历序列的二叉树列表
    // 由于没有父节点信息，所以需要构造父节点map
    // 计算任意两个点的距离，先找到最低公共祖先，然后计算两个节点经过这个祖先的距离
    public static int maxDistance1(Node head) {
        if (head == null) {
            return 0;
        }
        List<Node> preList = getPreList(head);
        Map<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < preList.size(); i++) {
            for (int j = i; j < preList.size(); j++) {
                max = Math.max(max, distance(parentMap, preList.get(i), preList.get(j)));
            }
        }

        return max;
    }

    private static int distance(Map<Node, Node> parentMap, Node node1, Node node2) {
        Node cur = node1;
        Set<Node> parentSet = new HashSet<>();
        parentSet.add(cur);
        while (parentMap.get(cur) != null) {
            parentSet.add(parentMap.get(cur));
            cur = parentMap.get(cur);
        }
        int distance = 1;
        cur = node2;
        while (!parentSet.contains(cur)) {
            distance++;
            cur = parentMap.get(cur);
        }
        Node lowestAncestor = cur;
        cur = node1;
        while (cur != lowestAncestor) {
            distance++;
            cur = parentMap.get(cur);
        }
        return distance;
    }

    private static Map<Node, Node> getParentMap(Node head) {
        Map<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fileParentMap(head, parentMap);
        return parentMap;
    }

    private static void fileParentMap(Node head, Map<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fileParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fileParentMap(head.right, parentMap);
        }
    }

    private static List<Node> getPreList(Node head) {
        List<Node> list = new ArrayList<>();
        fillPreList(head, list);
        return list;
    }

    private static void fillPreList(Node head, List<Node> list) {
        if (head == null) {
            return;
        }
        list.add(head);
        fillPreList(head.left, list);
        fillPreList(head.right, list);
    }

    public static int maxDistance2(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxDistance;
    }

    public static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info left = process(head.left);
        Info right = process(head.right);
        int maxDistance = Math.max(left.maxDistance, right.maxDistance);
        int height = Math.max(left.height, right.height) + 1;

        maxDistance = Math.max(maxDistance, left.height + right.height + 1);

        return new Info(maxDistance, height);
    }

    static class Info {
        int maxDistance;
        int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxDistance1(head) != maxDistance2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
