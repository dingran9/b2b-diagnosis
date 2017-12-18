package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/4/24.
 * 年级全学科成绩统计 数据封装对象
 *
 * 各班学生成绩分布情况  List<AllGradeScoreDto>
 */
@Data
public class AllGradeScoreDto implements Serializable {

    //学生总人数
    private long totalcount;

    //最高分
    private double max;

    //最低分
    private double min;

    //平均分
    private double ave;

    //标准差
    private  double deviation;

//    //高标准人数  >80%
//    private long highcount;
//
//    //中标准人数  >60%  <80%
//    private long inthecount;
//
//    //低标准人数  <60%
//    private long lowcount;
//
//    //高标准人数占比  >80%
//    private double highrange;
//
//    //中标准人数占比  >60%  <80%
//    private double intherange;
//
//    //低标准人数占比  <60%
//    private double lowrange;

    //人数 >=90%  <=100%
    private int firstcount;

    //人数  >=80% <90%
    private int secondcount;

    //人数  >=70%  <80%
    private int thirdcount;

    //人数  >=60 <70%
    private int fourthcount;

    //人数  <60%
    private int fifthcount;

    //占比 >=90%  <=100%
    private double firstrange;

    //占比  >=80% <90%
    private double secondrange;

    //占比  >=70%  <80%
    private double thirdrange;

    //占比   >=60 <70%
    private double fourthrange;

    //占比   <60%
    private double fifthrange;

    //班级code
    private String classcode;

    //班级name
    private String classname;

    //考试试卷总分
    private double tatalscore;

}
