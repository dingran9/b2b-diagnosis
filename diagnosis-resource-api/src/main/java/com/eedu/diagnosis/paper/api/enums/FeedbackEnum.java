package com.eedu.diagnosis.paper.api.enums;

/**
 * zz
 */
public enum FeedbackEnum {
	
	/**
	 * 用户类型
	 */
	USER_TYPE_STUDENT(1,"学生"),USER_TYPE_TEACHER(2,"教师"),USER_TYPE_PRINCIPAL(3,"校长"),
	DIAGNOSIS_TYPE_STUDENT(1,"诊断"),PRACTICE_TYPE_STUDENT(2,"练一练");

	private Integer type;

	private String typeName;

	FeedbackEnum(Integer type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}

	public String getValueByType(Integer type){

		return null;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
