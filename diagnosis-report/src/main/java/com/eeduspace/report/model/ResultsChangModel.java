package com.eeduspace.report.model;

import lombok.Data;

/**
 * 班级学生单科成绩表
 *
 * @author liuhongfei
 * @e-email: liuhongfei@e-eduspace.com
 * @create 2017-04-25
 **/
@Data
public class ResultsChangModel {

    //比例
    private double proportion;
    //班级name
    private String class_name;
    //发布code
    private String release_code;
    //创建时间
    private String create_time;
    //学科code
    private String subject_code;
    //学生单科成绩
    private double score;
    //学生code
    private String user_code;
    //发布ID
    private int code;
    //标准分
    private double standardpoints;
    //单科试卷总分
    private double paper_score;
    //全科试卷总分
    private double total_score;
}
