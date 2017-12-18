package com.eedu.diagnosis.exam.persist.po;


import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
public class DiagnosisStudentAnswerRecordPo {
    private String code = UUID.randomUUID().toString().replace("-", "").toUpperCase();;

    private String diagnosisRecordCode;

    private Integer studentCode;

    private String questionCode;

    private Integer isRight;

    private Date createTime;

    private Date updateTime;

    private String questionSn;

    private String moduleName;

    private String moduleCode;

    private String logicType;

    private String itemContent;

    private BigDecimal questionScore;

    private Integer isComplexQuestion;

    private String complexQuestionCode;

    private Integer isImg;

    private String rightAnswer;

    private String studentAnswer;

    private String baseProductionJson;

    private String knowledgeJson;

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

    public Integer getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(Integer studentCode) {
        this.studentCode = studentCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
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

    public String getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(String questionSn) {
        this.questionSn = questionSn;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getLogicType() {
        return logicType;
    }

    public void setLogicType(String logicType) {
        this.logicType = logicType;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public BigDecimal getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
    }

    public Integer getIsComplexQuestion() {
        return isComplexQuestion;
    }

    public void setIsComplexQuestion(Integer isComplexQuestion) {
        this.isComplexQuestion = isComplexQuestion;
    }

    public String getComplexQuestionCode() {
        return complexQuestionCode;
    }

    public void setComplexQuestionCode(String complexQuestionCode) {
        this.complexQuestionCode = complexQuestionCode;
    }

    public Integer getIsImg() {
        return isImg;
    }

    public void setIsImg(Integer isImg) {
        this.isImg = isImg;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getBaseProductionJson() {
        return baseProductionJson;
    }

    public void setBaseProductionJson(String baseProductionJson) {
        this.baseProductionJson = baseProductionJson;
    }

    public String getKnowledgeJson() {
        return knowledgeJson;
    }

    public void setKnowledgeJson(String knowledgeJson) {
        this.knowledgeJson = knowledgeJson;
    }
}