package com.askerlve.knowledge.data.structure.bloomfilter;

/**
 * @author Askerlve
 * @Description: 位图
 * @date 2019/4/9上午9:42
 */
public class BitMap {

    /** 用来进行数据存储的结构 */
    private char[] bytes;

    /** 存储结构的大小 */
    private int nbits;

    public BitMap(int nbits) {
        this.nbits = nbits;
        this.bytes = new char[nbits / 16 + 1];
    }

    public void set(int k) {
        // 超过范围直接返回
        if (k > nbits) {
            return;
        }
        int byteIndex = k / 16;
        int bitIndex = k % 16;
        bytes[byteIndex] |= (1 << bitIndex);
    }

    public boolean get(int k) {
        if (k > nbits) {
            return false;
        }
        int byteIndex = k / 16;
        int bitIndex = k % 16;
        return (bytes[byteIndex] & (1 << bitIndex)) != 0;
    }

}
