package com.eedu.diagnosis.paper.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisFavoriteMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisFavorite;
import com.eedu.diagnosis.paper.service.DiagnosisFavoriteService;



/**
 *  收藏
 * 
 * @author zz
 *
 */
@Service
public class DiagnosisFavoriteServiceImpl extends BaseServiceImpl<DiagnosisFavorite> implements DiagnosisFavoriteService {
	@Resource
	private DiagnosisFavoriteMapper diagnosisFavoriteMapperImpl;

	@Autowired
	public DiagnosisFavoriteServiceImpl(DiagnosisFavoriteMapper diagnosisFavoriteMapper) {
		this.baseDao = diagnosisFavoriteMapper;
	}

}