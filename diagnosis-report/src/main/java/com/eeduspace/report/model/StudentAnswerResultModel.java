package com.eeduspace.report.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 学生答题结果信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-21 10:12
 **/
@Data
public class StudentAnswerResultModel  {
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


    private Integer answerResult;
   // private Double score;
    private Integer isComplex;
    private String questionSn;
    //private Integer examCode;
   // private String productionJson;
    //private String knowledgeJson;
    private String questionCode;
    private String complexQuestionCode;
}
