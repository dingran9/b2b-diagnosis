package com.eedu.diagnosis.inclass.test.persist.dao;

import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisStudentAnswerMachine;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DiagnosisStudentAnswerMachineDao {
    int deleteByPrimaryKey(String studentAnswerMachineCode);

    int insertSelective(DiagnosisStudentAnswerMachine record);

    DiagnosisStudentAnswerMachine selectByPrimaryKey(String studentAnswerMachineCode);

    int updateByPrimaryKey(DiagnosisStudentAnswerMachine record);

    List<DiagnosisStudentAnswerMachine> selectByCondtionList(DiagnosisStudentAnswerMachine record);

    int batchSaveDiagnosisStudentAnswerMachine(Map<String, Object> map) ;
}