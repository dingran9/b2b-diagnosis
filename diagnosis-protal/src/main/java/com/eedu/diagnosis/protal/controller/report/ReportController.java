package com.eedu.diagnosis.protal.controller.report;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.protal.model.report.StudentReportVO;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eeduspace.b2b.report.model.StudentReportDto;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.service.B2BReportOpenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 报告controller
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-22 11:50
 **/
@RestController
@RequestMapping("/rest/report")
public class ReportController {
    @Autowired
    private B2BReportOpenService b2BReportOpenService;
    /**
     * 获取学生报告
     * @param requestId  请求标识
     * @param makePaperRecordCode 学生做卷记录code
     * @return
     */
    @RequestMapping(value = "/getStudentReport/{makePaperRecordCode}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public BaseResponse initClasswork(@RequestParam("requestId") String requestId, @PathVariable("makePaperRecordCode") String makePaperRecordCode) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        if(StringUtils.isEmpty(makePaperRecordCode)){
            return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "makePaperRecordCode");
        }
        try {
            StudentReportDto studentReport = b2BReportOpenService.getStudentReport(makePaperRecordCode);
            StudentReportVO studentReportVO=new StudentReportVO();
            BeanUtils.copyProperties(studentReport,studentReportVO);
            studentReportVO.setHistoricalScore(JSONObject.parseArray(studentReport.getHistoricalScore(), HistoryScoreModel.class));
            studentReportVO.setWrongQuestionSn(JSONObject.parseArray(studentReport.getWrongQuestionSn(),String.class));
            studentReportVO.setKnowledgeGrasp(JSONObject.parseArray(studentReport.getKnowledgeGrasp(),Columnar.class));
            studentReportVO.setModuleScoreIncrease(JSONObject.parseArray(studentReport.getModuleScoreIncrease(), Colorcolumnar.class));
            studentReportVO.setSubjectAbility(JSONObject.parseArray(studentReport.getSubjectAbility(), Radar.class));
            studentReportVO.setWrongKnowledgeRank( JSONObject.parseArray(studentReport.getWrongKnowledgeRank(), WrongKnowledgeRankModel.class));
            baseResponse.setResult(studentReportVO);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.setResponse(new BaseResponse(requestId),ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

}
