package com.eeduspace.report.model;

import lombok.Data;

import java.util.List;

/**
 * 成绩表
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-20 20:27
 **/
@Data
public class ResultTable {
    //及格学生
    List<StudentMakeResultModel> jige;
    //优秀学生
    List<StudentMakeResultModel> youxiu;
    //不及格学生
    List<StudentMakeResultModel> bujige;
}
