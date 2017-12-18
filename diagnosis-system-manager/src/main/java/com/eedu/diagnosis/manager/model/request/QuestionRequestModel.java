package com.eedu.diagnosis.manager.model.request;


/**
 * 
 * 试题详情
 *
 */
public class QuestionRequestModel {

	// 试题id
	private String  id;
	 
	//试题所属学科标记 1:语文学科 2:数学学科 3:英语学科
	private String subjectFlag;

	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubjectFlag() {
		return subjectFlag;
	}

	public void setSubjectFlag(String subjectFlag) {
		this.subjectFlag = subjectFlag;
	}

	public QuestionRequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
