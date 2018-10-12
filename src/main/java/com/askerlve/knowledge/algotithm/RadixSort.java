package com.askerlve.knowledge.algotithm;

import java.util.Arrays;

/**
 * @author Askerlve
 * @Description: 基数排序,考虑负数的情况还可以参考： https://code.i-harness.com/zh-CN/q/e98fa9
 * 参考:https://note.youdao.com/share/?id=8ded4000cae02c543e090aace786cfde&type=note#/
 * @date 2018/10/12下午6:19
 */
public class RadixSort implements IArraySort {


    @Override
    public int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int maxDigit = getMaxDigit(arr);
        return radixSort(arr, maxDigit);
    }

    /**
     * 获取最高位数
     */
    private int getMaxDigit(int[] arr) {
        int maxValue = getMaxValue(arr);
        return getNumLenght(maxValue);
    }

    private int getMaxValue(int[] arr) {
        int maxValue = arr[0];
        for (int value : arr) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    protected int getNumLenght(long num) {
        if (num == 0) {
            return 1;
        }
        int lenght = 0;
        for (long temp = num; temp != 0; temp /= 10) {
            lenght++;
        }
        return lenght;
    }

    private int[] radixSort(int[] arr, int maxDigit) {
        int mod = 10;
        int dev = 1;

        for (int i = 0; i < maxDigit; i++, dev *= 10) {

            int[][] counter = new int[mod][0];

            for (int j = 0; j < arr.length; j++) {
                int bucket = ((arr[j] / dev) % mod );
                counter[bucket] = arrayAppend(counter[bucket], arr[j]);
            }

            int pos = 0;
            for (int[] bucket : counter) {
                for (int value : bucket) {
                    arr[pos++] = value;
                }
            }
        }

        return arr;
    }

    /**
     * 自动扩容，并保存数据
     *
     * @param arr
     * @param value
     */
    private int[] arrayAppend(int[] arr, int value) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = value;
        return arr;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(new RadixSort().sort(testArr)));
    }

}
