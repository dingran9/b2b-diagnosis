package com.eedu.diagnosis.manager.service;

import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.manager.model.request.*;
import com.eedu.diagnosis.manager.model.response.DiagnosisClassRelationModel;
import com.eedu.diagnosis.manager.model.response.DiagnosisRecordTeacherModel;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dqy on 2017/3/20.
 */
public interface TeacherDiagnosisService {
    /**
     * 教师发布诊断
     * @param model
     * @return
     */
    boolean releaseSingleDiagnosis(DiagnosisReleaseModle model) throws Exception;

    /**
     * 教师获取诊断列表
     * @param model
     * @return
     */
    Map<String, Object> getTeacherDiagnosisList(TeacherModel model) throws Exception;

    /**
     * 将教师所选试题组合成试卷 存入redis，并保存为诊断试卷  返回诊断卷code
     * @return
     * @param smallQuestionSystems
     */
    String saveClassworkQuestionsToPaper(ClassworkModel smallQuestionSystems) throws Exception;

    /**
     * 将教师所选作业试卷保存为诊断卷，返回诊断卷code
     * @param model
     * @return
     */
    String saveClassworkPaper(ClassworkModel model) throws Exception;

    /**
     * 获取每个教师诊断记录下该老师所教班级的平均分等信息
     * @param model
     * @return
     */
    List<Map<String,Object>> getDiagnosisDetail(TeacherModel model) throws Exception;

    /**
     * 获取试卷详情
     * @param model
     * @return
     */
    PaperSystem paperDetail(DiagnosisPaperModel model) throws Exception;

    /**
     * 更新班级报告的已读状态
     * @param diagnosisClassRelationCode
     * @return
     */
    boolean updateReadStatus(String diagnosisClassRelationCode) throws Exception;

    /**
     * 校长、教师获取全科考试列表
     * @param model
     * @return
     */
    PageInfo<DiagnosisRecordTeacherModel> getDiagnosisListForMaster(TeacherModel model) throws Exception;

    /**
     * 校长查看报告时 获取该次考试所包含的学科
     * @param code
     * @return
     */
    List<Integer> getSubjectsForExam(String code) throws Exception;

    /**
     *普通教师获取班级诊断历史（参数为classCodes） 班级报告都为已读状态的数据
     * @param model
     * @return
     */
    Map<String,Object> getDiagnosisList(TeacherModel model) throws Exception;

    Map<String,Object> getTeacherDiagnosisListV2(TeacherModel model) throws Exception;

    /**
     * 教师获取全科考试历史记录
     * @param model
     * @return
     */
    PageInfo<DiagnosisRecordTeacherModel> getDiagnosisHistoryListForMaster(TeacherModel model) throws Exception;

    /**
     * 教育管理者发布考试
     * @param model
     * @return
     * @throws Exception
     */
    boolean teachingManagerRelease(ReleaseAreaExamModel model) throws Exception;

    Map<String,Object> areaExamList(DiagnosisRecordTeacherRequestModel teacherModel) throws Exception;

    /**
     * 获取全科考单科发布的试卷列表
     * @param teacherModel
     * @return
     * @throws Exception
     */
    List<DiagnosisClassRelationModel> getExamPaperByParameter(DiagnosisRecordTeacherRequestModel teacherModel) throws Exception;

    /**
     *全区考试 所包含的学科列表
     * @param code
     * @return
     * @throws Exception
     */
    List<Integer> examSubjectList(String code) throws Exception;

}
