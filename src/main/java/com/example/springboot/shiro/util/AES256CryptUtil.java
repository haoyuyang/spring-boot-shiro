package com.example.springboot.shiro.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class AES256CryptUtil {
    static final String KEY_ALGORITHM = "AES";


    private static AlgorithmParameterSpec getIV(String iv) {
        byte[] ivBytes = iv.getBytes();
        byte[] ivTemp = null;
        int ivBase = 16;
        if (ivBytes.length < 16) {
            int base = 16;
            if (ivBytes.length % ivBase != 0) {
                int groups = ivBytes.length / base + (ivBytes.length % base != 0 ? 1 : 0);
                ivTemp = new byte[groups * base];
                Arrays.fill(ivTemp, (byte) 0);
                System.arraycopy(ivBytes, 0, ivTemp, 0, ivBytes.length);
            }
        } else {
            ivTemp = new byte[ivBase];
            System.arraycopy(ivBytes, 0, ivTemp, 0, ivBase);
        }
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(ivTemp);
        System.out.println(Base64.toBase64String(ivParameterSpec.getIV()));
        return ivParameterSpec;
    }

    /**
     * 加密
     *
     * @param content
     * @param password
     * @param iv
     * @return
     */
    public static String encrypt(String content, String password, String iv) {
        try {
            //"AES"：请求的密钥算法的标准名称
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //256：密钥生成参数；securerandom：密钥生成器的随机源
            //SecureRandom securerandom = new SecureRandom(tohash256Deal(password));
            //  kgen.init(256, securerandom);
            //生成秘密（对称）密钥
            //SecretKey secretKey = kgen.generateKey();
            //返回基本编码格式的密钥
            byte[] enCodeFormat = tohash256Deal(password);
            //根据给定的字节数组构造一个密钥。enCodeFormat：密钥内容；"AES"：与给定的密钥内容相关联的密钥算法的名称
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            //将提供程序添加到下一个可用位置
            Security.addProvider(new BouncyCastleProvider());
            //创建一个实现指定转换的 Cipher对象，该转换由指定的提供程序提供。
            //"AES/ECB/PKCS7Padding"：转换的名称；"BC"：提供程序的名称
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key, getIV(iv));
            byte[] byteContent = content.getBytes("utf-8");
            byte[] cryptograph = cipher.doFinal(byteContent);
            return Base64.toBase64String(cryptograph);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String encrypt(byte[] content, String password, String iv) {
        return Base64.toBase64String(encryptForByte(content, password, iv));
    }

    /**
     * 解密
     *
     * @param cryptograph
     * @param password
     * @param iv
     * @return
     */
    public static String decrypt(byte[] cryptograph, String password, String iv) {
        return new String(decryptForByte(cryptograph, password, iv), StandardCharsets.UTF_8);
    }

    public static byte[] decryptForByte(byte[] cryptograph, String password, String iv) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            byte[] enCodeFormat = tohash256Deal(password);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, getIV(iv));
            byte[] content = cipher.doFinal(cryptograph);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static byte[] tohash256Deal(String datastr) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-256");
            digester.update(datastr.getBytes());
            byte[] hex = digester.digest();
            return hex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static byte[] encryptForByte(byte[] content, String password, String iv) {
        try {
            //"AES"：请求的密钥算法的标准名称
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //256：密钥生成参数；securerandom：密钥生成器的随机源
            //SecureRandom securerandom = new SecureRandom(tohash256Deal(password));
            //  kgen.init(256, securerandom);
            //生成秘密（对称）密钥
            //SecretKey secretKey = kgen.generateKey();
            //返回基本编码格式的密钥
            byte[] enCodeFormat = tohash256Deal(password);
            //根据给定的字节数组构造一个密钥。enCodeFormat：密钥内容；"AES"：与给定的密钥内容相关联的密钥算法的名称
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            //将提供程序添加到下一个可用位置
            Security.addProvider(new BouncyCastleProvider());
            //创建一个实现指定转换的 Cipher对象，该转换由指定的提供程序提供。
            //"AES/ECB/PKCS7Padding"：转换的名称；"BC"：提供程序的名称
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key, getIV(iv));
            byte[] byteContent = content;
            byte[] cryptograph = cipher.doFinal(byteContent);
            return cryptograph;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}