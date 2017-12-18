package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisSmallQuestionMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisSmallQuestion;
import com.eedu.diagnosis.paper.service.DiagnosisSmallQuestionService;



@Service
public class DiagnosisSmallQuestionServiceImpl extends BaseServiceImpl<DiagnosisSmallQuestion> implements DiagnosisSmallQuestionService {
	@Resource
	private DiagnosisSmallQuestionMapper diagnosisSmallQuestionMapperImpl;

	@Autowired
	public DiagnosisSmallQuestionServiceImpl(DiagnosisSmallQuestionMapper diagnosisSmallQuestionMapper) {
		this.baseDao = diagnosisSmallQuestionMapper;
	}

}