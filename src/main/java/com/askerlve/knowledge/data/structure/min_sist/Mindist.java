package com.askerlve.knowledge.data.structure.min_sist;

/**
 * @author Askerlve
 * @Description: 一个4*4的二维数组，只能往下或者往右走，从(0,0)-(n-1,n-1)的最小距离
 * @date 2019/3/28上午8:46
 */
public class Mindist {

    private int[][] w = {{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
    private int n = 4;
    private int[][] mem = new int[4][4];
    private int minDist = Integer.MAX_VALUE;


    /**
     * 通过回溯算法解决， 调用方式：minDistBT(0, 0, 0, w, n)
     *
     * @param i    开始位置 i
     * @param j    开始位置 j
     * @param dist 最小距离
     * @param w    数组
     * @param n    数组长度
     * @return
     */
    public void minDistBT(int i, int j, int dist, int[][] w, int n) {

        dist = dist + w[i][j];

        // 到达了 n-1, n-1
        if (i == n - 1 && j == n - 1) {
            if (dist < minDist) minDist = dist;
            return;
        }

        if (i < n - 1) { // 往下走，更新 i=i+1, j=j
            minDistBT(i + 1, j, dist, w, n);
        }

        if (j < n - 1) { // 往右走，更新 i=i, j=j+1
             minDistBT(i, j + 1, dist, w, n);
        }

    }

    /**
     * 使用动态规划状态转移表法解决
     *
     * @param matrix
     * @param n
     * @return
     */
    public int minDistDP(int[][] matrix, int n) {

        int[][] states = new int[n][n];
        int sum = 0;
        for (int j = 0; j < n; ++j) { // 初始化 states 的第一行数据
            sum += matrix[0][j];
            states[0][j] = sum;
        }

        sum = 0;
        for (int i = 0; i < n; ++i) { // 初始化 states 的第一列数据
            sum += matrix[i][0];
            states[i][0] = sum;
        }

        for (int i = 1; i < n; ++i) {
            for (int j = 1; j < n; ++j) {
                states[i][j] =
                        matrix[i][j] + Math.min(states[i][j - 1], states[i - 1][j]);
            }
        }

        return states[n - 1][n - 1];

    }

    /**
     * 动态规划状态转以方程式
     *
     * @param i      开始位置 i
     * @param j      开始位置 j
     * @return
     */
    public int minDist(int i, int j) {

        if (i == 0 && j == 0) return w[0][0];

        if (mem[i][j] > 0) return mem[i][j];

        int minLeft = Integer.MAX_VALUE;

        if (j - 1 >= 0) {
            minLeft = minDist(i, j - 1);
        }

        int minUp = Integer.MAX_VALUE;
        if (i - 1 >= 0) {
            minUp = minDist(i - 1, j);
        }

        int currMinDist = w[i][j] + Math.min(minLeft, minUp);
        mem[i][j] = currMinDist;

        return currMinDist;
    }

    public static void main(String[] args) {

        Mindist mindist = new Mindist();
        mindist.minDistBT(0, 0, 0, mindist.w, mindist.n);
        System.out.println(mindist.minDist);
        System.out.println(mindist.minDistDP(mindist.w, mindist.n));
        System.out.println(mindist.minDist(mindist.n - 1, mindist.n - 1));

    }

}
