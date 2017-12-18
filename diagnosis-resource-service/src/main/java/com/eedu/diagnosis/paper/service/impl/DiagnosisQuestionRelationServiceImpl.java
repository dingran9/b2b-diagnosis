package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisQuestionRelationMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisQuestionRelation;
import com.eedu.diagnosis.paper.service.DiagnosisQuestionRelationService;



@Service
public class DiagnosisQuestionRelationServiceImpl extends BaseServiceImpl<DiagnosisQuestionRelation> implements DiagnosisQuestionRelationService {
	@Resource
	private DiagnosisQuestionRelationMapper diagnosisQuestionRelationMapperImpl;

	@Autowired
	public DiagnosisQuestionRelationServiceImpl(DiagnosisQuestionRelationMapper diagnosisQuestionRelationMapper) {
		this.baseDao = diagnosisQuestionRelationMapper;
	}

}