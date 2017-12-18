package com.eeduspace.test.model;

import java.util.ArrayList;
import java.util.List;

/**
  *	@Author Dong_Qingyan 
  *	@Date 2016年10月26日 下午1:07:57
  *	@description 试卷每一种类型试题的集合
  */
public class QuestionSet {
	private String type;
	private List<BigQuestionSystem> typeList = new ArrayList<>(0);
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<BigQuestionSystem> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<BigQuestionSystem> typeList) {
		this.typeList = typeList;
	}
	
}
