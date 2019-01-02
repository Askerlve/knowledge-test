package com.askerlve.knowledge.algotithm.jike;

/**
 * @author Askerlve
 * @Description: 二分查找, 数组必须有序，且不重复
 * @date 2018/12/29下午3:06
 */
public class Bsearch {

    public static int bSearch(int[] arr, int size, int value) {

        int low = 0;
        int high = size - 1;

        while (low <= high) {

            //int mid = (low + high) / 2;
            //避免low和high比较大超过int最大值益处
            int mid = low + ((high - low) >> 1);

            if (arr[mid] == value) {
                return mid;
            } else if (arr[mid] < value) {
                //为什么不直接 low = mid/high=mid? 避免mid和high相等导致死循环
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;

    }

    // 二分查找的递归实现
    public int bsearch(int[] a, int n, int val) {
        return bsearchRecursion(a, 0, n - 1, val);
    }

    private static int bsearchRecursion(int[] a, int low, int high, int value) {
        if (low > high) return -1;

        int mid = low + ((high - low) >> 1);
        if (a[mid] == value) {
            return mid;
        } else if (a[mid] < value) {
            return bsearchRecursion(a, mid + 1, high, value);
        } else {
            return bsearchRecursion(a, low, mid - 1, value);
        }
    }

    //查找第一个值等于给定值
    public static int bSearchFristEqual(int arr[], int size, int value) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] >= value) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        if (low < size && arr[low] == value) return low;
        else return -1;

    }

    public int bSearchFristEqual2(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {

            int mid =  low + ((high - low) >> 1);

            if (a[mid] > value) {
                high = mid - 1;
            } else if (a[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == 0) || (a[mid - 1] != value)) return mid;
                else high = mid - 1;
            }

        }
        return -1;
    }

    //查找最后一个值等于给定值
    public static int bSearchLastEqual(int arr[], int size, int value) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] > value) {
                high = mid - 1;
            } else if(arr[mid] < value) {
                low = mid + 1;
            } else {
                if ((mid == size - 1) || (arr[mid + 1] != value)) return mid;
                else low = mid + 1;
            }
        }

        return -1;

    }

    //查找第一个大于等于给定值
    public static int bSearchFristGtOrEq(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid =  low + ((high - low) >> 1);
            if (a[mid] >= value) {
                if ((mid == 0) || (a[mid - 1] < value)) return mid;
                else high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    //查找最后一个小于等于给定值
    public int bSearchLastLtOrEq(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {

            int mid =  low + ((high - low) >> 1);

            if (a[mid] > value) {
                high = mid - 1;
            } else {
                if ((mid == n - 1) || (a[mid + 1] > value)) return mid;
                else low = mid + 1;
            }

        }

        return -1;
    }



}
