package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * 知识点模块
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-23 11:23
 **/
@Data
public class KnowledgeModuleModel implements Serializable{
    private Integer examCode;
    private String knowledgeModuleName;
    private String knowledgeModuleCode;
    private Double knowledgeModuleScore;
    private String teacherCode;
    private String teacherName;
    private String schoolName;
    private String schoolCode;
    private String className;
    private String classCode;
    private String gradeCode;
    private String subjectCode;

}
