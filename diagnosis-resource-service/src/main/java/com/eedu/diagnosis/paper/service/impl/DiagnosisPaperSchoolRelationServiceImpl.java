package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisPaperSchoolRelationMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisPaperSchoolRelation;
import com.eedu.diagnosis.paper.service.DiagnosisPaperSchoolRelationService;



@Service
public class DiagnosisPaperSchoolRelationServiceImpl extends BaseServiceImpl<DiagnosisPaperSchoolRelation> implements DiagnosisPaperSchoolRelationService {
	@Resource
	private DiagnosisPaperSchoolRelationMapper diagnosisPaperSchoolRelationMapperImpl;

	@Autowired
	public DiagnosisPaperSchoolRelationServiceImpl(DiagnosisPaperSchoolRelationMapper diagnosisPaperSchoolRelationMapper) {
		this.baseDao = diagnosisPaperSchoolRelationMapper;
	}

}