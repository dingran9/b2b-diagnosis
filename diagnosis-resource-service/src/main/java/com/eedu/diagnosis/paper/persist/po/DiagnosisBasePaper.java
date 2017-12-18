package com.eedu.diagnosis.paper.persist.po;

import java.util.Date;
/** 
 * 自组试卷（基础卷）
 * 
 **/
public class DiagnosisBasePaper{
	
	/** 主键code **/
    private String code;
    /** 试卷名称 **/
    private String paperName;
    /** 创建时间 **/
    private Date createTime;
    /** 修改时间 **/
    private Date updateTime;
    /** 学段 **/
    private Integer stageCode;
    /** 学年 **/
    private Integer gradeCode;
    /** 学科 **/
    private Integer sbujectCode;
    /** 所属学校 **/
    private String schoolCode;
    /** 学校名称 **/
    private String schoolName;
    /** 创建人 **/
    private String operator;
    /** 试卷总分 **/
    private Integer totalScore;
    /** 试卷难度 **/
    private Integer difficultStar;
    /** 考试时长 **/
    private Integer totalTime;
    /** 自组卷来源 **/
    private String platform;
    /** 是否删除 **/
    private Integer isDel;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
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

    public Integer getSbujectCode() {
        return sbujectCode;
    }

    public void setSbujectCode(Integer sbujectCode) {
        this.sbujectCode = sbujectCode;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getDifficultStar() {
        return difficultStar;
    }

    public void setDifficultStar(Integer difficultStar) {
        this.difficultStar = difficultStar;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
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
}