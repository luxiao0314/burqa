package com.github.mzule.activityrouter.compiler.test;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/4/18 23:01
 * @Version
 */
public class Sort {
    public static void main(String[] args) {
//        int[] array = {1, 20, 4, 12, 53, 7, 10};
        int[] array = new int[] {1, 3, 5, 7, 8, 9};
        System.out.println(binarySearch(array, 8));
//        bubbleSort(array);
//        for (int i : array) {
//            System.out.println(i + " ");
//        }
    }

    /**
     * 二分查找:必须是排序之后的,从小到大
     *
     * @param array 需要查找的数组
     * @param num   需要查找的值,查找不到返回-1
     */
    private static int binarySearch(int[] array, int num) {
        int low = 0;    //最小索引
        int high = array.length - 1;      //最大索引
        while (low <= high) {
            int middle = (high + low) / 2;  //获取中间值的索引
            if (num == array[middle]) {  //如果需要查找的值等于中间值
                return middle;
            } else if (num < array[middle]) {   //如果查找的值小于中间值
                high = middle - 1;    //那么就在左边的值中查找,那么最大索引就为中间索引减一
            } else {
                low = middle + 1;   //如果查找的值大于中间值,那么最小索引就为中间索引加一
            }
        }
        return -1;
    }

    private static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] < array[j + 1]) {    //如果前一位小于后一位就向前排
                    int temp = array[j];        //将小的放在临时变量上面
                    array[j] = array[j + 1];  //将大的放在前面
                    array[j + 1] = temp;    //将小的放在后一位
                }
            }
        }
    }


}
