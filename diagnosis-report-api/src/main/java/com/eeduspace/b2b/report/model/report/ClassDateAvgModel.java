package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/4/26.
 *  单班级 单次测试下 标准分  封装返回结果
 */
@Data
public class ClassDateAvgModel implements Serializable {

       //班级name
       private String class_name;
       //班级标准分
       private String class_avg;

}
