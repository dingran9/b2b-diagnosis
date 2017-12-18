package com.eedu.diagnosis.manager.controller.classtest;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerMachineDto;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisStudentAnswerMachineService;
import com.eedu.diagnosis.manager.model.request.ClassTest.*;
import com.eedu.diagnosis.manager.model.request.ClassTest.DiagnosisStudentAnswerMachineModel;
import com.eedu.diagnosis.manager.model.request.ClassTest.machine.*;
import com.eedu.diagnosis.manager.model.request.ClassTest.mina.IoSessionManager;
import com.eedu.diagnosis.manager.model.request.ClassTest.util.SJson;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.ClassTestService;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *   模块:答题绑定用户
 *  @author  zz
 */

@RestController
@RequestMapping("/answer/machine")
public class DiagnosisStudentAnswerMachineController {
    private final Logger logger = LoggerFactory.getLogger(DiagnosisStudentAnswerMachineController.class);
   @Autowired
   private ClassTestService classTestService ;
    @Autowired
    private DiagnosisStudentAnswerMachineService diagnosisStudentAnswerMachineService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    /**
     *  答题绑定用户分页列表
     */
   
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse classTestList(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerMachineModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerMachineModel.class);
        try {
            baseResponse.setResult(classTestService.studentList(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisStudentAnswerMachineController studentList  error: ", e);
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
                logger.error("DiagnosisStudentAnswerMachineController studentSave  error Exception：studentCode is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentCode");
            }
            if (StringUtils.isBlank(model.getStudentName())) {
                logger.error("DiagnosisStudentAnswerMachineController studentSave  error Exception：studentName is null." + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "studentName");
            }
            if (StringUtils.isBlank(model.getMachineCode())) {
                logger.error("DiagnosisStudentAnswerMachineController studentSave  error Exception：machineCode is null." + baseResponse.getRequestId());
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

    /** 答题绑定用户修改**/
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse studentUpdate(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerMachineModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerMachineModel.class);
        if (StringUtils.isBlank(model.getStudentAnswerMachineCode())) {
            logger.error("DiagnosisStudentAnswerMachineController studentUpdate  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        try {
            baseResponse.setResult(classTestService.studentUpdate(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisStudentAnswerMachineController  studentUpdate  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     *  答题绑定用户删除
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse studentDelete(@RequestParam("requestId") String requestId,@RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisStudentAnswerMachineModel model = JSONObject.parseObject(requestBody, DiagnosisStudentAnswerMachineModel.class);
        if (StringUtils.isBlank(model.getStudentAnswerMachineCode())) {
            logger.error("DiagnosisStudentAnswerMachineController studentDelete  error Exception：inClassTestCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "inClassTestCode");
        }
        try {
            baseResponse.setResult(classTestService.studentDelete(model));
        } catch (Exception e) {
            logger.error(requestId + "DiagnosisStudentAnswerMachineController  studentDelete  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
   /*
   * 教师发卷
   * 缓存信息
   * */
    @ResponseBody
    @RequestMapping(value = "/start", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String, Object> start( @RequestBody String requestBody ) {
        StartModel sm = JSONObject.parseObject(requestBody, StartModel.class);
        IoSession ioSession = IoSessionManager.getIoSession(sm.getBaseId());
        if (ioSession == null){
            Map<String, Object> map = new HashMap<>();
            map.put("code", "NO");
            return map;
        }
        if(StringUtils.isNotBlank(sm.getBaseId()) && StringUtils.isNotBlank(sm.getTestCode()) ){
            redisClientTemplate.set("sunvote"+sm.getTestCode(),sm.getBaseId());
        }
        SJson sjson = new SJson(sm);
        sjson.addFields("className", "StartModel");
        logger.error("---------------StartModel------------------------"+sjson.toString()+"\n");
        ioSession.write(sjson.toString()+"\n");
        return getOk();
    }
    /*
 * 教师发卷
 * 缓存信息
 * */
    @ResponseBody
    @RequestMapping(value = "/getBaseId", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String, Object> getBaseId( @RequestBody String requestBody ) {
        StartModel sm = JSONObject.parseObject(requestBody, StartModel.class);
        Map<String, Object> map = new HashMap<>();
        map.put("baseId",redisClientTemplate.get("sunvote" + sm.getTestCode()));
        return getOk();
    }
    @ResponseBody
    @RequestMapping(value = "/verification", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String, Object> val( @RequestBody String requestBody ) {
        CollectModel cm = JSONObject.parseObject(requestBody, CollectModel.class);
        IoSession ioSession = IoSessionManager.getIoSession(cm.getBaseId());
        if (ioSession == null){
            Map<String, Object> map = new HashMap<>();
            map.put("code", "NO");
            return map;
        }
        return getOk();
    }
    @ResponseBody
    @RequestMapping(value = "/collect", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String, Object> collect( @RequestBody String requestBody ) {
        CollectModel cm = JSONObject.parseObject(requestBody, CollectModel.class);
        IoSession ioSession = IoSessionManager.getIoSession(cm.getBaseId());
        SJson sjson = new SJson(cm);
        sjson.addFields("className", "CollectModel");
        ioSession.write(sjson.toString()+"\n");
        return getOk();
    }
    @ResponseBody
    @RequestMapping(value = "/collectExercisesAnswer", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String, Object> collectExercisesAnswer( @RequestBody String requestBody ) {
        ExercisesAnswer ea = JSONObject.parseObject(requestBody, ExercisesAnswer.class);
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isBlank(ea.getTestCode())) {
            map.put("code", "testCode is null");
            return map;
        }
        if (CollectionUtils.isEmpty(ea.getAnswers())) {
            map.put("code", "answers is null");
            return map;
        }
        try {
            boolean b = classTestService.collectExercisesAnswer(ea);
            map.put("code", "OK");
            if (!b){
                map.put("code", "NO");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

        public   Map<String, Object>  getOk(){
            Map<String, Object> map = new HashMap<>();
            map.put("code", "OK");
            return  map;
        }


}
