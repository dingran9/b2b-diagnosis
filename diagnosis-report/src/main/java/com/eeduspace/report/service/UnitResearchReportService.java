package com.eeduspace.report.service;

import com.eeduspace.report.model.ReleaseViewData;
import com.eeduspace.report.model.ResultsDistributionModel;
import com.eeduspace.report.po.ExamPo;
import com.eeduspace.report.po.StudentSubjectScorePo;
import com.eeduspace.report.po.UserAnswerResultPo;

import java.util.List;

/**
 * Created by liuhongfei on 2017/9/28.
 */
public interface UnitResearchReportService {

    public List<ReleaseViewData> getReleaseViewData(String unitCode, Integer districtId, String semester) throws Exception;

    public List<ExamPo> getexam(List<Integer> codes) throws Exception;

    public List<UserAnswerResultPo> getAnswerResult(List<Integer> codes) throws Exception;

    public List<StudentSubjectScorePo> getstudentTotalScore(List<Integer> codes) throws Exception;

    public List<ResultsDistributionModel> getstudentTotalScoreByGroup(List<Integer> codes, Integer school_code,Integer grade_code, Integer class_code,Integer subjectCode) throws Exception;

    /**
     * 获取多单元视图数据
     *
     * @param unitCodes 多单元code
     * @return
     * @throws Exception
     */
    public List<ReleaseViewData> getReleaseViewDataByCodes(List<String> unitCodes,Integer districtId,String semester) throws Exception;
}
