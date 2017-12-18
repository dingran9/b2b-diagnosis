package com.eeduspace.b2b.report.service;

import com.eeduspace.b2b.report.model.report.principal.*;

import java.util.List;

/**
 * <p>描述:校长单科报告服务</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/4/25 10:45
 * */
public interface PrincipalSingleReportOpenService {
    /**
     *学科年级成绩分布统计
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode  学科code
     */
    GradeResultsDto getDistributionOfSubjectScores(String releaseExamCode, String subjectCode) throws Exception;

    /**
     * 获取学科历史平均分变化
     * @param releaseExamCode 发布记录code
     * @param subjectCode  学科code
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     */
    List<HistoryAverageDto> getSubjectHistoryAverage(String releaseExamCode, String subjectCode,String schoolCode,String gradeCode) throws Exception;

    /**
     * 各班学生学科成绩分布情况概览
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<GradeResultsDto> getClassStudentSubjectResults(String releaseExamCode,String subjectCode) throws  Exception;

    /**
     * 各班级学科提分空间
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<ClassMentionScoreDto> getClasssSubjectMentionScore(String releaseExamCode, String subjectCode) throws Exception;

    /**
     * 各班级学科能力水平概览
     * @param releaseExamCode 发布记录code
     * @param subjectCode 学科code
     * @throws Exception
     */
    GradeSummaryAbilityDto getClassSubjectAbility(String releaseExamCode, String subjectCode) throws Exception;

    /**
     * 学科成绩变动信息
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode 学科code
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @return
     * @throws Exception
     */
    List<GradeSubjectScoreChangeDto> getClassSubjectScoreChange(String releaseExamCode,String subjectCode,String schoolCode,String gradeCode) throws Exception;

    /**
     * 各班学科能力水平历史变化情况概览
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode 学科code
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @throws Exception
     */
    List<ClassHistoryAbilityChangeDto> getClassAbilistyHistoryChange(String releaseExamCode,String subjectCode,String schoolCode,String gradeCode) throws Exception;

    /**
     * 教师学科成绩---》学科成绩平均分比对
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @throws Exception
     */
    GradeTeacherSubjectDto getTeacherSubjectAveScore(String releaseExamCode,String subjectCode) throws  Exception;

    /**
     * 教师教学成绩----》学科教学成绩贡献率
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<TeacherContributionDto> getTeacherSubjectContribution(String releaseExamCode,String subjectCode) throws Exception;

    /**
     * 获取各知识点教师教学情况
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<TeachingKnowledgeModuleDto> getTeachingKnowledgeModule(String releaseExamCode,String subjectCode) throws Exception;

    /**
     * 获取各项学科能力教师教学情况
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<TeachingAbilityDto> getTeachingAbility(String releaseExamCode,String subjectCode) throws Exception;

    /**
     * 获取教师学科平均成绩历史变化
     * @param releaseExamCode 考试发布记录code
     * @param subjectCode 学科code
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @throws Exception
     */
    List<TeacherHistoryStandardDto>  getTeacherHistoryAveScore(String releaseExamCode,String subjectCode,String schoolCode,String gradeCode) throws Exception;


    /**
     * 获取教师学科贡献指数变化情况
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @param schoolCode 学校code
     * @param gradeCode 学年code
     * @throws Exception
     */
    List<TeacherHistoryContributionDto> getTeacherSubjectContributionHistoryChange(String releaseExamCode,String subjectCode,String schoolCode,String gradeCode) throws Exception;

    /**
     * 获取班级学生原始得分信息
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<ClassStudentScoreDto> getsClassStudentOriginalScore(String releaseExamCode,String subjectCode) throws Exception;

    /**
     * 获取班级学生标准分信息
     * @param releaseExamCode 发布考试记录code
     * @param subjectCode 学科code
     * @return
     * @throws Exception
     */
    List<ClassStudentScoreDto> getsClassStudentStandardScore(String releaseExamCode,String subjectCode) throws Exception;

}
