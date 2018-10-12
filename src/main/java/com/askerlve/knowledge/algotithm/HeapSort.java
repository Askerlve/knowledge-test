package com.askerlve.knowledge.algotithm;

import java.util.Arrays;

/**
 * @author Askerlve
 * @Description: 堆排序:堆排序（Heapsort）是指利用堆这种数据结构所设计的一种排序算法。
 * 堆积是一个近似完全二叉树的结构，并同时满足堆积的性质：即子结点的键值或索引总是小于（或者大于）它的父节点。
 * 堆排序可以说是一种利用堆的概念来排序的选择排序。分为两种方法：
 * 大顶堆：每个节点的值都大于或等于其子节点的值，在堆排序算法中用于升序排列；
 * 小顶堆：每个节点的值都小于或等于其子节点的值，在堆排序算法中用于降序排列；
 * 堆排序的平均时间复杂度为 Ο(nlogn)。
 * @date 2018/10/12下午2:57
 */
public class HeapSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) throws Exception {

        int[] arr = Arrays.copyOf(sourceArray,sourceArray.length);

        int len = arr.length;

        //构建大顶堆
        buildMaxHeap(arr,len);

        for (int i = len - 1; i > 0; i--) {
            swap(arr, 0 , i);
            len--;
            //重新寻找最大顶点
            heapify(arr, 0 ,len);
        }

        return arr;
    }

    private void buildMaxHeap(int[] arr, int len) {

        for (int i = (int)Math.floor(len / 2); i >= 0; i--) {
            heapify(arr, i, len);
        }

    }

    private void heapify(int[] arr, int i, int len) {

        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if(left < len && arr[left] > arr[largest]) {
            largest = left;
        }

        if(right < len && arr[right] > arr[largest]) {
            largest = right;
        }

        if(largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest, len);
        }

    }

    private void swap(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }

    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.toString(new HeapSort().sort(testArr)));
    }
}
