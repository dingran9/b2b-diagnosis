package com.eedu.diagnosis.exam.persist.po;

import java.util.Date;
import java.util.UUID;

public class DiagnosisMarkQuestionRecordPo {
    private String code = UUID.randomUUID().toString().replace("-", "").toUpperCase();
    /**
     * 诊断记录code
     */
    private String diagnosisRecordCode;
    /**
     * 答题记录code
     */
    private String answerRecordCode;
    /**
     * 判题结果
     */
    private Integer markResult;
    /**
     * 本题得分
     */
    private Double score;
    /**
     * 基础产生式
     */
    private String baseProduction;
    /**
     * 产生式与知识点
     */
    private String productionKnowledge;
    /**
     * 卷面分
     */
    private Double surfaceScore;
    /**
     * 教师名称
     */
    private String teacherName;
    /**
     * 教师code
     */
    private String teacherCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiagnosisRecordCode() {
        return diagnosisRecordCode;
    }

    public void setDiagnosisRecordCode(String diagnosisRecordCode) {
        this.diagnosisRecordCode = diagnosisRecordCode;
    }

    public String getAnswerRecordCode() {
        return answerRecordCode;
    }

    public void setAnswerRecordCode(String answerRecordCode) {
        this.answerRecordCode = answerRecordCode;
    }

    public Integer getMarkResult() {
        return markResult;
    }

    public void setMarkResult(Integer markResult) {
        this.markResult = markResult;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getBaseProduction() {
        return baseProduction;
    }

    public void setBaseProduction(String baseProduction) {
        this.baseProduction = baseProduction;
    }

    public String getProductionKnowledge() {
        return productionKnowledge;
    }

    public void setProductionKnowledge(String productionKnowledge) {
        this.productionKnowledge = productionKnowledge;
    }

    public Double getSurfaceScore() {
        return surfaceScore;
    }

    public void setSurfaceScore(Double surfaceScore) {
        this.surfaceScore = surfaceScore;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
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
}