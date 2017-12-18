package com.eedu.diagnosis.common.model.paperEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 接收资源库信息时所用model
 * date: 2016年9月1日 上午8:50:32
 */
public class BigQuestionSystem {
	

	private String id;
	private List<SmallQuestionSystem> list = new ArrayList<SmallQuestionSystem>();
    private String itemContent;
	private String itemType;//题型的大分类 123456个
	private String enlargeType;//细分类
    private Double itemScore;
    private Integer sort;
    /**
     * 逻辑分类
     */
    private String logicType;
    
    
    public String getEnlargeType() {
		return enlargeType;
	}
	public void setEnlargeType(String enlargeType) {
		this.enlargeType = enlargeType;
	}
	public Double getItemScore() {
    	return itemScore;
    }
    public void setItemScore(Double itemScore) {
    	this.itemScore = itemScore;
    }
    public List<SmallQuestionSystem> getList() {
    	return list;
    }
    public void setList(List<SmallQuestionSystem> list) {
    	this.list = list;
    }
    public String getItemContent() {
        return itemContent;
    }
    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }
    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogicType() {
		return logicType;
	}
	public void setLogicType(String logicType) {
		this.logicType = logicType;
	}
	
}