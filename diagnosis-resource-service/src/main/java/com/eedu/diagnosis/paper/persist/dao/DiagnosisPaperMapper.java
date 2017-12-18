package com.eedu.diagnosis.paper.persist.dao;

import java.util.List;
import java.util.Map;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.po.DiagnosisPaper;


public interface DiagnosisPaperMapper extends BaseDao<DiagnosisPaper>{

	List<DiagnosisPaper> getDiagnosisPaperListByComplexPaperCode(Map<String, Object> queryMap);

	List<DiagnosisPaper> getDiagnosisPaperRelationSchoolByPaper(Map<String, Object> queryMap, Order order);


}