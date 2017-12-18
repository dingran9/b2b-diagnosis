package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级学科成绩变动
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-27 10:19
 **/
@Data
public class ClassSubjectScoreChangeDto implements Serializable{
    /**
     * 班级code
     */
    private String classCode;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 分数变动信息
     */
    private Double score;
}
