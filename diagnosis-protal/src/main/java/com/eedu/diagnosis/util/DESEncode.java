package com.eedu.diagnosis.util;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.security.Key;
/**
 * DESede对称加密算法演示
 *
 * @author zolly
 * */
public class DESEncode {
    /**
     * 密钥算法
     * */
    public static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法/工作模式/填充方式
     * */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

    /**
     *
     * 生成密钥
     *
     * @return byte[] 二进制密钥
     * */
    public static byte[] initkey() throws Exception {

        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥生成器
        kg.init(168);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式

        byte[] key = secretKey.getEncoded();
        BufferedOutputStream keystream =
                new BufferedOutputStream(new FileOutputStream("DESedeKey.dat"));
        keystream.write(key, 0, key.length);
        keystream.flush();
        keystream.close();

        return key;
    }

    /**
     * 转换密钥
     *
     * @param key
     *            二进制密钥
     * @return Key 密钥
     * */
    public static Key toKey(byte[] key) throws Exception {
        // 实例化Des密钥
        DESedeKeySpec dks = new DESedeKeySpec(key);
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory
                .getInstance(KEY_ALGORITHM);
        // 生成密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data
     *            待加密数据
     * @param key
     *            密钥
     * @return byte[] 加密后的数据
     * */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @return byte[] 解密后的数据
     * */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 欢迎密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }


    /*
    加密字符串
     */
    public static String encode(String str,String screatKey){
        String result = "";
        try {
            byte[] data = DESEncode.encrypt(str.getBytes(), screatKey.getBytes());
            result =  Base64.encode(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    解密字符串
     */
    public static String decode(String str,String screatKey){
        String result = "";
        try {
            byte[] data = Base64.decode(str);
            data = DESEncode.decrypt(data, screatKey.getBytes());
            result = new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        String userCode = "f2b7117e-b38d-429b-81d5-831bfda9ad5e";//明文
        String telephone = "18363625398";//明文
        String key = "6D8F4E85D844E2DD0AE1C133485F5517";//易教空间密钥
        String pw = DESEncode.encode(userCode, key);//加密userCode
        String pw2 = DESEncode.encode(telephone, key);//加密telephone
        String mw = DESEncode.decode(pw2,key);//解密telephone
        System.out.println("密文为："+pw);
        System.out.println("密文为："+pw2);
        System.out.println("明文为："+mw);
    }
}
