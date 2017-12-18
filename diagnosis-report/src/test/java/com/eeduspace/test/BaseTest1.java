package com.eeduspace.test;

import com.eeduspace.report.B2bReportApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by zhuchaowei on 2017/3/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = B2bReportApplication.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BaseTest1 {


    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){

    }

    @Test
    public void testRedis(){
        stringRedisTemplate.opsForValue().set("dong2B","董二笔22222");
    }

    public static void main(String[] args) {
        List<Integer> list=new ArrayList<Integer>();
        for(int i=0;i<100;i++){
            list.add(Integer.valueOf(i));
        }

        System.out.println(list.stream().reduce(
                (result,element)->
                        result=result+element));

        System.out.println(list.stream().reduce(
                0,
                (result,element)->
                        result=result+element));


        System.out.println(list.stream().reduce(
                0,
                (result,element)->
                        result=result+element
                ,(u,t) -> t));
    }

    @Test
    public void test8(){
        String []datas = new String[] {"peng","zhao","li"};
        Arrays.sort(datas);
        Stream.of(datas).forEach(o -> System.out.println(o));


        List<Integer> list=new ArrayList<Integer>();
        for(int i=0;i<100;i++){
            list.add(Integer.valueOf(i));
        }

        System.out.println(list.stream().reduce(
                (result,element)->
                        result=result+element));

        System.out.println(list.stream().reduce(
                0,
                (result,element)->
                        result=result+element));


        System.out.println(list.stream().reduce(
                0,
                (result,element)->
                        result=result+element,(u,t) -> t));



    }


    private static Stream<String> concat1(List<Collection<String>> collections) {

        Stream result = Stream.empty();

        for (Collection<String> strings : collections) {

            result = Stream.concat(result,  strings.stream());

        }

        return   result;

    }

}
