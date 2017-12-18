package com.eeduspace.b2b.report.model;

import com.eeduspace.b2b.report.model.report.ClassDateAvgModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuhongfei on 2017/4/25.
 *  各班学生总分成绩变化情况 封装返回对象
 */
@Data
public class ResultsVarietyDto implements Serializable {

    //第几次测试
     private int release_times;

//    //单次发布下班级合集平均分
//    private  double class_sum_vag;
//
//    //考试发布时间
//    private String release_time;
//
//    //考试发布code
//    private String release_code;

    //单次测试下班级标准集合
    private List<ClassDateAvgModel> classDateAvgModels;

}
