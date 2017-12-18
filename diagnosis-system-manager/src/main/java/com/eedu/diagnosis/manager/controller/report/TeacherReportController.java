package com.eedu.diagnosis.manager.controller.report;

import com.eedu.diagnosis.common.model.paperEntity.*;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordTeacherDto;
import com.eedu.diagnosis.exam.api.openService.DiagnosisRecordTeacherOpenService;
import com.eedu.diagnosis.manager.model.GsonUtils;
import com.eedu.diagnosis.manager.model.ResultResponse;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eeduspace.b2b.report.model.StudentAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentQuestionAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentReportDto;
import com.eeduspace.b2b.report.model.question.KnowledgeModel;
import com.eeduspace.b2b.report.model.question.Productionmodels;
import com.eeduspace.b2b.report.model.question.Sons;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.service.B2BReportOpenService;
import com.eeduspace.b2b.report.service.TeacherReportOpenService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师报告
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-22 12:45
 **/
@RestController
@RequestMapping("/teacherReport")
@Slf4j
public class TeacherReportController {
    @Autowired
    private TeacherReportOpenService teacherReportOpenService;
    @Autowired
    private B2BReportOpenService b2BReportOpenService;
    @Autowired
    private DiagnosisRecordTeacherOpenService diagnosisRecordTeacherOpenService;

