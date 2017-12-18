package com.eedu.diagnosis.manager.controller.classtest;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassTestDto;
import com.eedu.diagnosis.inclass.test.api.enums.ConstantEnum;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisInClassRelationService;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisInClassTestService;
import com.eedu.diagnosis.manager.model.request.ClassTest.DiagnosisInClassRelationModel;
import com.eedu.diagnosis.manager.model.request.ClassTest.DiagnosisInClassTestModel;
import com.eedu.diagnosis.manager.model.request.ClassTest.DiagnosisStudentAnswerSheetModel;
import com.eedu.diagnosis.manager.model.request.DiagnosisFeedbackModel;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.ClassTestService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *   模块:课中测
 *  @author  zz
 */

@RestController
@RequestMapping("/class/test")
public class DiagnosisInClassTestController {
    private final Logger logger = LoggerFactory.getLogger(DiagnosisInClassTestController.class);
   @Autowired
   private ClassTestService classTestService ;
    @Autowired
    private DiagnosisInClassTestService diagnosisInClassTestService ;//课中测

    /**
     *  课中测分页列表
     */
     @ResponseBody
     @RequestMapping(value = "/classTestList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestList(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
        try {
            if(StringUtils.isBlank(model.getTeacherCode())){
                logger.error("DiagnosisInClassTestController classTestList  Exception：teacherCode is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
            }
            baseResponse.setResult(classTestService.classTestList(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  课中测code查询基础卷code列表
     */
    @ResponseBody
    @RequestMapping(value = "/baseQuestionCodeListByClassTestCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse baseQuestionCodeListByClassTestCode(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
        try {
            if (StringUtils.isBlank(model.getInClassTestCode())) {
                logger.error("DiagnosisInClassTestController baseQuestionListByClassTestCode  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
            }
            baseResponse.setResult(classTestService.baseQuestionCodeListByClassTestCode(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  baseQuestionListByClassTestCode  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  课中测code查询基础卷列表
     */
     @ResponseBody
     @RequestMapping(value = "/baseQuestionListByClassTestCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse baseQuestionListByClassTestCode(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
         DiagnosisInClassRelationModel model = JSONObject.parseObject(requestBody, DiagnosisInClassRelationModel.class);
        try {
            if (StringUtils.isBlank(model.getInClassTestCode())) {
                logger.error("DiagnosisInClassTestController baseQuestionListByClassTestCode  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
            }
            baseResponse.setResult(classTestService.baseQuestionListByClassTestCode(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  baseQuestionListByClassTestCode  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  课中测试题的调整和删除
     */
    @ResponseBody
    @RequestMapping(value = "/classTestQuestionAdjustment", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestQuestionAdjustment(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
        try {
            if (StringUtils.isBlank(model.getInClassTestCode())) {
                logger.error("DiagnosisInClassTestController classTestQuestionAdjustment  error Exception：InClassTestCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "InClassTestCode");
            }
            if (CollectionUtils.isEmpty(model.getList())){
                logger.error("DiagnosisInClassTestController classTestQuestionAdjustment  Exception：DiagnosisInClassRelationModel is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisInClassRelationModelList");
            }
            baseResponse.setResult(classTestService.classTestQuestionAdjustment(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestQuestionAdjustment  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  课中测新增
     */
     @ResponseBody
     @RequestMapping(value = "/classTestSave", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestSave(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
         try {
            if (StringUtils.isBlank(model.getInClassTestName())) {
                logger.error("DiagnosisInClassTestController classTestSave  error Exception：inClassTestName is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestName");
            }
            if(StringUtils.isBlank(model.getTeacherCode())){
                logger.error("DiagnosisInClassTestController classTestSave  Exception：teacherCode is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
            }
           if(StringUtils.isBlank(model.getClassCode())){
             logger.error("DiagnosisInClassTestController classTestSave  Exception：classCode is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCode");
            }
            if(null == model.getSubjectCode()){
                logger.error("DiagnosisInClassTestController classTestSave  Exception：subjectCode is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
            }
            if (CollectionUtils.isEmpty(model.getList())){
                logger.error("DiagnosisInClassTestController classTestSave  Exception：DiagnosisInClassRelationModel is null."+ baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisInClassRelationModelList");
            }
             List<DiagnosisInClassTestDto> list= getInClassTestList(model.getTeacherCode());
             List<DiagnosisInClassTestDto> collect = list.stream().filter(g -> g.getInClassTestName().equals(model.getInClassTestName()) && g.getClassCode().equals(model.getClassCode())).collect(Collectors.toList());
             if (!CollectionUtils.isEmpty(collect)){
                 logger.error("DiagnosisQuestionBankController classTestSave  error Exception：inClassTestName is duplicate." + baseResponse.getRequestId());
                 return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "inClassTestName is duplicate");
             }
             baseResponse.setResult(classTestService.classTestSave(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestSave  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  课中测删除
     */
     @ResponseBody
     @RequestMapping(value = "/classTestDelete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestDelete(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
         if (StringUtils.isBlank(model.getInClassTestCode())) {
             logger.error("DiagnosisInClassTestController classTestSave  error Exception：InClassTestCode is null." + baseResponse.getRequestId());
             return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "InClassTestCode");
         }
        try {
            baseResponse.setResult(classTestService.classTestDelete(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestDelete  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 下一步教研用户名**/
    @ResponseBody
    @RequestMapping(value = "/classTestVerificationName", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestVerificationName(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
        try {
            if (StringUtils.isBlank(model.getInClassTestCode())) {
                logger.error("DiagnosisInClassTestController classTestVerificationName  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
            }
            List<DiagnosisInClassTestDto> list= getInClassTestList(model.getTeacherCode());
            List<DiagnosisInClassTestDto> collect = list.stream().filter(g -> g.getInClassTestName().equals(model.getInClassTestName()) && g.getClassCode().equals(model.getClassCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)){
                baseResponse.setResult(false);
            }else {
                baseResponse.setResult(true);
            }
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestVerificationName  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 课中测修改-开始-结束测试**/
    @ResponseBody
    @RequestMapping(value = "/classTestVerification", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestVerification(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
        try {
            if (StringUtils.isBlank(model.getInClassTestCode())) {
                logger.error("DiagnosisInClassTestController classTestUpdate  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
            }
                DiagnosisInClassTestDto bean= diagnosisInClassTestService.selectDiagnosisInClassTestDto(model.getInClassTestCode());
                boolean  flag = false;
                if (bean != null){
                   if ( bean.getStatus() == ConstantEnum.HAS_ENDED.getType()){
                        flag = true;
                   }
                }
                baseResponse.setResult(flag);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestUpdate  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 课中测修改-开始-结束测试**/
     @ResponseBody
     @RequestMapping(value = "/classTestUpdate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestUpdate(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
         try {
             if (StringUtils.isBlank(model.getInClassTestCode())) {
                logger.error("DiagnosisInClassTestController classTestUpdate  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
            }
             if(StringUtils.isBlank(model.getTeacherCode())){
                 logger.error("DiagnosisInClassTestController classTestUpdate  Exception：teacherCode is null."+ baseResponse.getRequestId());
                 return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
             }
             if (StringUtils.isNotBlank(model.getInClassTestName())) {
                 List<DiagnosisInClassTestDto> list= getInClassTestList(model.getTeacherCode());
                 List<DiagnosisInClassTestDto> collect = list.stream().filter(g -> g.getInClassTestName().equals(model.getInClassTestName()) && !g.getInClassTestCode().equals(model.getInClassTestCode())).collect(Collectors.toList());
                 if (!CollectionUtils.isEmpty(collect)){
                     logger.error("DiagnosisInClassTestController classTestUpdate  error Exception：inClassTestName is duplicate." + baseResponse.getRequestId());
                     return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "inClassTestName is duplicate");
                 }
             }

             DiagnosisInClassTestDto dtos = new DiagnosisInClassTestDto();
             dtos.setInClassTestCode(model.getTeacherCode());
             dtos.setStatus(ConstantEnum.STARTING.getType());
             List<DiagnosisInClassTestDto> list = diagnosisInClassTestService.selectDiagnosisInClassTestList(dtos);
             if (!CollectionUtils.isEmpty(list)){
                 logger.error("DiagnosisInClassTestController classTestUpdate  error Exception：inClassTestName is duplicate." + baseResponse.getRequestId());
                 return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_INUSE.toString(), "inClassTestName is duplicate");
             }

                 baseResponse.setResult(classTestService.classTestStartTest(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestUpdate  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 解锁课中测试题
     */
    @ResponseBody
    @RequestMapping(value = "/unlockClassTest", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse unlockClassTest(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        try {
            if (StringUtils.isBlank(model.getInClassTestCode())) {
                logger.error("DiagnosisInClassTestController unlockClassTest  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
            }
            baseResponse.setResult(classTestService.unlockClassTest(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  unlockClassTest  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /** 课中测关系表修改---下一题和结束本题**/
     @ResponseBody
     @RequestMapping(value = "/classTestRelationUpdate", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestRelationUpdate(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassRelationModel model = JSONObject.parseObject(requestBody, DiagnosisInClassRelationModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisInClassTestController classTestRelationUpdate  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        if (StringUtils.isBlank(model.getBaseCode())) {
            logger.error("DiagnosisInClassTestController classTestRelationUpdate  error Exception：baseCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "baseCode");
        }
        try {
            baseResponse.setResult(classTestService.classTestRelationUpdate(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestRelationUpdate  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 课中测---单题报告**/
     @ResponseBody
     @RequestMapping(value = "/classTestRelationSingleReport", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestRelationSingleReport(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisInClassTestController classTestRelationSingleReport  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        if (StringUtils.isBlank(model.getBaseCode())) {
            logger.error("DiagnosisInClassTestController classTestRelationSingleReport  error Exception：baseCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "baseCode");
        }
         if(null == model.getClassCode()){
             logger.error("DiagnosisInClassTestController classTestRelationSingleReport  Exception：classCode is null."+ baseResponse.getRequestId());
             return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCode");
         }
        try {
            baseResponse.setResult(classTestService.classTestRelationSingleReport(model.getBaseCode(),model.getInClassTestCode(),model.getClassCode()));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestRelationSingleReport  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /*********************************************班级报告**************************************************/
    /** 课中测---班级报告总览**/
     @ResponseBody
     @RequestMapping(value = "/report", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse reportOverview(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisInClassTestController report  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        try {
            baseResponse.setResult(classTestService.reportOverview(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  report  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 课中测--报告校验**/
     @ResponseBody
     @RequestMapping(value = "/reportVerification", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse reportByKnowledge(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisInClassTestController reportVerification  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        try {
            baseResponse.setResult(classTestService.reportVerification(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  reportVerification  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    public  List<DiagnosisInClassTestDto> getInClassTestList(String teacherCode) throws Exception {
         DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
         dto.setTeacherCode(teacherCode);
         List<DiagnosisInClassTestDto> list= diagnosisInClassTestService.selectDiagnosisInClassTestList(dto);
         return  list;
     }


}
