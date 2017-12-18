package com.eedu.diagnosis.exam.service;

import com.eedu.diagnosis.exam.persist.po.DiagnosisWrongQuestion;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
public interface DiagnosisWrongQuestionService extends BaseService<DiagnosisWrongQuestion> {
    List<DiagnosisWrongQuestion> getWrongQuestions(Map queryMap) throws Exception;

    void deleteByDiaWrongQuestion(String diagnosisRecordCode) throws Exception;
}
