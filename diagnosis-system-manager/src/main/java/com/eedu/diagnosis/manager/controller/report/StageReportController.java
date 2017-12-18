package com.eedu.diagnosis.manager.controller.report;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.manager.model.request.StageReportModel;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eeduspace.b2b.report.model.AchievementDto;
import com.eeduspace.b2b.report.model.BaseAbilityDto;
import com.eeduspace.b2b.report.model.BaseKnowledgeModuleDto;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.model.report.principal.StageTeachingProgressModel;
import com.eeduspace.b2b.report.service.StageTestReportOpenService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhongfei on 2017/10/13.
 */
@RestController
@RequestMapping("/stagereport")
@Slf4j
public class StageReportController {

    private final Logger logger = LoggerFactory.getLogger(StageReportController.class);

    @Autowired
    private StageTestReportOpenService stageTestReportOpenService;

    /**
     * 获取能力信息
     * @param requestId
     * @param requestBody 请求体
     * @return
     */
    @RequestMapping(value = "/getStageAbility", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse  getStageAbility(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        BaseResponse baseResponse=new BaseResponse(requestId);

        try {
            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getContrastType())){
                logger.error("contrastType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"contrastType is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            List<BaseAbilityDto> stageAbility = stageTestReportOpenService.getStageAbility(stageReportModel.getContrastType(), stageReportModel.getReleaseCode()
                                , stageReportModel.getSemester(), stageReportModel.getDistrictId(), stageReportModel.getSubjectCode() + "");
            baseResponse.setResult(stageAbility);
        } catch (Exception e) {
            log.error("ResearchReportController.getStageAbility error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取知识点模块掌握信息
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getStageKnowledgeModule", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse  getStageKnowledgeModule(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getContrastType())){
                logger.error("contrastType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"contrastType is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            List<BaseKnowledgeModuleDto> stageKnowledgeModule = stageTestReportOpenService.getStageKnowledgeModule(stageReportModel.getContrastType(), stageReportModel.getReleaseCode(),
                                stageReportModel.getSemester(), stageReportModel.getDistrictId(), stageReportModel.getSubjectCode() + "");
            baseResponse.setResult(stageKnowledgeModule);
        } catch (Exception e) {
            log.error("ResearchReportController.getStageKnowledgeModule error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取教学成绩
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getStageTeachineAchievement", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse  getStageTeachineAchievement(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getContrastType())){
                logger.error("contrastType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"contrastType is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            List<AchievementDto> stageTeachineAchievement = stageTestReportOpenService.getStageTeachineAchievement(stageReportModel.getContrastType(), stageReportModel.getReleaseCode(),
                                stageReportModel.getDistrictId(), stageReportModel.getSemester(), stageReportModel.getSubjectCode() + "");
            baseResponse.setResult(stageTeachineAchievement);
        } catch (Exception e) {
            log.error("ResearchReportController.getStageTeachineAchievement error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取标准分平均分历史变动
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getStandardScoreChange", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse  getStandardScoreChange(@RequestParam("requestId") String requestId,@RequestBody String requestBody){
        BaseResponse baseResponse=new BaseResponse(requestId);
        try {
            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getContrastType())){
                logger.error("contrastType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"contrastType is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getGradeCode() || stageReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            Map<String, Object> standardScoreChange = stageTestReportOpenService.getStandardScoreChange(stageReportModel.getContrastType(), stageReportModel.getReleaseCode(),
                                stageReportModel.getDistrictId(), stageReportModel.getSemester(), stageReportModel.getSubjectCode(),
                                stageReportModel.getGradeCode());
            baseResponse.setResult(standardScoreChange);
        } catch (Exception e) {
            log.error("ResearchReportController.getStageTeachineAchievement error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 教研员阶段考试题质量分析
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stageQualityofQuestionsReport", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse stageQualityofQuestionsReport(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);

            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getArtType() || stageReportModel.getArtType() < 0){
                logger.error("artType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"artType is null");
            }
            List<QuestionsqualityModel> result = stageTestReportOpenService.stageQualityofQuestionsReport( stageReportModel.getReleaseCode(),  stageReportModel.getDistrictId(),  stageReportModel.getSemester(),  stageReportModel.getSubjectCode(),  stageReportModel.getArtType());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.stageQualityofQuestionsReport error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }



    /**
     * 教研员阶段考全区测情况
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stageAreaHappening", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse stageAreaHappening(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);

            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            AreaHappenModel result = stageTestReportOpenService.stageAreaHappening(stageReportModel.getReleaseCode(),  stageReportModel.getSubjectCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.stageAreaHappening error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     * 教研员阶段考全区各学校测试情况
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stageAreaHappeningBySchool", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse stageAreaHappeningBySchool(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);

            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            List<SchoolHappenModel> result = stageTestReportOpenService.stageAreaHappeningBySchool(stageReportModel.getReleaseCode(),  stageReportModel.getSubjectCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.stageAreaHappeningBySchool error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }



    /**
     * 教研员阶段考全区各学校班级测试情况
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stageAreaHappeningByclass", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse stageAreaHappeningByclass(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);

            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            List<ClassHappenModel> result = stageTestReportOpenService.stageAreaHappeningByclass(stageReportModel.getReleaseCode(),  stageReportModel.getSubjectCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.stageAreaHappeningByclass error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     *  教研员阶段考区域学生成绩分布
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stageResultsDistribution", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse stageResultsDistribution(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);

            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getGradeCode() || stageReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<DistributedModel> result = stageTestReportOpenService.stageResultsDistribution( stageReportModel.getReleaseCode(),  stageReportModel.getDistrictId(),  stageReportModel.getSemester(),  stageReportModel.getGradeCode(),  stageReportModel.getSubjectCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.stageResultsDistribution error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     *  教研员阶段考区域学校成绩分布
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stageResultsDistributionBySchool", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse stageResultsDistributionBySchool(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);

            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getGradeCode() || stageReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<ResultsModel> result = stageTestReportOpenService.stageResultsDistributionBySchool( stageReportModel.getReleaseCode(),  stageReportModel.getDistrictId(),  stageReportModel.getSemester(),  stageReportModel.getGradeCode(),  stageReportModel.getSubjectCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.stageResultsDistributionBySchool error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     * 教研员阶段考区域学校班级成绩分布
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stageResultsDistributionByClass", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse stageResultsDistributionByClass(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);

            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getGradeCode() || stageReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<ResultsModel> result = stageTestReportOpenService.stageResultsDistributionByClass( stageReportModel.getReleaseCode(),  stageReportModel.getDistrictId(),  stageReportModel.getSemester(),  stageReportModel.getGradeCode(),  stageReportModel.getSubjectCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.stageResultsDistributionByClass error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }



    /**
     *  教研员阶段考全区各年级总体情进度
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/stageTeachingProgressByArea", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse stageTeachingProgressByArea(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            StageReportModel stageReportModel = JSONObject.parseObject(requestBody,StageReportModel.class);

            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getReleaseCode())){
                logger.error("releaseCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"releaseCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getDistrictId() || stageReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == stageReportModel || StringUtils.isBlank(stageReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == stageReportModel || null == stageReportModel.getSubjectCode() || stageReportModel.getSubjectCode() <= 0){
                logger.error("subjectCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"subjectCode is null");
            }
            if(null == stageReportModel || null == stageReportModel.getGradeCode() || stageReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<StageTeachingProgressModel>  result = stageTestReportOpenService.stageTeachingProgressByArea( stageReportModel.getReleaseCode(),  stageReportModel.getDistrictId(),  stageReportModel.getSemester(),  stageReportModel.getGradeCode(),  stageReportModel.getSubjectCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.stageTeachingProgressByArea error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


}
