package com.eedu.auth.service;

import com.eedu.auth.beans.AuthResourceBean;
import com.eedu.auth.beans.AuthRoleBean;
import com.eedu.auth.beans.AuthRoleResourceBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/15
 * Time: 18:37
 * Describe:
 */
public interface AuthResourceService {

	/**
	 * 查询所有资源
	 * @return
	 */
	List<AuthResourceBean> getResourceListByCondition(AuthResourceBean resourceBean);

	/**
	 * 修改资源信息
	 * @param resourceBean
	 * @return
	 */
	int updateResource(AuthResourceBean resourceBean);

	/**
	 * 添加资源信息
	 * @param resourceBean
	 * @return
	 */
	int addResource(AuthResourceBean resourceBean);

	/**
	 * 根据id删除资源信息
	 * @param resourceId
	 * @return
	 */
	int deleteResource(Integer resourceId);

	/**
	 * 根据 roleId 查询角色和资源的绑定信息
	 * @param roleId
	 * @return
	 */
	List<AuthResourceBean> getResourceListByRoleId(Integer roleId);

	/**
	 * 根据用户ID查询资源信息
	 * @param userId
	 * @return
	 */
	List<AuthResourceBean> getResourceByUserId(Integer userId);

	/**
	 * 根据角色ID更新对应资源信息
	 *
	 * @param authRoleResourceBean
	 * @return
	 */
	public int updateAuthRoleResources(AuthRoleResourceBean authRoleResourceBean);

	/**
	 * 添加角色并绑定角色资源信息
	 *
	 * @param authRoleBean
	 * @param authResourceBeans
	 * @return
	 */
	public int addRoleAndRoleResources(AuthRoleBean authRoleBean, List<AuthResourceBean> authResourceBeans);


	/**
	 * 查询所有资源信息并将角色绑定的资源赋予选中状态
	 *
	 * @param authRoleResourceBean
	 * @return
	 */
	public List<AuthResourceBean> getAuthResourceByRole(AuthRoleResourceBean  authRoleResourceBean);
}
