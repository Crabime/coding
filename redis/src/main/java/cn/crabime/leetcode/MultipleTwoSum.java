package cn.crabime.leetcode;

public class MultipleTwoSum {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            this.val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode nodeList1 = l1;
        ListNode nodeList2 = l2;
        ListNode firstNode = nodeList1;
        boolean first = true;
        // 判断前面一个数是否>10，否则近一
        boolean flag = false;
        // 相加后的值对10取余
        int digit = 0;
        while (nodeList1.next != null && nodeList2.next != null) {
            if (flag) {
                digit = nodeList1.val + nodeList2.val + 1;
                flag = false;
            } else {
                digit = nodeList1.val + nodeList2.val;
            }
            if (digit >= 10) {
                flag = true;
            }
            digit = digit % 10;
            // 将计算后的值放入nodeList1中
            nodeList1.val = digit;
            if (first) {
                first = false;
            }
            // 迭代
            nodeList1 = nodeList1.next;
            nodeList2 = nodeList2.next;
        }


        // 确定链表最后一个元素不被忽略
        if (flag) {
            digit = nodeList1.val + nodeList2.val + 1;
            flag = false;
        } else {
            digit = nodeList1.val + nodeList2.val;
        }
        if (digit >= 10) {
            flag = true;
        }
        digit = digit % 10;
        // 将计算后的值放入nodeList1中
        nodeList1.val = digit;



        if (nodeList1.next != null) { // l1 > l2
            nodeList1 = nodeList1.next;
            // 如果l1、l2对齐的最后一位之和大于10，这里人要对l1下一位近一
            // 如果输入为l1[9,9], l2[9]时，该逻辑会出错
            while (nodeList1.next != null) {
                if (flag) {
                    nodeList1.val = nodeList1.val + 1;
                    flag = false;
                }
                if (nodeList1.val >= 10) {
                    nodeList1.val = nodeList1.val % 10;
                    flag = true;
                }
                nodeList1 = nodeList1.next;
            }
            if (flag) {
                nodeList1.val = nodeList1.val + 1;
                if (nodeList1.val >= 10) {
                    nodeList1.val = nodeList1.val % 10;
                    nodeList1.next = new ListNode(1);
                }
            }
        } else if (nodeList2.next != null) { // l1 < l2，将l2链表追加到l1上
            nodeList2 = nodeList2.next;
            nodeList1.next = nodeList2;
            while (nodeList2.next != null) {
                if (flag) {
                    nodeList2.val = nodeList2.val + 1;
                    flag = false;
                }
                if (nodeList2.val >= 10) {
                    nodeList2.val = nodeList2.val % 10;
                    flag = true;
                }
                nodeList2 = nodeList2.next;
            }
            if (flag) {
                nodeList2.val = nodeList2.val + 1;
                // l1[1]，l2[9, 9] ==> 0-0-1
                if (nodeList2.val >= 10) {
                    nodeList2.val = nodeList2.val % 10;
                    nodeList2.next = new ListNode(1);
                }
            }
        } else { // l1 == l2，如果l1、l2两个元素均为5，则还需计算下一位，否则直接返回firstNode即可

            if (flag) {
                // 此时nodeList1下一位肯定是null，这里需要重新构建一个新的ListNode以重新构建链表
                nodeList1.next = new ListNode(0);
                nodeList1 = nodeList1.next;
                nodeList1.val = nodeList1.val + 1;
            }
        }

        return firstNode;
    }


    private void testDoubleSameValue(int val) {
        ListNode l1 = new ListNode(val);
        ListNode r1 = new ListNode(val);
        ListNode node = addTwoNumbers(l1, r1);
//        ListNode node = l1;
        while (node.next != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
        System.out.println(node.val);
    }

    private void testTwoSameLengthLinkedList() {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        l1.next = l2;
        l2.next = l3;
        ListNode r1 = new ListNode(5);
        ListNode r2 = new ListNode(6);
        ListNode r3 = new ListNode(4);
        r1.next = r2;
        r2.next = r3;

        ListNode node = addTwoNumbers(l1, r1);
//        ListNode node = l1;
        while (node.next != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
        System.out.println(node.val);
    }

    private void testFirstLargerThanSecond() {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        l1.next = l2;
        l2.next = l3;
        ListNode r1 = new ListNode(5);
        ListNode r2 = new ListNode(6);
        r1.next = r2;
        ListNode node = addTwoNumbers(l1, r1);
//        ListNode node = l1;
        while (node.next != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
        System.out.println(node.val);
    }

    private void testFirstLessThanSecond() {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(4);
        l1.next = l2;
        ListNode r1 = new ListNode(5);
        ListNode r2 = new ListNode(6);
        ListNode r3 = new ListNode(4);
        r1.next = r2;
        r2.next = r3;
        ListNode node = addTwoNumbers(l1, r1);
//        ListNode node = l1;
        while (node.next != null) {
            System.out.print(node.val + "->");
            node = node.next;
        }
        System.out.println(node.val);
    }

    public static void main(String[] args) {
        MultipleTwoSum sum = new MultipleTwoSum();
        System.out.println("======前后链表长度相等======");
        sum.testTwoSameLengthLinkedList();

        System.out.println("======前者长度小于后者======");
        sum.testFirstLargerThanSecond();

        System.out.println("======前者长度大于后者======");
        sum.testFirstLessThanSecond();

        System.out.println("======前后值均为零======");
        sum.testDoubleSameValue(0);

        System.out.println("======前后值均为五======");
        sum.testDoubleSameValue(5);
    }
}
