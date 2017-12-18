package com.eedu.diagnosis.manager.model.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by dqy on 2017/9/20.
 */
public class MarkQuestionModel {

    private String code ;
    /**
     * 诊断记录code
     */
    @NotBlank(message = "diagnosisRecordCode")
    private String diagnosisRecordCode;
    /**
     * 答题记录code
     */
    @NotBlank(message = "answerRecordCode")
    private String answerRecordCode;
    /**
     * 判题结果
     */
    @NotNull(message = "markResult")
    private Integer markResult;
    /**
     * 本题得分
     */
    @NotNull(message = "score")
    private Double score;
    /**
     * 基础产生式
     */
    @NotBlank(message = "baseProduction")
    private String baseProduction;
    /**
     * 产生式与知识点
     */
    @NotBlank(message = "productionKnowledge")
    private String productionKnowledge;
    /**
     * 试题总分
     */
    @NotBlank(message = "questionScore")
    private Double questionScore;
    /**
     * 教师名称
     */
    @NotBlank(message = "teacherName")
    private String teacherName;
    /**
     * 教师code
     */
    @NotBlank(message = "teacherCode")
    private String teacherCode;
    /**
     * 试题code
     */
    @NotBlank(message = "questionCode")
    private String questionCode;
    /**
     * 是否为复合题
     */
    @NotBlank(message = "IsComplex")
    private Integer IsComplex;
    /**
     * 复合题大试题code
     */
    @NotBlank(message = "ComplexQuestionCode")
    private String ComplexQuestionCode;
    @NotBlank(message = "questionSn")
    private String questionSn;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public String getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(String questionSn) {
        this.questionSn = questionSn;
    }

    public Integer getIsComplex() {
        return IsComplex;
    }

    public void setIsComplex(Integer isComplex) {
        IsComplex = isComplex;
    }

    public String getComplexQuestionCode() {
        return ComplexQuestionCode;
    }

    public void setComplexQuestionCode(String complexQuestionCode) {
        ComplexQuestionCode = complexQuestionCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

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

    public Double getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Double questionScore) {
        this.questionScore = questionScore;
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
