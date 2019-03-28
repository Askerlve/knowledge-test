package com.askerlve.knowledge.data.structure.zero_one_package;

/**
 * @author Askerlve
 * @Description: 对于一组不通重量、不同价值、不可分割的物品，我们选择将某些物品装入背包，在满足背包最大重量限制的前提下，背包中的物品价值最大？
 * @date 2019/3/25上午9:12
 */
public class ZeroPackageValue {

    private int maxV = Integer.MIN_VALUE; // 结果放到 maxV 中
    private int[] items = new int[]{2, 2, 4, 6, 3};  // 物品的重量
    private int[] value = {3, 4, 8, 9, 6}; // 物品的价值
    private int n = 5; // 物品个数
    private int w = 9; // 背包承受的最大重量

    /**
     * 回溯算法解决
     * @param i  第几个物品
     * @param cw 背包重量
     * @param cv 背包价值
     */
    public void f(int i, int cw, int cv) { // 调用 f(0, 0, 0)
        if (cw == w || i == n) { // cw==w 表示装满了，i==n 表示物品都考察完了
            if (cv > maxV) maxV = cv;
            return;
        }
        f(i + 1, cw, cv); // 选择不装第 i 个物品
        if (cw + items[i] <= w) {
            f(i + 1, cw + items[i], cv + value[i]); // 选择装第 i 个物品
        }
    }

}

/**
 * 动态规划解决
 */
class DynamicZeroOnePackage {

    /**
     * @param weight 物品重量
     * @param value  物品价值
     * @param n      物品个数
     * @param w      背包承受的最大重量
     * @return
     */
    public int f2(int[] weight, int[] value, int n, int w) {

        int[][] states = new int[n][w + 1];

        for (int i = 0; i < n; ++i) { // 初始化 states
            for (int j = 0; j < w + 1; ++j) {
                states[i][j] = -1;
            }
        }

        states[0][0] = 0;
        states[0][weight[0]] = value[0];

        for (int i = 1; i < n; ++i) { // 动态规划，状态转移

            for (int j = 0; j <= w; ++j) { // 不选择第 i 个物品
                if (states[i - 1][j] >= 0) states[i][j] = states[i - 1][j];
            }

            for (int j = 0; j <= w - weight[i]; ++j) { // 选择第 i 个物品
                if (states[i - 1][j] >= 0) {
                    int v = states[i - 1][j] + value[i];
                    //考虑到重量一致情况下，比较价值是否一致，若大，则替换原有重量下的价值最大
                    if (v > states[i][j + weight[i]]) {
                        states[i][j + weight[i]] = v;
                    }
                }
            }

        }

        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n - 1][j] > maxvalue) maxvalue = states[n - 1][j];
        }

        return maxvalue;

    }

}

class Double11Advince {


    /**
     * 双11期间，满足200块，优惠50元，现在有n个商品，选出几个最大程度接近满足条件
     *
     * @param items 商品价格
     * @param n     商品个数
     * @param w     满减条件
     */
    public static void double11advance(int[] items, int n, int w) {

        boolean[][] states = new boolean[n][3 * w + 1];// 超过 3 倍就没有薅羊毛的价值了
        states[0][0] = true;  // 第一行的数据要特殊处理
        states[0][items[0]] = true;

        for (int i = 1; i < n; ++i) { // 动态规划
            for (int j = 0; j <= 3 * w; ++j) {// 不购买第 i 个商品
                if (states[i - 1][j] == true) states[i][j] = states[i - 1][j];
            }
            for (int j = 0; j <= 3 * w - items[i]; ++j) {// 购买第 i 个商品
                if (states[i - 1][j] == true) states[i][j + items[i]] = true;
            }
        }

        int j;
        for (j = w; j < 3 * w + 1; ++j) {
            if (states[n - 1][j] == true) break; // 输出结果大于等于 w 的最小值
        }

        if (j == 3 * w + 1) return; // 没有可行解

        for (int i = n - 1; i >= 1; --i) { // i 表示二维数组中的行，j 表示列
            if (j - items[i] >= 0 && states[i - 1][j - items[i]] == true) {
                System.out.print(items[i] + " "); // 购买这个商品
                j = j - items[i];
            } // else 没有购买这个商品，j 不变。
        }

        if (j != 0) System.out.print(items[0]);

    }


}
