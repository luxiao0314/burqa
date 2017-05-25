package com.mvvm.lux.framework.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 * 1,密钥:key需要保持一致
 * 2,密钥偏移量:ivParameter向量值需要一致
 * 3,补码方式:padding模式需要一致
 */
public class AESOperator {

    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private String ivParameter = "9238513401340235";
    private static AESOperator instance;

    private AESOperator() {

    }

    public static AESOperator getInstance() {
        if (instance == null)
            instance = new AESOperator();
        return instance;
    }

    /**
     * AES加密
     * @param sSrc  原始数据
     * @param key   转码之后的密钥
     * @return      返回加密之后的数据
     * @throws Exception
     */
    public String encrypt(String sSrc,String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //padding模式
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return new String(Base64.encode(encrypted, Base64.NO_WRAP),"utf-8");// 此处使用BASE64做转码。
    }

    /**
     * AES 解密
     * @param sSrc  加密之后的数据
     * @param key   转码之后的密钥
     * @return      返回原始数据
     * @throws Exception
     */
    public String decrypt(String sSrc,String key) throws Exception {
        try {
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted = Base64.decode(sSrc, Base64.NO_WRAP);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, "utf-8");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 加密
     * @param encData       原始数据
     * @param secretKey   转码之后的密钥
     * @return vector    返回加密之后的数据
     * @return  返回加密之后的数据
     * @throws Exception
     */
    public static String encrypt(String encData, String secretKey, String vector) throws Exception {

        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        return new String(Base64.encode(encrypted, Base64.NO_WRAP),"utf-8");// 此处使用BASE64做转码。
    }

    /**
     * 解密
     * @param sSrc  加密之后的数据
     * @param key   转码之后的密钥
     * @param ivs   密钥偏移量
     * @return      返回原始数据
     * @throws Exception
     */
    public String decrypt(String sSrc, String key, String ivs) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(sSrc, Base64.NO_WRAP);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");
        } catch (Exception ex) {
            return null;
        }
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    public static String encryptSHA256(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(bt);
            strDes = StringUtil.bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("签名失败！");
            return null;
        }
        assert strDes != null;
        return strDes.substring(0, 16);
    }

}  