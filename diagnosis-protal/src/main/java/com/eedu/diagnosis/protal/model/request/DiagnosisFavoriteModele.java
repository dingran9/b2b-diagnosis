package com.eedu.diagnosis.protal.model.request;

import java.math.BigDecimal;
import java.util.Date;

import com.eedu.diagnosis.protal.model.BaseModel;

/** 
 * 收藏
 * 
 **/
public class DiagnosisFavoriteModele extends BaseModel{

	

	/** 主键code **/
    private String code;
    /**  **/
    private Date createTime;
    /**  **/
    private Date updateTime;
    /** 收藏来源 **/
    private Integer platform;
    /** 试题code **/
    private String questionCode;
    /** 视频/试题类型 **/
    private Integer type;
    /** 用户code **/
    private String userCode;
    /** 学段code **/
    private String stageCode;
    /** 学年code **/
    private String gradeCode;
    /** 学科code **/
    private String subjectCode;
    /** 单元code **/
    private String unitCode;
    /** 知识点code **/
    private String knowledgeCode;
    /** 是否删除 **/
    private Integer isDel;
    
    private String questionSort;

    private BigDecimal questionScore;
    
    private String questionStem;

    private String questionOption;

    private String questionAnalyze;
    
    private String rightAnswer;

    private String diffcultstar;

	/** 复合题的文章code**/
	private String articleCode;

	private String audioAnalyzePath;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
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

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getKnowledgeCode() {
		return knowledgeCode;
	}

	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getQuestionSort() {
		return questionSort;
	}

	public void setQuestionSort(String questionSort) {
		this.questionSort = questionSort;
	}

	public BigDecimal getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(BigDecimal questionScore) {
		this.questionScore = questionScore;
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

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getDiffcultstar() {
		return diffcultstar;
	}

	public void setDiffcultstar(String diffcultstar) {
		this.diffcultstar = diffcultstar;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getAudioAnalyzePath() {
		return audioAnalyzePath;
	}

	public void setAudioAnalyzePath(String audioAnalyzePath) {
		this.audioAnalyzePath = audioAnalyzePath;
	}
}