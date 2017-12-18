package com.eedu.diagnosis.paper.persist.dao;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.po.DiagnosisComplexPaper;

import java.util.List;
import java.util.Map;


public interface DiagnosisComplexPaperMapper extends BaseDao<DiagnosisComplexPaper>{

    List<DiagnosisComplexPaper> getDiagnosisComplexPaperPaperRelationPaper(Map<String, Object> queryMap, Order order);

    long getDiagnosisComplexPaperCount(Map<String, Object> queryMap);
}