package com.work.utils;import  org.apache.commons.codec.digest.DigestUtils;import java.io.*;import java.security.MessageDigest;import java.security.SignatureException;/** * Project Name: 常用工具类集合 * 功能描述：MD5签名 * * @author faritor@unmz.net * @version 1.0 * @date 2017-12-12 11:14 * @since JDK 1.8 */public class MD5 {    private static final char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};    /**     * 32位MD5加密算法     *     * @param s     * @return     */    public static String convert32 (String s) {        try {            byte[] bytes = s.getBytes();            MessageDigest md = MessageDigest.getInstance("MD5");            md.update(bytes);            bytes = md.digest();            int j = bytes.length;            char[] chars = new char[j * 2];            int k = 0;            for (int i = 0; i < bytes.length; i++) {                byte b = bytes[i];                chars[k++] = hexChars[b >>> 4 & 0xf];                chars[k++] = hexChars[b & 0xf];            }            return new String(chars);        } catch (Exception e) {            return null;        }    }    /**     * 16位MD5加密算法     *     * @param s     * @return     */    public static String convert16 (String s) {        try {            byte[] bytes = s.getBytes();            MessageDigest md = MessageDigest.getInstance("MD5");            md.update(bytes);            bytes = md.digest();            int j = bytes.length;            char[] chars = new char[j * 2];            int k = 0;            for (int i = 0; i < bytes.length; i++) {                byte b = bytes[i];                chars[k++] = hexChars[b >>> 4 & 0x0f];                chars[k++] = hexChars[b & 0x0f];            }            return new String(chars).substring(8, 24);        } catch (Exception e) {            return null;        }    }}