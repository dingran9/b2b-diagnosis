package com.eedu.diagnosis.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by dqy on 2017/4/19.
 */
public class MD5Utils {

    public static String getMD5(String str) {
        if(null == str || "".equals(str.trim())) return null;
//        try {
//            // 生成一个MD5加密计算摘要
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            // 计算md5函数
//            md.update(str.getBytes("utf-8"));
//            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
//            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
//            return new BigInteger(1, md.digest()).toString(16);
//        } catch (Exception e) {
//            throw new RuntimeException("MD5加密出现错误");
//        }
        try {
            byte[] data = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            return Hex.toHex(md.digest(data));
        } catch (Exception var3) {
            throw new RuntimeException("digest fail!", var3);
        }
    }

    private static class Hex {
        public Hex() {
        }

        public static String toHex(byte[] input) {
            if(input == null) {
                return null;
            } else {
                StringBuffer output = new StringBuffer(input.length * 2);

                for(int i = 0; i < input.length; ++i) {
                    int current = input[i] & 255;
                    if(current < 16) {
                        output.append("0");
                    }

                    output.append(Integer.toString(current, 16));
                }

                return output.toString();
            }
        }

        public static byte[] fromHex(String input) {
            if(input == null) {
                return null;
            } else {
                byte[] output = new byte[input.length() / 2];

                for(int i = 0; i < output.length; ++i) {
                    output[i] = (byte)Integer.parseInt(input.substring(i * 2, (i + 1) * 2), 16);
                }

                return output;
            }
        }
    }


    public static void main(String[] args) {
        String s = getMD5("15548387888");
        System.out.print(s);
    }
}
