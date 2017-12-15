package com.eedu.diagnosis.inclass.test.api.enums;



public enum ConstantEnum {


	CUSTOM(1,"自定义"),YIJIAO(2,"易教"),

	NOT_STARTED(0,"未"),STARTING(1,"已"),HAS_ENDED(2,"结束"),

	RIGHT(1,"对"),WRONG(0,"错"),

	START(0,"开始"),END(1,"结束")

	;


	private Integer type;

	private String typeName;

	ConstantEnum(Integer type, String typeName) {
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


	public static void main(String[] args) {
		System.out.println(ConstantEnum.CUSTOM.getType());;
	}

}