package com.eeduspace.b2b.report.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhongfei on 2017/4/27.
 *
 * 各学科教学成绩贡献率  封装返回结果
 */
@Data
public class TeachingratioDto implements Serializable {


    //发布考试下班级，学科，贡献比率集合
    private List<ClassachievementDto> classachievementDtos;

    //发布考试下的所有学科对应的所有班级比率和的平均分
    private Map<String,String> gradeavgscore;
}
