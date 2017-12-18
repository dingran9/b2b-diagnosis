package com.eedu.diagnosis.protal.model.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/27.
 */
public class WrongQuestionModel {
    private String code;

    private String studentCode;

    private String questionCode;

    private Integer isComplex;

    private String questionType;

    private String questionSort;

    private String platform;

    private String diagnosisRecordCode;

    private Integer subjectCode;

    private String studentAnswer;

    private String rightAnswer;

    private BigDecimal questionScore;

    private Date createTime;

    private Date updateTime;

    private String questionStem;

    private String questionOption;

    private String audioAnalyzePath;

    private String questionAnalyze;

    private List<QuestionOptionModel> questionOptions;

    private List<QuestionAnalyzeModel> questionAnalyzes;

    private String baseProduction;

    private String complexQuestionCode;

    private String complexQuestionStem;


    private Date lastTime;

    private Long times;

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getIsComplex() {
        return isComplex;
    }

    public void setIsComplex(Integer isComplex) {
        this.isComplex = isComplex;
    }

    public String getComplexQuestionCode() {
        return complexQuestionCode;
    }

    public void setComplexQuestionCode(String complexQuestionCode) {
        this.complexQuestionCode = complexQuestionCode;
    }

    public String getComplexQuestionStem() {
        return complexQuestionStem;
    }

    public void setComplexQuestionStem(String complexQuestionStem) {
        this.complexQuestionStem = complexQuestionStem;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionSort() {
        return questionSort;
    }

    public void setQuestionSort(String questionSort) {
        this.questionSort = questionSort;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDiagnosisRecordCode() {
        return diagnosisRecordCode;
    }

    public void setDiagnosisRecordCode(String diagnosisRecordCode) {
        this.diagnosisRecordCode = diagnosisRecordCode;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public BigDecimal getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
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

    public String getQuestionStem() {
        return questionStem;
    }

    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }

    public String getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(String questionOption) {
        this.questionOption = questionOption;
    }

    public String getQuestionAnalyze() {
        return questionAnalyze;
    }

    public void setQuestionAnalyze(String questionAnalyze) {
        this.questionAnalyze = questionAnalyze;
    }

    public List<QuestionOptionModel> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<QuestionOptionModel> questionOptions) {
        this.questionOptions = questionOptions;
    }

    public List<QuestionAnalyzeModel> getQuestionAnalyzes() {
        return questionAnalyzes;
    }

    public void setQuestionAnalyzes(List<QuestionAnalyzeModel> questionAnalyzes) {
        this.questionAnalyzes = questionAnalyzes;
    }

    public String getBaseProduction() {
        return baseProduction;
    }

    public void setBaseProduction(String baseProduction) {
        this.baseProduction = baseProduction;
    }

    public String getAudioAnalyzePath() {
        return audioAnalyzePath;
    }

    public void setAudioAnalyzePath(String audioAnalyzePath) {
        this.audioAnalyzePath = audioAnalyzePath;
    }
}

