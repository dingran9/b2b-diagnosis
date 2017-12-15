package com.eedu.diagnosis.inclass.test.api.service;

import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisBaseQuestionDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by zz on 2017/10/13.
 */
public interface DiagnosisBaseQuestionService {



    /**保存测试题**/
    public int saveDiagnosisBaseQuestion(DiagnosisBaseQuestionDto dto) throws Exception;

    /**批量保存测试题**/
    /**保存测试题**/
    public int batchSaveDiagnosisBaseQuestion(List<DiagnosisBaseQuestionDto> dtoList) throws Exception;

    /**测试题**/
    public DiagnosisBaseQuestionDto selectDiagnosisBaseQuestionDto(String baseCode) throws Exception ;

    /**课中测Page**/
    public PageInfo<DiagnosisBaseQuestionDto> selectDiagnosisBaseQuestionPage(DiagnosisBaseQuestionDto dto) throws Exception ;
    
    /**测试题list**/
    public List<DiagnosisBaseQuestionDto> selectDiagnosisBaseQuestionList(DiagnosisBaseQuestionDto dto) throws Exception ;

    /**测试题修改**/
    public int updateDiagnosisBaseQuestion(DiagnosisBaseQuestionDto dto) throws Exception;

    /**测试题删除**/
    public int deleteDiagnosisBaseQuestion(String baseCode) throws Exception;

    /**课中测code获取测试题Page**/
    public PageInfo<DiagnosisBaseQuestionDto> selectDiagnosisBaseQuestionByInClassTest(String inClassTestCode,Integer pageNum,Integer pageSize) throws Exception ;
}
