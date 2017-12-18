package com.eeduspace.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: dingran
 * Date: 2017/3/21
 * Description:
 */
public class TestLambdaImpl implements TestLambda {
    @Override
    public void display(List<String> s) {
        s.stream().forEach(pa-> System.out.println(pa));
    }

    public static void main(String[] args){
        List<String> s=new ArrayList<>();
        s.add("1");
        s.add("2");
        s.add("3");
        s.add("4");
//        TestLambda newWay = s1 -> {System.out.println("Display from new Lambda Expression");};
//        newWay.display(s);
        TestLambda testLambda=new TestLambdaImpl();

        final Runnable runnable = () -> testLambda.display(s);



    }
}
