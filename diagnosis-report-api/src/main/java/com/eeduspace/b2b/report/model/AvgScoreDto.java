package com.eeduspace.b2b.report.model;

import com.eeduspace.b2b.report.model.report.ClassAvgScoreModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhongfei on 2017/4/24.
 *
 * 班各学科成绩平均分以及学科排名 封装返回对象
 */
@Data
public class AvgScoreDto implements Serializable {

    //九大学科对应的所有班级的平均分
    private Map<String,String> gradeavgscore;

    //所有班级平均分，排名，总分，排名集合
    private List<ClassAvgScoreModel> classAvgScoreModels;

}
