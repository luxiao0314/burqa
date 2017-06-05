package com.github.mzule.activityrouter.compiler.demo;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/4/17 15:02
 * @Version
 */
public class SortTest {

    public void test() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put(null, "d");
        map.put(null, "e"); //键相同会覆盖上一个键相同的数据,所以不允许key有多个value

        Set<String> strings = map.keySet();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.print(entry.getKey() + ":" + entry.getValue() + " , ");
        }
        Collections.synchronizedMap(map);   //让map同步的方法
//        for (String s : strings) {
//            System.out.print(s);
//        }
    }

    //abcd倒序排序
    public void formatString(String s) {
        for (int i = s.length() - 1; i >= 0; i--) {
            System.out.print(s.charAt(i));
        }
    }

    //九九乘法表
    public void nineForm() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j + "*" + i + "=" + i * j + "  ");
            }
            System.out.println();
        }
    }

    /**
     * 方法名：bubbleSort 说明：冒泡排序 时间复杂度O(N^2)
     */
    public static <AnyType extends Comparable<? super AnyType>> void bubbleSort(
            AnyType[] a) {
        for (int i = 0; i < a.length; i++)
            for (int j = i + 1; j < a.length; j++) {
                if (a[i].compareTo(a[j]) > 0) {
                    AnyType temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
    }

    public static void main(String[] args) {
        SortTest test = new SortTest();
//        test.formatString("abcdefghi");
//        test.nineForm();
        test.test();
    }
}
