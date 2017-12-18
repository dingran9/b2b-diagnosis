package com.eedu.diagnosis.manager.controller.report;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.manager.model.request.ResearchReportModel;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eeduspace.b2b.report.model.AchievementDto;
import com.eeduspace.b2b.report.model.BaseAbilityDto;
import com.eeduspace.b2b.report.model.BaseKnowledgeModuleDto;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.service.ResearchReportOpenService;
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
@RequestMapping("/researchreport")
@Slf4j
public class ResearchReportController {

    private final Logger logger = LoggerFactory.getLogger(ResearchReportController.class);

    @Autowired
    private ResearchReportOpenService researchReportOpenService;

    /**
     * 获取单元区域分数 查看 全区年级与全区学校
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getAreaUnitScore", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getAreaUnitScore(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);

        try {
            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || CollectionUtils.isEmpty(researchReportModel.getUnitModels())){
                logger.error("unitModels is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitModels is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getContrastType())){
                logger.error("contrastType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"contrastType is null");
            }
            if(null == researchReportModel || null == researchReportModel.getGradeCode()){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }

            Map<String, Object> areaUnitScore = researchReportOpenService.getAreaUnitScore(researchReportModel.getContrastType(), researchReportModel.getGradeCode() + "",
                                researchReportModel.getSemester(), researchReportModel.getUnitModels(), researchReportModel.getDistrictId());
            baseResponse.setResult(areaUnitScore);
        } catch (Exception e) {
            log.error("ResearchReportController.getAreaUnitScore error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 单元统计--》获取知识点模块
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getUnitKnowledgeModule", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getUnitKnowledgeModule(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);

        try {
            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getContrastType())){
                logger.error("contrastType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"contrastType is null");
            }

            List<BaseKnowledgeModuleDto> unitKnowledgeModule = researchReportOpenService.getUnitKnowledgeModule(researchReportModel.getContrastType(), researchReportModel.getUnitCode(),
                                researchReportModel.getSemester(), researchReportModel.getDistrictId());
            baseResponse.setResult(unitKnowledgeModule);
        } catch (Exception e) {
            log.error("ResearchReportController.getUnitKnowledgeModule error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

    /**
     * 获取本单元教学成绩  维度-》班级与教师
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getUnitTeachingAchievement", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getUnitTeachingAchievement(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);

        try {
            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getContrastType())){
                logger.error("contrastType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"contrastType is null");
            }
            List<AchievementDto> unitTeachingAchievement = researchReportOpenService.getUnitTeachingAchievement(researchReportModel.getContrastType(), researchReportModel.getUnitCode(),
                                researchReportModel.getSemester(), researchReportModel.getDistrictId());
            baseResponse.setResult(unitTeachingAchievement);
        } catch (Exception e) {
            log.error("ResearchReportController.getUnitTeachingAchievement error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 获取能力信息
     * @param requestId
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/getUnitAbility", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse getUnitAbility(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse=new BaseResponse(requestId);

        try {
            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getContrastType())){
                logger.error("contrastType is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"contrastType is null");
            }
            List<BaseAbilityDto> unitAbility = researchReportOpenService.getUnitAbility(researchReportModel.getContrastType(), researchReportModel.getUnitCode(),
                                researchReportModel.getSemester(), researchReportModel.getDistrictId());
            baseResponse.setResult(unitAbility);
        } catch (Exception e) {
            log.error("ResearchReportController.getUnitTeachingAchievement error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


        /**
         * 教研员单元测试题质量分析
         * @return
         * @throws Exception
         */
    @RequestMapping(value = "/unitQualityofQuestionsReport", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse unitQualityofQuestionsReport(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getPaperCode())){
                logger.error("paperCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"paperCode is null");
            }
            List<QuestionsqualityModel> result = researchReportOpenService.unitQualityofQuestionsReport( researchReportModel.getUnitCode(),  researchReportModel.getDistrictId(),  researchReportModel.getSemester(), researchReportModel.getPaperCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.unitQualityofQuestionsReport error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     *  教研员单元全区测情况
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unitAreaHappening", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse unitAreaHappening(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            AreaHappenModel result = researchReportOpenService.unitAreaHappening(researchReportModel.getUnitCode(),researchReportModel.getDistrictId(),researchReportModel.getSemester());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.unitAreaHappening error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     *  教研员单元区域学生成绩分布
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unitResultsDistribution", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse unitResultsDistribution(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || null == researchReportModel.getGradeCode() || researchReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<DistributedModel> result = researchReportOpenService.unitResultsDistribution(researchReportModel.getUnitCode(), researchReportModel.getDistrictId(), researchReportModel.getSemester(), null, researchReportModel.getGradeCode(), null);
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.unitResultsDistribution error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }



    /**
     *  教研员单元全区各班级测试情况
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unitAreaHappeningByclass", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse unitAreaHappeningByclass(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            List<ClassHappenModel> result = researchReportOpenService.unitAreaHappeningByclass(researchReportModel.getUnitCode(), researchReportModel.getDistrictId(), researchReportModel.getSemester());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.unitAreaHappeningByclass error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }



    /**
     *  全区各年级总体情进度
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/teachingProgressByArea", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse teachingProgressByArea(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || CollectionUtils.isEmpty(researchReportModel.getUnitModels())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || null == researchReportModel.getGradeCode() || researchReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<TeachingProgressModel> result = researchReportOpenService.teachingProgressByArea(researchReportModel.getGradeCode(),researchReportModel.getUnitModels(), researchReportModel.getDistrictId(), researchReportModel.getSemester());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.teachingProgressByArea error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     * 全区各学校年级总体情进度
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/teachingProgressBySchool", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse teachingProgressBySchool(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || CollectionUtils.isEmpty(researchReportModel.getUnitModels())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || null == researchReportModel.getGradeCode() || researchReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<SchoolProgressModel> result = researchReportOpenService.teachingProgressBySchool(researchReportModel.getGradeCode(),researchReportModel.getUnitModels(), researchReportModel.getDistrictId(), researchReportModel.getSemester());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.teachingProgressBySchool error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     * 全区各学校教师总体情进度
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/teachingProgressByTeacher", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse teachingProgressByTeacher(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || CollectionUtils.isEmpty(researchReportModel.getUnitModels())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || null == researchReportModel.getGradeCode() || researchReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<SchoolProgressModel> result = researchReportOpenService.teachingProgressByTeacher(researchReportModel.getGradeCode(),researchReportModel.getUnitModels(), researchReportModel.getDistrictId(), researchReportModel.getSemester());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.teachingProgressByTeacher error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     * 教研员单元全区各学校测试情况
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unitAreaHappeningBySchool", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse unitAreaHappeningBySchool(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            List<SchoolHappenModel>  result = researchReportOpenService.unitAreaHappeningBySchool(researchReportModel.getUnitCode(), researchReportModel.getDistrictId(), researchReportModel.getSemester());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.unitAreaHappeningBySchool error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     * 全区各学校班级总体情进度
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/teachingProgressByClass", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse teachingProgressByClass(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || CollectionUtils.isEmpty(researchReportModel.getUnitModels())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || null == researchReportModel.getGradeCode() || researchReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<SchoolProgressModel> result = researchReportOpenService.teachingProgressByClass(researchReportModel.getGradeCode(),researchReportModel.getUnitModels(), researchReportModel.getDistrictId(), researchReportModel.getSemester());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.teachingProgressByClass error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }



    /**
     *  教研员单元测区域学校成绩分布
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unitResultsDistributionBySchool", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse unitResultsDistributionBySchool(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || null == researchReportModel.getGradeCode() || researchReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<ResultsModel> result = researchReportOpenService.unitResultsDistributionBySchool(researchReportModel.getUnitCode(), researchReportModel.getDistrictId(), researchReportModel.getSemester(),researchReportModel.getGradeCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.unitResultsDistributionBySchool error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }


    /**
     * 教研员单元测区域学校班级成绩分布
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unitResultsDistributionByClass", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public BaseResponse unitResultsDistributionByClass(@RequestParam("requestId") String requestId, @RequestBody String requestBody){

        BaseResponse baseResponse=new BaseResponse(requestId);

        try {

            ResearchReportModel researchReportModel = JSONObject.parseObject(requestBody,ResearchReportModel.class);

            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getUnitCode())){
                logger.error("unitCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"unitCode is null");
            }
            if(null == researchReportModel || null == researchReportModel.getDistrictId() || researchReportModel.getDistrictId() <= 0){
                logger.error("districtId is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"districtId is null");
            }
            if(null == researchReportModel || StringUtils.isBlank(researchReportModel.getSemester())){
                logger.error("semester is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"semester is null");
            }
            if(null == researchReportModel || null == researchReportModel.getGradeCode() || researchReportModel.getGradeCode() <= 0){
                logger.error("gradeCode is null," + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"gradeCode is null");
            }
            List<ResultsModel> result = researchReportOpenService.unitResultsDistributionByClass(researchReportModel.getUnitCode(), researchReportModel.getDistrictId(), researchReportModel.getSemester(),researchReportModel.getGradeCode());
            baseResponse.setResult(result);
        }catch (Exception e){
            log.error("ResearchReportController.unitResultsDistributionByClass error ",e);
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

}
