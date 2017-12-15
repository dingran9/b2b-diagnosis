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
public class AuthProvinceBean implements Serializable{

	private Integer provinceId;  //省编号

	private String provinceName; //省名称

	private Date createDate;  //创建时间

	private Date updateDate;  //修改时间

	private List<AuthCityBean> cityBeanList = new ArrayList<AuthCityBean>();  //市列表

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
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
