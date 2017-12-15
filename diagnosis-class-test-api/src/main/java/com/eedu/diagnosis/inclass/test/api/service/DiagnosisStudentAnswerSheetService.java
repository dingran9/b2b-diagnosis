package com.eedu.diagnosis.inclass.test.api.service;

import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerSheetDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by zz on 2017/10/13.
 */
public interface DiagnosisStudentAnswerSheetService {


    /**保存课中测答题记录**/
    public int saveDiagnosisStudentAnswerSheet(DiagnosisStudentAnswerSheetDto dto) throws Exception ;

    /**批量课中测答题记录**/
    public int batchSaveDiagnosisStudentAnswerSheet(List<DiagnosisStudentAnswerSheetDto> dtoList) throws Exception ;

    /**课中测答题记录Page**/
    public PageInfo<DiagnosisStudentAnswerSheetDto> selectDiagnosisStudentAnswerSheetPage(DiagnosisStudentAnswerSheetDto dto) throws Exception ;

    /**课中测答题记录list**/
    public List<DiagnosisStudentAnswerSheetDto> selectDiagnosisStudentAnswerSheetList(DiagnosisStudentAnswerSheetDto dto) throws Exception ;

    /**课中测答题记录修改**/
    public int updateDiagnosisStudentAnswerSheet(DiagnosisStudentAnswerSheetDto dto) throws Exception ;

    /**课中测答题记录删除**/
    public int deleteDiagnosisStudentAnswerSheet(DiagnosisStudentAnswerSheetDto dto) throws Exception ;

    /**课中测答题记录**/
    public DiagnosisStudentAnswerSheetDto selectDiagnosisStudentAnswerSheetDto(String studentAnswerSheetCode) throws Exception;

}
