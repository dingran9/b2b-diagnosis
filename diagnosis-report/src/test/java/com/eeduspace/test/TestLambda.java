package com.eeduspace.test;

import java.util.List;

/**
 * Author: dingran
 * Date: 2017/3/21
 * Description:
 */
@FunctionalInterface
public interface TestLambda {
    //抽象接口
    void display(List<String> s);
    // default
    default void test(){};
    // static不是抽象方法
    public static void staticMethod(){

    }
    // java.lang.Object中的方法不是抽象方法
    public boolean equals(Object var1);
}
