package com.eedu.diagnosis.exam.persist.dao;

import com.eedu.diagnosis.exam.persist.po.DiagnosisStudentAnswerRecordPo;

import java.util.List;
import java.util.Map;

public interface DiagnosisStudentAnswerRecordPoMapper extends BaseDao<DiagnosisStudentAnswerRecordPo>{
    void deleteByDiagnosisStudentAnswerRecordPo(DiagnosisStudentAnswerRecordPo diagnosisStudentAnswerRecordPo) throws Exception;

    List<DiagnosisStudentAnswerRecordPo> getListByAnswerRecordCodes(Map queryMap)throws Exception;
}