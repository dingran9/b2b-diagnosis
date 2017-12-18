package com.eedu.diagnosis.inclass.test.persist.dao;

import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisStudentAnswerSheet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DiagnosisStudentAnswerSheetDao {
    int deleteByPrimaryKey(String studentAnswerSheetCode);

    int insertSelective(DiagnosisStudentAnswerSheet record);

    DiagnosisStudentAnswerSheet selectByPrimaryKey(String studentAnswerSheetCode);

    int updateByPrimaryKey(DiagnosisStudentAnswerSheet record);

    List<DiagnosisStudentAnswerSheet> selectByCondtionList(DiagnosisStudentAnswerSheet record);

    int batchSaveDiagnosisStudentAnswerSheet(Map<String,Object> record);
}