package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/31
 * Time: 16:07
 * Describe: 组织和基础数据绑定关系
 */
public class AuthGroupDictionaryBindBean implements Serializable{

	private Integer id;

	private Integer groupId;

	private Integer groupType;

	private Integer dataId;

	private Integer dataType;

	private Date createDate;

	private Date updateDate;

	private List<AuthGroupBean> authGroupBeans;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
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

	public void setAuthGroupBeans(List<AuthGroupBean> authGroupBeans) {this.authGroupBeans = authGroupBeans; }

	public List<AuthGroupBean> getAuthGroupBeans() {return authGroupBeans; }
}
