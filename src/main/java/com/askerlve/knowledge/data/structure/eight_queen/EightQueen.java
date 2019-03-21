package com.askerlve.knowledge.data.structure.eight_queen;

/**
 * @author Askerlve
 * @Description: 8皇后问题，利用回溯算法实现。问题描述：8*8的棋盘，放置8颗棋子，每一颗所在行，列，对角线都不能有其他的棋子
 * @date 2019/3/19上午9:21
 */
public class EightQueen {

    int[] result = new int[8];// 全局或成员变量, 下标表示行, 值表示 queen 存储在哪一列

    /**
     * 调用方式：cal8queens(0);
     * @param row
     */
    public void cal8queens(int row) {

        if (row == 8) { // 8 个棋子都放置好了，打印结果
            printQueens(result);
            return; // 8 行棋子都放好了，已经没法再往下递归了，所以就 return
        }

        for (int column = 0; column < 8; ++column) { // 每一行都有 8 中放法

            if (isOk(row, column)) { // 有些放法不满足要求
                result[row] = column; // 第 row 行的棋子放到了 column 列
                cal8queens(row+1); // 考察下一行
            }

        }

    }

    /**
     * 判断 row 行 column 列放置是否合适
     * @param row
     * @param column
     * @return
     */
    private boolean isOk(int row, int column) {

        int leftup = column - 1, rightup = column + 1;

        // 逐行往上考察每一行
        for (int i = row-1; i >= 0; --i) {

            // 第 i 行的 column 列有棋子吗？
            if (result[i] == column) return false;

            // 考察左上对角线：第 i 行 leftup 列有棋子吗？
            if (leftup >= 0) {
                if (result[i] == leftup) return false;
            }

            // 考察右上对角线：第 i 行 rightup 列有棋子吗？
            if (rightup < 8) {
                if (result[i] == rightup) return false;
            }

            --leftup; ++rightup;

        }

        return true;
    }

    private void printQueens(int[] result) { // 打印出一个二维矩阵

        for (int row = 0; row < 8; ++row) {

            for (int column = 0; column < 8; ++column) {

                if (result[row] == column) System.out.print("Q ");

                else System.out.print("* ");

            }

            System.out.println();

        }

        System.out.println();

    }


    public static void main(String[] args) {
        new EightQueen().cal8queens(0);
    }

}
