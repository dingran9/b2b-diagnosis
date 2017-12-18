package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/9/29.
 */
public class QuestionsqualityModel implements Serializable{

    //试题编号
    private Integer questionSn;
    //试题CODE
    private String questionCode;
    //难度
    private Double difficulty;
    //区分度
    private Double distinction;

    public Integer getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(Integer questionSn) {
        this.questionSn = questionSn;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public Double getDistinction() {
        return distinction;
    }

    public void setDistinction(Double distinction) {
        this.distinction = distinction;
    }
}
