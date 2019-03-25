package com.askerlve.knowledge.data.structure.zero_one_package;

/**
 * @author Askerlve
 * @Description: 使用回溯的思想来解决0-1背包的问题,每个物品的重量不同，价值也不相同，在重量不超过背包重量的前提下，让背包的总价值最大化.
 * 解决的主要思路是通过回溯.
 * 重点，对于每个物品来说，有装入背包与不装入背包两种选择，也就是需要考察每个有加入背包的情况与不加入的情况.
 * @date 2019/3/19上午9:40
 */
public class ZeroOnePackage {

    // 存储背包中物品总重量的最大值
    public int maxW = Integer.MIN_VALUE;

    /**
     * 假设背包可承受重量 100，物品个数 10，物品重量存储在数组 a 中，那可以这样调用函数,f(0, 0, a, 10, 100)
     *
     * @param i     表示考察到哪个物品了
     * @param cw    表示当前已经装进去的物品的重量和
     * @param items 表示每个物品的重量
     * @param n     表示物品个数
     * @param w     背包重量
     */
    public void f(int i, int cw, int[] items, int n, int w) {

        // cw==w 表示装满了 ;i==n 表示已经考察完所有的物品
        if (cw == w || i == n) {
            if (cw > maxW) maxW = cw;
            return;
        }

        //这里换成for循环也许好理解一点
        f(i + 1, cw, items, n, w);

        //已经超过可以背包承受的重量的时候，就不要再装了
        if (cw + items[i] <= w) {
            f(i + 1, cw + items[i], items, n, w);
        }

    }

}
