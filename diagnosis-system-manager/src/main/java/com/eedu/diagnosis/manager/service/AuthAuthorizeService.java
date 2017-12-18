package com.eedu.diagnosis.manager.service;

import com.eedu.auth.beans.*;
import com.eedu.auth.beans.enums.EnumBean;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/17
 * Time: 18:07
 * Describe:权限管理模块
 */
public interface AuthAuthorizeService {

	/**
	 * 查询用户管理列表
	 * @param userManagerBean
	 * @return
	 */
	List<AuthUserManagerBean> getUserManagerList(AuthUserManagerBean userManagerBean);

	/**
	 * 查询角色管理列表
	 * @param roleBean
	 * @return
	 */
	List<AuthRoleBean> getRoleList(AuthRoleBean roleBean);

	/**
	 * 查询资源管理列表
	 * @param resourceBean
	 * @return
	 */
	List<AuthResourceBean> getResourceList(AuthResourceBean resourceBean);


	/**
	 * 权限管理-权限管理列表
	 * @param roleBean
	 * @return
	 */
	List<AuthRoleResourceBean> getRoleResourceList(AuthRoleBean roleBean);

	/**
	 * 权限管理-列表-管理用户
	 * @return
	 */
	List<AuthUserRoleBean> getUserListByRoleId(Integer roleId);

	/**
	 * 权限管理-列表-管理用户-用户操作-禁用、启用
	 * @return
	 */
	boolean updateAuthUserManager(AuthUserRoleBean condition);

	/**
	 * 权限管理-列表-管理用户-用户操作-删除
	 * @return
	 */
	boolean deleteAuthUserManager(Integer id);

	/**
	 * 权限管理-列表-管理用户-用户操作-重置密码
	 * @param userRoleBean
	 * @return
	 */
	boolean resetUserPwd(AuthUserRoleBean userRoleBean) throws Exception;

	/**
	 * 权限管理-列表-编辑权限-获取所有资源权限
	 * @param roleBean
	 * @return
	 */
	public List<AuthResourceBean> getAuthResources(AuthRoleBean roleBean);


	/**
	 * 权限管理-列表-管理用户-用户操作-更改角色
	 * @return
	 */
	public boolean updateAuthUserRole(AuthUserRoleBean authUserRoleBean);


	/**
	 * 权限管理-列表-编辑权限
	 * @param authRoleResourceBean
	 * @return
	 */
	public boolean updateAuthRoleResources(AuthRoleResourceBean authRoleResourceBean);


	/**
	 * 权限管理-新增角色（并绑定角色资源）
	 * @param authRoleBean
	 * @param authResourceBeans
	 * @return
	 */
	public boolean addRoleAndRoleResource(AuthRoleBean authRoleBean, List<AuthResourceBean> authResourceBeans);


	/**
	 * 权限管理-新增用户（并绑定用户角色）
	 * @param authUserBean
	 * @param authRoleBeans
	 * @return
	 */
	public boolean addUserAndUserRole(AuthUserManagerBean authUserBean, List<AuthRoleBean> authRoleBeans);

	/**
	 * 权限管理-管理用户-用户禁用，启用
	 *
	 * @param authUserBean
	 * @return
	 */
	public boolean updateUserManagerStatus(AuthUserManagerBean authUserBean);


	/**
	 * 一期  权限管理  用户登录获取左侧菜单列表(资源列表  一期只获取第一级)
	 *
	 * @param authUserManagerBean
	 * @return
	 */
	public List<EnumBean> getUserManagerResourceList(AuthUserManagerBean authUserManagerBean);

	/**
	 * 权限管理-列表-管理用户(分页)
	 *
	 * @param conditionBean
	 * @return
	 */
	public PageInfo<AuthUserRoleBean> getUserListByRoleId(AuthRoleConditionBean conditionBean);

	/**
	 * 后端管理，删除学生组织
	 * @param condition
	 * @return
	 */
	public boolean delStudentGroup(AuthUserBean condition) throws Exception;

	/**
	 * 权限管理-列表-新建权限
	 *
	 * @param resourceBean
	 * @return
	 */
	public boolean addResource(AuthResourceBean resourceBean);

	/**
	 * 权限管理-列表-新建删除
	 *
	 * @param resourceBean
	 * @return
	 */
	public boolean deleteResource(AuthResourceBean resourceBean);

	/**
	 * 根据条件分页查询管理者列表
	 *
	 * @param managerBean
	 * @return
	 */
	public PageInfo<AuthUserManagerBean> getUserManagerPage(AuthUserManagerBean managerBean);

	/**
	 * 权限管理-部门列表-管理用户-删除用户角色关系
	 *
	 * @param authUserRoleBean
	 * @return
	 */
	public boolean delRoleBindByUserAndRole(AuthUserRoleBean authUserRoleBean);

	/**
	 * 学生管理-批量删除学生组织关系
	 *
	 * @param  authUserBeans
	 * @return
	 */
	public boolean delStudentGroupBatch(List<AuthUserBean> authUserBeans)throws Exception;

	/**
	 *  2017-10-10
	 *  新增局长-教研员
	 */
    boolean addAuthUserAuthorityManager(AuthUserAuthorityManagerBean condition)throws Exception;

	List<AuthUserAuthorityManagerBean> getAuthUserAuthorityManagerList(AuthUserAuthorityManagerBean condition)throws Exception;

    PageInfo<AuthUserAuthorityManagerBean> getAuthUserAuthorityManagerPage(AuthUserAuthorityManagerBean condition)throws Exception;

	List<AuthUserAuthorityManagerBean> getUserByAccountAndPwd(AuthUserAuthorityManagerBean userManagerBeanCondition)throws Exception;
}


