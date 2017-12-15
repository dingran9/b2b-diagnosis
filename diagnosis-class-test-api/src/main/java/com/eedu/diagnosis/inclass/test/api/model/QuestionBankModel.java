package com.eedu.diagnosis.inclass.test.api.model;

import java.io.Serializable;
import java.util.Date;

public class QuestionBankModel implements Serializable{

    private String questionBookName;

    private Integer num;

    public String getQuestionBookName() {
        return questionBookName;
    }

    public void setQuestionBookName(String questionBookName) {
        this.questionBookName = questionBookName;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}