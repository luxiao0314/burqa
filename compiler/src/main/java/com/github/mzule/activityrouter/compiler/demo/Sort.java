package com.github.mzule.activityrouter.compiler.demo;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/4/18 21:49
 * @Version
 */
public class Sort {

    public static void main(String[] args) {
        //二分
        binarySearch();
        //冒泡
//        bubbleSort();
    }

    private static void binarySearch() {
        int[] src = new int[] {1, 3, 5, 7, 8, 9};
        System.out.println(binarySearch(src, 9));
    }

    private static void bubbleSort() {
        Integer[] list = {49, 38, 65, 97, 76, 13, 27, 14, 10};
        bubble(list);
        for (Integer aList : list) {
            System.out.println(aList + " ");
        }
    }

    public static void bubble(Integer[] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length - 1 - i; j++) {
                if (data[j] > data[j + 1]) {   //如果后一个数小于前一个数交换
                    int tmp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = tmp;
                }
            }
        }
    }

    /**
     * 二分查找算法
     *
     * @param srcArray 有序数组
     * @param des      查找元素
     * @return des的数组下标，没找到返回-1
     */
    public static int binarySearch(int[] srcArray, int des) {
        int low = 0;
        int high = srcArray.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (des == srcArray[middle]) {
                return middle;
            } else if (des < srcArray[middle]) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return -1;
    }
}
