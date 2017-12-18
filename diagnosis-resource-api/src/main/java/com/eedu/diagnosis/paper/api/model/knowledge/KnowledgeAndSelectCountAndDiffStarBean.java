package com.eedu.diagnosis.paper.api.model.knowledge;

/**
 * @aa 封装知识点code、试题难易度、要获取的试题个数Bean
 * @author jane
 *
 */
public class KnowledgeAndSelectCountAndDiffStarBean {
	private String knowledgeCode;//知识点code
	private Integer difficultStar;//试题难易度
	private Integer selectCount;//指定要获取的试题个数
	
	public String getKnowledgeCode() {
		return knowledgeCode;
	}
	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}
	public Integer getDifficultStar() {
		return difficultStar;
	}
	public void setDifficultStar(Integer difficultStar) {
		this.difficultStar = difficultStar;
	}
	public Integer getSelectCount() {
		return selectCount;
	}
	public void setSelectCount(Integer selectCount) {
		this.selectCount = selectCount;
	}
	

}
