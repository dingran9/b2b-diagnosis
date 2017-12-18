package com.eedu.diagnosis.protal.controller.classtest;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisInClassRelationDto;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerMachineDto;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerSheetDto;
import com.eedu.diagnosis.inclass.test.api.enums.ConstantEnum;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisInClassRelationService;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisStudentAnswerMachineService;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisStudentAnswerSheetService;
import com.eedu.diagnosis.protal.model.classTest.DiagnosisInClassTestModel;
import com.eedu.diagnosis.protal.model.classTest.DiagnosisStudentAnswerMachineModel;
import com.eedu.diagnosis.protal.model.classTest.DiagnosisStudentAnswerSheetModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.StudentClassTestService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 *  学生端-课中测
 */
@RestController
@RequestMapping("/class/test")
public class DiagnosisClassTestController {

    private final Logger logger = LoggerFactory.getLogger(DiagnosisClassTestController.class);

    @Autowired
	private StudentClassTestService classTestService;

    @Autowired
    private DiagnosisStudentAnswerSheetService diagnosisStudentAnswerSheetService ;//课中测答题

    @Autowired
    private DiagnosisStudentAnswerMachineService diagnosisStudentAnswerMachineService;

    @Autowired
    private DiagnosisInClassRelationService diagnosisInClassRelationService;


