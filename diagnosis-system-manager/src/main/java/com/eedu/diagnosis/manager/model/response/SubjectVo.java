package com.eedu.diagnosis.manager.model.response;

import com.eedu.diagnosis.manager.model.BaseModel;

public class SubjectVo extends BaseModel{

	private String subject_name;
	private String subject_code;
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public String getSubject_code() {
		return subject_code;
	}
	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}

	

}
