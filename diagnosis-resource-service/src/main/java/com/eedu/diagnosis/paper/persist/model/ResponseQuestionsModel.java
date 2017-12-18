package com.eedu.diagnosis.paper.persist.model;

import java.util.List;

/**
 * Created by dqy on 2017/8/25.
 */
public class ResponseQuestionsModel {
    private String message;
    private String status;
    private List<QuestionSet> questionObject;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<QuestionSet> getQuestionObject() {
        return questionObject;
    }

    public void setQuestionObject(List<QuestionSet> questionObject) {
        this.questionObject = questionObject;
    }
}
