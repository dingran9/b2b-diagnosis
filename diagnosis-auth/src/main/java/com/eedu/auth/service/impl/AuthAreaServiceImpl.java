package com.eedu.auth.service.impl;

import com.eedu.auth.beans.AuthCityBean;
import com.eedu.auth.beans.AuthDistrictBean;
import com.eedu.auth.beans.AuthProvinceBean;
import com.eedu.auth.dao.AuthAreaDao;
import com.eedu.auth.service.AuthAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/18
 * Time: 13:47
 * Describe:
 */
@Service("areaServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthAreaServiceImpl implements AuthAreaService {
	@Autowired
	private AuthAreaDao authAreaDao;
	/**
	 * 查询所有省
	 *
	 * @return
	 */
	@Override
	public List<AuthProvinceBean> getAllProvince() {
		return authAreaDao.getAllProvince();
	}

	/**
	 * 根据省编号获取隶属市列表
	 *
	 * @param provinceId
	 * @return
	 */
	@Override
	public List<AuthCityBean> getCityByProvinceId(Integer provinceId) {
		return authAreaDao.getCityByProvinceId(provinceId);
	}

	/**
	 * 根据市编号获取隶属区县列表
	 *
	 * @param cityId
	 * @return
	 */
	@Override
	public List<AuthDistrictBean> getDistrictByCityId(Integer cityId) {
		return authAreaDao.getDistrictByCityId(cityId);
	}
}
