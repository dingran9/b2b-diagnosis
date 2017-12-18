package com.eedu.diagnosis.paper.service;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.po.DiagnosisComplexPaper;

import java.util.List;
import java.util.Map;

/**
 *    全科诊断卷
 * 
 * @author zz
 *
 */

public interface DiagnosisComplexPaperService extends BaseService<DiagnosisComplexPaper>{
    List<DiagnosisComplexPaper> getDiagnosisComplexPaperPaperRelationPaper(Map<String, Object> queryMap, Order order);

    long getDiagnosisComplexPaperCount(Map<String, Object> queryMap);
}