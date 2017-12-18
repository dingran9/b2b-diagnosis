package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 10:29
 **/
@Data
public class TeacherSubjectAveScoreDto implements Serializable{
    /**
     * 教师所教学生学科平均分
     */
    private Double aveScore;
    private String teacherName;
    private String teacherCode;
}
