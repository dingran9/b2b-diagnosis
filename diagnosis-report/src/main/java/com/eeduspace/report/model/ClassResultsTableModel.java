package com.eeduspace.report.model;

import lombok.Data;

import java.util.DoubleSummaryStatistics;
import java.util.Map;

/**
 * 班级成绩表
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-20 20:25
 **/
@Data
public class ClassResultsTableModel {
    //班级名称
    String className;
    //班级统计信息
    DoubleSummaryStatistics doubleSummaryStatistics;
    //学科优秀率统计
    Map subjectCount;
    //班级分数段统计
    Map scoreCount;
    //平均分
    Map rankingAverage;
}
