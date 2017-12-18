package com.eedu.diagnosis.exam.api.dto;

import java.io.Serializable;

/**
 * @author zhuchaowei
 * 2016年8月24日
 * Description 知识点
 */
public class KnowledgeModelDto implements Serializable{
	/**
	 *  知识点名称
	 */
	private String knowledgeName;
	/**
	 * 知识点code
	 */
	private String knowledgeCode;
	public String getKnowledgeName() {
		return knowledgeName;
	}
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}
	public String getKnowledgeCode() {
		return knowledgeCode;
	}
	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}
	
}
