package com.eedu.diagnosis.paper.persist.po;

import java.util.Date;
import java.util.List;


/** 
 * 全科诊断卷
 * 
 **/
public class DiagnosisComplexPaper {
	
	/** 主键code **/
    private String code;
    /** 全科诊断卷名称**/
    private String diagnosisPaperName;
    /** 封面 **/
    private String coverUrl;
    /** 所属学校 **/
    private String schoolCode;
    /** 学校名称 **/
    private String schoolName;
    /**  **/
    private Date createTime;
    /**  **/
    private Date updateTime;
    /** 创建人 **/
    private String operator;
    /** 学段 **/
    private Integer stageCode;
    /** 学年 **/
    private Integer gradeCode;
    /**上下册标识 0上册  1下册*/
    private Integer volumeType;
    /** 全科诊断卷难度 **/
    private Integer difficultStar;
    /** 全科诊断来源 **/
    private String platform;
    /** 试卷类型 0单元测试 1期中 2期末 3模拟考 4会考 **/
    private String paperType;
    /** 是否删除 **/
    private Integer isDel;
    /** 是否发布 **/
    private Integer  isRelease;

    private String subjectCode;

    public Integer getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Integer isRelease) {
        this.isRelease = isRelease;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(Integer volumeType) {
        this.volumeType = volumeType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiagnosisPaperName() {
        return diagnosisPaperName;
    }

    public void setDiagnosisPaperName(String diagnosisPaperName) {
        this.diagnosisPaperName = diagnosisPaperName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getStageCode() {
        return stageCode;
    }

    public void setStageCode(Integer stageCode) {
        this.stageCode = stageCode;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getDifficultStar() {
        return difficultStar;
    }

    public void setDifficultStar(Integer difficultStar) {
        this.difficultStar = difficultStar;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}
    
}