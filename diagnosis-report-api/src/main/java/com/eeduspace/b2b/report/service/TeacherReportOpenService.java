package com.eeduspace.b2b.report.service;

import com.eeduspace.b2b.report.model.report.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>描述：教师报告接口</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * @Date 2017/3/22 14:16
 **/

public interface TeacherReportOpenService {



    /**
     * 获取教师班级成绩表
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    List<ClassResultsTableModel> getClassResultsTable(String teacherCode,String subjectCode,String releaseExamCode) throws Exception;

    /**
     *获取教师班级成绩表 为前端定制
     * @param teacherCode
     * @param subjectCode
     * @param releaseExamCode
     * @return
     * @throws Exception
     */
    Map<String,Object> getClassResultsTableForDaqiao(String teacherCode,String subjectCode,String releaseExamCode) throws Exception;

    /**
     * 获取班级合格率
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     */
    List<PassRateModel> getClassQualified(String teacherCode,String subjectCode,String releaseExamCode) throws Exception;

    /**
     * 获取班级达标率
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    HashMap<String,HashMap<String,Object>> getClassStandard(String teacherCode, String subjectCode, String releaseExamCode) throws Exception;

    /**
     * 获取错题排行榜
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    Map<String, List<WrongQuestionRankModel>> getClassWrongQuestionRank(String teacherCode, String subjectCode, String releaseExamCode) throws Exception;

    /**
     * 获取班级知识模块掌握
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    Map<String, Double> getClassKnowledgeMoudle(String teacherCode, String subjectCode, String releaseExamCode) throws Exception;

    /**
     * 获取班级共性错误知识点
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    Map<String,Integer> getClassWrongKnowledge(String teacherCode, String subjectCode, String releaseExamCode) throws Exception;

    /**
     * 各班级错误知识点情况
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     *
     * @return
     * @throws Exception
     */
    List<List<String>> getClassWrongKnowledgeGroupByClass(String teacherCode, String subjectCode, String releaseExamCode) throws Exception;

    /**
     * 获取班级平均能力
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return //0 记忆能力  1理解能力 2应用能力 3分析能力 4 综合能力
     * @throws Exception
     */
    Map<String, Map<String, Double>> getClassAbilityScore(String teacherCode, String subjectCode, String releaseExamCode) throws Exception;

    /**
     * 获取单个能力历史平均
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return //0 记忆能力  1理解能力 2应用能力 3分析能力 4 综合能力
     * @throws Exception
     */
    Map<String, Map<String, Map<Long, Double>>> getClassSingleAbilityScore(String teacherCode, String subjectCode, String releaseExamCode) throws Exception;

    /**
     * 学科能力平均值
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 发布考试记录code
     * @return //0 记忆能力  1理解能力 2应用能力 3分析能力 4 综合能力
     * @throws Exception
     */
    Map<String, Map<Long, Double>> getClassSubjectAverageScore(String teacherCode, String subjectCode, String releaseExamCode) throws Exception;

    /**
     * 获取单个班级班级概况报告
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @param classCode 班级code
     * @return
     * @throws Exception
     */
    SingleClassReportModel getSingleClassReport(String teacherCode, String subjectCode, String releaseExamCode,String classCode) throws Exception;

    /**
     * 获取班级标准分变动
     * @param teacherCode
     * @param subjectCode
     * @param releaseExamCode
     * @param type 0 同步辅导  1 学科测评
     * @return
     */
    StandardScoreChangeModel getStandardScoreChange(Integer type,String teacherCode,String subjectCode,String releaseExamCode) throws Exception;
}
