package com.eedu.diagnosis.common.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zyb on 2017/5/11.
 */
public class ProcessUtils {

    //自增数字ID转成六位数字和字母的串
    public static String parseLong(long id) {

        String s = Long.toString(id, 36);
//        System.out.println(String.format("%6s", s).replace(" ", "0"));
        s= String.format("%6s", s).replace(" ", "0");
        return s;
    }

  public static void main(String args[]){

      Set<String> set  = new HashSet<>();

      for(int i=1;i< 1000000 ;i++){

          boolean b = set.add(parseLong(i));

          if(!b)System.out.println("add fail "+i);
      }

  }

}
