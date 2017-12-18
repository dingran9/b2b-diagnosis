package com.eedu.diagnosis.protal.controller;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.protal.model.request.StudentModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.StudentClassworkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 课堂作业
 * Created by dqy on 2017/3/21.
 */
@RestController
@RequestMapping("/classwork")
public class StudentClassworkController {
    private final Logger logger = LoggerFactory.getLogger(StudentClassworkController.class);
    @Autowired
    private StudentClassworkService studentClassworkService;


    /**
     * 初始化作业页面
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/initClasswork", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse initClasswork(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        StudentModel model = JSONObject.parseObject(requestBody, StudentModel.class);
        if (null == model.getUserId()) {
            logger.error("StudentClassworkController initClasswork Exception：userId is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
        }
        if (null == model.getStageCode()) {
            logger.error("StudentClassworkController initClasswork Exception：stageCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
        }
        if (null == model.getGradeCode()) {
            logger.error("StudentClassworkController initClasswork Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getSubjectCodes() || model.getSubjectCodes().isEmpty()) {
            logger.error("StudentClassworkController initClasswork Exception：subjectCodes is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<Map<String,Object>> result = studentClassworkService.initClasswork(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "StudentClassworkController initClasswork error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

//    @RequestMapping(value = "/classworkList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//    public BaseResponse classworkList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
//        BaseResponse baseResponse = new BaseResponse(requestId);
//        StudentModel model = JSONObject.parseObject(requestBody,StudentModel.class);
//        if (null == model.getUserId()) {
//            logger.error("StudentClassworkController classworkList Exception：userId is null."
//                    + baseResponse.getRequestId());
//            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userId");
//        }
//        if (null == model.getStageCode()) {
//            logger.error("StudentClassworkController classworkList Exception：stageCode is null."
//                    + baseResponse.getRequestId());
//            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
//        }
//        if (null == model.getGradeCode()) {
//            logger.error("StudentClassworkController classworkList Exception：gradeCode is null."
//                    + baseResponse.getRequestId());
//            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
//        }
//        if (null == model.getSubjectCode()) {
//            logger.error("StudentClassworkController classworkList Exception：subjectCode is null."
//                    + baseResponse.getRequestId());
//            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
//        }
//
//        try {
//            List<StudentRecordModel> result = studentClassworkService.classworkList(model);
//            baseResponse.setResult(result);
//        } catch (Exception e) {
//            logger.error(requestId + "StudentClassworkController classworkList error: ", e);
//            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
//        }
//        return baseResponse;
//    }
}
