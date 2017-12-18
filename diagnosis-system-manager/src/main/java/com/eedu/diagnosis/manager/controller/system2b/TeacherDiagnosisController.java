package com.eedu.diagnosis.manager.controller.system2b;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.enumration.ExamScopeEnum;
import com.eedu.diagnosis.common.enumration.ExamTypeEnum;
import com.eedu.diagnosis.common.exception.DiagnosisException;
import com.eedu.diagnosis.common.model.paperEntity.PaperSystem;
import com.eedu.diagnosis.manager.model.request.*;
import com.eedu.diagnosis.manager.model.response.DiagnosisClassRelationModel;
import com.eedu.diagnosis.manager.model.response.DiagnosisRecordTeacherModel;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.TeacherDiagnosisService;
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
 * Created by dqy on 2017/3/20.
 */
@RestController
@RequestMapping("/teacher/diagnosis")
public class TeacherDiagnosisController {
    private final Logger logger = LoggerFactory.getLogger(TeacherDiagnosisController.class);
    @Autowired
    private TeacherDiagnosisService diagnosisService;

    /**
     * 发布测试
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/release", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse release(@RequestParam("requestId") String requestId,
                                                  @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisReleaseModle model = JSONObject.parseObject(requestBody,DiagnosisReleaseModle.class);
        if (StringUtils.isBlank(model.getDiagnosisName())) {
            logger.error("TeacherDiagnosisController release Exception：diagnosisName is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisName");
        }
        if (StringUtils.isBlank(model.getDiagnosisPaperName())) {
            logger.error("TeacherDiagnosisController release Exception：diagnosisPaperName is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperName");
        }
        if (null == model.getStartTime()) {
            logger.error("TeacherDiagnosisController release Exception：startTime is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "startTime");
        }
        if (null == model.getEndTime()) {
            logger.error("TeacherDiagnosisController release Exception：endTime is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "endTime");
        }
        if (null == model.getSchoolCode()) {
            logger.error("TeacherDiagnosisController release Exception：schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if (StringUtils.isBlank(model.getSchoolName())) {
            logger.error("TeacherDiagnosisController release Exception：schoolName is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolName");
        }
        if (StringUtils.isBlank(model.getDiagnosisPaperCode())) {
            logger.error("TeacherDiagnosisController release Exception：diagnosisPaperCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "diagnosisPaperCode");
        }
        if (null == model.getTeacherCode()) {
            logger.error("TeacherDiagnosisController release Exception：teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if (StringUtils.isBlank(model.getTeacherName())) {
            logger.error("TeacherDiagnosisController release Exception：teacherName is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherName");
        }
        if (null == model.getStageCode()) {
            logger.error("TeacherDiagnosisController release Exception：stageCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
        }
        if (null == model.getGradeCode()) {
            logger.error("TeacherDiagnosisController releaseDiagnosis Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getUseType()) {
            logger.error("TeacherDiagnosisController releaseDiagnosis Exception：useType is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "useType");
        }
        if (null == model.getExamType()) {
            logger.error("TeacherDiagnosisController release Exception：examType is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "examType");
        }
        if (StringUtils.isEmpty(model.getGroupAreaDistrictName())) {
            logger.error("TeacherDiagnosisController release Exception：groupAreaDistrictName is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupAreaDistrictName");
        }
        if (null == model.getGroupAreaDistrictId()) {
            logger.error("TeacherDiagnosisController release Exception：groupAreaDistrictId is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupAreaDistrictId");
        }
        if(ExamTypeEnum.UNIT.getCode().equals(model.getExamType())){
            if (null == model.getClassCodes() || model.getClassCodes().isEmpty()) {
                logger.error("TeacherDiagnosisController release Exception：classCodes is null."
                        + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCodes");
            }
            if (null == model.getSubjectCode()) {
                logger.error("TeacherDiagnosisController release Exception：subjectCode is null."
                        + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
            }
            if (null == model.getTotalScore()) {
                logger.error("TeacherDiagnosisController release Exception：totalScore is null."
                        + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "totalScore");
            }
            if (StringUtils.isEmpty(model.getUnitCode())) {
                logger.error("TeacherDiagnosisController release Exception：unitCode is null."
                        + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "unitCode");
            }
            if (StringUtils.isEmpty(model.getUnitName())) {
                logger.error("TeacherDiagnosisController release Exception：unitName is null."
                        + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "unitName");
            }
        }else{
            if (null == model.getGradeId()) {
                logger.error("TeacherDiagnosisController release Exception：gradeId is null."
                        + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "totalScore");
            }
        }
        try {
            boolean result = diagnosisService.releaseSingleDiagnosis(model);
            baseResponse.setResult(result);
        } catch (DiagnosisException e) {
            logger.error(requestId + "TeacherDiagnosisController release error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),e.getCode(),e.getMessage());
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController release error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 教育管理者发布区域考试
     * @param requestId
     * @param model
     * @return
     */
    @RequestMapping(value = "/teachingManagerRelease", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse teachingManagerRelease(@RequestParam("requestId") String requestId,
                                               @RequestBody @Valid ReleaseAreaExamModel model, BindingResult bindingResult) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : allErrors) {
                sb.append(" <" + error.getDefaultMessage() + "> ");
            }
            logger.error("TeacherDiagnosisController  teachingManagerRelease error Exception："+sb.toString());
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),
                    sb.toString());
        }
        if (model.getExamScope().equals(ExamScopeEnum.SOME_SCHOOL.getValue())
                && CollectionUtils.isEmpty(model.getSchools())) {
            logger.error("TeacherDiagnosisController teachingManagerRelease Exception：schools is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schools");
        }
        try {
            boolean result = diagnosisService.teachingManagerRelease(model);
            baseResponse.setResult(result);
        } catch (DiagnosisException e) {
            logger.error(requestId + "TeacherDiagnosisController release error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),e.getCode(),e.getMessage());
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController release error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取区域考试列表
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/areaExamList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse areaExamList(@RequestParam("requestId") String requestId,
                                               @RequestBody String jsonString) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisRecordTeacherRequestModel teacherModel = JSONObject.parseObject(jsonString, DiagnosisRecordTeacherRequestModel.class);
        if (null == teacherModel.getPageNum()) {
            logger.error("TeacherDiagnosisController areaExamList Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == teacherModel.getPageSize()) {
            logger.error("TeacherDiagnosisController areaExamList Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            Map<String, Object> diagnosisList = diagnosisService.areaExamList(teacherModel);
            baseResponse.setResult(diagnosisList);
        } catch (DiagnosisException e) {
            logger.error(requestId + "TeacherDiagnosisController areaExamList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),e.getCode(),e.getMessage());
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController areaExamList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 全区考试 所包含的学科列表
     * @param requestId
     * @param jsonString
     * @return
     */
    @RequestMapping(value = "/examSubjectList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse examSubjectList(@RequestParam("requestId") String requestId,
                                     @RequestBody String jsonString) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisRecordTeacherRequestModel teacherModel = JSONObject.parseObject(jsonString, DiagnosisRecordTeacherRequestModel.class);
        if (StringUtils.isEmpty(teacherModel.getCode())) {
            logger.error("TeacherDiagnosisController examSubjectList Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        try {
            List<Integer> subjects = diagnosisService.examSubjectList(teacherModel.getCode());
            baseResponse.setResult(subjects);
        } catch (DiagnosisException e) {
            logger.error(requestId + "TeacherDiagnosisController examSubjectList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),e.getCode(),e.getMessage());
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController examSubjectList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    @RequestMapping(value = "/paperDetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse paperDetail(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisPaperModel model = JSONObject.parseObject(requestBody,DiagnosisPaperModel.class);
        if (StringUtils.isBlank(model.getCode())) {
            logger.error("StudentDiagnosisController paperDetail Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
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
     * 普通教师获取自己发布的诊断列表（参数为teacherCode）
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/getTeacherDiagnosisList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getTeacherDiagnosisList(@RequestParam("requestId") String requestId,
                                         @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        TeacherModel model = JSONObject.parseObject(requestBody,TeacherModel.class);

        if (null == model.getSchoolCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception： schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
//        if (null == model.getSubjectCode()) {
//            logger.error("TeacherDiagnosisController getDiagnosisList Exception： subjectCode is null."
//                    + baseResponse.getRequestId());
//            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
//        }
//        if (null == model.getClassCodes() || model.getClassCodes().isEmpty()) {
//            logger.error("TeacherDiagnosisController getDiagnosisList Exception： classCodes is null."
//                    + baseResponse.getRequestId());
//            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCodes");
//        }
        if (null == model.getTeacherCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception： teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if (null == model.getPageNum()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            Map<String, Object> diagnosisList = diagnosisService.getTeacherDiagnosisList(model);
            baseResponse.setResult(diagnosisList);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController getDiagnosisList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 普通教师获取自己发布的诊断列表
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/getTeacherDiagnosisListV2", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getTeacherDiagnosisListV2(@RequestParam("requestId") String requestId,
                                                @RequestBody String modelJson) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        TeacherModel model = JSONObject.parseObject(modelJson, TeacherModel.class);
        if (null == model.getSchoolCode()) {
            logger.error("TeacherDiagnosisController getTeacherDiagnosisListV2 Exception： schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if (null == model.getSubjectCode()) {
            logger.error("TeacherDiagnosisController getTeacherDiagnosisListV2 Exception： subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (null == model.getClassCodes() || model.getClassCodes().isEmpty()) {
            logger.error("TeacherDiagnosisController getTeacherDiagnosisListV2 Exception： classCodes is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCodes");
        }
        if (null == model.getTeacherCode()) {
            logger.error("TeacherDiagnosisController getTeacherDiagnosisListV2 Exception： teacherCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
        }
        if (null == model.getPageNum()) {
            logger.error("TeacherDiagnosisController getTeacherDiagnosisListV2 Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("TeacherDiagnosisController getTeacherDiagnosisListV2 Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            Map<String, Object> diagnosisList = diagnosisService.getTeacherDiagnosisListV2(model);
            baseResponse.setResult(diagnosisList);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController getTeacherDiagnosisListV2 error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 普通教师获取班级诊断历史（参数为classCodes 由于从相应班级进去的  所以集合中只会有一个班级code） 班级报告都为已读状态的数据
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/getDiagnosisList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisList(@RequestParam("requestId") String requestId,
                                         @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        TeacherModel model = JSONObject.parseObject(requestBody,TeacherModel.class);
        if (null == model.getSchoolCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception： schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
//        if (null == model.getTeacherCode()) {
//            logger.error("TeacherDiagnosisController getDiagnosisList Exception： teacherCode is null."
//                    + baseResponse.getRequestId());
//            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherCode");
//        }
        if (null == model.getSubjectCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception： subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (null == model.getClassCodes() || model.getClassCodes().isEmpty()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception： classCodes is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCodes");
        }
        if (null == model.getPageNum()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("TeacherDiagnosisController getDiagnosisList Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            Map<String, Object> diagnosisList = diagnosisService.getDiagnosisList(model);
            baseResponse.setResult(diagnosisList);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController getDiagnosisList error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 校长、老师获取全科考试列表
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/getDiagnosisListForMaster", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisListForMaster(@RequestParam("requestId") String requestId,
                                                  @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        TeacherModel model = JSONObject.parseObject(requestBody,TeacherModel.class);
        if (null == model.getSchoolCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisListForMaster Exception： schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if (null == model.getPageNum()) {
            logger.error("TeacherDiagnosisController getDiagnosisListForMaster Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("TeacherDiagnosisController getDiagnosisListForMaster Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            PageInfo<DiagnosisRecordTeacherModel> diagnosisListForMaster = diagnosisService.getDiagnosisListForMaster(model);
            baseResponse.setResult(diagnosisListForMaster);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController getDiagnosisListForMaster error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

    /**
     * 教师获取全科考试历史记录
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/getDiagnosisHistoryListForMaster", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisHistoryListForMaster(@RequestParam("requestId") String requestId,
                                                         @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        TeacherModel model = JSONObject.parseObject(requestBody,TeacherModel.class);
        if (null == model.getSchoolCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisListForMaster Exception： schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        if (null == model.getClassCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisListForMaster Exception： classCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCode");
        }
        if (null == model.getPageNum()) {
            logger.error("TeacherDiagnosisController getDiagnosisListForMaster Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("TeacherDiagnosisController getDiagnosisListForMaster Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            PageInfo<DiagnosisRecordTeacherModel> diagnosisHistoryListForMaster = diagnosisService.getDiagnosisHistoryListForMaster(model);
            baseResponse.setResult(diagnosisHistoryListForMaster);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController getDiagnosisListForMaster error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

    /**
     * 校长查看报告时 获取该次考试所包含的学科
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/getSubjectsForExam", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getSubjectsForExam(@RequestParam("requestId") String requestId,
                                           @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisRecordTeacherRequestModel model = JSONObject.parseObject(requestBody, DiagnosisRecordTeacherRequestModel.class);
        if (StringUtils.isEmpty(model.getCode())) {
            logger.error("TeacherDiagnosisController getSubjectsForExam Exception： code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        try {
            List<Integer> subjects = diagnosisService.examSubjectList(model.getCode());
            baseResponse.setResult(subjects);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController getSubjectsForExam error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 该次测试 各班平均分
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/getDiagnosisDetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getDiagnosisDetail(@RequestParam("requestId") String requestId,
                                           @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        TeacherModel model = JSONObject.parseObject(requestBody,TeacherModel.class);
        if (StringUtils.isBlank(model.getCode())) {
            logger.error("TeacherDiagnosisController getDiagnosisDetail Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if (null == model.getGradeCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisDetail Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getSubjectCode()) {
            logger.error("TeacherDiagnosisController getDiagnosisDetail Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (null == model.getClassCodes() || model.getClassCodes().isEmpty()) {
            logger.error("TeacherDiagnosisController getDiagnosisDetail Exception： classCodes is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classCodes");
        }
        if (null == model.getPageNum()) {
            logger.error("TeacherDiagnosisController getDiagnosisDetail Exception：pageNum is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageNum");
        }
        if (null == model.getPageSize()) {
            logger.error("TeacherDiagnosisController getDiagnosisDetail Exception：pageSize is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "pageSize");
        }
        try {
            List<Map<String, Object>> diagnosisList = diagnosisService.getDiagnosisDetail(model);
            baseResponse.setResult(diagnosisList);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController getDiagnosisDetail error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 更新班级报告的已读状态
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/updateReadStatus", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse updateReadStatus(@RequestParam("requestId") String requestId,
                                           @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        JSONObject model = JSONObject.parseObject(requestBody);
        if (StringUtils.isBlank(model.getString("diagnosisClassRelationCode"))) {
            logger.error("TeacherDiagnosisController updateReadStatus Exception：diagnosisClassRelationCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        try {
            boolean a = diagnosisService.updateReadStatus(model.getString("diagnosisClassRelationCode"));
            baseResponse.setResult(a);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController updateReadStatus error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 布置作业（选资源库试卷模式）
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/saveClassworkPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse savePaper(@RequestParam("requestId") String requestId,
                                  @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ClassworkModel model = JSONObject.parseObject(requestBody,ClassworkModel.class);
        if (StringUtils.isBlank(model.getCode())) {
            logger.error("TeacherDiagnosisController saveClassworkPaper Exception：teacherName is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "teacherName");
        }
        if (null == model.getSubjectCode()) {
            logger.error("TeacherDiagnosisController saveClassworkPaper Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (null == model.getGradeCode()) {
            logger.error("TeacherDiagnosisController saveClassworkPaper Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getStageCode()) {
            logger.error("TeacherDiagnosisController saveClassworkPaper Exception：stageCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
        }
        if (null == model.getSchoolCode()) {
            logger.error("TeacherDiagnosisController saveClassworkPaper Exception：schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        try {
            String paperCode = diagnosisService.saveClassworkPaper(model);
            baseResponse.setResult(paperCode);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController saveClassworkPaper error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 布置作业（选题模式）
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/saveClassworkQuestionsToPaper", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse saveClassworkQuestionsToPaper(@RequestParam("requestId") String requestId,
                                                      @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        ClassworkModel model = JSONObject.parseObject(requestBody,ClassworkModel.class);
        if (null == model.getSmallQuestionSystems() || model.getSmallQuestionSystems().isEmpty()) {
            logger.error("TeacherDiagnosisController saveClassworkQuestionsToPaper Exception：smallQuestionSystems is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "smallQuestionSystems");
        }
        if (null == model.getSubjectCode()) {
            logger.error("TeacherDiagnosisController saveClassworkQuestionsToPaper Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        if (null == model.getGradeCode()) {
            logger.error("TeacherDiagnosisController saveClassworkQuestionsToPaper Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if (null == model.getStageCode()) {
            logger.error("TeacherDiagnosisController saveClassworkQuestionsToPaper Exception：stageCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "stageCode");
        }
        if (null == model.getSchoolCode()) {
            logger.error("TeacherDiagnosisController saveClassworkQuestionsToPaper Exception：schoolCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolCode");
        }
        try {
            String classworkPaperCode = diagnosisService.saveClassworkQuestionsToPaper(model);
            baseResponse.setResult(classworkPaperCode);
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController saveClassworkQuestionsToPaper error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取全科考单科发布的试卷列表
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/getExamPaperByParameter", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getExamPaperByParameter(@RequestParam("requestId") String requestId,
                                     @RequestBody String jsonString) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        DiagnosisRecordTeacherRequestModel teacherModel = JSONObject.parseObject(jsonString, DiagnosisRecordTeacherRequestModel.class);
        if (null == teacherModel || null == teacherModel.getCode() || "".equals(teacherModel.getCode())) {
            logger.error("TeacherDiagnosisController getExamPaperByParameter Exception：code is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "code");
        }
        if (null == teacherModel || null == teacherModel.getSubjectCode() || teacherModel.getSubjectCode() <= 0) {
            logger.error("TeacherDiagnosisController getExamPaperByParameter Exception：subjectCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectCode");
        }
        try {
            List<DiagnosisClassRelationModel>  diagnosisList = diagnosisService.getExamPaperByParameter(teacherModel);
            baseResponse.setResult(diagnosisList);
        } catch (DiagnosisException e) {
            logger.error(requestId + "TeacherDiagnosisController getExamPaperByParameter error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),e.getCode(),e.getMessage());
        } catch (Exception e) {
            logger.error(requestId + "TeacherDiagnosisController getExamPaperByParameter error: ", e);
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

}
