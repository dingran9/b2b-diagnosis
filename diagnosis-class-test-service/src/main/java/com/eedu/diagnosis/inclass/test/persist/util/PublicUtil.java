package com.eedu.diagnosis.inclass.test.persist.util;

import java.util.UUID;

/**
 * Created by Administrator on 2017/10/13.
 */
public class PublicUtil {

    public static String  getUUID(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }



}
