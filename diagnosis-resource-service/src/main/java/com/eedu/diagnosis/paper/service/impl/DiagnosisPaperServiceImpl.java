package com.eedu.diagnosis.paper.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.dao.DiagnosisPaperMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisPaper;
import com.eedu.diagnosis.paper.service.DiagnosisPaperService;




@Service
public class DiagnosisPaperServiceImpl extends BaseServiceImpl<DiagnosisPaper> implements DiagnosisPaperService {
	@Resource
	private DiagnosisPaperMapper diagnosisPaperMapperImpl;

	@Autowired
	public DiagnosisPaperServiceImpl(DiagnosisPaperMapper diagnosisPaperMapper) {
		this.baseDao = diagnosisPaperMapper;
	}

	@Override
	public List<DiagnosisPaper> getDiagnosisPaperListByComplexPaperCode(Map<String, Object> queryMap) {
		return diagnosisPaperMapperImpl.getDiagnosisPaperListByComplexPaperCode(queryMap);
	}

	@Override
	public List<DiagnosisPaper> getDiagnosisPaperRelationSchoolByPaper(Map<String, Object> queryMap, Order order) {
		return diagnosisPaperMapperImpl.getDiagnosisPaperRelationSchoolByPaper(queryMap,order);
	}

}