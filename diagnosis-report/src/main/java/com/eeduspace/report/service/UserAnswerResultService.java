package com.eeduspace.report.service;

import com.eeduspace.b2b.report.model.report.WrongQuestionRankModel;
import com.eeduspace.report.model.StudentAnswerResultModel;
import com.eeduspace.report.po.UserAnswerResultPo;

import java.util.List;

/**
 * <p>描述  用户答题结果信息</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/17 17:11
 * @param    
 * @return   
**/
public interface UserAnswerResultService {
    public UserAnswerResultPo save(UserAnswerResultPo userAnswerResultPo);

    public List<UserAnswerResultPo> batchSave(List<UserAnswerResultPo> userAnswerResultPoList);

    /**
     *获取用户答题结果信息
     * @param releaseExamCode 发布考试记录code
     * @return
     */
    public List<StudentAnswerResultModel> findByReleaseExamCode(String releaseExamCode) throws Exception;

    /**
     * 获取班级错题排行榜
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode 教师code  为null时 统计所有班级
     * @return
     * @throws Exception
     */
    public List<WrongQuestionRankModel> getWrongQuestionRank(String releaseExamCode, String teacherCode, String subjectCode) throws Exception;
}
