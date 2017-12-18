package com.eeduspace.b2b.report.model;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * <p>描述 基础公共信息</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 16:53
 * @param    
 * @return   
**/
public class BaseCommonModel implements Serializable{
    /**
     * 学校名称
     */
    String schoolName;
    /**
     * 学校code
     */
    String schoolCode;
    /**
     * 班级code
     */
    String classCode;
    /**
     * 班级名称
     */
    String className;
    /**
     * 学科名称
     */
    String subjectName;
    /**
     * 学科code
     */
    String subjectCode;
    /**
     * 学年code
     */
    String gradeCode;
    /**
     * 学年名称
     */
    String gradeName;
    /**
     * 教师code
     */
    @NotBlank(message = "teacherCode is  null")
    String teacherCode;
    /**
     * 教师名
     */
    @NotBlank(message = "teacherName is  null")
    String teacherName;
    /**
     * 学段
     */
    String stageCode;
    String stageName;
    /**
     * 教材版本
     */
    String bookTypeVersionCode;
    String bookTypeVersionName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStageCode() {
        return stageCode;
    }

    public void setStageCode(String stageCode) {
        this.stageCode = stageCode;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getBookTypeVersionCode() {
        return bookTypeVersionCode;
    }

    public void setBookTypeVersionCode(String bookTypeVersionCode) {
        this.bookTypeVersionCode = bookTypeVersionCode;
    }

    public String getBookTypeVersionName() {
        return bookTypeVersionName;
    }

    public void setBookTypeVersionName(String bookTypeVersionName) {
        this.bookTypeVersionName = bookTypeVersionName;
    }
}
