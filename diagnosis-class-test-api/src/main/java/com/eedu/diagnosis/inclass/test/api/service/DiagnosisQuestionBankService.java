package com.eedu.diagnosis.inclass.test.api.service;

import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisQuestionBankDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by zz on 2017/10/13.
 */
public interface DiagnosisQuestionBankService {

    /**保存题库**/
    public int saveDiagnosisQuestionBank(DiagnosisQuestionBankDto dto) throws Exception ;

    /**题库Page**/
    public PageInfo<DiagnosisQuestionBankDto> selectDiagnosisQuestionBankPage(DiagnosisQuestionBankDto dto) throws Exception ;

    /**题库list**/
    public List<DiagnosisQuestionBankDto> selectDiagnosisQuestionBankList(DiagnosisQuestionBankDto dto) throws Exception ;

    /**题库修改**/
    public int updateDiagnosisQuestionBank(DiagnosisQuestionBankDto dto) throws Exception ;

    /**题库删除**/
    public int deleteDiagnosisQuestionBank(String questionBookCode) throws Exception;

    /**题库**/
    public DiagnosisQuestionBankDto selectDiagnosisQuestionBankDto(String questionBookCode) throws Exception ;
}
