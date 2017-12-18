package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyb on 2017/4/24.
 *  单班级全学科平均分，排名，总分，排名 封装返回对象
 */
@Data
public class ClassAvgScoreModel implements Serializable {

    //班级总分
    private String totalscore;

    //班级排名
    private String classranking;

    //班级名称
    private String classname;

    //班级下九大学科的平均分，排名
    private List<AvgScoreModel> avgScoreModels;
}
