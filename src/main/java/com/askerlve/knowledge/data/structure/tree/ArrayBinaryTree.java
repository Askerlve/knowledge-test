package com.askerlve.knowledge.data.structure.tree;

/**
 * @author Askerlve
 * @Description: 基于数组的二叉树
 * @date 2019/1/24上午9:45
 */
public class ArrayBinaryTree {

    private int[] array = new int[64];

    public void putRoot() {

        for (int i = 1; i < 16; i++) {
            array[i] = i;
        }
    }


    /**
     * 前序遍历
     *
     * 递推公式为
     *
     * preOrder(r) = print r -> preorder(r -> left) -> preorder(r->right)
     *
     * @param index
     */
    public void leftPrint(int index) {
        if (array[index] == 0) {
            return;
        }
        System.out.println(array[index]);
        leftPrint(2 * index);
        leftPrint(2 * index + 1);
    }


    /**
     * 中序遍历
     *
     * preorder(r) = preorder(r-> left) -> print r -> preorder(r -> right)
     *
     * @param index
     */
    public void midPrint(int index) {
        if (array[index] == 0) {
            return;
        }

        midPrint(index * 2);
        System.out.println(array[index]);
        midPrint(index * 2 + 1);
    }

    /**
     * 后序遍历
     *
     * preorder(r) = preorder(r -> left) -> preorder(r -> right) -> print r
     *
     * @param index
     */
    public void rightPrint(int index) {
        if (array[index] == 0) {
            return;
        }

        rightPrint(index * 2);
        rightPrint(index * 2 + 1);
        System.out.println(array[index]);
    }

}
