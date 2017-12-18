package com.eedu.diagnosis.paper.service;

import java.util.List;
import java.util.Map;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.po.DiagnosisPaper;


/**
 *   单科诊断试卷
 * 
 * @author zz
 *
 */
public interface DiagnosisPaperService extends BaseService<DiagnosisPaper>{

	public List<DiagnosisPaper> getDiagnosisPaperListByComplexPaperCode(Map<String, Object> queryMap);

	public List<DiagnosisPaper> getDiagnosisPaperRelationSchoolByPaper(Map<String, Object> queryMap, Order order);
}