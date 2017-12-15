package com.eedu.auth.service;

import com.eedu.auth.beans.AuthCityBean;
import com.eedu.auth.beans.AuthDistrictBean;
import com.eedu.auth.beans.AuthProvinceBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/18
 * Time: 13:47
 * Describe:
 */
public interface AuthAreaService {
	/**
	 * 查询所有省
	 * @return
	 */
	List<AuthProvinceBean> getAllProvince();

	/**
	 * 根据省编号获取隶属市列表
	 * @param provinceId
	 * @return
	 */
	List<AuthCityBean> getCityByProvinceId(Integer provinceId);

	/**
	 * 根据市编号获取隶属区县列表
	 * @param cityId
	 * @return
	 */
	List<AuthDistrictBean> getDistrictByCityId(Integer cityId);
}
