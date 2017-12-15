package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/16
 * Time: 20:45
 * Describe:
 */
public class AuthDistrictBean implements Serializable {

	private Integer districtId;  //区县编号

	private String districtName;  //区县名称

	private Integer cityId;  //隶属市ID

	private Date createDate;  //创建时间

	private Date updateDate;  //修改时间

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
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
