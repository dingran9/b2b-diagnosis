package com.eedu.diagnosis.manager.model.response;

import com.eedu.diagnosis.manager.model.BaseModel;

public class BaseBookVersionVo extends BaseModel{

	private String subject;
	private String grade;
	private String ctb_code;//学年
	private String book_type;//学科
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getCtb_code() {
		return ctb_code;
	}
	public void setCtb_code(String ctb_code) {
		this.ctb_code = ctb_code;
	}
	public String getBook_type() {
		return book_type;
	}
	public void setBook_type(String book_type) {
		this.book_type = book_type;
	}
	

	
	
	

}
