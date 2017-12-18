package com.eedu.diagnosis.paper.persist.model.knowledge;
import java.util.List;


public class KQuesOutputBean {
	private String knowledgeCode;//知识点code
	private List<ParamQuestionEntity> questionList;//封装试题数据的集合
	
	public String getKnowledgeCode() {
		return knowledgeCode;
	}
	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}
	public List<ParamQuestionEntity> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<ParamQuestionEntity> questionList) {
		this.questionList = questionList;
	}


}
