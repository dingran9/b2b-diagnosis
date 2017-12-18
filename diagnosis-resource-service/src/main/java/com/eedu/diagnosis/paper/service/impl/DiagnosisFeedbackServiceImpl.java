package com.eedu.diagnosis.paper.service.impl;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisFeedbackMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisFeedback;
import com.eedu.diagnosis.paper.service.DiagnosisFeedbackService;




@Service
public class DiagnosisFeedbackServiceImpl extends BaseServiceImpl<DiagnosisFeedback> implements  DiagnosisFeedbackService {
	@Resource
	private DiagnosisFeedbackMapper diagnosisFeedbackMapperImpl;

	@Autowired
	public DiagnosisFeedbackServiceImpl(DiagnosisFeedbackMapper diagnosisFeedbackMapper) {
		this.baseDao = diagnosisFeedbackMapper;
	}


}