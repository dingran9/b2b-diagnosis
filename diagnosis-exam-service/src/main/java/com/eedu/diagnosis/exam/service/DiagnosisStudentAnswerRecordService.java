package com.eedu.diagnosis.exam.service;

import com.eedu.diagnosis.exam.persist.po.DiagnosisStudentAnswerRecordPo;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
public interface DiagnosisStudentAnswerRecordService extends BaseService<DiagnosisStudentAnswerRecordPo> {
    void deleteByDiagnosisStudentAnswerRecordPo(DiagnosisStudentAnswerRecordPo diagnosisStudentAnswerRecordPo) throws Exception;

    List<DiagnosisStudentAnswerRecordPo> getListByAnswerRecordCodes(Map queryMap) throws Exception;
}
