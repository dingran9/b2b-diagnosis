package com.eedu.diagnosis.inclass.test.api.service;

import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerMachineDto;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerSheetDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zz on 2017/10/13.
 */
public interface DiagnosisStudentAnswerMachineService {

    /**保存学生绑定答题器**/
    public int saveDiagnosisStudentAnswerMachine(DiagnosisStudentAnswerMachineDto dto) throws Exception ;

    /**批量学生绑定答题器**/
    public int batchSaveDiagnosisStudentAnswerMachine(List<DiagnosisStudentAnswerMachineDto> dtoList) throws Exception;

    /**学生绑定答题器Page**/
    public PageInfo<DiagnosisStudentAnswerMachineDto> selectDiagnosisStudentAnswerMachinePage(DiagnosisStudentAnswerMachineDto dto) throws Exception ;

    /**学生绑定答题器list**/
    public List<DiagnosisStudentAnswerMachineDto> selectDiagnosisStudentAnswerMachineList(DiagnosisStudentAnswerMachineDto dto) throws Exception ;

    /**学生绑定答题器修改**/
    public int updateDiagnosisStudentAnswerMachine(DiagnosisStudentAnswerMachineDto dto) throws Exception ;

    /**学生绑定答题器删除**/
    public int deleteDiagnosisStudentAnswerMachine(String  studentAnswerMachineCode) throws Exception;

    /**学生绑定答题器**/
    public DiagnosisStudentAnswerMachineDto selectDiagnosisStudentAnswerMachineDto(String studentAnswerSheetCode) throws Exception;
}
