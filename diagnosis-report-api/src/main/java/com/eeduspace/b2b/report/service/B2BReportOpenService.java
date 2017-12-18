package com.eeduspace.b2b.report.service;

import com.eeduspace.b2b.report.enumerate.ReportCategoryEnum;
import com.eeduspace.b2b.report.exception.ReportException;
import com.eeduspace.b2b.report.model.ReleaseExamDto;
import com.eeduspace.b2b.report.model.StudentAnswerResultDto;
import com.eeduspace.b2b.report.model.StudentReportDto;

import javax.validation.Valid;
import javax.validation.ValidationException;

/**
 * <p>描述  报告对外接口</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 15:10
**/
public interface B2BReportOpenService {
    /**
     * 获取学生报告
     * @param makePaperRecord  学生答卷记录code
     * @return  StudentReportDto 学生报告
     * @throws Exception
     */
    public StudentReportDto getStudentReport(String makePaperRecord) throws Exception;

    /**
     * 生成报告
     * @param studentAnswerResultDto
     * @return StudentReportDto 学生报告
     * @throws Exception
     */
    public StudentReportDto generateStudentReport(@Valid StudentAnswerResultDto studentAnswerResultDto) throws ReportException,ValidationException;

    /**
     * 生成教师报告或校长报告
     * @param releaseExamRecordCode 考试发布记录code
     * @param reportCategoryEnum 报告类别枚举
     */
    public void  generateTeacherOrPrincipalReport(String releaseExamRecordCode, ReportCategoryEnum reportCategoryEnum) throws Exception;


    /**
     * 获取教师报告
     * @param releaseExamRecordCode 发布考试记录code
     * @throws Exception
     */
    public void getTeacherReport(String releaseExamRecordCode) throws Exception;

    /**
     * 获取校长报告
     * @param releaseExamRecordCode 发布考试记录code
     * @throws Exception
     */
    public void getPrincipalReport(String releaseExamRecordCode) throws Exception;

    /**
     * 发布考试记录
     * @param releaseExamDto 发布考试记录实体
     * @return
     * @throws Exception
     */
    public Boolean releaseExam(ReleaseExamDto releaseExamDto) throws Exception;
}
