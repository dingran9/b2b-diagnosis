package com.eeduspace.report.model;

import lombok.Data;

/**
 * 分数提升空间
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-26 11:21
 **/
@Data
public class ThreeColorModel {
    /**
     * 蓝色部分
     */
    private Double buleScore;
    /**
     * 灰色部分
     */
    private Double grayScore;
    /**
     * 橙色部分
     */
    private Double orangeScore;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 模块编码
     */
    private String moduleCode;
    /**
     * 班级code
     */
    private String classCode;
    /**
     * 班级名
     */
    private String className;
    /**
     * 学年code
     */
    private String gradeCode;
    /**
     * 学科code
     */
    private String subjectCode;
    /**
     * 考试发布记录code
     */
    private Integer releaseExamCode;


}
