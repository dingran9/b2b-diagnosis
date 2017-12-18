package com.eedu.diagnosis.exam.service;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordStudentPo;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
public interface DiagnosisRecordStudentService extends BaseService<DiagnosisRecordStudentPo>{
    Map<String, Object> getClassAverage(DiagnosisRecordStudentPo po);

    List<DiagnosisRecordStudentPo> getListGroupByDiagName(Map queryMap, Order order);

    List<DiagnosisRecordStudentPo> getMarkList(Map queryMap, Order order) throws Exception;

    List<DiagnosisRecordStudentPo> getListByRealseCodes(Map queryMap) throws Exception;
}
