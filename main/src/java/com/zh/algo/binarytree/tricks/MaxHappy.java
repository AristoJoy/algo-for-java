package com.zh.algo.binarytree.tricks;

import java.util.ArrayList;
import java.util.List;

/**
 * 体系学习班class13
 *
 * 派对的最大快乐值
 *  员工信息的定义如下:
 * class Employee {
 *     public int happy; // 这名员工可以带来的快乐值
 *     List<Employee> subordinates; // 这名员工有哪些直接下级
 * }
 * 公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、 没有环的多叉树
 * 树的头节点是公司唯一的老板，除老板之外的每个员工都有唯一的直接上级
 * 叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外每个员工都有一个或多个直接下级
 * 这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
 * 1.如果某个员工来了，那么这个员工的所有直接下级都不能来
 * 2.派对的整体快乐值是所有到场员工快乐值的累加
 * 3.你的目标是让派对的整体快乐值尽量大
 * 给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
 */
public class MaxHappy {

    static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }

    }

    public static int maxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process1(boss, false);
    }

    /**
     * 获取的最大快乐值
     * @param employee 当前雇员
     * @param bossCome 上级来不来
     * @return 最大快乐值
     */
    private static int process1(Employee employee, boolean bossCome) {
        if (bossCome) {
            int no = 0;
            for (Employee next : employee.nexts) {
                no += process1(next, false);
            }
            return no;
        } else {
            int yes = employee.happy;
            int no = 0;
            for (Employee next : employee.nexts) {
                yes += process1(next, true);
                no += process1(next, false);
            }
            return Math.max(yes, no);
        }

    }

    public static int maxHappy2(Employee boss) {
        if (boss == null) {
            return 0;
        }
        Info info = process2(boss);
        return Math.max(info.yes, info.no);
    }

    private static Info process2(Employee boss) {
        if (boss == null) {
            return new Info(0, 0);
        }
        int yes = boss.happy;
        int no = 0;
        for (Employee next : boss.nexts) {
            Info info = process2(next);
            yes += info.no;
            no += Math.max(info.yes, info.no);
        }
        return new Info(yes, no);
    }

    static class Info {
        int yes;
        int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    // for test
    public static Employee genarateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        genarateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    // for test
    public static void genarateNexts(Employee e, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }
        int nextsSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextsSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            e.nexts.add(next);
            genarateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


}
