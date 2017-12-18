package com.eeduspace.b2b.report.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 学生得分信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-11 14:38
 **/
@Data
public class StudentGetScoreDo implements Serializable{
    private Double score;
    /**
     * 发布记录主键code
     */
    private Integer releaseExamCode;
    /**
     * 发布记录code 外键
     */
    private String releaseFKCode;
    private String schoolCode;
    private String schoolName;
    private String classCode;
    private String className;
//    private String gradeCode;
//    private String gradeName;
//    private String subjectName;
//    private String subjectCode;
    private String teacherName;
    private String teacherCode;
    private String subjectCode;
}
