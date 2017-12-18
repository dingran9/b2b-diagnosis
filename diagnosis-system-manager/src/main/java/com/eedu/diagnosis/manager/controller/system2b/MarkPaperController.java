package com.eedu.diagnosis.manager.controller.system2b;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.manager.model.request.DiagnosisPaperModel;
import com.eedu.diagnosis.manager.model.request.MarkPaperModel;
import com.eedu.diagnosis.manager.model.response.DiagnosisRecordTeacherModel;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.MarkPaperService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 判卷
 * Created by dqy on 2017/9/11.
 */
@RestController
@RequestMapping("/teacher/markPaper")
public class MarkPaperController {
    private final Logger logger = LoggerFactory.getLogger(MarkPaperController.class);

    @Autowired
    private MarkPaperService markPaperService;


    /**
     * 根据教师发布的考试记录code获取学生考试列表  markStatus 0未判1已判
     * @param requestId
     * @param model
     * @return
     */
    @RequestMapping(value = "/studentExamList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse studentExamList(@RequestParam("requestId") String requestId, @RequestBody DiagnosisRecordTeacherModel model) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        if (StringUtils.isBlank(model.getCode())) {
            logger.error("MarkPaperController studentExamList Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if (null == model.getPageNum()) {
            logger.error("MarkPaperController studentExamList Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("MarkPaperController studentExamList Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            Map<String,Object> result = markPaperService.studentExamList(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "MarkPaperController paperDetail error: ", e);
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
            logger.error("MarkPaperController getPaperAndAnswerResult Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if (StringUtils.isBlank(model.getStudentDiagnosisRecordCode())) {
            logger.error("MarkPaperController getPaperAndAnswerResult Exception：studentDiagnosisRecordCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentDiagnosisRecordCode");
        }
        try {
            PaperSystem paperModel = markPaperService.getPaperAndAnswerResult(model);
            baseResponse.setResult(paperModel);
        } catch (Exception e) {
            logger.error(requestId + "MarkPaperController getPaperAndAnswerResult error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }




    /**
     * 教师获取获取试卷 附带学生的答题信息 附带教师判题结果信息
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getMarkResult", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getMarkResult(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperModel model = JSONObject.parseObject(requestBody, DiagnosisPaperModel.class);
        if (StringUtils.isBlank(model.getCode())) {
            logger.error("MarkPaperController getMarkResult Exception：code is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if (StringUtils.isBlank(model.getStudentDiagnosisRecordCode())) {
            logger.error("MarkPaperController getMarkResult Exception：studentDiagnosisRecordCode is null."
                                + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentDiagnosisRecordCode");
        }
        try {
            PaperSystem paperModel = markPaperService.getMarkResult(model);
            baseResponse.setResult(paperModel);
        } catch (Exception e) {
            logger.error(requestId + "MarkPaperController getMarkResult error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 保存判卷信息
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/saveMarkPaperInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse saveMarkPaperInfo(@RequestParam("requestId") String requestId,
                                          @RequestBody @Valid MarkPaperModel model, BindingResult result) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        if (result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            StringBuffer sb = new StringBuffer();
            for(ObjectError error : errorList){
                sb.append("<"+error.getDefaultMessage() + "> ");
            }
            logger.error("MarkPaperController  saveMarkPaperInfo error Exception："+sb.toString()+" is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),
                    sb.toString()+" is null");
        }
        try {
            markPaperService.saveMarkPaperInfo(model);
            baseResponse.setResult(true);
        } catch (Exception e) {
            logger.error(requestId + "MarkPaperController saveMarkPaperInfo error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
}
