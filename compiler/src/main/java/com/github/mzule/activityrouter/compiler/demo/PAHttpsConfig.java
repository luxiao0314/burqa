package com.github.mzule.activityrouter.compiler.demo;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者： shenyonghe689 on 15/12/31.
 */
public class PAHttpsConfig
{
    /**
     * 使用线程池，防止频繁调用导致内存不足
     */
    public static ExecutorService MYPOOL = Executors.newCachedThreadPool();

    /**
     * 证书文件密码
     */
    public static final String BKSFILEKEY = "paic1024";

    /**
     * get请求的url
     */
    public static final String URL = "https://test2-mhis-siapp.pingan.com" +
            ".cn:20443/mhis-siapp/open/policyQuery/indistinctlist" +
            ".do?start=0&limit=20&typeID=0&title=";

    /**
     * post请求的url地址
     */
    public static final String POSTURL = "https://test2-mhis-siapp.pingan.com" +
            ".cn:20443/mhis-siapp/security/continueAnagraph/applyAnagraph.do";

    /**
     * post请求的地址
     */
    public static final String POSTPARAM = "{\"drugDetail\":\"[{\\\"itemCoding" +
            "\\\":\\\"10808063109024840101\\\",\\\"imageUrl\\\":\\\"http:\\/\\/7xolri.com1.z0.glb" +
            ".clouddn.com\n" +
            "\\/79.png\\\",\\\"itemName\\\":\\\"拜唐苹\\\",\\\"specifications\\\":\\\"50毫克\\/30片   " +
            "\\\"," +
            "\\\"number\n" +
            "\\\":4}]\",\"diseaseType\":\"5\",\"talkContent\":\"hcuufufhxhxhxhdyx\"}";

    public static final String IMGURL = "http://h.hiphotos.baidu" +
            ".com/image/pic/item/4ec2d5628535e5dd2820232370c6a7efce1b623a.jpg";

    public static HashMap<String, String> FILEPATHMAP = new HashMap<String, String>();

    /**
     * 网络连接超时 10秒
     */
    public static final int READTIMEOUT = 5 * 1000;

    /**
     * 网络连接超时 10秒
     */
    public static final int CONNTIMEOUT = 10 * 1000;

    /**
     * 文件宽处理，建立白名单过滤
     */
    static
    {
        PAHttpsConfig.FILEPATHMAP.put("a", "a");
        PAHttpsConfig.FILEPATHMAP.put("b", "b");
        PAHttpsConfig.FILEPATHMAP.put("c", "c");
        PAHttpsConfig.FILEPATHMAP.put("d", "d");
        PAHttpsConfig.FILEPATHMAP.put("e", "e");
        PAHttpsConfig.FILEPATHMAP.put("f", "f");
        PAHttpsConfig.FILEPATHMAP.put("g", "g");
        PAHttpsConfig.FILEPATHMAP.put("h", "h");
        PAHttpsConfig.FILEPATHMAP.put("i", "i");
        PAHttpsConfig.FILEPATHMAP.put("j", "j");
        PAHttpsConfig.FILEPATHMAP.put("k", "k");
        PAHttpsConfig.FILEPATHMAP.put("l", "l");
        PAHttpsConfig.FILEPATHMAP.put("m", "m");
        PAHttpsConfig.FILEPATHMAP.put("n", "n");
        PAHttpsConfig.FILEPATHMAP.put("o", "o");
        PAHttpsConfig.FILEPATHMAP.put("p", "p");
        PAHttpsConfig.FILEPATHMAP.put("q", "q");
        PAHttpsConfig.FILEPATHMAP.put("r", "r");
        PAHttpsConfig.FILEPATHMAP.put("s", "s");
        PAHttpsConfig.FILEPATHMAP.put("t", "t");
        PAHttpsConfig.FILEPATHMAP.put("u", "u");
        PAHttpsConfig.FILEPATHMAP.put("v", "v");
        PAHttpsConfig.FILEPATHMAP.put("w", "w");
        PAHttpsConfig.FILEPATHMAP.put("x", "x");
        PAHttpsConfig.FILEPATHMAP.put("y", "y");
        PAHttpsConfig.FILEPATHMAP.put("z", "z");

        PAHttpsConfig.FILEPATHMAP.put("A", "A");
        PAHttpsConfig.FILEPATHMAP.put("B", "B");
        PAHttpsConfig.FILEPATHMAP.put("C", "C");
        PAHttpsConfig.FILEPATHMAP.put("D", "D");
        PAHttpsConfig.FILEPATHMAP.put("E", "E");
        PAHttpsConfig.FILEPATHMAP.put("F", "F");
        PAHttpsConfig.FILEPATHMAP.put("G", "G");
        PAHttpsConfig.FILEPATHMAP.put("H", "H");
        PAHttpsConfig.FILEPATHMAP.put("I", "I");
        PAHttpsConfig.FILEPATHMAP.put("J", "J");
        PAHttpsConfig.FILEPATHMAP.put("K", "K");
        PAHttpsConfig.FILEPATHMAP.put("L", "L");
        PAHttpsConfig.FILEPATHMAP.put("M", "M");
        PAHttpsConfig.FILEPATHMAP.put("N", "N");
        PAHttpsConfig.FILEPATHMAP.put("O", "O");
        PAHttpsConfig.FILEPATHMAP.put("P", "P");
        PAHttpsConfig.FILEPATHMAP.put("Q", "Q");
        PAHttpsConfig.FILEPATHMAP.put("R", "R");
        PAHttpsConfig.FILEPATHMAP.put("S", "S");
        PAHttpsConfig.FILEPATHMAP.put("T", "T");
        PAHttpsConfig.FILEPATHMAP.put("U", "U");
        PAHttpsConfig.FILEPATHMAP.put("V", "V");
        PAHttpsConfig.FILEPATHMAP.put("W", "W");
        PAHttpsConfig.FILEPATHMAP.put("X", "X");
        PAHttpsConfig.FILEPATHMAP.put("Y", "Y");
        PAHttpsConfig.FILEPATHMAP.put("Z", "Z");

        PAHttpsConfig.FILEPATHMAP.put("0", "0");
        PAHttpsConfig.FILEPATHMAP.put("1", "1");
        PAHttpsConfig.FILEPATHMAP.put("2", "2");
        PAHttpsConfig.FILEPATHMAP.put("3", "3");
        PAHttpsConfig.FILEPATHMAP.put("4", "4");
        PAHttpsConfig.FILEPATHMAP.put("5", "5");
        PAHttpsConfig.FILEPATHMAP.put("6", "6");
        PAHttpsConfig.FILEPATHMAP.put("7", "7");
        PAHttpsConfig.FILEPATHMAP.put("8", "8");
        PAHttpsConfig.FILEPATHMAP.put("9", "9");
    }

}
