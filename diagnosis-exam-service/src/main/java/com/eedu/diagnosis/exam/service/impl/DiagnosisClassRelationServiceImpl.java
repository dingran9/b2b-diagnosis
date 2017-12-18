package com.eedu.diagnosis.exam.service.impl;

import com.eedu.diagnosis.exam.persist.dao.DiagnosisClassRelationPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisClassRelationPo;
import com.eedu.diagnosis.exam.service.DiagnosisClassRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Service
public class DiagnosisClassRelationServiceImpl extends BaseServiceImpl<DiagnosisClassRelationPo> implements DiagnosisClassRelationService {
    @Autowired
    private DiagnosisClassRelationPoMapper diagnosisClassRelationPoMapper;
    @Autowired
    public DiagnosisClassRelationServiceImpl(DiagnosisClassRelationPoMapper diagnosisClassRelationPoMapper){
        this.baseDao = diagnosisClassRelationPoMapper;
    }

    @Override
    public List<DiagnosisClassRelationPo> getAreaExamHistoryList(Map<String, Object> queryMap) throws Exception {
        return diagnosisClassRelationPoMapper.getAreaExamHistoryList(queryMap);
    }

    @Override
    public List<DiagnosisClassRelationPo> getExamPaperByParameter(Map<String, Object> queryMap) throws Exception {
        return diagnosisClassRelationPoMapper.getExamPaperByParameter(queryMap);
    }
}
