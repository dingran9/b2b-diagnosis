package com.eedu.diagnosis.exam.service.impl;

import com.eedu.diagnosis.exam.persist.dao.DiagnosisUnitSchedulePoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisUnitSchedulePo;
import com.eedu.diagnosis.exam.service.DiagnosisUnitScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dqy on 2017/10/12.
 */
@Service
public class DiagnosisUnitScheduleServiceImpl extends BaseServiceImpl<DiagnosisUnitSchedulePo> implements DiagnosisUnitScheduleService {
    @Autowired
    private DiagnosisUnitSchedulePoMapper diagnosisUnitSchedulePoMapper;

    @Autowired
    public DiagnosisUnitScheduleServiceImpl(DiagnosisUnitSchedulePoMapper diagnosisUnitSchedulePoMapper){
        this.baseDao = diagnosisUnitSchedulePoMapper;
    }
}
