package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import com.eedu.diagnosis.common.utils.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisComplexPaperMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisComplexPaper;
import com.eedu.diagnosis.paper.service.DiagnosisComplexPaperService;

import java.util.List;
import java.util.Map;


@Service
public class DiagnosisComplexPaperServiceImpl extends BaseServiceImpl<DiagnosisComplexPaper> implements DiagnosisComplexPaperService {
	@Resource
	private DiagnosisComplexPaperMapper diagnosisComplexPaperMapperImpl;

	@Autowired
	public DiagnosisComplexPaperServiceImpl(DiagnosisComplexPaperMapper diagnosisComplexPaperMapper) {
		this.baseDao = diagnosisComplexPaperMapper;
	}

    @Override
    public List<DiagnosisComplexPaper> getDiagnosisComplexPaperPaperRelationPaper(Map<String, Object> queryMap, Order order) {


        return diagnosisComplexPaperMapperImpl.getDiagnosisComplexPaperPaperRelationPaper(queryMap,  order);
    }

    @Override
    public long getDiagnosisComplexPaperCount(Map<String, Object> queryMap) {

        return diagnosisComplexPaperMapperImpl.getDiagnosisComplexPaperCount(queryMap);
    }
}