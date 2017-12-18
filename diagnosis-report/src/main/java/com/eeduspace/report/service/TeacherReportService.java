package com.eeduspace.report.service;

/**
 * <p>描述 教师报告业务</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/18 12:13
 * @param    
 * @return   
**/
public interface TeacherReportService {
    /**
     * 生成教师报告
     * @param releaseExamCode 发布考试记录code
     * @param teacherCode 教师code
     * @param subjectCode 学科code
     */
    public void  generateTeacherReport(String releaseExamCode,String teacherCode,String subjectCode) throws Exception;
}
