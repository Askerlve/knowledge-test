package com.askerlve.knowledge.data.structure.max_common_substring;

/**
 * @author Askerlve
 * @Description: 最长公共子串求两个制服穿的相似程度,
 * @date 2019/3/29上午11:21
 */
public class MaxCommonSubString {

    /**
     * 如果：a[i]==b[j]，那么：max_lcs(i, j) 就等于：max(max_lcs(i-1,j-1)+1, max_lcs(i-1, j), max_lcs(i, j-1))；
     * 如果：a[i]!=b[j]，那么：max_lcs(i, j) 就等于：max(max_lcs(i-1,j-1), max_lcs(i-1, j), max_lcs(i, j-1))；其中 max 表示求三数中的最大值。
     * @param a
     * @param n
     * @param b
     * @param m
     * @return
     */
    public int lcs(char[] a, int n, char[] b, int m) {
        int[][] maxlcs = new int[n][m];
        for (int j = 0; j < m; ++j) {// 初始化第 0 行：a[0..0] 与 b[0..j] 的 maxlcs
            if (a[0] == b[j]) maxlcs[0][j] = 1;
            else if (j != 0) maxlcs[0][j] = maxlcs[0][j-1];
            else maxlcs[0][j] = 0;
        }
        for (int i = 0; i < n; ++i) {// 初始化第 0 列：a[0..i] 与 b[0..0] 的 maxlcs
            if (a[i] == b[0]) maxlcs[i][0] = 1;
            else if (i != 0) maxlcs[i][0] = maxlcs[i-1][0];
            else maxlcs[i][0] = 0;
        }
        for (int i = 1; i < n; ++i) { // 填表
            for (int j = 1; j < m; ++j) {
                if (a[i] == b[j]) maxlcs[i][j] = max(
                        maxlcs[i-1][j], maxlcs[i][j-1], maxlcs[i-1][j-1]+1);
                else maxlcs[i][j] = max(
                        maxlcs[i-1][j], maxlcs[i][j-1], maxlcs[i-1][j-1]);
            }
        }
        return maxlcs[n-1][m-1];
    }

    private int max(int x, int y, int z) {
        int maxv = Integer.MIN_VALUE;
        if (x > maxv) maxv = x;
        if (y > maxv) maxv = y;
        if (z > maxv) maxv = z;
        return maxv;
    }


}
