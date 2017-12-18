package com.eedu.diagnosis.exam.service.impl;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.dao.DiagnosisRecordStudentPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordStudentPo;
import com.eedu.diagnosis.exam.service.DiagnosisRecordStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Service
public class DiagnosisRecordStudentServiceImpl extends BaseServiceImpl<DiagnosisRecordStudentPo> implements DiagnosisRecordStudentService {
    @Autowired
    private DiagnosisRecordStudentPoMapper diagnosisRecordStudentPoMapper;
    @Autowired
    public DiagnosisRecordStudentServiceImpl(DiagnosisRecordStudentPoMapper diagnosisRecordStudentPoMapper){
        this.baseDao = diagnosisRecordStudentPoMapper;
    }

    @Override
    public Map<String, Object> getClassAverage(DiagnosisRecordStudentPo po) {
        return diagnosisRecordStudentPoMapper.getClassAverage(po);
    }

    @Override
    public List<DiagnosisRecordStudentPo> getListGroupByDiagName(Map queryMap, Order order) {
        return diagnosisRecordStudentPoMapper.getListGroupByDiagName(queryMap,order);
    }

    @Override
    public List<DiagnosisRecordStudentPo> getMarkList(Map queryMap, Order order)throws Exception {
        return diagnosisRecordStudentPoMapper.getMarkList(queryMap,order);
    }

    @Override
    public List<DiagnosisRecordStudentPo> getListByRealseCodes(Map queryMap)throws Exception {
        return diagnosisRecordStudentPoMapper.getListByRealseCodes(queryMap);
    }
}
