package com.eeduspace.test.model;

public class ProductionModel{
	/**
	 * 产生式名称
	 */
	private String productionName;
	/**
	 * 0 错  1对
	 */
	private Integer isTrue=1;

	/**
	 * 产生式code
	 */
	private String productionCode;

	/**
     *  知识点名称
     */
    private String knowledgeName;
	/**
     * 知识点code
     */
    private String knowledgeCode;
	public Integer getIsTrue() {
		return isTrue;
	}

	public void setIsTrue(Integer isTrue) {
		this.isTrue = isTrue;
	}
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

    public String getProductionName() {
		return productionName;
	}

	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}

	public String getProductionCode() {
		return productionCode;
	}

	public void setProductionCode(String productionCode) {
		this.productionCode = productionCode;
	}

}	
