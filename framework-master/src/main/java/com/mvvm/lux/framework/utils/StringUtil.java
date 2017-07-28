package com.mvvm.lux.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.EditText;

import com.mvvm.lux.framework.BaseApplication;

import java.security.MessageDigest;

/**
 * Author: jin on 16/7/6 15:34
 * Email:  JINXUGUANG@pingan.com.cn
 * Version:2.4
 */
public class StringUtil {

    private static Context mContext = BaseApplication.getAppContext();
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 判断EditText 文本内容是否为空
     */
    public static boolean isEmpty(EditText editText) {
        return  isEmpty(editText.getText().toString().trim());
    }

    public static String filter(String str) {
        if (isEmpty(str)) {
            return "";
        }

        return str;
    }



    /**
     * 判断是否为电话号码
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number) {
        if (number == null) {
            return false;
        }
        String telRegex = "[1][34578]\\d{9}";
        return number.matches(telRegex);
    }

    public static Boolean checkInputWithSize(String code,int size) {

        return !isEmpty(code)&&code.length()>=size;
    }

    public static Bitmap Base64ToBitmap(String imgString) {

        byte[] bytes = Base64.decode(imgString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;

    }

    /**
     * 对象不为null
     * @param objects 校验是否为null的对象集合
     * @return 对象是否为null
     */
    public static boolean isNotNull(Object... objects) {

        for (Object o :objects){
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static String hintNumber(String number) {

        StringBuilder sb  =new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (i >= 3 && i <= 6) {
                sb.append('*');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();

    }

    /**
     * Convert
     * byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string*
     */
    public static String bytes2Hex(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hex2Bytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 's');
        }
        String s = new String(a);
        return s;

    }

    /**
     * 字符串转化数字.
     *
     * @param s
     * @return
     */
    public static int parseFromString(String s) {
        int num = 0;
        try {
            num = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }
}
