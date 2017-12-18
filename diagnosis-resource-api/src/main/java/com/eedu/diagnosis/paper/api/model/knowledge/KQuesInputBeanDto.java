package com.eedu.diagnosis.paper.api.model.knowledge;

import java.io.Serializable;
import java.util.List;

/**
 * @aa 根据知识、难易度 获取试题
 * @aa 封装获取试题的参数Bean(知识点、难易度、试题个数)
 * @author jane
 *
 */
public class KQuesInputBeanDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String subjectFlag;//学科标记
	List<KnowledgeAndSelectCountAndDiffStarBean> knowAndCountAndDiffStars;//封装 获取试题的参数集合
	
	private String knowledgeCode;//知识点code
	private Integer difficultStar;//试题难易度
	private Integer selectCount;//指定要获取的试题个数
	
	
	public String getSubjectFlag() {
		return subjectFlag;
	}
	public void setSubjectFlag(String subjectFlag) {
		this.subjectFlag = subjectFlag;
	}
	public List<KnowledgeAndSelectCountAndDiffStarBean> getKnowAndCountAndDiffStars() {
		return knowAndCountAndDiffStars;
	}
	public void setKnowAndCountAndDiffStars(
			List<KnowledgeAndSelectCountAndDiffStarBean> knowAndCountAndDiffStars) {
		this.knowAndCountAndDiffStars = knowAndCountAndDiffStars;
	}
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
