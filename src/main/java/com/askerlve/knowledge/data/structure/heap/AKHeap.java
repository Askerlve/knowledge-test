package com.askerlve.knowledge.data.structure.heap;

/**
 * @author Askerlve
 * @Description: 堆：堆是一颗完全二叉树
 *                  堆中每一个节点的值都必须大于等于（或小于等于其子数中的每个节点的值）
 * @date 2019/1/17上午9:27
 */
public class AKHeap {

    private int[] a; // 数组，从下标 1 开始存储数据
    private int n;  // 堆可以存储的最大数据个数
    private int count; // 堆中已经存储的数据个数

    public AKHeap(int capacity) {
        a = new int[capacity + 1];
        n = capacity;
        count = 0;
    }

    /**
     * 这里的堆底层用数组，下标为0不存储数据，从下标1开始
     * @param data
     */
    public void insert(int data) {
        if (count >= n) return; // 堆满了
        ++count;
        a[count] = data;
        int i = count;
        while (i/2 > 0 && a[i] > a[i/2]) { // 自下往上堆化
            swap(a, i, i/2); // swap() 函数作用：交换下标为 i 和 i/2 的两个元素
            i = i/2;
        }
    }

    /**
     * 排序思路：将堆顶元素与最后一个元素交换，然后将最后一个元素排除在外，将剩下的元素从新堆化，直到堆中只剩下表为1(存储数据从1开始)的元素，排序完成
     *
     *
     * @param a
     * @param n 表示数据的个数，数组 a 中的数据从下标 1 到 n 的位置
     */
    public void sort(int[] a, int n) {
        buildHeap(a, n);
        int k = n;
        while (k > 1) {
            swap(a, 1, k);
            --k;
            heapify(a, k, 1);
        }
    }


    /**
     * 删除堆顶元素
     * 思路:将最后一个节点放到堆顶，然后从新堆化
     */
    public void removeMax() {
        if (count == 0) return; // 堆中没有数据
        a[1] = a[count];
        --count;
        heapify(a, count, 1);
    }

    /**
     * 构建堆，从第一个非叶子节点开始堆化
     * @param a
     * @param n
     */
    private void buildHeap(int[] a, int n) {
        for (int i = n/2; i >= 1; --i) {
            heapify(a, n, i);
        }
    }

    private void heapify(int[] a, int n, int i) { // 自上往下堆化
        while (true) {
            int maxPos = i;
            if (i*2 <= n && a[i] < a[i*2]) maxPos = i*2;
            if (i*2+1 <= n && a[maxPos] < a[i*2+1]) maxPos = i*2+1;
            if (maxPos == i) break;
            swap(a, i, maxPos);
            i = maxPos;
        }
    }

    private void swap(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }

}

