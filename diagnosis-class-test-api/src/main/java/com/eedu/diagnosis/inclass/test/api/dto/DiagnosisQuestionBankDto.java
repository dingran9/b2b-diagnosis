package com.eedu.diagnosis.inclass.test.api.dto;

import com.eedu.diagnosis.inclass.test.api.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

public class DiagnosisQuestionBankDto extends BaseModel implements Serializable {
    private String questionBookCode;

    private Date createTime;

    private Date updateTime;

    private String questionBookName;

    private String coverUrl;

    private String description;

    private Integer questionCount;

    private String teacherCode;

    private String teacherName;

    public String getQuestionBookCode() {
        return questionBookCode;
    }

    public void setQuestionBookCode(String questionBookCode) {
        this.questionBookCode = questionBookCode;
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

    public String getQuestionBookName() {
        return questionBookName;
    }

    public void setQuestionBookName(String questionBookName) {
        this.questionBookName = questionBookName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}