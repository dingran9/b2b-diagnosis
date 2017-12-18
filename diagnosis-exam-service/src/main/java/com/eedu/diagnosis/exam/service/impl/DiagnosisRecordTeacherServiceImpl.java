package com.eedu.diagnosis.exam.service.impl;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.dao.DiagnosisRecordTeacherPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordTeacherPo;
import com.eedu.diagnosis.exam.service.DiagnosisRecordTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Service
public class DiagnosisRecordTeacherServiceImpl extends BaseServiceImpl<DiagnosisRecordTeacherPo> implements DiagnosisRecordTeacherService {
    @Autowired
    private DiagnosisRecordTeacherPoMapper diagnosisRecordTeacherPoMapper;
    @Autowired
    public DiagnosisRecordTeacherServiceImpl(DiagnosisRecordTeacherPoMapper diagnosisRecordTeacherPoMapper){
        this.baseDao = diagnosisRecordTeacherPoMapper;
    }

    @Override
    public List<DiagnosisRecordTeacherPo> getDiagnosisListWithReportCount(Map map, Order order) throws Exception{
        return diagnosisRecordTeacherPoMapper.getDiagnosisListWithReportCount(map,order);
    }

    @Override
    public List<DiagnosisRecordTeacherPo> getDiagnosisListForMaster(Map map) throws Exception {
        return diagnosisRecordTeacherPoMapper.getDiagnosisListForMaster(map);
    }

    @Override
    public List<Map<String, Object>> getTeachingProgressByClasses(Map queryMap) throws Exception {
        return diagnosisRecordTeacherPoMapper.getTeachingProgressByClasses(queryMap);
    }

    @Override
    public List<DiagnosisRecordTeacherPo> getDiagnosisListBySubject(Map map, Order order) {
        return diagnosisRecordTeacherPoMapper.getDiagnosisListBySubject(map,order);
    }
}
