package com.eedu.diagnosis.inclass.test.api.service;

import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassRelationDto;
import com.eedu.diagnosis.inclass.test.api.model.QuestionBankModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by zz on 2017/10/13.
 */
public interface DiagnosisInClassRelationService {

    /**课中测-基础试题绑定列表**/
    public List<DiagnosisInClassRelationDto> selectDiagnosisInClassRelationList(DiagnosisInClassRelationDto dto) throws Exception;

    /**保存课中测-基础试题绑定**/
    public int saveDiagnosisInClassRelation(DiagnosisInClassRelationDto dto) throws Exception;

    /**批量课中测-基础试题绑定**/
    public int batchSaveDiagnosisInClassRelation(List<DiagnosisInClassRelationDto> dtoList) throws Exception ;

    /**根据课中测的code查询题库数量**/
    public List<QuestionBankModel> selectDiagnosisQuestionBankCount(String inClassTestCode) throws Exception ;

    /**删除课中测-基础试题绑定**/
    public int deleteDiagnosisInClassRelation(String InClassTestCode) throws Exception;

    /**修改课中测-基础试题绑定**/
    public int updateDiagnosisInClassRelation(DiagnosisInClassRelationDto dto) throws Exception;


    public PageInfo<DiagnosisInClassRelationDto> selectDiagnosisInClassRelationPage(DiagnosisInClassRelationDto dto) throws Exception ;

    }
