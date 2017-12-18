package com.eedu.diagnosis.inclass.test.persist.dao;

import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisBaseQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DiagnosisBaseQuestionDao {
    int deleteByPrimaryKey(String baseCode);

    int insertSelective(DiagnosisBaseQuestion record);

    DiagnosisBaseQuestion selectByPrimaryKey(String baseCode);

    int updateByPrimaryKey(DiagnosisBaseQuestion record);

    List<DiagnosisBaseQuestion> selectByCondtionList(DiagnosisBaseQuestion record);

    int batchSaveDiagnosisBaseQuestion(Map<String,Object> record);

    List<DiagnosisBaseQuestion> selectDiagnosisBaseQuestionByInClassTest(String inClassTestCode);
}