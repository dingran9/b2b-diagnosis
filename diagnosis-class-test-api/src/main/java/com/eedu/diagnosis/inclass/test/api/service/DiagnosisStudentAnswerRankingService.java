package com.eedu.diagnosis.inclass.test.api.service;

import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerRankingDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by zz on 2017/10/13.
 */
public interface DiagnosisStudentAnswerRankingService {


    /**保存课中测答题排名**/
    public int saveDiagnosisStudentAnswerRanking(DiagnosisStudentAnswerRankingDto dto) throws Exception ;

    /**批量课中测答题排名**/
    public int batchSaveDiagnosisStudentAnswerRanking(List<DiagnosisStudentAnswerRankingDto> dtoList) throws Exception ;

    /**课中测答题排名Page**/
    public PageInfo<DiagnosisStudentAnswerRankingDto> selectDiagnosisStudentAnswerRankingPage(DiagnosisStudentAnswerRankingDto dto) throws Exception ;

    /**课中测答题排名list**/
    public List<DiagnosisStudentAnswerRankingDto> selectDiagnosisStudentAnswerRankingList(DiagnosisStudentAnswerRankingDto dto) throws Exception ;

    /**课中测答题排名修改**/
    public int updateDiagnosisStudentAnswerRanking(DiagnosisStudentAnswerRankingDto dto) throws Exception ;

    /**课中测答题排名删除**/
    public int deleteDiagnosisStudentAnswerRanking(DiagnosisStudentAnswerRankingDto dto) throws Exception ;

    /**课中测答题排名**/
    public DiagnosisStudentAnswerRankingDto selectDiagnosisStudentAnswerRankingDto(String studentAnswerSheetCode) throws Exception;

}
