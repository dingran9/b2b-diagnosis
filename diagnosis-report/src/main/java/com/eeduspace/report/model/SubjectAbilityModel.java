package com.eeduspace.report.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 学习能力实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-21 16:28
 **/
@Data
public class SubjectAbilityModel {
    String abilityName;
    String abilityCode;
    Double abilityScore;
    String className;
    String classCode;
    String userCode;
    Integer releaseExamCode;
    Timestamp releaseExamCreateTime;
    String teacherCode;
    String teacherName;
    String schoolName;
    String schoolCode;
    String gradeCode;
    String subjectCode;
}
