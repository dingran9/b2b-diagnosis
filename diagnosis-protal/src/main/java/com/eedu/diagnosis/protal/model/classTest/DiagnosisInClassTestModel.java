package com.eedu.diagnosis.protal.model.classTest;



import com.eedu.diagnosis.protal.model.BaseModel;

import java.util.Date;
import java.util.List;

public class DiagnosisInClassTestModel extends BaseModel {
    private String inClassTestCode;

    private Date createTime;

    private Date updateTime;

    private String inClassTestName;

    private Integer questionCount;

    private String teacherCode;

    private String teacherName;

    private Integer gradeCode;

    private Integer subjectCode;

    private String classCode;

    private String className;

    private Integer status;

    private Date totalTime;

    private String baseCode;

    private Integer timeSort;//0正1倒

    private Integer numSort;

    private Date testTime;

    private Integer equipmentType;//设备类型 0手机app，1答题器，2智能手表

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    
    public String getInClassTestCode() {
        return inClassTestCode;
    }

    public void setInClassTestCode(String inClassTestCode) {
        this.inClassTestCode = inClassTestCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getInClassTestName() {
        return inClassTestName;
    }

    public void setInClassTestName(String inClassTestName) {
        this.inClassTestName = inClassTestName;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
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

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Date totalTime) {
        this.totalTime = totalTime;
    }


    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public Integer getTimeSort() {
        return timeSort;
    }

    public void setTimeSort(Integer timeSort) {
        this.timeSort = timeSort;
    }

    public Integer getNumSort() {
        return numSort;
    }

    public void setNumSort(Integer numSort) {
        this.numSort = numSort;
    }

    public Integer getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(Integer equipmentType) {
        this.equipmentType = equipmentType;
    }
}