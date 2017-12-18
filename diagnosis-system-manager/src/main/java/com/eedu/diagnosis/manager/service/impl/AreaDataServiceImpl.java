package com.eedu.diagnosis.manager.service.impl;

import com.eedu.auth.beans.AuthCityBean;
import com.eedu.auth.beans.AuthDistrictBean;
import com.eedu.auth.beans.AuthProvinceBean;
import com.eedu.auth.service.AuthAreaService;
import com.eedu.diagnosis.manager.service.AreaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 18:19
 * Describe:
 */
@Service
public class AreaDataServiceImpl implements AreaDataService {
	@Autowired
	private AuthAreaService areaService;

	/**
	 * 查询所有省
	 *
	 * @return
	 */
	@Override
	public List<AuthProvinceBean> getAllProvince() {
		return areaService.getAllProvince();
	}

	/**
	 * 根据省编号获取隶属市列表
	 *
	 * @param provinceId
	 * @return
	 */
	@Override
	public List<AuthCityBean> getCityByProvinceId(Integer provinceId) {
		return areaService.getCityByProvinceId(provinceId);
	}

	/**
	 * 根据市编号获取隶属区县列表
	 *
	 * @param cityId
	 * @return
	 */
	@Override
	public List<AuthDistrictBean> getDistrictByCityId(Integer cityId) {
		return areaService.getDistrictByCityId(cityId);
	}
}
