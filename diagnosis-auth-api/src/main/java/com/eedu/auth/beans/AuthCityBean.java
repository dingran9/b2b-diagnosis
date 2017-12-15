package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/16
 * Time: 20:44
 * Describe:
 */
public class AuthCityBean implements Serializable {

	private Integer cityId;   //市编号

	private String cityName;  //市名称

	private Integer zipCode;  //邮政编码

	private Integer provinceId;  //隶属省ID

	private Date createDate;  //创建时间

	private Date updateDate;  //修改时间

	private List<AuthDistrictBean> districtBeanList = new ArrayList<AuthDistrictBean>(); //区县列表

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
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
