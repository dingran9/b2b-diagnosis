package com.eedu.diagnosis.manager.controller.sysytem;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.manager.model.request.*;
import com.eedu.diagnosis.manager.model.response.DiagnosisComplexPaperVo;
import com.eedu.diagnosis.manager.model.response.DiagnosisPaperSchoolRelationVo;
import com.eedu.diagnosis.manager.model.response.DiagnosisPaperVo;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.ManagerTeacherService;
import com.github.pagehelper.PageInfo;
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
 * zz
 */
@RestController
@RequestMapping("/manager/teacher")
public class ManagerTeacherController {
    private final Logger logger = LoggerFactory.getLogger(ManagerTeacherController.class);
    @Autowired
    private ManagerTeacherService managerTeacherServiceImpl;

    /**
     * 用户反馈
     */
    @RequestMapping(value = "/saveDiagnosisFeedback", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse saveDiagnosisFeedback(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisFeedbackModel model = JSONObject.parseObject(requestBody, DiagnosisFeedbackModel.class);

        if (StringUtils.isBlank(model.getUserCode())) {
            logger.error("ManagerTeacherController saveDiagnosisFeedback  error Exception：UserCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "UserCode");
        }
        if (StringUtils.isBlank(model.getFeedbackContent())) {
            logger.error("ManagerTeacherController saveDiagnosisFeedback  Exception：fedbackContent is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "feedbackContent");
        }
        if (null == model.getUserType()) {
            logger.error("ManagerTeacherController saveDiagnosisFeedback  Exception：userType is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userType");
        }
        try {
            boolean result = managerTeacherServiceImpl.saveDiagnosisFeedback(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  saveDiagnosisFeedback  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 添加单科卷
     */
    @RequestMapping(value = "/saveDiagnosisPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse saveDiagnosisPaper(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperModel model = JSONObject.parseObject(requestBody, DiagnosisPaperModel.class);
        if (StringUtils.isBlank(model.getDiagnosisPaperName())) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：diagnosisPaperName is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperName");
        }
        if (StringUtils.isBlank(model.getUnitCode())) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：unitCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "unitCode");
        }
        if (StringUtils.isBlank(model.getUnitName())) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：unitName is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "unitName");
        }
        if (StringUtils.isBlank(model.getResourcePaperCode())) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：resourcePaperCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "resourcePaperCode");
        }
        if (StringUtils.isBlank(model.getResourcePaperName())) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：resourcePaperName is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "resourcePaperName");
        }
        if (null == model.getSubjectCode()) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：subjectCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (null == model.getPaperType()) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：paperType is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "paperType");
        }
        if (null == model.getStageCode()) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：stageCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
        }
        if (null == model.getGradeCode()) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：gradeCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getTotalScore()) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：totalScore is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "totalScore");
        }
        if (null == model.getResourceType()) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：resourceType is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "resourceType");
        }
        if (StringUtils.isBlank(model.getOperator())) {
            logger.error("ManagerTeacherController saveDiagnosisPaper Exception：operator is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "operator");
        }
        try {
            boolean result = managerTeacherServiceImpl.saveDiagnosisPaper(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  saveDiagnosisPaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 修改单科卷
     */
    @RequestMapping(value = "/updateDiagnosisPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse updateDiagnosisPaper(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperModel model = JSONObject.parseObject(requestBody, DiagnosisPaperModel.class);
        try {
            boolean result = managerTeacherServiceImpl.updateDiagnosisPaper(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  updateDiagnosisPaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 添加单科卷和学校的关系
     */
    @RequestMapping(value = "/saveDiagnosisPaperSchoolRelation", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse saveDiagnosisPaperSchoolRelation(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperSchoolRelationModel model = JSONObject.parseObject(requestBody, DiagnosisPaperSchoolRelationModel.class);
        if (model.getSchoolModelList() == null || model.getSchoolModelList().size() == 0) {
            logger.error("ManagerTeacherController saveDiagnosisPaperSchoolRelation Exception：schoolModelList is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolModeList");
        }
        try {
            boolean result = managerTeacherServiceImpl.saveDiagnosisPaperSchoolRelation(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  saveDiagnosisPaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 添加全科卷
     */
    @RequestMapping(value = "/saveDiagnosisComplexPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse saveDiagnosisComplexPaper(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisComplexPaperModel model = JSONObject.parseObject(requestBody, DiagnosisComplexPaperModel.class);
        if (null == model.getSchoolCode()) {
            logger.error("ManagerTeacherController saveDiagnosisComplexPaper Exception：schoolCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if (null == model.getGradeCode()) {
            logger.error("ManagerTeacherController saveDiagnosisComplexPaper Exception：gradeCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getDiagnosisPaperModelsList() || model.getDiagnosisPaperModelsList().isEmpty()) {
            logger.error("ManagerTeacherController saveDiagnosisComplexPaper Exception：diagnosisPaperModelsList is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperModelsList");
        }
        if (StringUtils.isEmpty(model.getPaperType())) {
            logger.error("ManagerTeacherController saveDiagnosisComplexPaper Exception：paperType is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "paperType");
        }
        if (StringUtils.isEmpty(model.getDiagnosisPaperName())) {
            logger.error("ManagerTeacherController saveDiagnosisComplexPaper Exception：diagnosisPaperName is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperName");
        }
        try {
            boolean result = managerTeacherServiceImpl.saveDiagnosisComplexPaper(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  saveDiagnosisComplexPaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 查询单科卷 分页（后台管理调用）
     */
    @RequestMapping(value = "/getDiagnosisPaperByPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisPaperByPaper(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperModel model = JSONObject.parseObject(requestBody, DiagnosisPaperModel.class);
        if (null == model.getPageNum()) {
            logger.error("ManagerTeacherController  getDiagnosisPaperByPaper Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("ManagerTeacherController  getDiagnosisPaperByPaper Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            PageInfo<DiagnosisPaperVo> result = managerTeacherServiceImpl.getDiagnosisPaperByPaper(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  getDiagnosisPaperByPaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 查询单科卷 分页(B端老师发布测试 调用)
     */
    @RequestMapping(value = "/getDiagnosisPaperRelationSchoolByPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisPaperRelationSchoolByPaper(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperModel model = JSONObject.parseObject(requestBody, DiagnosisPaperModel.class);
        if (null == model.getPageNum()) {
            logger.error("ManagerTeacherController  getDiagnosisPaperRelationSchoolByPaper Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("ManagerTeacherController  getDiagnosisPaperRelationSchoolByPaper Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        if (StringUtils.isBlank(model.getUnitCode())) {
            logger.error("ManagerTeacherController getDiagnosisPaperRelationSchoolByPaper Exception：unitCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "unitCode");
        }
        if (null == model.getSubjectCode()) {
            logger.error("ManagerTeacherController getDiagnosisPaperRelationSchoolByPaper Exception：subjectCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (StringUtils.isBlank(model.getSchoolCode())) {
            logger.error("ManagerTeacherController getDiagnosisPaperRelationSchoolByPaper Exception：schoolCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        try {
            PageInfo<DiagnosisPaperVo> result = managerTeacherServiceImpl.getDiagnosisPaperRelationSchoolByPaper(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  getDiagnosisPaperRelationSchoolByPaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 查询全科卷 分页
     */
    @RequestMapping(value = "/getDiagnosisComplexPaperByPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisComplexPaperByPaper(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisComplexPaperModel model = JSONObject.parseObject(requestBody, DiagnosisComplexPaperModel.class);
        if (null == model.getPageNum()) {
            logger.error("ManagerTeacherController  getDiagnosisComplexPaperByPaper Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("ManagerTeacherController  getDiagnosisComplexPaperByPaper Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            PageInfo<DiagnosisComplexPaperVo> result = managerTeacherServiceImpl.getDiagnosisComplexPaperByPaper(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  getDiagnosisComplexPaperByPaper  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 查询全科-关联的详情
     */
    @RequestMapping(value = "/getDiagnosisComplexPaperList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisComplexRelationList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisComplexPaperModel model = JSONObject.parseObject(requestBody, DiagnosisComplexPaperModel.class);
        try {
            List<DiagnosisComplexPaperVo> result = managerTeacherServiceImpl.getDiagnosisComplexRelationList(model.getCode());
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  getDiagnosisComplexPaperList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 全科code查询下面的单科卷
     */
    @RequestMapping(value = "/getDiagnosisPaperListByComplexPaperCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisPaperListByComplexPaperCode(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisComplexPaperModel model = JSONObject.parseObject(requestBody, DiagnosisComplexPaperModel.class);
        try {
            List<DiagnosisPaperVo> result = managerTeacherServiceImpl.getDiagnosisPaperListByComplexPaperCode(model.getCode());
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  getDiagnosisComplexPaperList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 修改全科卷发布状态
     */
    @RequestMapping(value = "/bathUpdateDiagnosisComplexPaperByIsRelease", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse BathUpdateDiagnosisComplexPaperByIsRelease(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        List<String> list = JSONObject.parseArray(requestBody, String.class);
        try {
            boolean result = managerTeacherServiceImpl.bathUpdateDiagnosisComplexPaperByIsRelease(list);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  getDiagnosisComplexPaperList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 添加全科卷
     */
    @RequestMapping(value = "/updateDiagnosisComplexPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse updateDiagnosisComplexPaper(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisComplexPaperModel model = JSONObject.parseObject(requestBody, DiagnosisComplexPaperModel.class);
        try {
            boolean result = managerTeacherServiceImpl.updateDiagnosisComplexPaper(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseResponse;
    }

    /**
     * 查询单科卷和学校的关系
     */
    @RequestMapping(value = "/getDiagnosisPaperSchoolRelationList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisPaperSchoolRelationList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperSchoolRelationModel model = JSONObject.parseObject(requestBody, DiagnosisPaperSchoolRelationModel.class);
        if (null == model.getPageNum()) {
            logger.error("ManagerTeacherController  getDiagnosisPaperSchoolRelationList Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("ManagerTeacherController  getDiagnosisPaperSchoolRelationList Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        if (StringUtils.isBlank(model.getDiagnosisPaperCode())) {
            logger.error("ManagerTeacherController getDiagnosisPaperSchoolRelationList Exception：diagnosisPaperCode is null." + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperCode");
        }
        try {
            PageInfo<DiagnosisPaperSchoolRelationVo> result = managerTeacherServiceImpl.getDiagnosisPaperSchoolRelationList(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  getDiagnosisPaperSchoolRelationList  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 修改单科卷和学校的关系发布状态
     */
    @RequestMapping(value = "/updateDiagnosisPaperSchoolRelation", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse updateDiagnosisPaperSchoolRelation(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        List<DiagnosisPaperSchoolRelationModel> list = JSONObject.parseArray(requestBody, DiagnosisPaperSchoolRelationModel.class);
        if (null == list || list.size() == 0) {
            logger.error("ManagerTeacherController  updateDiagnosisPaperSchoolRelation Exception：diagnosisPaperSchoolRelationModel is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperSchoolRelationModel");
        }
        try {
            boolean result = managerTeacherServiceImpl.updateDiagnosisPaperSchoolRelation(list);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  updateDiagnosisPaperSchoolRelation  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 新增和更新单元进度
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/saveAndUpdateUnitSchedule", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse saveAndUpdateUnitSchedule(@RequestParam("requestId") String requestId,
                                                  @RequestBody @Valid DiagnosisUnitScheduleListModel model, BindingResult bindingResult) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : allErrors) {
                sb.append(" <" + error.getDefaultMessage() + "> ");
            }
            logger.error("ManagerTeacherController  teachingManagerRelease error Exception："+sb.toString());
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),
                    sb.toString());
        }
        try {
            managerTeacherServiceImpl.saveAndUpdateUnitSchedule(model);
            baseResponse.setResult(true);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  updateDiagnosisPaperSchoolRelation  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取单元进度列表
     * @param requestId
     * @param model
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/getScheduleList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getScheduleList(@RequestParam("requestId") String requestId,
                                                  @RequestBody @Valid GetUnitScheduleListModel model, BindingResult bindingResult) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : allErrors) {
                sb.append(" <" + error.getDefaultMessage() + "> ");
            }
            logger.error("ManagerTeacherController  getScheduleList error Exception："+sb.toString());
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),
                    sb.toString());
        }
        if (null == model.getPageNum()) {
            logger.error("ManagerTeacherController  getScheduleList Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("ManagerTeacherController  getScheduleList Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            Map<String, Object> result = managerTeacherServiceImpl.getScheduleList(model);
            baseResponse.setResult(result);
        } catch (Exception e) {
            logger.error(requestId + "ManagerTeacherController  updateDiagnosisPaperSchoolRelation  error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
}
