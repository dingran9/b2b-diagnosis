package com.eedu.diagnosis.manager.service;

import com.eedu.auth.beans.AuthCityBean;
import com.eedu.auth.beans.AuthDistrictBean;
import com.eedu.auth.beans.AuthProvinceBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 18:19
 * Describe:
 */
public interface AreaDataService {
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
