package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 教师知识模块教学掌握情况
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 14:48
 **/
@Data
public class TeacherKnowledgeModuleDto implements Serializable{
    private String teacherName;
    private String teacherCode;
    /**
     * 知识点模块得分
     */
    private Double knowledgeModuleScore;

}
