package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 班级学生得分信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-04 13:04
 **/
@Data
public class ClassStudentScoreDto implements Serializable{
    private String classCode;
    private String className;
    /**
     * 学生得分情况
     */
    private List<StudentScoreDto> studentScoreDtos;
}
