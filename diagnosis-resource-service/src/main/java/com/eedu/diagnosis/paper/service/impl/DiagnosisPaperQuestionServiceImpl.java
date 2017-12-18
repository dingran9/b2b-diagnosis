package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisPaperQuestionMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisPaperQuestion;
import com.eedu.diagnosis.paper.service.DiagnosisPaperQuestionService;




@Service
public class DiagnosisPaperQuestionServiceImpl extends BaseServiceImpl<DiagnosisPaperQuestion> implements DiagnosisPaperQuestionService {
	@Resource
	private DiagnosisPaperQuestionMapper diagnosisPaperQuestionMapperImpl;

	@Autowired
	public DiagnosisPaperQuestionServiceImpl(DiagnosisPaperQuestionMapper diagnosisPaperQuestionMapper) {
		this.baseDao = diagnosisPaperQuestionMapper;
	}

}