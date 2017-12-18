package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 知识木块教学掌握情况
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 14:51
 **/
@Data
public class TeachingKnowledgeModuleDto implements Serializable{
    private String knowledgeModuleName;
    private String knowledgeModuleCode;
    /**
     * 教师知识模块掌握信息
     */
    private List<TeacherKnowledgeModuleDto> teacherKnowledgeModuleDtoList;
}
