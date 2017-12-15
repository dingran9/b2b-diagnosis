package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/15
 * Time: 18:34
 * Describe:
 */
public class AuthResourceBean implements Serializable{

	private Integer resourceId;  //资源编号

	private String resourceName;  //资源名称

	private String resourceUrl;  //访问url

	private String resourceDesc;  //资源描述

	private Integer resourceParentId;  //资源隶属父标签Id

	private Integer resourceSort;  //资源排序

	private Integer resourceType;  //资源类型(0:菜单,1:按钮)

	private Integer resourceProductId;  //产品id

	private Date createDate;  //创建时间

	private Date updateDate;  //修改时间

	private int selected;

	private String resourceIcon;//图标类型

	private List<AuthResourceBean> resourceBeen = new ArrayList<AuthResourceBean>();  //子标签

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public Integer getResourceParentId() {
		return resourceParentId;
	}

	public void setResourceParentId(Integer resourceParentId) {
		this.resourceParentId = resourceParentId;
	}

	public Integer getResourceSort() {
		return resourceSort;
	}

	public void setResourceSort(Integer resourceSort) {
		this.resourceSort = resourceSort;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getResourceProductId() {
		return resourceProductId;
	}

	public void setResourceProductId(Integer resourceProductId) {
		this.resourceProductId = resourceProductId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<AuthResourceBean> getResourceBeen() {
		return resourceBeen;
	}

	public void setResourceBeen(List<AuthResourceBean> resourceBeen) {
		this.resourceBeen = resourceBeen;
	}

	public void setSelected(int selected) { this.selected = selected; }

	public int getSelected(){ return selected; }

	public void setResourceIcon(String resourceIcon){this.resourceIcon = resourceIcon; }

	public String getResourceIcon(){return resourceIcon; }
}
