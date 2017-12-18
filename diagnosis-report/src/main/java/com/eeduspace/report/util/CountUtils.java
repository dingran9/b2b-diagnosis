package com.eeduspace.report.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 统计班级分段人数
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-20 20:06
 **/
public class CountUtils {


    public static List<Long> getScoreTimes(Long total){
        //统计段
        long item=10; //默认为10
        //计数
         int count=1;
        List<Long> list= Lists.newArrayList();
        return getTimes(total,item,count,list);
    }

    protected static List<Long> getTimes(Long total,Long item,Integer count, List<Long> times){
        if(total<=item){
            if(count==1){
                times.add(item);
                return times;
            }
            else {
                times.add(item*(count));
            }
        }
        if(total>item){
            times.add(item*count);
            count++;
            return  getTimes(total-item,item,count,times);
        }else{
            return times;
        }
    }


}
