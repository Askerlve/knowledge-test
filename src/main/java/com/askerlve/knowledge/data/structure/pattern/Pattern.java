package com.askerlve.knowledge.data.structure.pattern;

/**
 * @author Askerlve
 * @Description: 字符正则
 * @date 2019/3/21上午9:36
 */
public class Pattern {

    private boolean matched = false;

    private char[] pattern; // 正则表达式
    private int plen; // 正则表达式长度

    public Pattern(char[] pattern, int plen) {
        this.pattern = pattern;
        this.plen = plen;
    }

    public boolean match(char[] text, int tlen) { // 文本串及长度
        matched = false;
        rmatch(0, 0, text, tlen);
        return matched;
    }

    /**
     * 进行匹配操作
     *
     * @param ti 当前匹配的字符的下标
     * @param pj 模式串的下标
     * @param text 匹配的文本
     * @param tlen 文本的长度
     */
    private void rmatch(int ti, int pj, char[] text, int tlen) {
        if (matched) return; // 如果已经匹配了，就不要继续递归了
        if (pj == plen) { // 正则表达式到结尾了
            if (ti == tlen) matched = true; // 文本串也到结尾了
            return;
        }
        if (pattern[pj] == '*') { // * 匹配任意个字符
            for (int k = 0; k <= tlen-ti; ++k) {
                rmatch(ti+k, pj+1, text, tlen);
            }
        } else if (pattern[pj] == '?') { // ? 匹配 0 个或者 1 个字符
            rmatch(ti, pj+1, text, tlen);
            rmatch(ti+1, pj+1, text, tlen);
        } else if (ti < tlen && pattern[pj] == text[ti]) { // 纯字符匹配才行
            rmatch(ti+1, pj+1, text, tlen);
        }
    }
}

