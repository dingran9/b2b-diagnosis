package com.eedu.diagnosis.inclass.test.api.service;

import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassTestDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by zz on 2017/10/13.
 */
public interface DiagnosisInClassTestService {



    /**保存课中测**/
    public int saveDiagnosisInClassTest(DiagnosisInClassTestDto dto) throws Exception;

    /**课中测Page**/
    public PageInfo<DiagnosisInClassTestDto> selectDiagnosisInClassTestPage(DiagnosisInClassTestDto dto) throws Exception ;


    /**课中测list**/
    public List<DiagnosisInClassTestDto> selectDiagnosisInClassTestList(DiagnosisInClassTestDto dto) throws Exception ;

    /**课中测修改**/
    public int updateDiagnosisInClassTest(DiagnosisInClassTestDto dto) throws Exception;

    /**课中测删除**/
    public int deleteDiagnosisInClassTest(String inClassTestCode) throws Exception;

    /**课中测**/
    public DiagnosisInClassTestDto selectDiagnosisInClassTestDto(String inClassTestCode) throws Exception ;

}
