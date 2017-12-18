package com.eedu.diagnosis.manager.converter;

/**
 * Created by zyb on 2017/5/11.
 */
public class AccountConverter {


    //自增数字ID转成六位数字和字母的串
    public static String parseLong(long id) {

        String s = Long.toString(id, 36);
        System.out.println(String.format("%6s", s).replace(" ", "0"));
        long l = Long.parseLong("00000" + s, 36);
        return String.valueOf(l);

    }

}
