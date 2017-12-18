package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisAppUpdateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.po.DiagnosisAppUpdate;
import com.eedu.diagnosis.paper.service.DiagnosisAppUpdateService;


	@Service
	public class DiagnosisAppUpdateServiceImpl extends BaseServiceImpl<DiagnosisAppUpdate> implements DiagnosisAppUpdateService {
		@Resource
		private DiagnosisAppUpdateMapper diagnosisAppUpdateMapperImpl;

		@Autowired
		public DiagnosisAppUpdateServiceImpl(DiagnosisAppUpdateMapper diagnosisAppUpdateMapper) {
			this.baseDao = diagnosisAppUpdateMapper;
		}

	}
	
