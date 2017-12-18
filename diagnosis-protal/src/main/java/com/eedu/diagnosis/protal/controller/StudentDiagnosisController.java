package com.eedu.diagnosis.protal.controller;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.exception.DiagnosisException;
import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.exam.api.dto.DiagnosisRecordStudentDto;
import com.eedu.diagnosis.protal.model.DiagnosisPaperModel;
import com.eedu.diagnosis.protal.model.report.StudentReportVO;
import com.eedu.diagnosis.protal.model.request.AnswerSheetModel;
import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.model.response.DiagnosisRecordStudentModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.StudentDiagnosisService;
import com.eeduspace.b2b.report.exception.ReportException;
import com.eeduspace.b2b.report.model.StudentReportDto;
import com.eeduspace.b2b.report.model.report.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by dqy on 2017/3/20.
 */
@RestController
@RequestMapping("/student/diagnosis")
public class StudentDiagnosisController {
    private final Logger logger = LoggerFactory.getLogger(StudentDiagnosisController.class);
    @Autowired
    private StudentDiagnosisService diagnosisService;


    /**
     * 初始化学生 学科评测主页面
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/initExamList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse initExamList(@RequestParam("requestId") String requestId,
                                 @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        StudentModel model = JSONObject.parseObject(requestBody,StudentModel.class);
        if (null == model.getSchoolCode()) {
            logger.error("StudentDiagnosisController initExamList Exception：schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if (null == model.getClassCode()) {
            logger.error("StudentDiagnosisController initExamList Exception：classCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCode");
        }
        if (null == model.getUserId()) {
            logger.error("StudentDiagnosisController initExamList Exception：userId is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
        }
        if (null == model.getGradeCode()) {
            logger.error("StudentDiagnosisController initExamList Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getStageCode()) {
            logger.error("StudentDiagnosisController initExamList Exception：stageCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
        }

        try {
            List<Map<Integer,Object>> result = diagnosisService.initExamList(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "StudentDiagnosisController initExamList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 根据学科获取作业列表
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/examList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse examList(@RequestParam("requestId") String requestId,
                                         @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        StudentModel model = JSONObject.parseObject(requestBody,StudentModel.class);
        if (null == model.getSubjectCode()) {
            logger.error("StudentDiagnosisController examList Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (null == model.getClassCode()) {
            logger.error("StudentDiagnosisController examList Exception：classCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCode");
        }
        if (null == model.getUserId()) {
            logger.error("StudentDiagnosisController examList Exception：userId is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
        }
        if (null == model.getGradeCode()) {
            logger.error("StudentDiagnosisController examList Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getStageCode()) {
            logger.error("StudentDiagnosisController examList Exception：stageCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
        }
        if (null == model.getPageNum()) {
            logger.error("StudentDiagnosisController examList Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("StudentDiagnosisController examList Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        PageInfo<DiagnosisRecordStudentModel> result = null;
        try {
            result = diagnosisService.examList(model);
        } catch (Exception e) {
            logger.error(requestId + "StudentDiagnosisController examList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        baseResponse.setResult(result);
        return baseResponse;
    }

    /**
     * 学生获取全科考试列表
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/complexExamList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse complexExamList(@RequestParam("requestId") String requestId,
                                        @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        StudentModel model = JSONObject.parseObject(requestBody,StudentModel.class);
        if (null == model.getUserId()) {
            logger.error("StudentDiagnosisController complexExamList Exception：userId is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
        }
        if (null == model.getGradeCode()) {
            logger.error("StudentDiagnosisController complexExamList Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getClassCode()) {
            logger.error("StudentDiagnosisController complexExamList Exception：classCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCode");
        }
        if (null == model.getPageNum()) {
            logger.error("StudentDiagnosisController complexExamList Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("StudentDiagnosisController complexExamList Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        PageInfo<DiagnosisRecordStudentDto> result = null;
        try {
            Map<String,Object> pages = new HashMap<>();
            pages.put("list",null);
            result = diagnosisService.complexExamList(model);
            List<Map<String,Object>> list = new ArrayList<>();
            if(!result.getList().isEmpty()){
                result.getList().forEach(diagnosisRecordStudentDto -> {
                    Map<String,Object> map = new HashMap<>();
                    map.put("diagnosisCode",diagnosisRecordStudentDto.getDiagnosisTeacherRecordCode());
                    map.put("diagnosisName",diagnosisRecordStudentDto.getDiagnosisName());
                    map.put("startTime",diagnosisRecordStudentDto.getStartTime());
                    map.put("endTime",diagnosisRecordStudentDto.getEndTime());
                    map.put("gradeCode",diagnosisRecordStudentDto.getGradeCode());
                    map.put("diagnosisType",diagnosisRecordStudentDto.getDiagnosisType());
                    map.put("examType",diagnosisRecordStudentDto.getExamType());
                    map.put("diagnosisPaperCode",diagnosisRecordStudentDto.getDiagnosisPaperCode());
                    map.put("examStatus",diagnosisRecordStudentDto.getExamStatus());
                    list.add(map);
                });
                pages.put("list",list);
            }
            pages.put("totalPage",result.getPages());
            pages.put("total",result.getTotal());
            pages.put("currPage",result.getPageNum());
            baseResponse.setResult(pages);
        } catch (Exception e) {
            logger.error(requestId + "StudentDiagnosisController complexExamList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 学生获取 全科考试各学科的考试列表
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/complexSubjectExamList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse complexSubjectExamList(@RequestParam("requestId") String requestId,
                                               @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisRecordStudentModel model = JSONObject.parseObject(requestBody,DiagnosisRecordStudentModel.class);
        if (StringUtils.isBlank(model.getCode())) {
            logger.error("StudentDiagnosisController complexSubjectExamList Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if (null == model.getStudentCode()) {
            logger.error("StudentDiagnosisController complexSubjectExamList Exception：studentCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
        }
        List<DiagnosisRecordStudentModel> result = null;
        try {
            result = diagnosisService.complexSubjectExamList(model);
            List<Map<String,Object>> list = new ArrayList<>();
            if(!result.isEmpty()){
                result.forEach(diagnosisRecordStudentModel -> {
                    Map<String,Object> map = new HashMap<>();
                    map.put("diagnosisTeacherRecordCode",diagnosisRecordStudentModel.getDiagnosisTeacherRecordCode());
                    map.put("code",diagnosisRecordStudentModel.getCode());
                    map.put("startTime",diagnosisRecordStudentModel.getStartTime());
                    map.put("endTime",diagnosisRecordStudentModel.getEndTime());
                    map.put("diagnosisPaperCode",diagnosisRecordStudentModel.getDiagnosisPaperCode());
                    map.put("diagnosisPaperName",diagnosisRecordStudentModel.getDiagnosisPaperName());
                    map.put("gradeCode",diagnosisRecordStudentModel.getGradeCode());
                    map.put("subjectCode",diagnosisRecordStudentModel.getSubjectCode());
                    //如果该次考试已经结束 并且学生未作答 将diagnosisStatus设置为4 前端置灰
                    if(diagnosisRecordStudentModel.getEndTime().before(new Date()) && diagnosisRecordStudentModel.getDiagnosisStatus() == 0){
                        map.put("diagnosisStatus",4);
                    }else {
                        map.put("diagnosisStatus",diagnosisRecordStudentModel.getDiagnosisStatus());
                    }
                    map.put("diagnosisName",diagnosisRecordStudentModel.getDiagnosisName());
                    list.add(map);
                });
            }
            baseResponse.setResult(list);
        } catch (Exception e) {
            logger.error(requestId + "StudentDiagnosisController complexSubjectExamList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取试卷详情
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/paperDetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse paperDetail(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperModel model = JSONObject.parseObject(requestBody, DiagnosisPaperModel.class);
        if (StringUtils.isBlank(model.getCode())) {
            logger.error("StudentDiagnosisController paperDetail Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if (null == model.getStudentCode()) {
            logger.error("StudentDiagnosisController paperDetail Exception：studentCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
        }
        try {
            PaperSystem paperModel = diagnosisService.paperDetail(model);
            baseResponse.setResult(paperModel);
        } catch (Exception e) {
            logger.error(requestId + "StudentDiagnosisController paperDetail error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 提交答题卡
     * @param requestId
     * @param model
     * @param result
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse submit(@RequestParam("requestId") String requestId,
                              @RequestBody @Valid AnswerSheetModel model, BindingResult result){
        BaseResponse baseResponse = new BaseResponse(requestId);
        if (result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            StringBuffer sb = new StringBuffer();
            for(ObjectError error : errorList){
                sb.append("<"+error.getDefaultMessage() + "> ");
            }
            logger.error("StudentDiagnosisController  submit error Exception："+sb.toString()+" is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),
                    sb.toString()+" is null");
        }
        try {
            Map<String,Object> submit = diagnosisService.submit(model);
            baseResponse.setResult(submit);
        } catch (ReportException e) {
            logger.error(requestId + "StudentDiagnosisController submit error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString(),".报告尚未生成...");
        }catch (DiagnosisException e){
            logger.error(requestId + "StudentDiagnosisController submit error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.RESOURCE_DUPLICATE.toString(),e.getMessage());
        }
        catch (Exception e1) {
            logger.error(requestId + "StudentDiagnosisController submit error: ", e1);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 获取试卷 附带学生的答题信息
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getPaperAndAnswerResult", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getPaperAndAnswerResult(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperModel model = JSONObject.parseObject(requestBody, DiagnosisPaperModel.class);
        if (StringUtils.isBlank(model.getCode())) {
            logger.error("StudentDiagnosisController getPaperAndAnswerResult Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if (StringUtils.isBlank(model.getStudentDiagnosisRecordCode())) {
            logger.error("StudentDiagnosisController getPaperAndAnswerResult Exception：studentDiagnosisRecordCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentDiagnosisRecordCode");
        }
        try {
            PaperSystem paperModel = diagnosisService.getPaperAndAnswerResult(model);
            baseResponse.setResult(paperModel);
        } catch (Exception e) {
            logger.error(requestId + "StudentDiagnosisController getPaperAndAnswerResult error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 校验学生是否已经参加该测试 false 未参加 true已参加
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/checkExamStatus", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse checkExamStatus(@RequestParam("requestId") String requestId,
                                     @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        JSONObject model = JSONObject.parseObject(requestBody);
        if (StringUtils.isBlank(model.getString("diagnosisRecordCode"))) {
            logger.error("StudentDiagnosisController checkExamStatus Exception：diagnosisRecordCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisRecordCode");
        }
        try {
            boolean b = diagnosisService.checkExamStatus(model.getString("diagnosisRecordCode"));
            baseResponse.setResult(b);
        } catch (Exception e) {
            logger.error(requestId + "StudentDiagnosisController checkExamStatus error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 再次生成报告 （提交答题卡时如果生成报告失败，可再次获取报告）
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/reGetReprot", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse reGetReprot(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        JSONObject model = JSONObject.parseObject(requestBody);
        if (StringUtils.isBlank(model.getString("diagnosisRecordCode"))) {
            logger.error("StudentDiagnosisController reGetReprot Exception：diagnosisRecordCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisRecordCode");
        }
        try {
            StudentReportDto studentReport = diagnosisService.reGetReprot(model.getString("diagnosisRecordCode"));
            StudentReportVO studentReportVO = new StudentReportVO();
            BeanUtils.copyProperties(studentReport,studentReportVO);
            studentReportVO.setHistoricalScore(JSONObject.parseArray(studentReport.getHistoricalScore(), HistoryScoreModel.class));
            studentReportVO.setWrongQuestionSn(JSONObject.parseArray(studentReport.getWrongQuestionSn(),String.class));
            studentReportVO.setKnowledgeGrasp(JSONObject.parseArray(studentReport.getKnowledgeGrasp(),Columnar.class));
            studentReportVO.setModuleScoreIncrease(JSONObject.parseArray(studentReport.getModuleScoreIncrease(), Colorcolumnar.class));
            studentReportVO.setSubjectAbility(JSONObject.parseArray(studentReport.getSubjectAbility(), Radar.class));
            studentReportVO.setWrongKnowledgeRank(JSONObject.parseArray(studentReport.getWrongKnowledgeRank(), WrongKnowledgeRankModel.class));
            baseResponse.setResult(studentReportVO);
        } catch (Exception e) {
            logger.error(requestId + "StudentDiagnosisController reGetReprot error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取系统当前时间
     * @Author Dong_Qingyan
     * @Date 2016年10月9日 上午10:52:17
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/fetchServerCurrTime", method = RequestMethod.GET)
    public BaseResponse fetchServerCurrTime(@RequestParam("requestId") String requestId) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("shortDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            result.put("longDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            result.put("millonsDate",  Clock.systemUTC().millis());
            baseResponse.setResult(result);
            baseResponse.setMessage("success");
            return baseResponse;
        } catch (Exception e) {
            logger.error(requestId + " error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

}
