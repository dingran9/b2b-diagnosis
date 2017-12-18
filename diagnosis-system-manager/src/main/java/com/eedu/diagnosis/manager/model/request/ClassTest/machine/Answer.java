package com.eedu.diagnosis.manager.model.request.ClassTest.machine;

public class Answer {

	
	private String keyPadCode;
	private String answer;
	 
	public String getKeyPadCode() {
		return keyPadCode;
	}
	public void setKeyPadCode(String keyPadCode) {
		this.keyPadCode = keyPadCode;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Answer [keyPadCode=" + keyPadCode + ", answer=" + answer + "]";
	}
	
	
}
