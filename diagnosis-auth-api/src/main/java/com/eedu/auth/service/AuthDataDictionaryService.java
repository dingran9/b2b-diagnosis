package com.eedu.auth.service;

import com.eedu.auth.beans.AuthDataDictionaryBean;
import com.eedu.auth.beans.AuthGroupDictionaryBindBean;
import com.eedu.auth.beans.AuthUserDictionaryBindBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/31
 * Time: 16:43
 * Describe:
 */
public interface AuthDataDictionaryService {
	/**
	 * 根据条件查询基础数据信息
	 * @param bean
	 * @return
	 */
	List<AuthDataDictionaryBean> getDataDictionaryByCondition(AuthDataDictionaryBean bean);

	/**
	 * 根据条件组织和基础数据绑定信息
	 * @param bindBean
	 * @return
	 */
	List<AuthGroupDictionaryBindBean> getGroupDictionaryBindByCondition(AuthGroupDictionaryBindBean bindBean);

	/**
	 * 添加组织和基础数据的绑定关系
	 * @param bindBean
	 * @return
	 */
	int addGroupDictionaryBind(AuthGroupDictionaryBindBean bindBean);

	/**
	 * 根据条件查询用户和基础数据绑定信息
	 * @param bindBean
	 * @return
	 */
	List<AuthUserDictionaryBindBean> getUserDictionaryBindByCondition(AuthUserDictionaryBindBean bindBean);
}
