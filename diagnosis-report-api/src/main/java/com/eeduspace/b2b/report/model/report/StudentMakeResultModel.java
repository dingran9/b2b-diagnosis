package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 学生答卷结果查询组合实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-18 13:13
 **/
@Data
public class StudentMakeResultModel implements Serializable{
    private Double score;
    private Double standardScore;
    private Double paperScore;
    private int code;
    private Integer releaseExamCode;
    private String schoolCode;
    private String schoolName;
    private String classCode;
    private String className;
    private String gradeCode;
    private String gradeName;
    private String subjectName;
    private String subjectCode;
    private String teacherName;
    private String teacherCode;
    private String userCode;
    private String userName;
    private String userMakePaperCode;
    private String paperName;
    private String paperCode;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String bookTypeVersionCode;
    private String bookTypeVersionName;
    private String releaseCode;
    /**
     * 发布考试记录时间
     */
    private Timestamp releaseCrateTime;
    /**
     * 文理类型  0 无  1文  2理
     */
    private Integer artType;



}
