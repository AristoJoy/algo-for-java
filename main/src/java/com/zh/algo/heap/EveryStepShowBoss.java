package com.zh.algo.heap;

import java.util.*;

/**
 * 体系班class7 加强堆
 *
 */
public class EveryStepShowBoss {
    static class Customer {
        private int id;
        private int buy;
        private int enterTime;

        public Customer(int id, int buy, int enterTime) {
            this.id = id;
            this.buy = buy;
            this.enterTime = enterTime;
        }
    }

    static class CandidateComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? o2.buy - o1.buy : o1.enterTime - o2.enterTime;
        }
    }

    static class DaddyComparator implements Comparator<Customer> {

        @Override
        public int compare(Customer o1, Customer o2) {
            return o1.buy != o2.buy ? o1.buy - o2.buy: o1.enterTime - o2.enterTime;
        }
    }

    static class WhosYourDaddy {
        private Map<Integer, Customer> customers;
        private HeapGreater<Customer> daddyHeap;
        private HeapGreater<Customer> candidateHeap;
        private int daddyLimit;


        public WhosYourDaddy(int daddyLimit) {
            this.daddyLimit = daddyLimit;
            customers = new HashMap<>();
            daddyHeap = new HeapGreater<>(new DaddyComparator());
            candidateHeap = new HeapGreater<>(new CandidateComparator());
        }

        public void operator(int time, int id, boolean buyOrRefund) {
            // 用户购买商品数量为0，但是发生了退货，该事件无效
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }
            // 初始化新用户
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }
            Customer customer = customers.get(id);
            // 计算用户购买商品数量
            if (buyOrRefund) {
                customer.buy++;
            } else {
                customer.buy--;
            }
            // 用户购买数为0，移除
            if (customer.buy == 0) {
                customers.remove(id);
            }

            // 新来的
            if (!daddyHeap.contains(customer) && !candidateHeap.contains(customer)) {

                // 得奖区不满，直接入得奖区
                if (daddyHeap.size() < daddyLimit) {
                    customer.enterTime = time;
                    daddyHeap.push(customer);
                } else {
                    // 进入候选区（肯定无法进入得奖区，因为当前用户的数量只有一件）
                    customer.enterTime = time;
                    candidateHeap.push(customer);
                }
            } else if (candidateHeap.contains(customer)) {
                // 候选区，如果当前购买数量为0，从候选区中移除；否则调整当前用户的位置
                if (customer.buy == 0) {
                    candidateHeap.remove(customer);
                } else {
                    candidateHeap.resign(customer);
                }
            } else {
                // 得奖区，如果当前购买数量为0，从候选区中移除；否则调整当前用户的位置
                if (customer.buy == 0) {
                    daddyHeap.remove(customer);
                } else {
                    daddyHeap.resign(customer);
                }
            }
            // 尝试从候选区移动用户到得奖区
            daddyMove(time);
        }

        private void daddyMove(int time) {
            if (candidateHeap.isEmpty()) {
                return;
            }
            if (daddyHeap.size() < daddyLimit) {
                Customer pop = candidateHeap.pop();
                pop.enterTime = time;
                daddyHeap.push(pop);
            } else if (daddyHeap.peek().buy < candidateHeap.peek().buy){
                Customer newCandi = daddyHeap.pop();
                newCandi.enterTime = time;
                Customer newDaddy = candidateHeap.pop();
                newDaddy.enterTime = time;
                daddyHeap.push(newDaddy);
                candidateHeap.push(newCandi);
            }
        }

        public List<Integer> getDaddies() {
            List<Integer> ans = new ArrayList<>();
            List<Customer> allElements = daddyHeap.getAllElements();
            for (Customer customer : allElements) {
                ans.add(customer.id);
            }
            return ans;
        }
    }

    public static List<List<Integer>>  topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        WhosYourDaddy whosYourDaddy = new WhosYourDaddy(k);
        for (int i = 0; i < arr.length; i++) {
            whosYourDaddy.operator(i, arr[i], op[i]);
            ans.add(whosYourDaddy.getDaddies());
        }
        return ans;
    }

    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Customer> daddyHeap = new ArrayList<>();
        List<Customer> candidateHeap = new ArrayList<>();
        Map<Integer, Customer> customers = new HashMap<>();
        CandidateComparator candidateComparator = new CandidateComparator();
        DaddyComparator daddyComparator = new DaddyComparator();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];

            // 没有发生：用户购买数为0并且又退货了
            // 用户之前购买数是0，此时买货事件
            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货

            if (!buyOrRefund && !customers.containsKey(id)) {
                ans.add(getCurAns(daddyHeap));
                continue;
            }

            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0, 0));
            }

            Customer customer = customers.get(id);
            if (buyOrRefund) {
                customer.buy++;
            } else {
                customer.buy--;
            }

            if (customer.buy == 0) {
                customers.remove(id);
            }

            if (!candidateHeap.contains(customer) && !daddyHeap.contains(customer)) {
                if (daddyHeap.size() < k) {
                    customer.enterTime = i;
                    daddyHeap.add(customer);
                } else {
                    customer.enterTime = i;
                    candidateHeap.add(customer);
                }
            }

            cleanZeroBuy(candidateHeap);
            cleanZeroBuy(daddyHeap);
            candidateHeap.sort(candidateComparator);
            daddyHeap.sort(daddyComparator);
            move(candidateHeap, daddyHeap, k, i);
            ans.add(getCurAns(daddyHeap));
        }


        return ans;
    }

    private static void move(List<Customer> candidateHeap, List<Customer> daddyHeap, int k, int time) {
        if (candidateHeap.isEmpty()) {
            return;
        }
        if (daddyHeap.size() < k) {
            Customer customer = candidateHeap.remove(0);
            customer.enterTime = time;
            daddyHeap.add(customer);
        } else {
            if (candidateHeap.get(0).buy > daddyHeap.get(0).buy) {
                Customer newDaddy = candidateHeap.remove(0);
                Customer oldDaddy = daddyHeap.remove(0);
                newDaddy.enterTime = time;
                oldDaddy.enterTime = time;
                daddyHeap.add(newDaddy);
                candidateHeap.add(oldDaddy);
            }
        }
    }

    private static void cleanZeroBuy(List<Customer> customers) {
        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer next = iterator.next();
            if (next.buy == 0) {
                iterator.remove();
            }
        }
    }

    private static List<Integer> getCurAns(List<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer customer : daddy) {
            ans.add(customer.id);
        }
        return ans;
    }

    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    // 为了测试
    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    // 为了测试
    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort((a, b) -> a - b);
            cur2.sort((a, b) -> a - b);
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 30;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " , " + op[j]);
                }
                topK(arr, op, k);
                compare(arr, op, k);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

//    public static void main(String[] args) {
//        int[] arr = new int[]{0, 1, 2, 2, 8, 8, 8, 6, 6, 4, 8, 7, 8, 5, 8, 2, 6};
//        boolean[] op = new boolean[]{true, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true, false};
//        int k = 1;
//        List<List<Integer>> list1 = topK(arr, op, k);
//        List<List<Integer>> list2 = compare(arr, op, k);
//        System.out.println(list1);
//        System.out.println(list2);
//    }
}
