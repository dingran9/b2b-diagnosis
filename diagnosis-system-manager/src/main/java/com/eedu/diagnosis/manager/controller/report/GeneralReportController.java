package com.eedu.diagnosis.manager.controller.report;

import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eeduspace.b2b.report.model.AvgScoreDto;
import com.eeduspace.b2b.report.model.PassingDot;
import com.eeduspace.b2b.report.model.ResultsVarietyDto;
import com.eeduspace.b2b.report.model.TeachingratioDto;
import com.eeduspace.b2b.report.model.report.AllGradeScoreDto;
import com.eeduspace.b2b.report.service.GeneralReportOpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhongfei on 2017/5/2.
 */
@RestController
@RequestMapping("/generalReport")
@Slf4j
public class GeneralReportController {


    @Autowired
    private GeneralReportOpenService generalReportOpenService;


    /**
     * 全年级总分成绩分布情况
     * http://localhost:8080/generalReport/getGradeTotalScoreReport/33/943EC83F1EB44BB0AB272BC9C882B509?requestId=2
     * @param gradeCode       学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getGradeTotalScoreReport/{gradeCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getGradeTotalScoreReport(@RequestParam("requestId") String requestId,
                                             @PathVariable("gradeCode") String gradeCode,
                                             @PathVariable("releaseExamCode") String releaseExamCode){

        BaseResponse baseResponse=new BaseResponse(requestId);


        if(StringUtils.isEmpty(gradeCode)){
            log.error("getGradeTotalScoreReport  Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getGradeTotalScoreReport  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }

        try {
            AllGradeScoreDto dto = generalReportOpenService.getGradeTotalScoreReport(gradeCode, releaseExamCode);
            baseResponse.setResult(dto);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

    /**
     * 各班学生成绩分布情况概览
     * http://localhost:8080/generalReport/getGradeTotalScoreByClassReport/33/943EC83F1EB44BB0AB272BC9C882B509?requestId=2
     * @param gradeCode       学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getGradeTotalScoreByClassReport/{gradeCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getGradeTotalScoreByClassReport(@RequestParam("requestId") String requestId,
                                                 @PathVariable("gradeCode") String gradeCode,
                                                 @PathVariable("releaseExamCode") String releaseExamCode){
        BaseResponse baseResponse=new BaseResponse(requestId);


        if(StringUtils.isEmpty(gradeCode)){
            log.error("getGradeTotalScoreByClassReport  Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getGradeTotalScoreByClassReport  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }

        try {
            List<AllGradeScoreDto> dtos = generalReportOpenService.getGradeTotalScoreByClassReport(gradeCode, releaseExamCode);
            baseResponse.setResult(dtos);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

    /**
     * 全学科班级平均成绩，排名统计
     * http://localhost:8080/generalReport/getSubjectAvgScoreByClass/33/943EC83F1EB44BB0AB272BC9C882B509?requestId=2
     * @param gradeCode       学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSubjectAvgScoreByClass/{gradeCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getSubjectAvgScoreByClass(@RequestParam("requestId") String requestId,
                                                 @PathVariable("gradeCode") String gradeCode,
                                                 @PathVariable("releaseExamCode") String releaseExamCode){

        BaseResponse baseResponse=new BaseResponse(requestId);


        if(StringUtils.isEmpty(gradeCode)){
            log.error("getSubjectAvgScoreByClass  Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getSubjectAvgScoreByClass  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }

        try {
            AvgScoreDto dto = generalReportOpenService.getSubjectAvgScoreByClass(gradeCode, releaseExamCode);
            baseResponse.setResult(dto);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

    /**
     * 全年级，各学科优秀，及格人数占比
     * http://localhost:8080/generalReport/getPassingResults/33/943EC83F1EB44BB0AB272BC9C882B509?requestId=2
     * @param gradeCode       学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPassingResults/{gradeCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getPassingResults(@RequestParam("requestId") String requestId,
                                                 @PathVariable("gradeCode") String gradeCode,
                                                 @PathVariable("releaseExamCode") String releaseExamCode){

        BaseResponse baseResponse=new BaseResponse(requestId);


        if(StringUtils.isEmpty(gradeCode)){
            log.error("getPassingResults  Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getPassingResults  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }

        try {
            List<PassingDot> dtos = generalReportOpenService.getPassingResults(gradeCode, releaseExamCode);
            baseResponse.setResult(dtos);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }


        return baseResponse;
    }

    /**
     * 学生总分成绩变化（高二，高三 分 文。理 科）
     * http://localhost:8080/generalReport/getChangePerformance/33/2/943EC83F1EB44BB0AB272BC9C882B509?requestId=2
     * @param gradeCode       学科code
     * @param artType         文理类型
     * @param releaseExamCode 发布考试记录code0 无类型  1文  2理
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getChangePerformance/{gradeCode}/{artType}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getChangePerformance(@RequestParam("requestId") String requestId,
                                                 @PathVariable("gradeCode") String gradeCode,
                                                 @PathVariable("artType") String artType,
                                                 @PathVariable("releaseExamCode") String releaseExamCode){

        BaseResponse baseResponse=new BaseResponse(requestId);


        if(StringUtils.isEmpty(gradeCode)){
            log.error("getChangePerformance  Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(artType)){
            log.error("getChangePerformance  Exception：artType is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "artType");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getChangePerformance  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }

        try {
            List<ResultsVarietyDto> dtos = generalReportOpenService.getChangePerformance(gradeCode,artType, releaseExamCode);
            baseResponse.setResult(dtos);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

    /**
     * 各学科教学成绩贡献率
     * http://localhost:8080/generalReport/getSubjectContributionrate/33/943EC83F1EB44BB0AB272BC9C882B509?requestId=2
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSubjectContributionrate/{gradeCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getSubjectContributionrate(@RequestParam("requestId") String requestId,
                                                 @PathVariable("gradeCode") String gradeCode,
                                                 @PathVariable("releaseExamCode") String releaseExamCode){

        BaseResponse baseResponse=new BaseResponse(requestId);


        if(StringUtils.isEmpty(gradeCode)){
            log.error("getSubjectContributionrate  Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getSubjectContributionrate  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }
        try {
            TeachingratioDto dto = generalReportOpenService.getSubjectContributionrate(releaseExamCode);
            baseResponse.setResult(dto);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }


        return baseResponse;
    }

    /**
     * 各学科教师教学成绩
     * http://localhost:8080/generalReport/getTeachchanges/33/943EC83F1EB44BB0AB272BC9C882B509?requestId=2
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTeachchanges/{gradeCode}/{releaseExamCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse getTeachchanges(@RequestParam("requestId") String requestId,
                                                 @PathVariable("gradeCode") String gradeCode,
                                                 @PathVariable("releaseExamCode") String releaseExamCode){

        BaseResponse baseResponse=new BaseResponse(requestId);


        if(StringUtils.isEmpty(gradeCode)){
            log.error("getTeachchanges  Exception：gradeCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeCode");
        }
        if(StringUtils.isEmpty(releaseExamCode)){
            log.error("getTeachchanges  Exception：releaseExamCode is null."
                    + baseResponse.getRequestId());
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "releaseExamCode");
        }

        try {
            Map<String, Map<String,String>> map = generalReportOpenService.getTeachchanges(releaseExamCode);
            baseResponse.setResult(map);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.SERVICE_ERROR.toString());
        }

        return baseResponse;
    }

}
