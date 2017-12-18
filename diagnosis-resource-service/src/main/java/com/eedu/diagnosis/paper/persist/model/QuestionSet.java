package com.eedu.diagnosis.paper.persist.model;

import com.eedu.diagnosis.common.model.paperEntity.SmallQuestionSystem;

import java.util.List;

/**
 * Created by dqy on 2017/8/25.
 */
public class QuestionSet {
    private List<SmallQuestionSystem> questionList;

    public List<SmallQuestionSystem> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<SmallQuestionSystem> questionList) {
        this.questionList = questionList;
    }
}
