package com.eedu.diagnosis.paper.persist.model.knowledge;

import java.util.List;

public class MessageBeanForQuestion {
	private String status;
	private String message;
	private List<KQuesOutputBean> questionObject;
	
	
	public MessageBeanForQuestion(){}


	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public MessageBeanForQuestion(String status,String message){
		this.setStatus(status);
		this.setMessage(message);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}




	public List<KQuesOutputBean> getQuestionObject() {
		return questionObject;
	}




	public void setQuestionObject(List<KQuesOutputBean> questionObject) {
		this.questionObject = questionObject;
	}

	
	


}
