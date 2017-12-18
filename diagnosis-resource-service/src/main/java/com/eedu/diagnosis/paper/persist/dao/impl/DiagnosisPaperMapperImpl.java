package com.eedu.diagnosis.paper.persist.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.dao.DiagnosisPaperMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisPaper;


@Repository("diagnosispapermapperimpl")
public class DiagnosisPaperMapperImpl extends BaseDaoImpl<DiagnosisPaper> implements DiagnosisPaperMapper {

	
	
	private static final String DIAGNOSIS_PAPER_LIST_BY_COMPLEX_PAPER_CODE = "diagnosis_paper_list_by_complex_paper_code";
	private static final String DIAGNOSIS_PAPER_RELATION_SCHOOL_BY_PAPER = "diagnosis_paper_relation_school_by_paper";

	@Override
	public List<DiagnosisPaper> getDiagnosisPaperListByComplexPaperCode(Map<String, Object> queryMap) {
		return sessionTemplate.selectList(DIAGNOSIS_PAPER_LIST_BY_COMPLEX_PAPER_CODE, queryMap);
	}

	@Override
	public List<DiagnosisPaper> getDiagnosisPaperRelationSchoolByPaper(Map<String, Object> queryMap, Order order) {
		if(order!=null){
			queryMap.put("orderProperty", order.getProperty());
			queryMap.put("orderDirection", order.getDirection());
		}
		List<DiagnosisPaper> list = sessionTemplate.selectList(DIAGNOSIS_PAPER_RELATION_SCHOOL_BY_PAPER, queryMap);
		return list;
	}


}