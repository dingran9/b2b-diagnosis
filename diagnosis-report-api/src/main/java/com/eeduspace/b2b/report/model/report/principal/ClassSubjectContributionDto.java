package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 11:12
 **/
@Data
public class ClassSubjectContributionDto implements Serializable{
    /**
     * 班级名称
     */
    private String className;
    private String classCode;
    /**
     * 实际贡献
     */
    private Double practicalContribution;
    /**
     * 理论贡献
     */
    private Double theoreticalContribution;
    /**
     * 学科贡献指数
     */
    private Double subjectContribution;
    /**
     * 学科作答率
     */
    private Double subjectAnswerRate;

}
