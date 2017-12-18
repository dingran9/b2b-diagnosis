package com.eedu.diagnosis.protal.controller;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.model.request.WrongQuestionRequestModel;
import com.eedu.diagnosis.protal.model.response.WrongQuestionModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.StudentWrongQuestionService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dqy on 2017/3/27.
 */
@RestController
@RequestMapping("/wrongQuestion")
public class StudentWrongQuestionController {
    private final Logger logger = LoggerFactory.getLogger(StudentWrongQuestionController.class);
    @Autowired
    private StudentWrongQuestionService studentWrongQuestionService;



    @RequestMapping(value = "/getWrongQuestions", method = RequestMethod.POST)
    public BaseResponse getWrongQuestions(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        StudentModel model = JSONObject.parseObject(requestBody,StudentModel.class);
        if (null == model.getSubjectCode()) {
            logger.error("StudentWrongQuestionController getWrongQuestions Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (null == model.getUserId()) {
            logger.error("StudentWrongQuestionController getWrongQuestions Exception：userId is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
        }
        if (null == model.getPageNum()) {
            logger.error("StudentWrongQuestionController getWrongQuestions Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("StudentWrongQuestionController getWrongQuestions Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            PageInfo<WrongQuestionModel> result = studentWrongQuestionService.getWrongQuestions(model);
            baseResponse.setResult(result);
            return baseResponse;
        } catch (Exception e) {
            logger.error(requestId + "StudentWrongQuestionController getWrongQuestions error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
    }

    @RequestMapping(value = "/delWrongQuestions", method = RequestMethod.POST)
    public BaseResponse delWrongQuestions(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        WrongQuestionRequestModel model = JSONObject.parseObject(requestBody,WrongQuestionRequestModel.class);
        if(null == model.getQuestionCodes() || model.getQuestionCodes().isEmpty()){
            logger.error("StudentWrongQuestionController delWrongQuestions Exception：questionCodes is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionCodes");
        }
        try {
            studentWrongQuestionService.delWrongQuestions(model);
            baseResponse.setResult(true);
        } catch (Exception e) {
            logger.error(requestId + "StudentWrongQuestionController delWrongQuestions error:", e);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

}
