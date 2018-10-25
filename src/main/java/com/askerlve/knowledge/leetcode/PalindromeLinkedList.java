package com.askerlve.knowledge.leetcode;

/**
 * @author Askerlve
 * @Description: 给定一个单项链表，判断是否为回文链表。思路: 使用快慢两个指针找到链表中间节点，慢指针每次前进一步，快指针每次前进两步。在慢指针前进的过程中，同时修改其 next 指针，使得链表前半部分反序。
 *              最后比较中点两侧的链表是否相等。
 * @date 2018/10/18上午11:52
 */
public class PalindromeLinkedList {

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            ListNode next = slow.next;

            //后半段链表反转
            slow.next = prev;
            prev = slow;
            slow = next;
        }

        if (fast != null) {
            slow = slow.next;
        }

        while (slow != null) {
            if (slow.val != prev.val) {
                return false;
            }
            slow = slow.next;
            prev = prev.next;
        }

        return true;
    }

    public static void main(String[] args) {
        ListNode lastNode = new ListNode(0,null);
        ListNode forthNode = new ListNode(1,lastNode);
        ListNode thirdNode = new ListNode(1,forthNode);
        ListNode secondNode = new ListNode(1,thirdNode);
        ListNode fristNode = new ListNode(0,secondNode);
        System.out.println(isPalindrome(fristNode));
    }

}

class ListNode {

    public int val;

    public ListNode next;

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
