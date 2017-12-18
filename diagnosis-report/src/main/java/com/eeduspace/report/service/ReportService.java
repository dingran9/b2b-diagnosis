package com.eeduspace.report.service;

import com.eeduspace.report.model.GradeTotalModel;
import com.eeduspace.report.po.ReportPo;

import java.util.List;

/**
 * <p>描述</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 9:31
 */
public interface ReportService {

    /**
     * 根据做卷记录 获取报告
     * @param makePaperRecordCode 做卷记录code
     * @return ReportPo
     */
    ReportPo findByMakePaperRecordCode(String makePaperRecordCode);

    ReportPo save(ReportPo reportPo);
    /**
     * 全年级各学生总分成绩
     * @param releaseExamCode 考试发布code
     * @return GradeTotalModel
     */
    public List<GradeTotalModel> getTotalScoreByGrade(String releaseExamCode)  throws Exception;
    /**
     * 全年级各班级总分成绩
     * @param releaseExamCode 考试发布code
     * @return GradeTotalModel
     */
    public List<GradeTotalModel> getAvgScoreByClass(String releaseExamCode) throws Exception;

    /**
     * 全年级总分优秀  及格 人数
     * @param releaseExamCode 考试发布code
     * @return GradeTotalModel
     */
    public List<GradeTotalModel> getPassingByGrade(String releaseExamCode) throws Exception;

    /**
     * 获取参加考试的学生数
     * @param releaseExamCode 考试发布code
     * @return
     * @throws Exception
     */
    public Integer getTotalPeople(String releaseExamCode) throws Exception;
}


