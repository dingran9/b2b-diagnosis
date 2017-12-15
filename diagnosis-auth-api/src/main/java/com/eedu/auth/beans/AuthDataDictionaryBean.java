package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/31
 * Time: 15:46
 * Describe: 基础数据
 */
public class AuthDataDictionaryBean implements Serializable{

	private Integer dataId;

	private String dataIden;

	private String dataName;

	private Integer dataType;

	private Integer dataParent;

	private String dataDesc;

	private Date createDate;

	private Date updateDate;

	public Integer getDataId() {
		return dataId;
	}

	public String getDataIden() {
		return dataIden;
	}

	public void setDataIden(String dataIden) {
		this.dataIden = dataIden;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getDataParent() {
		return dataParent;
	}

	public void setDataParent(Integer dataParent) {
		this.dataParent = dataParent;
	}

	public String getDataDesc() {
		return dataDesc;
	}

	public void setDataDesc(String dataDesc) {
		this.dataDesc = dataDesc;
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
}
