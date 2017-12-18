package com.eedu.diagnosis.exam.persist.dao.impl;

import com.eedu.diagnosis.exam.persist.dao.DiagnosisClassRelationPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisClassRelationPo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Repository
public class DiagnosisClassRelationPoMapperImpl extends BaseDaoImpl<DiagnosisClassRelationPo> implements DiagnosisClassRelationPoMapper {
    private static String GETAREAEXAMHISTORYLIST = "getAreaExamHistoryList";

    private static String GETEXAMPAPERBYPARAMETER = "getExamPaperByParameter";
    @Override
    public List<DiagnosisClassRelationPo> getAreaExamHistoryList(Map<String, Object> queryMap) throws Exception {
        return sessionTemplate.selectList(GETAREAEXAMHISTORYLIST, queryMap);
    }

    @Override
    public List<DiagnosisClassRelationPo> getExamPaperByParameter(Map<String, Object> queryMap) throws Exception {
        return sessionTemplate.selectList(GETEXAMPAPERBYPARAMETER, queryMap);
    }
}
