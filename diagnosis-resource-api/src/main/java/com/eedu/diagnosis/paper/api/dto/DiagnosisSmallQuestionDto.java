package com.eedu.diagnosis.paper.api.dto;

import java.io.Serializable;
import java.util.Date;

/** 
 * 试题小题
 * 
 **/
public class DiagnosisSmallQuestionDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键code **/
    private String code;
    /** 资源库code **/
    private String repositoryQuestionCode;
    /** 试题答案 **/
    private String questionAnswer;
    /** 试题分数 **/
    private String questionScore;
    /** 试题选项 **/
    private String quesionOption;
    /** 试题难度 **/
    private Integer difficultStar;
    /** 语音解析 **/
    private String voiceAnalyze;
    /** 试题类型 **/
    private Integer questionType;
    /** 客观/主观题 **/
    private Integer type;
    /** 学段 **/
    private Integer stageCode;
    /** 学年 **/
    private String gradeCode;
    /** 学科 **/
    private String subjectCode;
    /** 教材版本 **/
    private String bookVersion;
    /**  **/
    private Date createTime;
    /**  **/
    private Date updateTime;
    /** 所属复合题code**/
    private String questionCode;
    /** 复合题小题序号 **/
    private String sort;
    /** 题干 **/
    private String questionStem;
    /** 试题解析 **/
    private String questionAnalyze;
    /** 知识点 **/
    private String questionKnowledge;
    /** 产生式 **/
    private String questionProduction;
    
    public String getQuestionStem() {
        return questionStem;
    }

    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }

    public String getQuestionAnalyze() {
        return questionAnalyze;
    }

    public void setQuestionAnalyze(String questionAnalyze) {
        this.questionAnalyze = questionAnalyze;
    }

    public String getQuestionKnowledge() {
        return questionKnowledge;
    }

    public void setQuestionKnowledge(String questionKnowledge) {
        this.questionKnowledge = questionKnowledge;
    }

    public String getQuestionProduction() {
        return questionProduction;
    }

    public void setQuestionProduction(String questionProduction) {
        this.questionProduction = questionProduction;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRepositoryQuestionCode() {
        return repositoryQuestionCode;
    }

    public void setRepositoryQuestionCode(String repositoryQuestionCode) {
        this.repositoryQuestionCode = repositoryQuestionCode;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    public String getQuesionOption() {
        return quesionOption;
    }

    public void setQuesionOption(String quesionOption) {
        this.quesionOption = quesionOption;
    }

    public Integer getDifficultStar() {
        return difficultStar;
    }

    public void setDifficultStar(Integer difficultStar) {
        this.difficultStar = difficultStar;
    }

    public String getVoiceAnalyze() {
        return voiceAnalyze;
    }

    public void setVoiceAnalyze(String voiceAnalyze) {
        this.voiceAnalyze = voiceAnalyze;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStageCode() {
        return stageCode;
    }

    public void setStageCode(Integer stageCode) {
        this.stageCode = stageCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(String bookVersion) {
        this.bookVersion = bookVersion;
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

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}