package com.eedu.diagnosis.exam.persist.dao;

import com.eedu.diagnosis.exam.persist.po.DiagnosisClassRelationPo;

import java.util.List;
import java.util.Map;

public interface DiagnosisClassRelationPoMapper extends BaseDao<DiagnosisClassRelationPo>{
    List<DiagnosisClassRelationPo> getAreaExamHistoryList(Map<String, Object> queryMap) throws Exception;
    public List<DiagnosisClassRelationPo> getExamPaperByParameter(Map<String, Object> queryMap) throws Exception;
}