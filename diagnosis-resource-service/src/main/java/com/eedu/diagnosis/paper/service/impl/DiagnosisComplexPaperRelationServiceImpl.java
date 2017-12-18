package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisComplexPaperRelationMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisComplexPaperRelation;
import com.eedu.diagnosis.paper.service.DiagnosisComplexPaperRelationService;




@Service
public class DiagnosisComplexPaperRelationServiceImpl extends BaseServiceImpl<DiagnosisComplexPaperRelation> implements DiagnosisComplexPaperRelationService {
	@Resource
	private DiagnosisComplexPaperRelationMapper diagnosisComplexPaperRelationMapperImpl;

	@Autowired
	public DiagnosisComplexPaperRelationServiceImpl(DiagnosisComplexPaperRelationMapper diagnosisComplexPaperRelationMapper) {
		this.baseDao = diagnosisComplexPaperRelationMapper;
	}

}