package com.eedu.diagnosis.inclass.test.persist.dao;

import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisQuestionBank;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DiagnosisQuestionBankDao {
    int deleteByPrimaryKey(String questionBookCode);

    int insertSelective(DiagnosisQuestionBank record);

    DiagnosisQuestionBank selectByPrimaryKey(String questionBookCode);

    int updateByPrimaryKey(DiagnosisQuestionBank record);

    List<DiagnosisQuestionBank> selectByCondtionList(DiagnosisQuestionBank record);

}