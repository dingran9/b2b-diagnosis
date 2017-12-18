package com.eeduspace.b2b.report.service;

import com.eeduspace.b2b.report.model.AvgScoreDto;
import com.eeduspace.b2b.report.model.PassingDot;
import com.eeduspace.b2b.report.model.ResultsVarietyDto;
import com.eeduspace.b2b.report.model.TeachingratioDto;
import com.eeduspace.b2b.report.model.report.AllGradeScoreDto;

import java.util.List;
import java.util.Map;

/**
 * <p>描述：全科学习情况统计 接口</p>
 * @Author liuhongfei
 * e-mail:liuhongfei@e-eduspace.com
 * @Date 2017/4/24
 **/
public interface GeneralReportOpenService {



    /**
     * 年级全学科成绩统计
     *
     * @param gradeCode     学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    public AllGradeScoreDto getGradeTotalScoreReport(String gradeCode, String releaseExamCode) throws Exception;


    /**
     * 年级班级分组全学科成绩统计
     *
     * @param gradeCode     学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    public List<AllGradeScoreDto> getGradeTotalScoreByClassReport(String gradeCode, String releaseExamCode) throws Exception;

    /**
     * 全学科班级平均成绩，排名统计
     *
     * @param gradeCode     学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    public AvgScoreDto getSubjectAvgScoreByClass(String gradeCode, String releaseExamCode) throws Exception;

    /**
     * 全年级，各学科优秀，及格人数占比
     *
     * @param gradeCode     学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    public List<PassingDot> getPassingResults(String gradeCode, String releaseExamCode) throws Exception;



    /**
     * 学生总分成绩变化（高二，高三 分 文。理 科）
     * @param gradeCode   学年code
     * @param artType     文理类型 0 无类型  1文  2理
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    public List<ResultsVarietyDto> getChangePerformance(String gradeCode,String artType,String releaseExamCode) throws Exception;


    /**
     * 各学科教学成绩贡献率
     *
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    public TeachingratioDto getSubjectContributionrate(String releaseExamCode) throws  Exception;




    /**
     * 各学科教师教学成绩
     *
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    public Map<String, Map<String,String>> getTeachchanges(String releaseExamCode)throws  Exception;


}
