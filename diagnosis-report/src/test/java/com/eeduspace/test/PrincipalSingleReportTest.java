package com.eeduspace.test;

import com.eeduspace.b2b.report.model.report.principal.*;
import com.eeduspace.b2b.report.service.PrincipalSingleReportOpenService;
import com.eeduspace.report.B2bReportApplication;
import com.eeduspace.report.util.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 教师报告测试类
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-22 16:27
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = B2bReportApplication.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PrincipalSingleReportTest {
    @Autowired
    private PrincipalSingleReportOpenService principalSingleReportOpenService;

    /**
     * 学科年级成绩分布统计
     * @throws Exception
     */
    @Test
    public void getDistributionOfSubjectScores() throws Exception{
        String releaseCode="EEA3603424AF4C92B6059961AFF01343";
        String subjectCode="2";
        GradeResultsDto gradeResultsDto = principalSingleReportOpenService.getDistributionOfSubjectScores(releaseCode, subjectCode);
        System.out.println("getDistributionOfSubjectScores=========>"+GsonUtils.toJson(gradeResultsDto));
    }

    /**
     * 获取学科历史平均分变化
     * @throws Exception
     */
    @Test
    public void getSubjectHistoryAverage() throws Exception{
        String releaseCode="70906EA984824EF5B8AB003AFE846DBA";
        String subjectCode="2";
        String schoolCode="343";
        String gradeCode="32";
        List<HistoryAverageDto> historyAverage = principalSingleReportOpenService.getSubjectHistoryAverage(releaseCode, subjectCode,schoolCode,gradeCode);
        System.out.println("getSubjectHistoryAverage=========>"+GsonUtils.toJson(historyAverage));

    }

    /**
     * 各班学生学科成绩分布情况概览
     * @throws Exception
     */
    @Test
    public void getClassStudentSubjectResults() throws Exception{
        String releaseCode="DA9A2321B2D84B019958EC839DFB9BB1";
        String subjectCode="2";
        List<GradeResultsDto> studentSubjectResults = principalSingleReportOpenService.getClassStudentSubjectResults(releaseCode, subjectCode);
        System.out.println("getSubjectHistoryAverage=========>"+GsonUtils.toJson(studentSubjectResults));
    }

    /**
     * 各班级学科提分空间
     * @throws Exception
     */
    @Test
    public  void getClasssSubjectMentionScore() throws Exception{
        String releaseCode="EEA3603424AF4C92B6059961AFF01343";
        String subjectCode="2";
        List<ClassMentionScoreDto> mentionScore = principalSingleReportOpenService.getClasssSubjectMentionScore(releaseCode, subjectCode);
        System.out.println("getClasssSubjectMentionScore=========>"+GsonUtils.toJson(mentionScore));

    }

    /**
     * 各班级学科能力水平概览
     * @throws Exception
     */
    @Test
    public void getClassSubjectAbility() throws Exception{
        String releaseCode="DA9A2321B2D84B019958EC839DFB9BB1";
        String subjectCode="2";
        GradeSummaryAbilityDto classSubjectAbility = principalSingleReportOpenService.getClassSubjectAbility(releaseCode, subjectCode);
        System.out.println("getClassSubjectAbility=========>"+GsonUtils.toJson(classSubjectAbility));

    }
    /**
     * 学科成绩变动信息
     * @throws Exception
     */
    @Test
    public void getClassSubjectScoreChange() throws Exception{
        String releaseCode="73731290E4464613919560CC10B1D292";
        String subjectCode="1";
        String schoolCode="708";
        String gradeCode="32";
        List<GradeSubjectScoreChangeDto> classSubjectScoreChange = principalSingleReportOpenService.getClassSubjectScoreChange(releaseCode, subjectCode,schoolCode,gradeCode);
        System.out.println("getClassSubjectScoreChange=========>"+GsonUtils.toJson(classSubjectScoreChange));

    }

    /**
     * 各班学科能力水平历史变化情况概览
     * @throws Exception
     */
    @Test
    public void getClassAbilistyHistoryChange() throws Exception{
        String releaseCode="70906EA984824EF5B8AB003AFE846DBA";
        String subjectCode="2";
        String schoolCode="343";
        String gradeCode="32";
        List<ClassHistoryAbilityChangeDto> classAbilistyHistoryChange = principalSingleReportOpenService.getClassAbilistyHistoryChange(releaseCode, subjectCode,schoolCode,gradeCode);
        System.out.println("getClassAbilistyHistoryChange=========>"+GsonUtils.toJson(classAbilistyHistoryChange));

    }

    /**
     * 教师学科成绩---》学科成绩平均分比对
     * @throws Exception
     */
    @Test
    public void getTeacherSubjectAveScore() throws Exception{
        String releaseCode="EEA3603424AF4C92B6059961AFF01343";
        String subjectCode="2";
        GradeTeacherSubjectDto teacherSubjectAveScore = principalSingleReportOpenService.getTeacherSubjectAveScore(releaseCode, subjectCode);
        System.out.println("getTeacherSubjectAveScore=========>"+GsonUtils.toJson(teacherSubjectAveScore));

    }

    /**
     * 教师教学成绩----》学科教学成绩贡献率
     * @throws Exception
     */
    @Test
    public void getTeacherSubjectContribution() throws Exception{
        String releaseCode="EEA3603424AF4C92B6059961AFF01343";
        String subjectCode="2";
        List<TeacherContributionDto> teacherSubjectContribution = principalSingleReportOpenService.getTeacherSubjectContribution(releaseCode, subjectCode);
        System.out.println("getTeacherSubjectContribution=========>"+GsonUtils.toJson(teacherSubjectContribution));

    }

    /**
     * 获取各知识点教师教学情况
     * @throws Exception
     */
    @Test
    public void getTeachingKnowledgeModule() throws Exception{
        String releaseCode="EEA3603424AF4C92B6059961AFF01343";
        String subjectCode="2";
        List<TeachingKnowledgeModuleDto> teachingKnowledgeModule = principalSingleReportOpenService.getTeachingKnowledgeModule(releaseCode, subjectCode);
        System.out.println("getTeachingKnowledgeModule=========>"+GsonUtils.toJson(teachingKnowledgeModule));
    }

    /**
     * 获取各项学科能力教师教学情况
     * @throws Exception
     */
    @Test
    public void getTeachingAbility() throws Exception{
        String releaseCode="EEA3603424AF4C92B6059961AFF01343";
        String subjectCode="2";
        List<TeachingAbilityDto> teachingAbility = principalSingleReportOpenService.getTeachingAbility(releaseCode, subjectCode);
        System.out.println("getTeachingAbility=========>"+GsonUtils.toJson(teachingAbility));

    }

    /**
     * 获取教师学科平均成绩历史变化
     * @throws Exception
     */
    @Test
    public void getTeacherHistoryAveScore() throws Exception{
        String releaseCode="70906EA984824EF5B8AB003AFE846DBA";
        String subjectCode="2";
        String schoolCode="343";
        String gradeCode="32";
        List<TeacherHistoryStandardDto> teacherHistoryAveScore = principalSingleReportOpenService.getTeacherHistoryAveScore(releaseCode, subjectCode,schoolCode,gradeCode);
        System.out.println("getTeacherHistoryAveScore=========>"+GsonUtils.toJson(teacherHistoryAveScore));

    }

    /**
     * 获取教师学科贡献指数变化情况
     * @throws Exception
     */
    @Test
    public void getTeacherSubjectContributionHistoryChange() throws Exception{
        String releaseCode="70906EA984824EF5B8AB003AFE846DBA";
        String subjectCode="2";
        String schoolCode="343";
        String gradeCode="32";
        List<TeacherHistoryContributionDto> teacherSubjectContributionHistoryChange = principalSingleReportOpenService.getTeacherSubjectContributionHistoryChange(releaseCode, subjectCode,schoolCode,gradeCode);
        System.out.println("getTeacherHistoryAveScore=========>"+GsonUtils.toJson(teacherSubjectContributionHistoryChange));
    }

    /**
     * 获取班级原始分
     * @throws Exception
     */
    @Test
    public void getsClassStudentOriginalScore() throws Exception{
        String releaseCode="EEA3603424AF4C92B6059961AFF01343";
        String subjectCode="2";
        List<ClassStudentScoreDto> classStudentScoreDtoList = principalSingleReportOpenService.getsClassStudentOriginalScore(releaseCode, subjectCode);
        System.out.println("getsClassStudentOriginalScore=========>"+GsonUtils.toJson(classStudentScoreDtoList));

    }

    /**
     * 获取班级标准分信息
     * @throws Exception
     */
    @Test
    public void getsClassStudentStandardScore() throws Exception{
        String releaseCode="EEA3603424AF4C92B6059961AFF01343";
        String subjectCode="2";
        List<ClassStudentScoreDto> classStudentScoreDtoList = principalSingleReportOpenService.getsClassStudentStandardScore(releaseCode, subjectCode);
        System.out.println("getsClassStudentStandardScore=========>"+GsonUtils.toJson(classStudentScoreDtoList));
    }
}
