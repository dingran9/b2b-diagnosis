package com.eeduspace.report.service;

import com.eeduspace.report.model.ResultsChangModel;

import java.util.List;

/**
 * <p>描述</p>
 * @Author liuhongfei
 * e-mail:liuhongfei@e-eduspace.com
 * Date: 2017/4/25 9:31
 */
public interface ReportstatisticsService {


    /**
     * 各班学生总分成绩变化情况 (高一，高二要区分文理科  文理类型  0 无类型  1文  2理)
     * @param art_type 文理类型  0 无类型  1文  2理
     * @param releaseExamCode 发布code
     * @return
     * @throws Exception
     */
    public List<ResultsChangModel> getResultsChang(String art_type,String releaseExamCode)throws Exception;


    /**
     * 获取单次考试发布班级总平均分
     * @param releaseExamCode
     * @return
     * @throws Exception
     */
    public List<ResultsChangModel> getAverageClassaverage(String releaseExamCode)throws Exception;



    /**
     * 获取单次考试发布班级科目平均分
     * @param releaseExamCode
     * @return
     * @throws Exception
     */
    public List<ResultsChangModel> getClassSubjectaverage(String releaseExamCode)throws Exception;



    /**
     * 获取发布考试下的学科班级教学成绩
     *  @param type 单次或学期标记  all为查询学期
     * @param releaseExamCode 发布code
     * @return
     * @throws Exception
     */
    public List<ResultsChangModel> getTeachingachievement(String type,String releaseExamCode)throws Exception;




}