    /**
     * 获取班级平均标准分变化历史
     * @param requestId 请求标识
     * @param teacherCode  教师code
     * @param subjectCode 学科code
     * @deprecated  type  // 已弃用
     * @param releaseExamCode 发布考试记录code
     * @return
     */
    @RequestMapping(value = "/getStandardScoreChange/{teacherCode}/{subjectCode}/{releaseExamCode}/{type}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getStandardScoreChange(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("type") Integer type,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            DiagnosisRecordTeacherDto diagnosisRecordTeacherDto = diagnosisRecordTeacherOpenService.getDiagnosisRecordTeacherByCode(releaseExamCode);
            log.debug("诊断类型---》"+diagnosisRecordTeacherDto.getDiagnosisType());
            StandardScoreChangeModel standardScoreChange = teacherReportOpenService.getStandardScoreChange(diagnosisRecordTeacherDto.getDiagnosisType(), teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(standardScoreChange);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * <p>描述：获取教师班级成绩表</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return 
     **/
    @RequestMapping(value = "/getClassResultsTable/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassResultsTable(@RequestParam("requestId") String requestId,
                                                 @PathVariable("teacherCode") String teacherCode,
                                                 @PathVariable("subjectCode") String subjectCode,
                                                 @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            List<ClassResultsTableModel> classResultsTable = teacherReportOpenService.getClassResultsTable(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classResultsTable);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }



    /**
     * <p>描述：获取教师班级成绩表 大桥定制</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassResultsTableForDaqiao/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassResultsTableForDaqiao(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            Map<String, Object> classResultsTableForDaqiao = teacherReportOpenService.getClassResultsTableForDaqiao(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classResultsTableForDaqiao);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }



    /**
     * <p>描述：获取教师班级合格率</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassQualified/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassQualified(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            List<PassRateModel> classQualified = teacherReportOpenService.getClassQualified(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classQualified);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * <p>描述：获取教师班级达标率</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassStandard/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassStandard(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            HashMap<String, HashMap<String, Object>> classStandard = teacherReportOpenService.getClassStandard(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classStandard);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * <p>描述：获取教师班级错题排行榜</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassWrongQuestionRank/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassWrongQuestionRank(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            Map<String, List<WrongQuestionRankModel>> classWrongQuestionRank = teacherReportOpenService.getClassWrongQuestionRank(teacherCode, subjectCode, releaseExamCode);

            baseResponse.setResult(classWrongQuestionRank);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * <p>描述：获取教师班级知识模块掌握情况</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassKnowledgeMoudle/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassKnowledgeMoudle(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            Map<String, Double> classKnowledgeMoudle = teacherReportOpenService.getClassKnowledgeMoudle(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classKnowledgeMoudle);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * <p>描述：获取教师班级共性错误知识点</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassWrongKnowledge/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassWrongKnowledge(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            Map<String, Integer> classWrongKnowledge = teacherReportOpenService.getClassWrongKnowledge(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classWrongKnowledge);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * <p>描述：获取教师班级错误知识点情况</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassWrongKnowledgeGroupByClass/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassWrongKnowledgeGroupByClass(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            List<List<String>> classWrongKnowledgeGroupByClass = teacherReportOpenService.getClassWrongKnowledgeGroupByClass(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classWrongKnowledgeGroupByClass);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * <p>描述：获取教师班级平均能力</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassAbilityScore/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassAbilityScore(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            Map<String, Map<String, Double>> classAbilityScore = teacherReportOpenService.getClassAbilityScore(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classAbilityScore);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * <p>描述：获取教师班级成学科 单个能力历史平均</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassSingleAbilityScore/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassSingleAbilityScore(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            Map<String, Map<String, Map<Long, Double>>> classSingleAbilityScore = teacherReportOpenService.getClassSingleAbilityScore(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classSingleAbilityScore);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * <p>描述：获取教师班级学科能力平均值</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getClassSubjectAverageScore/{teacherCode}/{subjectCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getClassSubjectAverageScore(@RequestParam("requestId") String requestId,
                                             @PathVariable("teacherCode") String teacherCode,
                                             @PathVariable("subjectCode") String subjectCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getClassResultsTable  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getClassResultsTable  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getClassResultsTable  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            Map<String, Map<Long, Double>> classSubjectAverageScore = teacherReportOpenService.getClassSubjectAverageScore(teacherCode, subjectCode, releaseExamCode);
            baseResponse.setResult(classSubjectAverageScore);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    @RequestMapping(value = "/testStudentReport", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse testStudentReport(@RequestParam("requestId") String requestId){
        BaseResponse baseResponse=new BaseResponse(requestId);
        int i=1;
        int wrong=0;
        int right=0;
        RestTemplate restTemplate=new RestTemplate();
        SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(600000);
        factory.setReadTimeout(600000);
        restTemplate.setRequestFactory(factory);
        List<StudentQuestionAnswerResultDto> resultDtos= Lists.newArrayList();
        ResultResponse forObject = restTemplate.getForObject("http://211.157.179.196:9090/llsfw/paperController/getPaperByscore3/8a2a74685acc4b52015b0de990f0391c", ResultResponse.class);
        PaperSystem paperModel= GsonUtils.toEntity(GsonUtils.toJson(forObject.getDatas()),PaperSystem.class);
        for (QuestionSet questionSet : paperModel.getPaperSystemQusetionType()) {
            for (BigQuestionSystem bigQuestionSystem : questionSet.getTypeList()) {
                for (SmallQuestionSystem smallQuestionSystem : bigQuestionSystem.getList()) {
                    List<KnowledgeModel> knowledgeModels= Lists.newArrayList();
                    List<Productionmodels> productionmodelsList= Lists.newArrayList();
                    String qsn=i+"";
                    StudentQuestionAnswerResultDto dto=new StudentQuestionAnswerResultDto();
                    if(i%2==0){
                        dto.setAnswerResult(0);
                        wrong++;
                    }else{
                        dto.setAnswerResult(1);
                        right++;
                    }
                    dto.setScore(smallQuestionSystem.getScore());
                    dto.setIsComplex(0);
                    dto.setQuestionSn(qsn);
                    dto.setQuestionCode(smallQuestionSystem.getId());
                    for (SimpleTree simpleTree : smallQuestionSystem.getTree()) {
                        KnowledgeModel knowledgeModel=new KnowledgeModel();
                        knowledgeModel.setIsTrue(dto.getAnswerResult());
                        knowledgeModel.setKnowledgeName(simpleTree.getName());
                        knowledgeModel.setKnowledgeCode(simpleTree.getId());
                        knowledgeModels.add(knowledgeModel);
                    }
                    for (BaseProductionModel baseProductionModel : smallQuestionSystem.getBasetree()) {
                        Productionmodels productionmodels=new Productionmodels();
                        productionmodels.setBasecodeVo(baseProductionModel.getBasecodeVo());
                        productionmodels.setBasenameVo(baseProductionModel.getBasenameVo());
                        productionmodels.setIsTrue(dto.getAnswerResult());
                        productionmodels.setTypecodeVo(baseProductionModel.getTypecodeVo());
                        productionmodels.setSons(GsonUtils.toList(GsonUtils.toJson(baseProductionModel.getSons()), Sons.class));
                        productionmodelsList.add(productionmodels);
                    }
                    dto.setKnowledgeModelList(knowledgeModels);
                    dto.setProductionmodelsList(productionmodelsList);
                    i++;
                    resultDtos.add(dto);
                }
            }
        }

        StudentAnswerResultDto studentAnswerResultDto=new StudentAnswerResultDto();
        studentAnswerResultDto.setGradeCode(paperModel.getGradeCode());
        studentAnswerResultDto.setSubjectCode(paperModel.getSubjectCode());
        studentAnswerResultDto.setBookTypeVersionCode(paperModel.getBooktype());
        studentAnswerResultDto.setPaperCode(paperModel.getId());
        studentAnswerResultDto.setMakePaperTime(new Timestamp(new Date().getTime()));
        studentAnswerResultDto.setMarkPaperRecordCode("100012");
        studentAnswerResultDto.setPaperName(paperModel.getPaperName());
        studentAnswerResultDto.setPaperStandardScore(80d);
        studentAnswerResultDto.setPaperScore(paperModel.getTotalScore()+"");
        studentAnswerResultDto.setReleaseCode("000001");
        studentAnswerResultDto.setReleaseName("第一次发布考试记录");
        studentAnswerResultDto.setRightCount(right);
        studentAnswerResultDto.setScore(85d);
        studentAnswerResultDto.setUseTime(150);
        studentAnswerResultDto.setWrongCount(wrong);
        studentAnswerResultDto.setClassCode("01");
        studentAnswerResultDto.setClassName("1班");
        studentAnswerResultDto.setUserCode("9002");
        studentAnswerResultDto.setUserName("董大");
        studentAnswerResultDto.setTeacherCode("t_001");
        // studentAnswerResultDto.setTeacherName("董大教授");
        studentAnswerResultDto.setSchoolCode("sc_0001");
        studentAnswerResultDto.setSchoolName("学校名称");
        studentAnswerResultDto.setStudentQuestionAnswerResultDtos(resultDtos);

        StudentReportDto studentReportDto = b2BReportOpenService.generateStudentReport(studentAnswerResultDto);
        baseResponse.setResult(studentReportDto);
        return baseResponse;
    }



    /**
     * <p>描述：获取教师班级学科能力平均值</p>
     * @Author zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * @Date 2017/3/22 14:14
     * @param requestId  请求标识
     * @param teacherCode 教师code  not null
     * @param subjectCode 学科code  not null
     * @param releaseExamCode 考试发布记录code not null
     * @return
     **/
    @RequestMapping(value = "/getSingleClassReport/{teacherCode}/{subjectCode}/{releaseExamCode}/{classCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getSingleClassReport(@RequestParam("requestId") String requestId,
                                                    @PathVariable("teacherCode") String teacherCode,
                                                    @PathVariable("subjectCode") String subjectCode,
                                                    @PathVariable("releaseExamCode") String releaseExamCode,
                                                    @PathVariable("classCode") String classCode
                                             ){
        BaseResponse baseResponse=new BaseResponse(requestId);

        if(StringUtils.isEmpty(teacherCode)){
            log.error("getSingleClassReport  Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if(StringUtils.isEmpty(subjectCode)){
            log.error("getSingleClassReport  Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getSingleClassReport  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        if(StringUtils.isEmpty(classCode)){
            log.error("getSingleClassReport  Exception：classCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            SingleClassReportModel singleClassReport = teacherReportOpenService.getSingleClassReport(teacherCode, subjectCode, releaseExamCode, classCode);
            baseResponse.setResult(singleClassReport);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
}
