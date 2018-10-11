package com.askerlve.knowledge.algotithm;

import java.util.Arrays;

/**
 * @author Askerlve
 * @Description: 希尔排序
 * @date 2018/10/11下午5:15
 */
public class ShellSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int gap = 1;
        while (gap < arr.length) {
            gap = gap * 3 + 1;
        }

        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int tmp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                if(j != i - gap){
                    arr[j + gap] = tmp;
                }
            }
            gap = (int) Math.floor(gap / 3);
        }

        return arr;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(new ShellSort().sort(testArr)));
    }
}
