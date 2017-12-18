package com.eedu.diagnosis.exam.api.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AnswerSheetQuestionDto implements Serializable {
    /**
     * 诊断试题code
     */
    private String questionCode;
    /**
     * 学生答题结果
     */
    private String studentAnswer;
    /**
     * 试题类型
     */
    private String logicType;
    /**
     * 试题序号
     */
    private String questionSn;
    /**
     * 正确答案
     */
    private String rightAnswer;
    /**
     * 是否答对 0 否 1是
     */
    private Integer isRight;
    /**
     * 是否是复合题 0 否 1 是
     */
    private Integer isComplex;
    /**
     * 是否图片 0否 1是
     */
    private Integer isImg;
    /**
     * 复合题答题code
     */
    private String complexQuestionCode;

    /**
     * 试题分数
     */
    private String questionScore;
    private String itemContent;


    private String productionJson;
    private String knowledgeJson;

    public String getProductionJson() {
        return productionJson;
    }

    public void setProductionJson(String productionJson) {
        this.productionJson = productionJson;
    }

    public String getKnowledgeJson() {
        return knowledgeJson;
    }

    public void setKnowledgeJson(String knowledgeJson) {
        this.knowledgeJson = knowledgeJson;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getLogicType() {
        return logicType;
    }

    public void setLogicType(String logicType) {
        this.logicType = logicType;
    }

    public String getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(String questionSn) {
        this.questionSn = questionSn;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    public Integer getIsComplex() {
        return isComplex;
    }

    public void setIsComplex(Integer isComplex) {
        this.isComplex = isComplex;
    }

    public Integer getIsImg() {
        return isImg;
    }

    public void setIsImg(Integer isImg) {
        this.isImg = isImg;
    }

    public String getComplexQuestionCode() {
        return complexQuestionCode;
    }

    public void setComplexQuestionCode(String complexQuestionCode) {
        this.complexQuestionCode = complexQuestionCode;
    }

    public String getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }
}
