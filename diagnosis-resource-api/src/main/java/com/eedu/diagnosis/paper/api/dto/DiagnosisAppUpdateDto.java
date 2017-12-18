package com.eedu.diagnosis.paper.api.dto;

import java.io.Serializable;
import java.util.Date;
/** 
 * app更新
 **/
public class DiagnosisAppUpdateDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String code;

	private String appDescribe;

	private String appName;

	private String appVersion;

	private String available;

	private String downUrl;

	private Integer necessary;

	private Integer type;

	private Date createTime;

	private Date updateTime;

	private String appType;

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAppDescribe() {
		return appDescribe;
	}

	public void setAppDescribe(String appDescribe) {
		this.appDescribe = appDescribe;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public Integer getNecessary() {
		return necessary;
	}

	public void setNecessary(Integer necessary) {
		this.necessary = necessary;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}