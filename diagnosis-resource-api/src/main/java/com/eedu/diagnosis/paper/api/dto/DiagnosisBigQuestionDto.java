package com.eedu.diagnosis.paper.api.dto;

import java.io.Serializable;
import java.util.List;

/** 
 * 试题大题
 * 
 **/
public class DiagnosisBigQuestionDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键code **/
    private String questionCode;
    /** 大题名称 **/
    private String itemContent;
    /** 题型分类 **/
    private Integer itemType;
    /** 精细分类 **/
    private String logicType;
    /** 大题分值 **/
    private Integer score;
    /** 小题集合 **/
    private List<DiagnosisSmallQuestionDto> diagnosisSmallQuestionDtosList;

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getLogicType() {
        return logicType;
    }

    public void setLogicType(String logicType) {
        this.logicType = logicType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

	public List<DiagnosisSmallQuestionDto> getDiagnosisSmallQuestionDtosList() {
		return diagnosisSmallQuestionDtosList;
	}

	public void setDiagnosisSmallQuestionDtosList(
			List<DiagnosisSmallQuestionDto> diagnosisSmallQuestionDtosList) {
		this.diagnosisSmallQuestionDtosList = diagnosisSmallQuestionDtosList;
	}
    
}