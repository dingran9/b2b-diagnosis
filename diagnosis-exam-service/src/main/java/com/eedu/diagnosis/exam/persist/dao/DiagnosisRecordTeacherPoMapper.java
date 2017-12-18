package com.eedu.diagnosis.exam.persist.dao;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordTeacherPo;

import java.util.List;
import java.util.Map;

public interface DiagnosisRecordTeacherPoMapper extends BaseDao<DiagnosisRecordTeacherPo>{
    List<DiagnosisRecordTeacherPo> getDiagnosisListWithReportCount(Map map, Order order) throws  Exception;

    List<DiagnosisRecordTeacherPo> getDiagnosisListForMaster(Map map) throws Exception;

    List<Map<String,Object>> getTeachingProgressByClasses(Map queryMap);

    List<DiagnosisRecordTeacherPo> getDiagnosisListBySubject(Map map, Order order);
}