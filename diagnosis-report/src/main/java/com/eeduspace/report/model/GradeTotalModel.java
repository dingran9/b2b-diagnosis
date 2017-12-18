package com.eeduspace.report.model;

import lombok.Data;

/**
 * 班级成绩表
 *
 * @author liuhongfei
 * @e-email: liuhongfei@e-eduspace.com
 * @create 2017-04-24
 **/
@Data
public class GradeTotalModel {

    //考试发布code
   private String release_code;
   //考试发布名称
   private String release_name;
   //学年code
   private String grade_code;
   //学年名称
   private String grade_name;
   //用户名
   private String user_name;
   //用户code
   private String user_code;
   //班级code
   private String class_code;
   //班级名称
   private String class_name;
   //学生全学科总分
   private double totalscore;
   //学科name
   private String subject_name;
   //学科code
   private String subject_code;
   //平均分
   private double avgscore;
   //优秀人数
   private int excellent_count;
   //及格人数
   private int passing_count;
   //科目总分
   private double allscore;
   //班级单科学生总成绩
   private double sumscore;
   //班级单科学生考试人数
   private int usercount;

}
