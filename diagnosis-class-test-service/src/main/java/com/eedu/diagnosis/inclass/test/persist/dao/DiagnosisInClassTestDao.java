package com.eedu.diagnosis.inclass.test.persist.dao;

import com.eedu.diagnosis.inclass.test.persist.po.DiagnosisInClassTest;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DiagnosisInClassTestDao {
    int deleteByPrimaryKey(String inClassTestCode);

    int insertSelective(DiagnosisInClassTest record);

    DiagnosisInClassTest selectByPrimaryKey(String inClassTestCode);

    int updateByPrimaryKey(DiagnosisInClassTest record);

    List<DiagnosisInClassTest> selectByCondtionList(DiagnosisInClassTest record);

}