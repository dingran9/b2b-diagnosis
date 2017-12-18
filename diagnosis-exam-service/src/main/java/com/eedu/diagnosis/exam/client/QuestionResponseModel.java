package com.eedu.diagnosis.exam.client;

import java.util.List;


public class QuestionResponseModel {
	List<BigQuestionModel> bigQuestion;

	public List<BigQuestionModel> getBigQuestion() {
		return bigQuestion;
	}

	public void setBigQuestion(List<BigQuestionModel> bigQuestion) {
		this.bigQuestion = bigQuestion;
	}
}
