package com.eedu.diagnosis.inclass.test.api.dto;

import com.eedu.diagnosis.inclass.test.api.model.BaseModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DiagnosisBaseQuestionDto extends BaseModel implements Serializable {
    private String baseCode;

    private Date createTime;

    private Date updateTime;

    private String baseName;

    private String resourceCode;

    private String source;

    private String coverUrl;

    private String rightAnswer;

    private Integer questionType;

    private String knowledges;

    private Integer subjectCode;

    private BigDecimal questionScore;
    
    private String questionStem;

    private String questionOption;

    private String questionAnalyze;

    private String sout;

    private String questionBookName;

    private String questionBookCode;


    private Integer difficultStar;


    public Integer getDifficultStar() {
        return difficultStar;
    }

    public void setDifficultStar(Integer difficultStar) {
        this.difficultStar = difficultStar;
    }


    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
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

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(String knowledges) {
        this.knowledges = knowledges;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
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

    public String getSout() {
        return sout;
    }

    public void setSout(String sout) {
        this.sout = sout;
    }

    public String getQuestionBookName() {
        return questionBookName;
    }

    public void setQuestionBookName(String questionBookName) {
        this.questionBookName = questionBookName;
    }

    public String getQuestionBookCode() {
        return questionBookCode;
    }

    public void setQuestionBookCode(String questionBookCode) {
        this.questionBookCode = questionBookCode;
    }
}