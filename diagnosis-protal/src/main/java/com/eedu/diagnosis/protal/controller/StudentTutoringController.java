package com.eedu.diagnosis.protal.controller;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.protal.model.request.ResourceBaseModel;
import com.eedu.diagnosis.protal.model.response.VideoInfoVo;
import com.eedu.diagnosis.protal.model.response.question.QuestionListModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.StudentTutoringService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 同步辅导
 * Created by dqy on 2017/3/21.
 */
@RestController
@RequestMapping("/student/tutoring")
public class StudentTutoringController {
	
    private final Logger logger = LoggerFactory.getLogger(StudentTutoringController.class);

	@Autowired
	private StudentTutoringService studentTutoringServiceImpl;
	
       
	 /**
     *  根据知识点获取视频
     */
    @RequestMapping(value = "/getVideoByKnowledge", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getVideoByKnowledge(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
        if (StringUtils.isBlank(model.getDiagnosisPaperCode())) {
        	logger.error("StudentTutoringController  getVideoByKnowledge error Exception：diagnosisPaperCode is null." + baseResponse.getRequestId());
        	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperCode");
        }
        if (StringUtils.isBlank(model.getSubjectCode())) {
            logger.error("StudentTutoringController  getVideoByKnowledge error Exception：SubjectCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "SubjectCode");
        }
        if (StringUtils.isBlank(model.getGradeCode())) {
            logger.error("StudentTutoringController  getVideoByKnowledge error Exception：GradeCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "GradeCode");
        }
        if (StringUtils.isBlank(model.getKnowledgeCode())) {
        	logger.error("StudentTutoringController  getVideoByKnowledge error Exception：knowledgeCode is null." + baseResponse.getRequestId());
        	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "knowledgeCode");
        }
        try {
        	VideoInfoVo  videoByKnowledge = studentTutoringServiceImpl.getVideoByKnowledge(model);
            baseResponse.setResult(videoByKnowledge);
        } catch (Exception e) {
            logger.error(requestId + "StudentTutoringController  getVideoByKnowledge   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    
    /**
     *  根据学年、学科、教材版本 、知识点获取试题
     */
    @RequestMapping(value = "/getknowledgequestion", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getknowledgequestion(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
        if (StringUtils.isBlank(model.getGradeCode())) {
			logger.error("StudentTutoringController  getknowledgequestion  error Exception：GradeCode is null." + baseResponse.getRequestId());
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
		}
        if (StringUtils.isBlank(model.getSubjectCode())) {
        	logger.error("StudentTutoringController  getknowledgequestion  Exception：subjectCode is null." + baseResponse.getRequestId());
        	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (StringUtils.isBlank(model.getBooktypeCode())) {
        	logger.error("StudentTutoringController  getknowledgequestion  Exception：booktypeCode is null." + baseResponse.getRequestId());
        	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "booktypeCode");
        }
        if (StringUtils.isBlank(model.getKnowledgeCode())) {
        	logger.error("StudentTutoringController  getknowledgequestion  Exception：knowledgeCode is null." + baseResponse.getRequestId());
        	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "knowledgeCode");
        }
        if (null == model.getPageNum()) {
        	logger.error("StudentTutoringController  getknowledgequestion  Exception：pageNum is null." + baseResponse.getRequestId());
        	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
        	logger.error("StudentTutoringController  getknowledgequestion  Exception：pageSize is null." + baseResponse.getRequestId());
        	return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
        	QuestionListModel questionsModel = studentTutoringServiceImpl.getknowledgequestion(model);
            baseResponse.setResult(questionsModel);
        } catch (Exception e) {
            logger.error(requestId + "StudentTutoringController  getknowledgequestion   getResourcePaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     *
     *  根据单元卷code获取单元
     *  单元获取下面的试题
     */
    @RequestMapping(value = "/getQuestionByDiagnosisPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getQuestionByDiagnosisPaper(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
        if (StringUtils.isBlank(model.getDiagnosisPaperCode())) {
            logger.error("StudentTutoringController  getQuestionByDiagnosisPaper error Exception：diagnosisPaperCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperCode");
        }
        if (StringUtils.isBlank(model.getKnowledgeName())) {
            logger.error("StudentTutoringController  getQuestionByDiagnosisPaper error Exception：knowledgeName is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "knowledgeName");
        }
        try {
            QuestionListModel questionsModel = studentTutoringServiceImpl.getQuestionByDiagnosisPaper(model);
            baseResponse.setResult(questionsModel);
        } catch (Exception e) {
            logger.error(requestId + "StudentTutoringController  getQuestionByDiagnosisPaper   error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     *  获取练一练试题（弃用）
     */
    @RequestMapping(value = "/getknowledgequestionV2", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getknowledgequestionV2(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
        if (StringUtils.isBlank(model.getKnowledgeCode())) {
            logger.error("StudentTutoringController  getknowledgequestionV2  Exception：knowledgeCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "knowledgeCode");
        }
        if (StringUtils.isBlank(model.getDiagnosisPaperCode())) {
            logger.error("StudentTutoringController  getknowledgequestionV2  Exception：diagnosisPaperCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperCode");
        }
        try {
            QuestionListModel questionsModel = studentTutoringServiceImpl.getknowledgequestionV2(model);
            baseResponse.setResult(questionsModel);
        } catch (Exception e) {
            logger.error(requestId + "StudentTutoringController  getknowledgequestionV2 error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 根据视频ID 获取练一练试题
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getQuestionsByVideo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getQuestionsByVideo(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
        if (StringUtils.isBlank(model.getVideoId())) {
            logger.error("StudentTutoringController  getQuestionsByVideo  Exception：videoId is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "videoId");
        }
        if (StringUtils.isBlank(model.getSubjectCode())) {
            logger.error("StudentTutoringController  getQuestionsByVideo  Exception：subjectCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            QuestionListModel questionsModel = studentTutoringServiceImpl.getQuestionsByVideo(model);
            baseResponse.setResult(questionsModel);
        } catch (Exception e) {
            logger.error(requestId + "StudentTutoringController  getQuestionsByVideo error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

}
