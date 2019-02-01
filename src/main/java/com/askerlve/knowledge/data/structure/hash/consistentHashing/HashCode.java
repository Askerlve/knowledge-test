package com.askerlve.knowledge.data.structure.hash.consistentHashing;

/**
 * @author Askerlve
 * @Description: 获取hash的方法
 * @date 2019/2/1上午10:35
 */
public class HashCode {

    private HashCode(){}

    /** 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别 */
    public static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) hash = Math.abs(hash);
        return hash;
    }

}
