package com.eedu.diagnosis.paper.api.dto;

import java.io.Serializable;

/** 
 * 自组卷与试题（大题）关系表
 * 
 **/
public class DiagnosisPaperQuestionDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键code **/
    private String code;
    /** 试卷code **/
    private String paperCode;
    /** 大题code **/
    private String bigQuestionCode;
    /** 序号 **/
    private String sort;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getBigQuestionCode() {
        return bigQuestionCode;
    }

    public void setBigQuestionCode(String bigQuestionCode) {
        this.bigQuestionCode = bigQuestionCode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}