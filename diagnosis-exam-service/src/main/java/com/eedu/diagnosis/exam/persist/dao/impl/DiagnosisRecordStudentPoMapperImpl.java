package com.eedu.diagnosis.exam.persist.dao.impl;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.exam.persist.dao.DiagnosisRecordStudentPoMapper;
import com.eedu.diagnosis.exam.persist.po.DiagnosisRecordStudentPo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/15.
 */
@Repository
public class DiagnosisRecordStudentPoMapperImpl extends BaseDaoImpl<DiagnosisRecordStudentPo> implements DiagnosisRecordStudentPoMapper {
    private static String GETCLASSAVERAGE = "getClassAverageAndExamCount";
    private static String GETLISTGROUPBYDIAGNAME = "getListGroupByDiagName";
    private static String DELETEBYPO = "deleteByPo";
    private static String GETMARKLIST = "getMarkList";
    private static String GETLISTBYREALSECODES = "getListByRealseCodes";

    @Override
    public Map<String, Object> getClassAverage(DiagnosisRecordStudentPo po) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("subjectCode", po.getSubjectCode());
//        queryMap.put("gradeCode", po.getGradeCode());
        queryMap.put("classCode", po.getClassCode());
        queryMap.put("diagnosisTeacherRecordCode", po.getDiagnosisTeacherRecordCode());
        try {
            return sessionTemplate.selectOne(GETCLASSAVERAGE, queryMap);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<DiagnosisRecordStudentPo> getListGroupByDiagName(Map queryMap, Order order) {
        if(null != order){
            queryMap.put("orderProperty", order.getProperty());
            queryMap.put("orderDirection", order.getDirection());
        }

        return sessionTemplate.selectList(GETLISTGROUPBYDIAGNAME,queryMap);
    }

    @Override
    public void deleteByPo(DiagnosisRecordStudentPo po) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("diagnosisTeacherRecordCode", po.getDiagnosisTeacherRecordCode());
        queryMap.put("studentCode", po.getStudentCode());
        sessionTemplate.delete(DELETEBYPO,queryMap);
    }

    @Override
    public List<DiagnosisRecordStudentPo> getListByRealseCodes(Map queryMap) throws Exception {
        return sessionTemplate.selectList(GETLISTBYREALSECODES,queryMap);
    }

    @Override
    public List<DiagnosisRecordStudentPo> getMarkList(Map queryMap, Order order) throws Exception{
        if(null != order){
            queryMap.put("orderProperty", order.getProperty());
            queryMap.put("orderDirection", order.getDirection());
        }
        return sessionTemplate.selectList(GETMARKLIST,queryMap);
    }
}
