package com.eedu.diagnosis.protal.service;

import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerMachineDto;
import com.eedu.diagnosis.protal.model.classTest.DiagnosisInClassTestModel;
import com.eedu.diagnosis.protal.model.classTest.DiagnosisStudentAnswerMachineModel;
import com.eedu.diagnosis.protal.model.classTest.DiagnosisStudentAnswerSheetModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zz on 2017/10/18.
 */
public interface StudentClassTestService {


    Map<String,Object> reportOverview(DiagnosisStudentAnswerSheetModel model)throws  Exception;

    Map<String,Object> reportByKnowledge(DiagnosisStudentAnswerSheetModel model)throws Exception ;

    Map<String,Object> reportByAccuracy(DiagnosisStudentAnswerSheetModel model)throws Exception ;

    boolean studentSubmit(DiagnosisStudentAnswerSheetModel model)throws Exception ;

    Map<String, Object> baseQuestionListByClassTestCode(DiagnosisStudentAnswerSheetModel model)throws Exception ;

    Map<String, Object> unlockClassTest(DiagnosisStudentAnswerSheetModel model)throws Exception ;

    Map<String,Object> classTestList(DiagnosisInClassTestModel model)throws Exception ;

    Map<String,Object> questionDetails(DiagnosisStudentAnswerSheetModel model)throws Exception;

    List<Map<String, Object>> classTestListCount(DiagnosisInClassTestModel model)throws Exception;


    boolean studentSave(DiagnosisStudentAnswerMachineModel model)throws Exception;

    List<DiagnosisStudentAnswerMachineDto>  studentList(DiagnosisStudentAnswerMachineModel model)throws Exception;;

    Map<String,Object> report(DiagnosisStudentAnswerSheetModel model)throws Exception;

    boolean classTestUpdate(DiagnosisInClassTestModel model)throws Exception;
}