package com.askerlve.knowledge.data.structure.tree;

/**
 * @author Askerlve
 * @Description: 二叉查找树,特点:书中任意一个节点的左子树中的每个节点的值，都要小于这个节点的值。中序遍历将会得到一个有序的数据序列,这里的实现不支持重复数据
 *               对于重复的数据怎么办？
 *               方法一:
 *                      每个节点新增一个Node链表或者支持动态扩容的数组
 *               方法二：
 *                      每个节点仍然值存储一个数据。在查找插入位置的过程中，如果碰到一个节点的值，与要插入数据的值相等，我们就将这个要插入的数据放到这个节点的右子树，
 *               也就是说吧这个新插入的数据当作大于这个节点的值来处理（右子树的值是大于等于当前节点）。当查找操作的时候，遇到遇到值相同的节点，并不停止查找，
 *               而是继续在这个节点的右子树中继续查找，直到遇到叶子节点为止。对于删除操作，找到每个要删除的节点，用查找树的删除方法依次删除即可。
 *
 * @date 2019/1/10上午9:40
 */
public class BinarySearchTree {

    private Node tree;

    public Node find(int data) {
        Node p = tree;
        while (p != null) {
            if (data < p.data) p = p.left;
            else if (data > p.data) p = p.right;
            else return p;
        }
        return null;
    }

    public void insert(int data) {
        if (tree == null) {
            tree = new Node(data);
            return;
        }

        Node p = tree;
        while (p != null) {
            if (data > p.data) {
                if (p.right == null) {
                    p.right = new Node(data);
                    return;
                }
                p = p.right;
            } else { // data < p.data
                if (p.left == null) {
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }
        }
    }

    public void delete(int data) {
        Node p = tree; // p指向要删除的节点，初始化指向根节点
        Node pp = null; // pp记录的是p的父节点
        while (p != null && p.data != data) {
            pp = p;
            if (data > p.data) p = p.right;
            else p = p.left;
        }
        if (p == null) return; // 没有找到

        // 要删除的节点有两个子节点
        if (p.left != null && p.right != null) { // 查找右子树中最小节点
            Node minP = p.right;
            Node minPP = p; // minPP表示minP的父节点
            while (minP.left != null) {
                minPP = minP;
                minP = minP.left;
            }
            p.data = minP.data; // 将minP的数据替换到p中
            p = minP; // 将p右子树中的最小节点的值替换掉p的值后，我们需要删除minP，所以将p指向minP
            pp = minPP;
        }

        // 删除节点是叶子节点或者仅有一个子节点
        Node child; // p的子节点
        if (p.left != null) child = p.left;
        else if (p.right != null) child = p.right;
        else child = null;

        if (pp == null) tree = child; // 删除的是根节点
        else if (pp.left == p) pp.left = child;
        else pp.right = child;
    }

    public Node findMin() {
        if (tree == null) return null;
        Node p = tree;
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    public Node findMax() {
        if (tree == null) return null;
        Node p = tree;
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }


    public static class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }
}
