package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 单个班级报告信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-10 11:57
 **/
@Data
public class SingleClassReportModel implements Serializable{
    /**
     * 试卷总分
     */
    private Double paperTotalScore;
    /**
     * 最高分
     */
    private Double maxScore;
    /**
     * 最低分
     */
    private Double minScore;
    /**
     * 平均分
     */
    private Double avgScore;
    /**
     * 标准差
     */
    private Double standardDeviation;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 优秀率
     */
    private Double excellent;
    /**
     * 及格率
     */
    private Double qualified;
    /**
     * 不及格率
     */
    private Double unQualified;
    /**
     * 达标率
     */
    private Double standard;
    /**
     * 不达标率
     */
    private Double notStandard;
    /**
     * 参加考试的学生
     */
    List<StudentScoreModel> studentScoreModels = new ArrayList<>();
    /**
     * 未参加考试的学生
     */
    List<StudentScoreModel>  notAttendedStudentScoreModels = new ArrayList<>();


}
