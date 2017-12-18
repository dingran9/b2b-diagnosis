package com.eedu.diagnosis.manager.controller.sysytem;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.manager.model.request.ResourceBaseModel;
import com.eedu.diagnosis.manager.model.response.BaseBookVersionVo;
import com.eedu.diagnosis.manager.model.response.BaseDataVo;
import com.eedu.diagnosis.manager.model.response.SubjectVo;
import com.eedu.diagnosis.manager.model.response.question.ChineseQuestion;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.TeacherRescoursService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *   zz
 */
@RestController
@RequestMapping("/teacher/rescours")
public class TeacherRescoursController {
    private final Logger logger = LoggerFactory.getLogger(TeacherRescoursController.class);
    @Autowired
    private TeacherRescoursService teacherRescoursServiceImpl ;

    /**
     * 查询资源卷
     */
    @RequestMapping(value = "/getResourcePaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getResourcePaper(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
        
        if(StringUtils.isBlank(model.getPaperCode())){
            logger.error("TeacherRescoursController getResourcePaper error Exception：PaperCode is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "PaperCode");
        }
        try {
        	PaperSystem resourcePaper = teacherRescoursServiceImpl.getResourcePaper(model.getPaperCode());
            baseResponse.setResult(resourcePaper);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  getResourcePaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 	 根据学段,学科获取教材版本
     */
    @RequestMapping(value = "/getSubjectByGradeCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getSubjectByGradeCode(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
		 if(StringUtils.isBlank(model.getGradeCode())){
	            logger.error("TeacherRescoursController getSubjectByGradeCode error Exception：gradeCode is null."+baseResponse.getRequestId());
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
	        }
        try {
        	List<SubjectVo> vo = teacherRescoursServiceImpl.getSubjectByGradeCode(model.getGradeCode());
            baseResponse.setResult(vo);
        } catch (Exception e) {
            logger.error(requestId + "TeacherRescoursController  getSubjectByGradeCode  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 	 根据学段,学科获取教材版本
     */
    @RequestMapping(value = "/getBookTypeVersion", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getBookTypeVersion(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
		 if(StringUtils.isBlank(model.getGradeCode())){
	            logger.error("TeacherRescoursController getBookTypeVersion error Exception：gradeCode is null."+baseResponse.getRequestId());
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
	        }
		 if (StringUtils.isBlank(model.getSubjectCode())) {
	            logger.error("TeacherRescoursController getBookTypeVersion Exception：subjectCode is null."+ baseResponse.getRequestId());
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
	        }		
        try {
        	List<BaseBookVersionVo> vo = teacherRescoursServiceImpl.getBookTypeVersion(model.getGradeCode(), model.getSubjectCode());
            baseResponse.setResult(vo);
        } catch (Exception e) {
            logger.error(requestId + "TeacherRescoursController  getBookTypeVersion  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 	 根据学段,学科获取教材上下册和单元
     */
    @RequestMapping(value = "/getbookTypeVersionAndUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getbookTypeVersionAndUnit(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
		 if(StringUtils.isBlank(model.getGradeCode())){
	            logger.error("TeacherRescoursController getbookTypeVersionAndUnit error Exception：gradeCode is null."+baseResponse.getRequestId());
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
	        }
		 if (StringUtils.isBlank(model.getSubjectCode())) {
	            logger.error("TeacherRescoursController getbookTypeVersionAndUnit Exception：subjectCode is null."+ baseResponse.getRequestId());
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
	        }
		 if (StringUtils.isBlank(model.getBooktypeCode())) {
	            logger.error("TeacherRescoursController getbookTypeVersionAndUnit Exception：booktypeCode is null."+ baseResponse.getRequestId());
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "booktypeCode");
	        }
		 if(StringUtils.isBlank(model.getProductsIds())){
	            logger.error("TeacherRescoursController getbookTypeVersionAndUnit error Exception：productsIds is null."+baseResponse.getRequestId());
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "productsIds");
	        }
		 if(StringUtils.isBlank(model.getCode())){
	            logger.error("TeacherRescoursController getbookTypeVersionAndUnit error Exception：code is null."+baseResponse.getRequestId());
	            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
	        }
        try {
        	List<BaseDataVo> vo = teacherRescoursServiceImpl.getbookTypeVersionAndUnit(model.getGradeCode(), model.getSubjectCode(), model.getBooktypeCode(), model.getProductsIds(), model.getCode());
            baseResponse.setResult(vo);
        } catch (Exception e) {
            logger.error(requestId + "TeacherRescoursController  getbookTypeVersionAndUnit  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 发布考试调用  获取上下册  根据系统时间自动判断返回上/下册 如果是全册直接返回
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getVolume", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getVolume(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
        if(StringUtils.isBlank(model.getGradeCode())){
            logger.error("TeacherRescoursController getbookTypeVersionAndUnit error Exception：gradeCode is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (StringUtils.isBlank(model.getSubjectCode())) {
            logger.error("TeacherRescoursController getbookTypeVersionAndUnit Exception：subjectCode is null."+ baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (StringUtils.isBlank(model.getBooktypeCode())) {
            logger.error("TeacherRescoursController getbookTypeVersionAndUnit Exception：booktypeCode is null."+ baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "booktypeCode");
        }
        if(StringUtils.isBlank(model.getProductsIds())){
            logger.error("TeacherRescoursController getbookTypeVersionAndUnit error Exception：productsIds is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "productsIds");
        }
        if(StringUtils.isBlank(model.getCode())){
            logger.error("TeacherRescoursController getbookTypeVersionAndUnit error Exception：code is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        try {
            List<BaseDataVo> vo = teacherRescoursServiceImpl.getVolume(model.getGradeCode(), model.getSubjectCode(), model.getBooktypeCode(), model.getProductsIds(), model.getCode());
            baseResponse.setResult(vo);
        } catch (Exception e) {
            logger.error(requestId + "TeacherRescoursController  getbookTypeVersionAndUnit  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 教研员报告 获取单元列表
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getUnitListWithStatus", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getUnitListWithStatus(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ResourceBaseModel model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
        if(StringUtils.isBlank(model.getGradeCode())){
            logger.error("TeacherRescoursController getUnitListWithStatus error Exception：gradeCode is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (StringUtils.isBlank(model.getSubjectCode())) {
            logger.error("TeacherRescoursController getUnitListWithStatus Exception：subjectCode is null."+ baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (StringUtils.isBlank(model.getBooktypeCode())) {
            logger.error("TeacherRescoursController getUnitListWithStatus Exception：booktypeCode is null."+ baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "booktypeCode");
        }
        if(StringUtils.isBlank(model.getProductsIds())){
            logger.error("TeacherRescoursController getUnitListWithStatus error Exception：productsIds is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "productsIds");
        }
        if(StringUtils.isBlank(model.getCode())){
            logger.error("TeacherRescoursController getUnitListWithStatus error Exception：code is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if(StringUtils.isBlank(model.getExamYear())){
            logger.error("TeacherRescoursController getUnitListWithStatus error Exception：examYear is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "examYear");
        }
        if(null == model.getDistrictId()){
            logger.error("TeacherRescoursController getUnitListWithStatus error Exception：districtId is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "districtId");
        }
        try {
            List<BaseDataVo> vo = teacherRescoursServiceImpl.getUnitListWithStatus(model);
            baseResponse.setResult(vo);
        } catch (Exception e) {
            logger.error(requestId + "TeacherRescoursController  getbookTypeVersionAndUnit  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
      /**
    	 * 根据单元查询试卷
    	 **/
        @RequestMapping(value = "/getPaperByUnit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
        public BaseResponse getPaperByUnit(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
            BaseResponse baseResponse = new BaseResponse(requestId);
            ResourceBaseModel  model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
            if(StringUtils.isBlank(model.getUnitCode())){
                logger.error("TeacherRescoursController getPaperByUnit error Exception：unitCode is null."+baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "unitCode");
            }
            try {
            	 List<BaseDataVo> list = teacherRescoursServiceImpl.getPaperByUnit(model.getUnitCode());
                baseResponse.setResult(list);
            } catch (Exception e) {
                logger.error(requestId + "TeacherRescoursController  getPaperByUnit  error: ", e);
                return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
            }
            return baseResponse;
        }
        
        /**
      	 * 查询试题详情
      	 **/
          @RequestMapping(value = "/getQuestionInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
          public BaseResponse getQuestionInfo(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
              BaseResponse baseResponse = new BaseResponse(requestId);
              ResourceBaseModel  model = JSONObject.parseObject(requestBody, ResourceBaseModel.class);
              if(StringUtils.isBlank(model.getSubjectFlag())){
                  logger.error("TeacherRescoursController getQuestionInfo error Exception：subjectFlag is null."+baseResponse.getRequestId());
                  return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectFlag");
              }
              if(StringUtils.isBlank(model.getQuestionCode())){
                  logger.error("TeacherRescoursController getQuestionInfo error Exception：questionCode is null."+baseResponse.getRequestId());
                  return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "questionCode");
              }
              try {
              	 List<ChineseQuestion> list = teacherRescoursServiceImpl.getQuestionInfo(model.getQuestionCode(),model.getSubjectFlag());
                  baseResponse.setResult(list);
              } catch (Exception e) {
                  logger.error(requestId + "TeacherRescoursController  getQuestionInfo  error: ", e);
                  return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
              }
              return baseResponse;
          }

    /**
     * 	 根据年级,年级获取未绑定的教材版本
     */
    @RequestMapping(value = "/getNoSubjectBySchoolGrade", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getNoSubjectBySchoolGrade(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        Map<String, Object> maps = JSONObject.parseObject(requestBody);
        if (null == maps || !maps.containsKey("schoolId") || maps.get("schoolId") == null) {
            logger.error("TeacherRescoursController getNoSubjectBySchoolGrade error Exception：schoolId is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
        }
        if (null == maps || !maps.containsKey("gradeCode") || maps.get("gradeCode") == null) {
            logger.error("TeacherRescoursController getNoSubjectBySchoolGrade error Exception：gradeCode is null."+baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode is null");
        }
        try {
            List<SubjectVo> vo = teacherRescoursServiceImpl.getNoSubjectBySchoolGrade(String.valueOf(maps.get("schoolId")),String.valueOf(maps.get("gradeCode")));
            baseResponse.setResult(vo);
        } catch (Exception e) {
            logger.error(requestId + "TeacherRescoursController  getNoSubjectBySchoolGrade  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
        
}
    
    
    
    
    
    
    
    
    
		

    
    
    
    

    
   
    


