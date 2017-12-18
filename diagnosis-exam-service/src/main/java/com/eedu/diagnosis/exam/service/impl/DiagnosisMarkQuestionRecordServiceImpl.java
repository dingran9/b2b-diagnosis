package com.eedu.diagnosis.exam.service.impl;

import com.eedu.diagnosis.exam.persist.dao.DiagnosisMarkQuestionRecordPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisMarkQuestionRecordPo;
import com.eedu.diagnosis.exam.service.DiagnosisMarkQuestionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dqy on 2017/3/15.
 */
@Service
public class DiagnosisMarkQuestionRecordServiceImpl extends BaseServiceImpl<DiagnosisMarkQuestionRecordPo> implements DiagnosisMarkQuestionRecordService {
    @Autowired
    private DiagnosisMarkQuestionRecordPoMapper diagnosisMarkQuestionRecordPoMapper;
    @Autowired
    public DiagnosisMarkQuestionRecordServiceImpl(DiagnosisMarkQuestionRecordPoMapper diagnosisMarkQuestionRecordPoMapper){
        this.baseDao = diagnosisMarkQuestionRecordPoMapper;
    }
}
