package com.eedu.auth.service;

import com.eedu.auth.beans.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/15
 * Time: 18:15
 * Describe:
 */
public interface AuthRoleService {

	/**
	 * 根据条件查询角色列表
	 * @param roleBean
	 * @return
	 */
	List<AuthRoleBean> getRoleListByCondition(AuthRoleBean roleBean);

	/**
	 * 修改角色信息
	 * @param roleBean
	 * @return
	 */
	int updateRole(AuthRoleBean roleBean);

	/**
	 * 添加角色信息
	 * @param roleBean
	 * @return
	 */
	int addRole(AuthRoleBean roleBean);

	/**
	 * 根据角色id删除角色信息
	 * @param roleId
	 * @return
	 */
	int deleteRole(Integer roleId);

	/**
	 * 根据用户Id查询用户拥有的角色信息
	 * @param userId
	 * @return
	 */
	List<AuthRoleBean> getRoleByUserId(Integer userId);

	/**
	 * 添加用户和角色绑定关系，userId,roleId
	 * @param maps
	 * @return
	 */
	int addUserRoleInfo(Map<String, Object> maps);

	/**
	 * 根据Id 删除用户角色绑定信息
	 * @param id
	 * @return
	 */
	int delUserRoleInfo(Integer id);

	/**
	 * 根据条件修改数据
	 * @param roleBean
	 * @return
	 */
	int updateUserRoleInfo(AuthUserRoleBean roleBean);

	/**
	 * 查询用户角色绑定信息
	 * @param userRoleBean
	 * @return
	 */
	List<AuthUserRoleBean> getUserRoleBindInfo(AuthUserRoleBean userRoleBean);

	/**
	 * 权限管理-权限管理列表
	 * @param roleBean
	 * @return
	 */
	List<AuthRoleResourceBean> getRoleResourceList(AuthRoleBean roleBean);

	/**
	 * 根据角色查询该角色下的用户
	 * @return
	 */
	List<AuthUserRoleBean> getUserListByRoleId(Integer roleId);

	/**
	 * 根据用户Id 删除用户角色绑定信息
	 * @param id
	 * @return
	 */
	int delUserRoleInfoByUserId(Integer id);

	/**
	 * 权限管理-更新用户对应角色信息
	 * @param authUserRoleBean
	 * @return
	 */
	int updateAuthUserRole(AuthUserRoleBean authUserRoleBean);


	/**
	 * 添加用户并绑定用户角色信息
	 * @param authUserBean
	 * @param authRoleBeans
	 * @return
	 */
	int addUserAndUserRole(AuthUserManagerBean authUserBean, List<AuthRoleBean> authRoleBeans);


	/**
	 * 根据用户Id 删除用户以及角色绑定信息
	 *
	 * @param user_id
	 * @return
	 */
	public int delUserAndUserRoleInfo(Integer user_id);

	/**
	 * 根据角色查询该角色下的用户  分页
	 *
	 * @param conditionBean
	 * @return
	 */
	public PageInfo<AuthUserRoleBean> getUserListByRoleIdForPage(AuthRoleConditionBean conditionBean);

	/**
	 * 根据用户Id 角色ID 删除用户，角色绑定关系
	 *
	 * @param authUserRoleBean
	 * @return
	 */
	public int delRoleBindByUserAndRole(AuthUserRoleBean authUserRoleBean);
}
