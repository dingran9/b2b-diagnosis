package com.eedu.diagnosis.exam.persist.dao;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordStudentPo;

import java.util.List;
import java.util.Map;

public interface DiagnosisRecordStudentPoMapper extends BaseDao<DiagnosisRecordStudentPo>{
    Map<String, Object> getClassAverage(DiagnosisRecordStudentPo po);

    List<DiagnosisRecordStudentPo> getListGroupByDiagName(Map queryMap, Order order);

    void deleteByPo(DiagnosisRecordStudentPo po);

    List<DiagnosisRecordStudentPo> getListByRealseCodes(Map queryMap)throws Exception;

    List<DiagnosisRecordStudentPo> getMarkList(Map queryMap, Order order) throws Exception;
}
