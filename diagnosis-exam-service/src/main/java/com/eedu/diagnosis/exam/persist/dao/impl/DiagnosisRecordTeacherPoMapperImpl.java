package com.eedu.diagnosis.exam.persist.dao.impl;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.dao.DiagnosisRecordTeacherPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordTeacherPo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Repository
public class DiagnosisRecordTeacherPoMapperImpl extends BaseDaoImpl<DiagnosisRecordTeacherPo> implements DiagnosisRecordTeacherPoMapper {
    private static String GETDIAGNOSISLISTWITHREPORTCOUNT = "get_diagnosis_list_with_report_count";
    private static String GETDIAGNOSISLISTFORMASTER = "getDiagnosisListForMaster";
    private static String GETTEACHINGPROGRESSBYCLASSES = "getTeachingProgressByClasses";
    private static String GETDIAGNOSISLISTBYSUBJECT = "getDiagnosisListBySubject";
    @Override
    public List<DiagnosisRecordTeacherPo> getDiagnosisListWithReportCount(Map map, Order order) throws Exception {
        if(null != order){
            map.put("orderProperty", order.getProperty());
            map.put("orderDirection", order.getDirection());
        }
        return sessionTemplate.selectList(GETDIAGNOSISLISTWITHREPORTCOUNT,map);
    }

    @Override
    public List<DiagnosisRecordTeacherPo> getDiagnosisListForMaster(Map map) throws Exception {
        return sessionTemplate.selectList(GETDIAGNOSISLISTFORMASTER,map);
    }

    @Override
    public List<Map<String, Object>> getTeachingProgressByClasses(Map queryMap) {
        return sessionTemplate.selectList(GETTEACHINGPROGRESSBYCLASSES,queryMap);
    }

    @Override
    public List<DiagnosisRecordTeacherPo> getDiagnosisListBySubject(Map map, Order order) {
        if(null != order){
            map.put("orderProperty", order.getProperty());
            map.put("orderDirection", order.getDirection());
        }
        return sessionTemplate.selectList(GETDIAGNOSISLISTBYSUBJECT,map);
    }
}
