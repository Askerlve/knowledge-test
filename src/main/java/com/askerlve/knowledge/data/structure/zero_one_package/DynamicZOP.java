package com.askerlve.knowledge.data.structure.zero_one_package;

/**
 * @author Askerlve
 * @Description: 0-1背包的问题,使用动态规划实现
 * @date 2019/3/22上午9:45
 */
public class DynamicZOP {

    /**
     * @param weight 物品重量
     * @param n      物品个数
     * @param w      背包客承载重量
     * @return
     */
    public int knapsack(int[] weight, int n, int w) {

        boolean[][] states = new boolean[n][w + 1]; // 默认值 false

        states[0][0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        states[0][weight[0]] = true;

        for (int i = 1; i < n; ++i) { // 动态规划状态转移

            for (int j = 0; j <= w; ++j) {// 不把第 i 个物品放入背包
                if (states[i - 1][j] == true) states[i][j] = states[i - 1][j];
            }

            for (int j = 0; j <= w - weight[i]; ++j) {// 把第 i 个物品放入背包
                if (states[i - 1][j] == true) states[i][j + weight[i]] = true;
            }

        }

        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[n - 1][i] == true) return i;
        }

        return 0;

    }

    /**
     * 省内存版本
     *
     * @param items
     * @param n
     * @param w
     * @return
     */
    public int knapsack2(int[] items, int n, int w) {
        boolean[] states = new boolean[w + 1]; // 默认值 false
        states[0] = true;  // 第一行的数据要特殊处理，可以利用哨兵优化
        states[items[0]] = true;
        for (int i = 1; i < n; ++i) { // 动态规划

            /**
             * 把第 i 个物品放入背包,利用j从大到小，避免重复计算
             */
            for (int j = w - items[i]; j >= 0; --j) {
                if (states[j] == true) states[j + items[i]] = true;
            }
        }
        for (int i = w; i >= 0; --i) { // 输出结果
            if (states[i] == true) return i;
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] items = new int[] {10, 20, 30, 40, 35, 45, 55, 75};
        System.out.println(new DynamicZOP().knapsack2(items, items.length, 200));
    }


}