    /**
     *  课中测分页列表
     */
    @ResponseBody
    @RequestMapping(value = "/classTestListCount", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestListCount(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
        try {

            if (null == model.getClassCode()) {
                logger.error("DiagnosisInClassTestController classTestList  error Exception：ClassCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "ClassCode");
            }
            model.setStatus(ConstantEnum.HAS_ENDED.getType());
            baseResponse.setResult(classTestService.classTestListCount(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  课中测分页列表
     */
    @ResponseBody
    @RequestMapping(value = "/classTestList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestList(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisInClassTestModel model = JSONObject.parseObject(requestBody, DiagnosisInClassTestModel.class);
        try {

            if (null == model.getClassCode()) {
                logger.error("DiagnosisInClassTestController classTestList  error Exception：ClassCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "ClassCode");
            }
            baseResponse.setResult(classTestService.classTestList(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 课中测修改阅读状态**/
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
            baseResponse.setResult(classTestService.classTestUpdate(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  classTestUpdate  error: ", e);
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
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
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
    /** 课中测---学生端提交**/
    @ResponseBody
    @RequestMapping(value = "/studentSubmit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse studentSubmit(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisClassTestController studentSubmit  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        if (StringUtils.isBlank(model.getBaseCode())) {
            logger.error("DiagnosisClassTestController studentSubmit  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        if (StringUtils.isBlank(model.getStudentCode())) {
            logger.error("DiagnosisClassTestController studentSubmit  error Exception：studentCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
        }
        if (StringUtils.isBlank(model.getStudentName())) {
            logger.error("DiagnosisClassTestController studentSubmit  error Exception：studentName is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentName");
        }
        if (StringUtils.isBlank(model.getStudentAnswer())) {
            logger.error("DiagnosisClassTestController studentSubmit  error Exception：studentAnswer is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentAnswer");
        }
        if (StringUtils.isBlank(model.getSout())) {
            logger.error("DiagnosisClassTestController studentSubmit  error Exception：sout is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "sout");
        }
        try {

            List<DiagnosisInClassRelationDto> lists = getList(model.getInClassTestCode(), model.getSout());

            if (lists.get(0).getIsEnd()==ConstantEnum.HAS_ENDED.getType()){
                logger.error("DiagnosisClassTestController studentSubmit  error Exception：RESOURCE_NOTFOUND is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "RESOURCE_NOTFOUND");
            }
            if (lists.get(0).getIsEnd()==ConstantEnum.STARTING.getType()){
                logger.error("DiagnosisClassTestController studentSubmit  error Exception：RESOURCE_NOTFOUND is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "RESOURCE_NOTFOUND");
            }

            DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
            dto.setStudentCode(model.getStudentCode());
            dto.setInClassTestCode(model.getInClassTestCode());
            dto.setBaseCode(model.getBaseCode());
            List<DiagnosisStudentAnswerSheetDto> studentAnswerSheetDtos = diagnosisStudentAnswerSheetService.selectDiagnosisStudentAnswerSheetList(dto);
            if (!CollectionUtils.isEmpty(studentAnswerSheetDtos)){
                logger.error("DiagnosisClassTestController studentSubmit  error Exception：The request resource is in use." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_INUSE.toString(), "The request resource is in use");
            }
            baseResponse.setResult(classTestService.studentSubmit(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisClassTestController  studentSubmit  error: ", e);
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
    /** 课中测---学生端总览报告**/
    @ResponseBody
    @RequestMapping(value = "/reportOverview", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse accuracyReport(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisClassTestController reportOverview  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        if (null == model.getStudentCode()) {
            logger.error("DiagnosisClassTestController reportByAccuracy  error Exception：studentCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
        }
        try {
            baseResponse.setResult(classTestService.reportOverview(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisClassTestController  reportOverview  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 课中测--知识点报告**/
    @ResponseBody
    @RequestMapping(value = "/reportByKnowledge", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse reportByKnowledge(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisClassTestController reportByKnowledge  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        if (null == model.getStudentCode()) {
            logger.error("DiagnosisClassTestController reportByKnowledge  error Exception：studentCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
        }
        try {
            baseResponse.setResult(classTestService.reportByKnowledge(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisClassTestController  reportByKnowledge  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 课中测--正确率报告**/
    @ResponseBody
    @RequestMapping(value = "/reportByAccuracy", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse reportByAccuracy(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisClassTestController reportByAccuracy  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        if (null == model.getStudentCode()) {
            logger.error("DiagnosisClassTestController reportByAccuracy  error Exception：studentCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
        }
        try {
            baseResponse.setResult(classTestService.reportByAccuracy(model));
         } catch (Exception e) {
            logger.error(requestId + "DiagnosisClassTestController  reportByAccuracy  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /** 课中测--正确率报告**/
    @ResponseBody
    @RequestMapping(value = "/report", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse report(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        if (StringUtils.isBlank(model.getInClassTestCode())) {
            logger.error("DiagnosisClassTestController reportByAccuracy  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        if (null == model.getStudentCode()) {
            logger.error("DiagnosisClassTestController reportByAccuracy  error Exception：studentCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
        }
        try {
            baseResponse.setResult(classTestService.report(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisClassTestController  reportByAccuracy  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 测试详情
     */
    @ResponseBody
    @RequestMapping(value = "/questionDetails", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse questionDetails(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerSheetModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerSheetModel.class);
        try {
            if (StringUtils.isBlank(model.getInClassTestCode())) {
                logger.error("DiagnosisInClassTestController questionDetails  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
            }
            if (null == model.getStudentCode()) {
                logger.error("DiagnosisClassTestController questionDetails  error Exception：studentCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
            }
            baseResponse.setResult(classTestService.questionDetails(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisInClassTestController  questionDetails  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     *  答题绑定用户新增
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse studentSave(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerMachineModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerMachineModel.class);
        try {
            if (StringUtils.isBlank(model.getStudentCode())) {
                logger.error("DiagnosisClassTestController studentSave  error Exception：studentCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
            }
            if (StringUtils.isBlank(model.getStudentName())) {
                logger.error("DiagnosisClassTestController studentSave  error Exception：studentName is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentName");
            }
            if (StringUtils.isBlank(model.getMachineCode())) {
                logger.error("DiagnosisClassTestController studentSave  error Exception：machineCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "machineCode");
            }
            DiagnosisStudentAnswerMachineDto dto = new DiagnosisStudentAnswerMachineDto();
            dto.setMachineCode(model.getMachineCode());
            List<DiagnosisStudentAnswerMachineDto> list = diagnosisStudentAnswerMachineService.selectDiagnosisStudentAnswerMachineList(dto);
            List<DiagnosisStudentAnswerMachineDto> collect = list.stream().filter(g -> !g.getStudentCode().equals(model.getStudentCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)){
                logger.error("DiagnosisQuestionBankController classTestSave  error Exception：inClassTestName is duplicate." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "inClassTestName is duplicate");
            }


            baseResponse.setResult(classTestService.studentSave(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisStudentAnswerMachineController  studentSave  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  答题绑定用户新增
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse studentList(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerMachineModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerMachineModel.class);
        try {
            if (StringUtils.isBlank(model.getStudentCode())) {
                logger.error("DiagnosisClassTestController studentList  error Exception：studentCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("rows",classTestService.studentList(model));
            baseResponse.setResult(map);
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisStudentAnswerMachineController  studentList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    public List<DiagnosisInClassRelationDto> getList(String InClassTestCode,  String sout) throws Exception {
        DiagnosisInClassRelationDto dto1 = new DiagnosisInClassRelationDto();
        dto1.setInClassTestCode(InClassTestCode);
        dto1.setSout(sout);
        List<DiagnosisInClassRelationDto> lists = diagnosisInClassRelationService.selectDiagnosisInClassRelationList(dto1);
        return  lists;
    }

}
