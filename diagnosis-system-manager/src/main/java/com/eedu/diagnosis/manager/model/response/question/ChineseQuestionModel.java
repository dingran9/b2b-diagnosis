package com.eedu.diagnosis.manager.model.response.question;

import java.io.Serializable;
import java.util.List;


public class ChineseQuestionModel implements Serializable {
    
	private List<ChineseQuestion> questions;
	
	

	public List<ChineseQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<ChineseQuestion> questions) {
		this.questions = questions;
	}
	
	
   }