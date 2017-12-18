package com.eedu.diagnosis.manager.controller.report;

import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eeduspace.b2b.report.exception.ReportException;
import com.eeduspace.b2b.report.model.report.principal.*;
import com.eeduspace.b2b.report.service.PrincipalSingleReportOpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 校长单科报告控制层
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-04 13:55
 **/
@RestController
@RequestMapping("/principalSingleReport")
@Slf4j
public class PrincipalSingleReportController {
    @Autowired
    private PrincipalSingleReportOpenService principalSingleReportOpenService;

    /**
     * 获取学科年级成绩分布统计
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getDistributionOfSubjectScores/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getDistributionOfSubjectScores(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            GradeResultsDto distributionOfSubjectScores = principalSingleReportOpenService.getDistributionOfSubjectScores(releaseExamCode, subjectCode);
            baseResponse.setResult(distributionOfSubjectScores);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取学科历史平均分变化
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getSubjectHistoryAverage/{releaseExamCode}/{subjectCode}/{schoolCode}/{gradeCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getSubjectHistoryAverage(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("schoolCode") String schoolCode,
                                                       @PathVariable("gradeCode") String gradeCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(schoolCode)){
            log.error("getDistributionOfSubjectScores  Exception：schoolCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if(StringUtils.isEmpty(gradeCode)){
            log.error("getDistributionOfSubjectScores  Exception：gradeCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<HistoryAverageDto> subjectHistoryAverage = principalSingleReportOpenService.getSubjectHistoryAverage(releaseExamCode, subjectCode,schoolCode,gradeCode);
            baseResponse.setResult(subjectHistoryAverage);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 各班学生学科成绩分布情况概览
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getClassStudentSubjectResults/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getClassStudentSubjectResults(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<GradeResultsDto> classStudentSubjectResults = principalSingleReportOpenService.getClassStudentSubjectResults(releaseExamCode, subjectCode);
            baseResponse.setResult(classStudentSubjectResults);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }


    /**
     * 各班级学科提分空间
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getClasssSubjectMentionScore/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getClasssSubjectMentionScore(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<ClassMentionScoreDto> classsSubjectMentionScore = principalSingleReportOpenService.getClasssSubjectMentionScore(releaseExamCode, subjectCode);
            baseResponse.setResult(classsSubjectMentionScore);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 各班级学科能力水平概览
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getClassSubjectAbility/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getClassSubjectAbility(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            GradeSummaryAbilityDto classSubjectAbility = principalSingleReportOpenService.getClassSubjectAbility(releaseExamCode, subjectCode);
            baseResponse.setResult(classSubjectAbility);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 学科成绩变动信息
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getClassSubjectScoreChange/{releaseExamCode}/{subjectCode}/{schoolCode}/{gradeCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getClassSubjectScoreChange(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                   @PathVariable("schoolCode") String schoolCode,
                                                   @PathVariable("gradeCode") String gradeCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(schoolCode)){
            log.error("getDistributionOfSubjectScores  Exception：schoolCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if(StringUtils.isEmpty(gradeCode)){
            log.error("getDistributionOfSubjectScores  Exception：gradeCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<GradeSubjectScoreChangeDto> classSubjectScoreChange = principalSingleReportOpenService.getClassSubjectScoreChange(releaseExamCode, subjectCode,schoolCode,gradeCode);
            baseResponse.setResult(classSubjectScoreChange);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 各班学科能力水平历史变化情况概览
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getClassAbilistyHistoryChange/{releaseExamCode}/{subjectCode}/{schoolCode}/{gradeCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getClassAbilistyHistoryChange(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                      @PathVariable("schoolCode") String schoolCode,
                                                      @PathVariable("gradeCode") String gradeCode,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(schoolCode)){
            log.error("getDistributionOfSubjectScores  Exception：schoolCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if(StringUtils.isEmpty(gradeCode)){
            log.error("getDistributionOfSubjectScores  Exception：gradeCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<ClassHistoryAbilityChangeDto> classAbilistyHistoryChange = principalSingleReportOpenService.getClassAbilistyHistoryChange(releaseExamCode, subjectCode,schoolCode,gradeCode);
            baseResponse.setResult(classAbilistyHistoryChange);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 教师学科成绩---》学科成绩平均分比对
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getTeacherSubjectAveScore/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getTeacherSubjectAveScore(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            GradeTeacherSubjectDto teacherSubjectAveScore = principalSingleReportOpenService.getTeacherSubjectAveScore(releaseExamCode, subjectCode);
            baseResponse.setResult(teacherSubjectAveScore);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }


    /**
     * 教师教学成绩----》学科教学成绩贡献率
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getTeacherSubjectContribution/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getTeacherSubjectContribution(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<TeacherContributionDto> teacherSubjectContribution = principalSingleReportOpenService.getTeacherSubjectContribution(releaseExamCode, subjectCode);
            baseResponse.setResult(teacherSubjectContribution);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取各知识点教师教学情况
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getTeachingKnowledgeModule/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getTeachingKnowledgeModule(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<TeachingKnowledgeModuleDto> teachingKnowledgeModule = principalSingleReportOpenService.getTeachingKnowledgeModule(releaseExamCode, subjectCode);
            baseResponse.setResult(teachingKnowledgeModule);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取各项学科能力教师教学情况
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getTeachingAbility/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getTeachingAbility(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<TeachingAbilityDto> teachingAbility = principalSingleReportOpenService.getTeachingAbility(releaseExamCode, subjectCode);
            baseResponse.setResult(teachingAbility);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取教师学科平均成绩历史变化
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getTeacherHistoryAveScore/{releaseExamCode}/{subjectCode}/{schoolCode}/{gradeCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getTeacherHistoryAveScore(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                  @PathVariable("schoolCode") String schoolCode,
                                                  @PathVariable("gradeCode") String gradeCode,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(schoolCode)){
            log.error("getDistributionOfSubjectScores  Exception：schoolCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if(StringUtils.isEmpty(gradeCode)){
            log.error("getDistributionOfSubjectScores  Exception：gradeCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<TeacherHistoryStandardDto> teacherHistoryAveScore = principalSingleReportOpenService.getTeacherHistoryAveScore(releaseExamCode, subjectCode,schoolCode,gradeCode);
            baseResponse.setResult(teacherHistoryAveScore);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取教师学科贡献指数变化情况
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getTeacherSubjectContributionHistoryChange/{releaseExamCode}/{subjectCode}/{schoolCode}/{gradeCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getTeacherSubjectContributionHistoryChange(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                                   @PathVariable("schoolCode") String schoolCode,
                                                                   @PathVariable("gradeCode") String gradeCode,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(schoolCode)){
            log.error("getDistributionOfSubjectScores  Exception：schoolCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if(StringUtils.isEmpty(gradeCode)){
            log.error("getDistributionOfSubjectScores  Exception：gradeCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<TeacherHistoryContributionDto> teacherSubjectContributionHistoryChange = principalSingleReportOpenService.getTeacherSubjectContributionHistoryChange(releaseExamCode, subjectCode,schoolCode,gradeCode);
            baseResponse.setResult(teacherSubjectContributionHistoryChange);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }


    /**
     * 获取班级学生原始得分信息
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getsClassStudentOriginalScore/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getsClassStudentOriginalScore(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                       @PathVariable("subjectCode") String subjectCode,
                                                       @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<ClassStudentScoreDto> classStudentScoreDtoList = principalSingleReportOpenService.getsClassStudentOriginalScore(releaseExamCode, subjectCode);
            baseResponse.setResult(classStudentScoreDtoList);
            return baseResponse;
        } catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

    /**
     * 获取班级学生标准分信息
     * @param requestId 请求id
     * @param subjectCode 学科code
     * @param releaseExamCode 考试发布记录code
     * @return
     */
    @GetMapping(value = "/getsClassStudentStandardScore/{releaseExamCode}/{subjectCode}", produces = "application/json; charset=utf-8")
    public BaseResponse getsClassStudentStandardScore(HttpServletRequest httpServletRequest,@RequestParam("requestId") String requestId,
                                                      @PathVariable("subjectCode") String subjectCode,
                                                      @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getDistributionOfSubjectScores  Exception：releaseExamCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getDistributionOfSubjectScores  Exception：subjectCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<ClassStudentScoreDto> classStudentScoreDtoList = principalSingleReportOpenService.getsClassStudentStandardScore(releaseExamCode, subjectCode);
            baseResponse.setResult(classStudentScoreDtoList);
            return baseResponse;
        }catch (ReportException reportException){
            reportException.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_NOTFOUND.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
    }

}

