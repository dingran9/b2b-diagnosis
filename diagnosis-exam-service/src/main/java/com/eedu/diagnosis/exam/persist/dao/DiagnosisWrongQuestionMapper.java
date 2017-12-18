package com.eedu.diagnosis.exam.persist.dao;

import com.eedu.diagnosis.exam.persist.po.DiagnosisWrongQuestion;

import java.util.List;
import java.util.Map;

public interface DiagnosisWrongQuestionMapper extends BaseDao<DiagnosisWrongQuestion>{
    List<DiagnosisWrongQuestion> getWrongQuestions(Map queryMap) throws Exception;

    void deleteByDiaWrongQuestion(String diagnosisRecordCode);
}