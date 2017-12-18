package com.eedu.diagnosis.protal.model.request;

import java.util.List;

/**
 * Created by dqy on 2017/3/29.
 */
public class WrongQuestionRequestModel {
    private String questionCode;
    private List<String> questionCodes;

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public List<String> getQuestionCodes() {
        return questionCodes;
    }

    public void setQuestionCodes(List<String> questionCodes) {
        this.questionCodes = questionCodes;
    }
}
