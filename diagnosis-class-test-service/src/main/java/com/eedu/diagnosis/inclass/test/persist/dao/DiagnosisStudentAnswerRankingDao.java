package com.eedu.diagnosis.inclass.test.persist.dao;

import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisStudentAnswerRanking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DiagnosisStudentAnswerRankingDao {
    int deleteByPrimaryKey(String studentAnswerSheetCode);

    int insertSelective(DiagnosisStudentAnswerRanking record);

    DiagnosisStudentAnswerRanking selectByPrimaryKey(String studentAnswerSheetCode);

    int updateByPrimaryKey(DiagnosisStudentAnswerRanking record);

    List<DiagnosisStudentAnswerRanking> selectByCondtionList(DiagnosisStudentAnswerRanking record);

    int batchSaveDiagnosisStudentAnswerRanking(Map<String, Object> record);
}