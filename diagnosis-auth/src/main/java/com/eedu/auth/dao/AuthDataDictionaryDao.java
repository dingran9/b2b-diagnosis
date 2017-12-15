package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthDataDictionaryBean;
import com.eedu.auth.beans.AuthGroupDictionaryBindBean;
import com.eedu.auth.beans.AuthUserDictionaryBindBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/31
 * Time: 16:06
 * Describe:
 */
@Repository
public interface AuthDataDictionaryDao {

	/**
	 * 根据条件查询基础数据信息
	 * @param bean
	 * @return
	 */
	List<AuthDataDictionaryBean> getDataDictionaryByCondition(AuthDataDictionaryBean bean);

	/**
	 * 根据条件查询组织和基础数据绑定信息
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
	 * 修改组织和基础数据的绑定关系
	 * @param bindBean
	 * @return
	 */
	int updateGroupDictionaryBind(AuthGroupDictionaryBindBean bindBean);

	/**
	 * 根据ID删除组织和基础数据的绑定关系
	 * @param id
	 * @return
	 */
	int deleteGroupDictionaryBind(Integer id);


	/**
	 * 根据条件查询用户和基础数据绑定信息
	 * @param bindBean
	 * @return
	 */
	List<AuthUserDictionaryBindBean> getUserDictionaryBindByCondition(AuthUserDictionaryBindBean bindBean);

	/**
	 * 添加用户和基础数据的绑定关系
	 * @param bindBean
	 * @return
	 */
	int addUserDictionaryBindByCondition(AuthUserDictionaryBindBean bindBean);

	/**
	 * 修改用户和基础数据的绑定关系
	 * @param bindBean
	 * @return
	 */
	int updateUserDictionaryBind(AuthUserDictionaryBindBean bindBean);

	/**
	 * 根据ID删除用户和基础数据的绑定关系
	 * @param id
	 * @return
	 */
	int deleteUserDictionaryBind(Integer id);


	/**
	 * 根据用户ID删除用户和基础数据的绑定关系
	 * @param authUserManagerBean
	 * @return
	 */
	int deleteUserDictionaryBindByUserId(AuthUserManagerBean authUserManagerBean);

}
