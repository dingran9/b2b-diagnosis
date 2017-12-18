package com.eedu.diagnosis.exam.service.impl;

import com.eedu.diagnosis.exam.persist.dao.DiagnosisStudentAnswerRecordPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisStudentAnswerRecordPo;
import com.eedu.diagnosis.exam.service.DiagnosisStudentAnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Service
public class DiagnosisStudentAnswerRecordServiceImpl extends BaseServiceImpl<DiagnosisStudentAnswerRecordPo> implements DiagnosisStudentAnswerRecordService {
    @Autowired
    private DiagnosisStudentAnswerRecordPoMapper diagnosisStudentAnswerRecordPoMapper;
    @Autowired
    public DiagnosisStudentAnswerRecordServiceImpl(DiagnosisStudentAnswerRecordPoMapper diagnosisStudentAnswerRecordPoMapper){
        this.baseDao = diagnosisStudentAnswerRecordPoMapper;
    }

    @Override
    public void deleteByDiagnosisStudentAnswerRecordPo(DiagnosisStudentAnswerRecordPo diagnosisStudentAnswerRecordPo) throws Exception {
        diagnosisStudentAnswerRecordPoMapper.deleteByDiagnosisStudentAnswerRecordPo(diagnosisStudentAnswerRecordPo);
    }

    @Override
    public List<DiagnosisStudentAnswerRecordPo> getListByAnswerRecordCodes(Map queryMap) throws Exception {
        return diagnosisStudentAnswerRecordPoMapper.getListByAnswerRecordCodes(queryMap);
    }
}
