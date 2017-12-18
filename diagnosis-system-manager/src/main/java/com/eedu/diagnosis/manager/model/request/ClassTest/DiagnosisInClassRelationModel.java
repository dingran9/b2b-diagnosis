package com.eedu.diagnosis.manager.model.request.ClassTest;

import com.eedu.diagnosis.manager.model.BaseModel;

import java.util.Date;

public class DiagnosisInClassRelationModel extends BaseModel {
    private String inClassRelationCode;

    private Date createTime;

    private Date updateTime;

    private String inClassTestCode;

    private String baseCode;

    private Integer isEnd;

    private String sout;

    private Integer classCode;


    public String getSout() {
        return sout;
    }

    public void setSout(String sout) {
        this.sout = sout;
    }

    public String getInClassRelationCode() {
        return inClassRelationCode;
    }

    public void setInClassRelationCode(String inClassRelationCode) {
        this.inClassRelationCode = inClassRelationCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getInClassTestCode() {
        return inClassTestCode;
    }

    public void setInClassTestCode(String inClassTestCode) {
        this.inClassTestCode = inClassTestCode;
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}