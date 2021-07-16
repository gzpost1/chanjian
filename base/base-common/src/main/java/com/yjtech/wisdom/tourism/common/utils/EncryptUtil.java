package com.yjtech.wisdom.tourism.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class EncryptUtil {
    private static final String AES_KEY = "T@h$8i%n^k-c#l~o^u*n+d";

    public EncryptUtil() {
    }

    public static String makeMD5(String value) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(value.getBytes("UTF8"));
        byte[] s = md5.digest();
        return CommonUtil.toHex(s);
    }

    public static byte[] signWithHmacSHA(String value, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        mac.init(spec);
        return mac.doFinal(value.getBytes());
    }

    public static String makeSHA(String value) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(value.getBytes("UTF8"));
        byte[] s = sha.digest();
        return CommonUtil.toHex(s);
    }

    public static String reversibleEncrypt(String data) throws Exception {
        if (!Objects.isNull(data) && !data.isEmpty()) {
            StringBuilder buff = new StringBuilder(data);
            buff.append(new char[]{makeRandChar(), makeRandChar(), makeRandChar(), makeRandChar(), makeRandChar()});
            buff.insert(buff.length() % 5, new char[]{makeRandChar(), makeRandChar(), makeRandChar()});
            buff.insert(0, new char[]{makeRandChar(), makeRandChar(), makeRandChar()});
            buff.append(new char[]{makeRandChar(), makeRandChar(), makeRandChar()});
            String obscured = buff.toString();
            buff.setLength(0);
            int len = obscured.length();

            int index;
            for (index = len / 2; index >= 0; --index) {
                buff.append(obscured.charAt(index));
            }

            for (index = len - 1; index > len / 2; --index) {
                buff.append(obscured.charAt(index));
            }

            byte[] encrypted = encryptWithAES(buff.toString());
            buff.setLength(0);
            return Base64.getEncoder().encodeToString(encrypted);
        } else {
            throw new IllegalArgumentException("input parameter can't be null or empty");
        }
    }

    public static String reversibleDecrypt(String data) throws Exception {
        if (!Objects.isNull(data) && !data.isEmpty()) {
            byte[] encrypted = Base64.getDecoder().decode(data);
            byte[] obscured = decryptWithAES(encrypted);
            int len = obscured.length;
            StringBuilder buff = new StringBuilder(len);

            int pos;
            for (pos = len / 2; pos >= 0; --pos) {
                buff.append((char) obscured[pos]);
            }

            for (pos = len - 1; pos > len / 2; --pos) {
                buff.append((char) obscured[pos]);
            }

            buff.delete(0, 3);
            buff.delete(buff.length() - 3, buff.length());
            pos = (buff.length() - 3) % 5;
            buff.delete(pos, pos + 3);
            buff.delete(buff.length() - 5, buff.length());
            return buff.toString();
        } else {
            throw new IllegalArgumentException("input parameter can't be null or empty");
        }
    }

    private static char makeRandChar() {
        return (char) (33 + ThreadLocalRandom.current().nextInt(1, 10000) % 93);
    }

    public static byte[] encryptWithAES(String content) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom srandom = SecureRandom.getInstance("SHA1PRNG");
        srandom.setSeed("T@h$8i%n^k-c#l~o^u*n+d".getBytes("UTF-8"));
        keyGenerator.init(128, srandom);
        byte[] key = keyGenerator.generateKey().getEncoded();
        if (Objects.isNull(key)) {
            throw new Exception("failed to generate AES key");
        } else {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, secretKey);
            return cipher.doFinal(content.getBytes("UTF-8"));
        }
    }

    public static byte[] decryptWithAES(byte[] content) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom srandom = SecureRandom.getInstance("SHA1PRNG");
        srandom.setSeed("T@h$8i%n^k-c#l~o^u*n+d".getBytes("UTF-8"));
        keyGenerator.init(128, srandom);
        byte[] key = keyGenerator.generateKey().getEncoded();
        if (Objects.isNull(key)) {
            throw new Exception("failed to generate AES key");
        } else {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKey);
            return cipher.doFinal(content);
        }
    }
}