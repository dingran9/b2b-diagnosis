package com.eedu.diagnosis.exam.service;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordTeacherPo;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
public interface DiagnosisRecordTeacherService extends BaseService<DiagnosisRecordTeacherPo> {
    List<DiagnosisRecordTeacherPo> getDiagnosisListWithReportCount(Map map, Order order) throws Exception;

    List<DiagnosisRecordTeacherPo> getDiagnosisListForMaster(Map map) throws Exception;

    List<Map<String,Object>> getTeachingProgressByClasses(Map queryMap) throws Exception;

    List<DiagnosisRecordTeacherPo> getDiagnosisListBySubject(Map map, Order order);
}
