package com.eeduspace.report.service;

import com.eeduspace.report.model.KnowledgeModel;
import com.eeduspace.report.po.KnowledgePo;

import java.util.List;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
**/
public interface KnowledgeService {
    KnowledgePo save(KnowledgePo knowledgePo);
    List<KnowledgePo> batchSave(List<KnowledgePo> knowledgePoList);

    List<KnowledgeModel> findByReleaseExamCodeAndTeacherCodeAndSubjectCode(String releaseExamCode, String teacherCode, String subjectCode) throws Exception;
}
