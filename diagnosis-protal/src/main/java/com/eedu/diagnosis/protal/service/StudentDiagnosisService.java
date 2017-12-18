package com.eedu.diagnosis.protal.service;

import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.protal.model.DiagnosisPaperModel;
import com.eedu.diagnosis.protal.model.request.AnswerSheetModel;
import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.model.response.DiagnosisRecordStudentModel;
import com.eeduspace.b2b.report.model.StudentReportDto;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/20.
 */
public interface StudentDiagnosisService {
    /**
     * 学生获取诊断列表
     * @param model
     * @return
     */
    PageInfo<DiagnosisRecordStudentModel> examList(StudentModel model) throws Exception;

    /**
     * 获取试卷详情
     * @param model
     * @return
     * @throws Exception
     */
    PaperSystem paperDetail(DiagnosisPaperModel model) throws Exception;

    /**
     * 提交答题卡
     * @param model
     * @return
     */
    Map<String,Object> submit(AnswerSheetModel model) throws Exception;

    /**
     * 初始化学生诊断页面
     * @param model
     * @return
     */
    List<Map<Integer, Object>> initExamList(StudentModel model) throws IllegalAccessException, InstantiationException, Exception;

    /**
     * 再次获取报告
     * @param diagnosisRecordCode
     * @return
     */
    StudentReportDto reGetReprot(String diagnosisRecordCode) throws Exception;

    /**
     * 获取试卷 附带学生的答题信息
     */
    PaperSystem getPaperAndAnswerResult(DiagnosisPaperModel model) throws Exception;

    /**
     * 校验学生该测试是否已经参加
     * @param diagnosisRecordCode
     * @return
     */
    boolean checkExamStatus(String diagnosisRecordCode) throws Exception;

    /**
     * 学生获取全科考试列表
     * @param model
     * @return
     */
    PageInfo<DiagnosisRecordStudentDto> complexExamList(StudentModel model) throws Exception;

    /**
     * 学生获取全科考试各学科考试列表
     * @param model
     * @return
     */
    List<DiagnosisRecordStudentModel> complexSubjectExamList(DiagnosisRecordStudentModel model) throws Exception;
}
