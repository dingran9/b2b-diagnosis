package com.eedu.diagnosis.manager.service;

import com.eedu.diagnosis.manager.model.request.ClassTest.*;
import com.eedu.diagnosis.manager.model.request.ClassTest.machine.ExercisesAnswer;

import java.util.Map;


/**
 * zz
 */
public interface ClassTestService {

    /***********************************题库**************************************/
    boolean diagnosisQuestionBankSave(DiagnosisQuestionBankModel model) throws Exception;

    Map<String,Object> diagnosisQuestionBankList(DiagnosisQuestionBankModel model) throws Exception;

    boolean diagnosisQuestionBankDelete(DiagnosisQuestionBankModel model)throws Exception ;

    /***********************************基础题**************************************/


    Map<String, Object> baseQuestionListByQuestionBankCode(DiagnosisBaseQuestionModel model) throws Exception;

    boolean diagnosisBaseQuestionSave(DiagnosisBaseQuestionModel model)throws Exception ;

    boolean deleteDiagnosisBaseQuestion(String baseCode, String questionBookCode)throws Exception;

    boolean baseQuestionUpdate(DiagnosisBaseQuestionModel model) throws Exception;

    /***********************************课中测**************************************/
    Map<String, Object> classTestList(DiagnosisInClassTestModel model)throws Exception ;

    String classTestSave(DiagnosisInClassTestModel model)throws Exception ;

    Map<String, Object>  baseQuestionListByClassTestCode(DiagnosisInClassRelationModel model)throws Exception ;

    boolean classTestDelete(DiagnosisInClassTestModel model)throws Exception ;

    Map<String, Object> classTestStartTest(DiagnosisInClassTestModel model)throws Exception ;

    Map<String, Object> classTestRelationUpdate(DiagnosisInClassRelationModel model)throws Exception ;

    Map<String, Object> classTestRelationSingleReport(String baseCode, String inClassTestCode, Integer classCode)throws Exception ;

    Map<String,Object> reportOverview(DiagnosisStudentAnswerSheetModel model)throws  Exception;

//    Map<String,Object> reportByKnowledge(DiagnosisStudentAnswerSheetModel model)throws Exception ;

//    Map<String,Object> reportByAccuracy(DiagnosisStudentAnswerSheetModel model)throws Exception ;

    Map<String,Object> studentList(DiagnosisStudentAnswerMachineModel model)throws Exception ;

    boolean studentSave(DiagnosisStudentAnswerMachineModel model)throws Exception ;

    boolean studentDelete(DiagnosisStudentAnswerMachineModel model)throws Exception ;

    boolean studentUpdate(DiagnosisStudentAnswerMachineModel model)throws Exception ;

    boolean questionBankUpdate(DiagnosisQuestionBankModel model)throws Exception ;

    boolean collectExercisesAnswer(ExercisesAnswer ea)throws Exception ;

    boolean classTestQuestionAdjustment(DiagnosisInClassTestModel model)throws Exception ;

    Map<String,Object> baseQuestionCodeListByClassTestCode(DiagnosisInClassTestModel model)throws Exception ;

    Map<String, Object> unlockClassTest(DiagnosisStudentAnswerSheetModel model)throws Exception ;

    boolean reportVerification(DiagnosisStudentAnswerSheetModel model) throws Exception;
}
