package com.eedu.diagnosis.exam.service.impl;

import com.eedu.diagnosis.exam.persist.dao.DiagnosisWrongQuestionMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisWrongQuestion;
import com.eedu.diagnosis.exam.service.DiagnosisWrongQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Service
public class DiagnosisWrongQuestionServiceImpl extends BaseServiceImpl<DiagnosisWrongQuestion> implements DiagnosisWrongQuestionService {
    @Autowired
    private DiagnosisWrongQuestionMapper diagnosisWrongQuestionMapper;
    @Autowired
    public DiagnosisWrongQuestionServiceImpl(DiagnosisWrongQuestionMapper diagnosisWrongQuestionMapper){
        this.baseDao = diagnosisWrongQuestionMapper;
    }

    @Override
    public List<DiagnosisWrongQuestion> getWrongQuestions(Map queryMap)  throws Exception{
        return diagnosisWrongQuestionMapper.getWrongQuestions(queryMap);
    }

    @Override
    public void deleteByDiaWrongQuestion(String diagnosisRecordCode) throws Exception {
        diagnosisWrongQuestionMapper.deleteByDiaWrongQuestion(diagnosisRecordCode);
    }
}
