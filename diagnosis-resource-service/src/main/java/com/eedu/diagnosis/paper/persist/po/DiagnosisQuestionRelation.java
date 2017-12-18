package com.eedu.diagnosis.paper.persist.po;

/** 
 *  大题与小题关系表
 * 
 **/
public class DiagnosisQuestionRelation {
	
	/** 主键code **/
    private String code;
    /** 大题code **/
    private String bigQuestionCode;
    /** 小题code **/
    private String smallQuestionCode;
    /** 序号 **/
    private String sort;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBigQuestionCode() {
        return bigQuestionCode;
    }

    public void setBigQuestionCode(String bigQuestionCode) {
        this.bigQuestionCode = bigQuestionCode;
    }

    public String getSmallQuestionCode() {
        return smallQuestionCode;
    }

    public void setSmallQuestionCode(String smallQuestionCode) {
        this.smallQuestionCode = smallQuestionCode;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}