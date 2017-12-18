package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisBigQuestionMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisBigQuestion;
import com.eedu.diagnosis.paper.service.DiagnosisBigQuestionService;



@Service
public class DiagnosisBigQuestionServiceImpl extends BaseServiceImpl<DiagnosisBigQuestion> implements DiagnosisBigQuestionService {
	@Resource
	private DiagnosisBigQuestionMapper diagnosisBigQuestionMapperImpl;

	@Autowired
	public DiagnosisBigQuestionServiceImpl(DiagnosisBigQuestionMapper diagnosisBigQuestionMapper) {
		this.baseDao = diagnosisBigQuestionMapper;
	}

}