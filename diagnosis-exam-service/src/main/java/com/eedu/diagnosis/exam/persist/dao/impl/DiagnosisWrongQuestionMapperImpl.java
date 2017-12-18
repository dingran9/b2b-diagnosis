package com.eedu.diagnosis.exam.persist.dao.impl;

import com.eedu.diagnosis.exam.persist.dao.DiagnosisWrongQuestionMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisWrongQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */@Repository
public class DiagnosisWrongQuestionMapperImpl extends BaseDaoImpl<DiagnosisWrongQuestion> implements DiagnosisWrongQuestionMapper {
    private final String GETWRONGQUESTIONS = "getWrongQuestions";
    private final String DELETEBYDIAWRONGQUESTION = "deleteByDiaWrongQuestion";
    @Override
    public List<DiagnosisWrongQuestion> getWrongQuestions(Map queryMap)  throws Exception{
        return sessionTemplate.selectList(GETWRONGQUESTIONS,queryMap);
    }

    @Override
    public void deleteByDiaWrongQuestion(String diagnosisRecordCode) {
        sessionTemplate.delete(DELETEBYDIAWRONGQUESTION,diagnosisRecordCode);
    }
}
