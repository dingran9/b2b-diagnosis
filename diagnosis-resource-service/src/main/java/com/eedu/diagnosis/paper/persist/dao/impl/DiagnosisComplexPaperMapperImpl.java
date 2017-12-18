package com.eedu.diagnosis.paper.persist.dao.impl;

import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.paper.persist.po.DiagnosisPaper;
import org.springframework.stereotype.Repository;

import com.eedu.diagnosis.paper.persist.dao.DiagnosisComplexPaperMapper;
import com.eedu.diagnosis.paper.persist.po.DiagnosisComplexPaper;

import java.util.List;
import java.util.Map;


@Repository("diagnosiscomplexpapermapperimpl")
public class DiagnosisComplexPaperMapperImpl extends BaseDaoImpl<DiagnosisComplexPaper> implements DiagnosisComplexPaperMapper {

    private static final String Diagnosis_Complex_Paper_Relation_Paper = "diagnosis_complex_paper_relation_paper";
    private static final String GET_DIAGNOSIS_COMPLEX_PAPER_COUNT = "get_diagnosis_complex_paper_count";
    @Override
    public List<DiagnosisComplexPaper> getDiagnosisComplexPaperPaperRelationPaper(Map<String, Object> queryMap, Order order) {
        if(order!=null){
            queryMap.put("orderProperty", order.getProperty());
            queryMap.put("orderDirection", order.getDirection());
        }
        List<DiagnosisComplexPaper> list = sessionTemplate.selectList(Diagnosis_Complex_Paper_Relation_Paper, queryMap);
        return list;
    }

    @Override
    public long getDiagnosisComplexPaperCount(Map<String, Object> queryMap) {
        return sessionTemplate.selectOne(GET_DIAGNOSIS_COMPLEX_PAPER_COUNT, queryMap);
    }
}