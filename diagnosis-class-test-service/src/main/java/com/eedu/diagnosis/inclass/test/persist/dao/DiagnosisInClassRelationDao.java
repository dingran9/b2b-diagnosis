package com.eedu.diagnosis.inclass.test.persist.dao;

import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassRelationDto;
import com.eedu.diagnosis.inclass.test.api.model.QuestionBankModel;
import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisInClassRelation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DiagnosisInClassRelationDao {
    int deleteByPrimaryKey(String questionBankRelationCode);

    int insertSelective(DiagnosisInClassRelation record);

    DiagnosisInClassRelation selectByPrimaryKey(String questionBankRelationCode);

    int updateByPrimaryKey(DiagnosisInClassRelation record);

    int batchSaveDiagnosisInClassRelation(Map<String,Object> record);

    List<DiagnosisInClassRelation> selectByCondtionList(DiagnosisInClassRelation po);

    List<QuestionBankModel> selectByCondtionCount(String inClassTestCode);

    int deleteByPrimaryKeyByPo(String InClassTestCode);
}