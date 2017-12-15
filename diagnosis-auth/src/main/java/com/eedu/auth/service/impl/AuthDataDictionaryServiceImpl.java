package com.eedu.auth.service.impl;

import com.eedu.auth.beans.AuthDataDictionaryBean;
import com.eedu.auth.beans.AuthGroupDictionaryBindBean;
import com.eedu.auth.beans.AuthUserDictionaryBindBean;
import com.eedu.auth.dao.AuthDataDictionaryDao;
import com.eedu.auth.service.AuthDataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/31
 * Time: 16:21
 * Describe:
 */
@Service("dataDictionaryServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthDataDictionaryServiceImpl implements AuthDataDictionaryService{

	@Autowired
	private AuthDataDictionaryDao dataDictionaryDao;

	/**
	 * 根据条件查询基础数据信息
	 *
	 * @param bean
	 * @return
	 */
	@Override
	public List<AuthDataDictionaryBean> getDataDictionaryByCondition(AuthDataDictionaryBean bean) {
		return dataDictionaryDao.getDataDictionaryByCondition(bean);
	}

	/**
	 * 根据条件查询组织和基础数据绑定信息
	 *
	 * @param bindBean
	 * @return
	 */
	@Override
	public List<AuthGroupDictionaryBindBean> getGroupDictionaryBindByCondition(AuthGroupDictionaryBindBean bindBean) {
		return dataDictionaryDao.getGroupDictionaryBindByCondition(bindBean);
	}

	/**
	 * 添加组织和基础数据的绑定关系
	 *
	 * @param bindBean
	 * @return
	 */
	@Override
	public int addGroupDictionaryBind(AuthGroupDictionaryBindBean bindBean) {
		return dataDictionaryDao.addGroupDictionaryBind(bindBean);
	}

	/**
	 * 根据条件查询用户和基础数据绑定信息
	 *
	 * @param bindBean
	 * @return
	 */
	@Override
	public List<AuthUserDictionaryBindBean> getUserDictionaryBindByCondition(AuthUserDictionaryBindBean bindBean) {
		return dataDictionaryDao.getUserDictionaryBindByCondition(bindBean);
	}
}
