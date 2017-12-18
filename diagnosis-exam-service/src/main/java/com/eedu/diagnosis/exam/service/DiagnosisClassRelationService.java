package com.eedu.diagnosis.exam.service;

import com.eedu.diagnosis.exam.persist.po.DiagnosisClassRelationPo;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
public interface DiagnosisClassRelationService extends BaseService<DiagnosisClassRelationPo>{
    List<DiagnosisClassRelationPo> getAreaExamHistoryList(Map<String, Object> queryMap) throws Exception;
    public List<DiagnosisClassRelationPo> getExamPaperByParameter(Map<String, Object> queryMap) throws Exception;
}
