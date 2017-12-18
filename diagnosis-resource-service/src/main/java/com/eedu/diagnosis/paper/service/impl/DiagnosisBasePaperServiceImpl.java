package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisBasePaperMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisBasePaper;
import com.eedu.diagnosis.paper.service.DiagnosisBasePaperService;


	@Service
	public class DiagnosisBasePaperServiceImpl extends BaseServiceImpl<DiagnosisBasePaper> implements DiagnosisBasePaperService {
		@Resource
		private DiagnosisBasePaperMapper diagnosisBasePaperMapperImpl;

		@Autowired
		public DiagnosisBasePaperServiceImpl(DiagnosisBasePaperMapper diagnosisBasePaperMapper) {
			this.baseDao = diagnosisBasePaperMapper;
		}

	}
	
