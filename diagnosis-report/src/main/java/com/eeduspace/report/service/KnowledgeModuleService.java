package com.eeduspace.report.service;

import com.eeduspace.b2b.report.model.report.KnowledgeModuleModel;
import com.eeduspace.report.po.KnowledgeModulePo;

import java.util.List;

/**
 * <p>描述 知识点模块</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 11:47
 * @param    
 * @return   
**/
public interface KnowledgeModuleService {
    /**
     * 保存
     * @param knowledgeModulePo
     * @return
     */
    KnowledgeModulePo save(KnowledgeModulePo knowledgeModulePo);

    List<KnowledgeModulePo> batchSave(List<KnowledgeModulePo> knowledgeModulePoList);

    /**
     * 获取知识点模块统计信息
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @return
     */
    List<KnowledgeModuleModel> findByReleaseExamCodeAndSubjectCodeAndTeacherCode(String releaseExamCode, String teacherCode, String subjectCode) throws Exception;


    /**
     * 获取模块掌握情况
     * @param codes 发布记录主键集合
     * @return
     * @throws Exception
     */
    List<KnowledgeModuleModel> findByReleaseCodeIn(List<String> codes) throws Exception;

    /**
     * 获取模块掌握情况
     * @param releaseCode 发布记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<KnowledgeModuleModel> findByReleaseCodeAndSubjectCode(String releaseCode,String subjectCode) throws Exception;

    /**
     * 获取知识点模块信息
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<KnowledgeModuleModel> findByReleaseExamCodeAndSubjectCode(String releaseExamCode,String subjectCode) throws Exception;
}
