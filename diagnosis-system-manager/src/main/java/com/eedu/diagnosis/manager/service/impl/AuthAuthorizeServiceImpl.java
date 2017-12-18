package com.eedu.diagnosis.manager.service.impl;

import com.eedu.auth.beans.*;
import com.eedu.auth.beans.enums.EnumBean;
import com.eedu.auth.service.*;
import com.eedu.diagnosis.manager.service.AuthAuthorizeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/17
 * Time: 18:08
 * Describe:权限管理模块
 */
@Service
public class AuthAuthorizeServiceImpl implements AuthAuthorizeService {

	@Autowired
	private AuthUserManagerService userManagerService;
	@Autowired
	private AuthRoleService roleService;
	@Autowired
	private AuthResourceService resourceService;
	@Autowired
	private AuthUserService authUserService;
    @Autowired
    private AuthUserAuthorityManagerService authUserAuthorityManagerService;

	/**
	 * 查询用户管理列表
	 *
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public List<AuthUserManagerBean> getUserManagerList(AuthUserManagerBean userManagerBean) {
		return userManagerService.getUserManagerList(userManagerBean);
	}

	/**
	 * 查询角色管理列表
	 *
	 * @param roleBean
	 * @return
	 */
	@Override
	public List<AuthRoleBean> getRoleList(AuthRoleBean roleBean) {
		return roleService.getRoleListByCondition(roleBean);
	}

	/**
	 * 查询资源管理列表
	 *
	 * @param resourceBean
	 * @return
	 */
	@Override
	public List<AuthResourceBean> getResourceList(AuthResourceBean resourceBean) {
		return resourceService.getResourceListByCondition(resourceBean);
	}

	/**
	 * 权限管理-权限管理列表
	 *
	 * @param roleBean
	 * @return
	 */
	@Override
	public List<AuthRoleResourceBean> getRoleResourceList(AuthRoleBean roleBean) {
		return roleService.getRoleResourceList(roleBean);
	}

	/**
	 * 权限管理-列表-管理用户
	 *
	 * @param roleId
	 * @return
	 */
	@Override
	public List<AuthUserRoleBean> getUserListByRoleId(Integer roleId) {
		return roleService.getUserListByRoleId(roleId);
	}

	/**
	 * 权限管理-列表-管理用户-用户操作-禁用、启用
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public boolean updateAuthUserManager(AuthUserRoleBean condition) {
		return roleService.updateUserRoleInfo(condition) > 0;
	}

	/**
	 * 权限管理-列表-管理用户-用户操作-删除
	 *
	 * @param user_id
	 * @return
	 */
	@Override
	public boolean deleteAuthUserManager(Integer user_id) {
		return roleService.delUserAndUserRoleInfo(user_id) > 0;
	}

	/**
	 * 权限管理-列表-管理用户-用户操作-重置密码
	 *
	 * @param userRoleBean
	 * @return
	 */
	@Override
	public boolean resetUserPwd(AuthUserRoleBean userRoleBean) throws Exception {
		AuthUserManagerBean userManagerBean = new AuthUserManagerBean();
		userManagerBean.setUserId(userRoleBean.getUserId());
		userManagerBean.setUserPassword(userRoleBean.getUserPassword());
		boolean b = userManagerService.updateUserManager(userManagerBean) > 0;
		if (b){
			AuthUserManagerBean userByUserId = userManagerService.getUserByUserId(userRoleBean.getUserId());
			AuthUserAuthorityManagerBean record = new AuthUserAuthorityManagerBean();
			record.setUserAccount(userByUserId.getUserAccount());
			List<AuthUserAuthorityManagerBean> userAuthorityManagerList = authUserAuthorityManagerService.getUserAuthorityManagerList(record);
			if (!CollectionUtils.isEmpty(userAuthorityManagerList)){
				AuthUserAuthorityManagerBean records =userAuthorityManagerList.get(0);
				records.setUserPassword(userRoleBean.getUserPassword());
				authUserAuthorityManagerService.updateUserAuthorityManager(records);
			}

		}
		return b;
	}

	/**
	 * 权限管理-列表-编辑权限-查询所有资源权限
	 *
	 * @param roleBean
	 * @return
	 */
	@Override
	public List<AuthResourceBean> getAuthResources(AuthRoleBean roleBean){
		AuthResourceBean resourceBean = new AuthResourceBean();
		List<AuthResourceBean> resourceBeans = resourceService.getResourceListByCondition(resourceBean);
		return resourceBeans;
	}


	/**
	 * 权限管理-列表-管理用户-用户操作-更改角色
	 * @return
	 */
	@Override
	public boolean updateAuthUserRole(AuthUserRoleBean authUserRoleBean){

		return roleService.updateAuthUserRole(authUserRoleBean) >= 0;
	}

	/**
	 * 权限管理-列表-编辑权限
	 * @param authRoleResourceBean
	 * @return
	 */
	@Override
	public boolean updateAuthRoleResources(AuthRoleResourceBean authRoleResourceBean){

      return resourceService.updateAuthRoleResources(authRoleResourceBean) >= 0;
	}


	/**
	 * 权限管理-新增角色（并绑定角色资源）
	 * @param authRoleBean
	 * @param authResourceBeans
	 * @return
	 */
	@Override
	public boolean addRoleAndRoleResource(AuthRoleBean authRoleBean, List<AuthResourceBean> authResourceBeans){
		return resourceService.addRoleAndRoleResources(authRoleBean,authResourceBeans) > 0;
	}


	/**
	 * 权限管理-新增用户（并绑定用户角色）
	 * @param authUserBean
	 * @param authRoleBeans
	 * @return
	 */
	@Override
	public boolean addUserAndUserRole(AuthUserManagerBean authUserBean, List<AuthRoleBean> authRoleBeans){
           return roleService.addUserAndUserRole(authUserBean,authRoleBeans) > 0;
	}

	/**
	 * 权限管理-管理用户-用户禁用，启用
	 *
	 * @param authUserBean
	 * @return
	 */
	@Override
	public boolean updateUserManagerStatus(AuthUserManagerBean authUserBean){

		return userManagerService.updateUserManagerStatus(authUserBean) > 0;
	}


	/**
	 * 一期  权限管理  用户登录获取左侧菜单列表(资源列表  一期只获取第一级)
	 *
	 * @param authUserManagerBean
	 * @return
	 */
	@Override
	public List<EnumBean> getUserManagerResourceList(AuthUserManagerBean authUserManagerBean){

		return userManagerService.getUserManagerResourceList(authUserManagerBean);
	}


	/**
	 * 权限管理-列表-管理用户(分页)
	 *
	 * @param conditionBean
	 * @return
	 */
	@Override
	public PageInfo<AuthUserRoleBean> getUserListByRoleId(AuthRoleConditionBean conditionBean) {
		return roleService.getUserListByRoleIdForPage(conditionBean);
	}

	/**
	 * 权限管理-列表-管理用户(分页)
	 *
	 * @param resourceBean
	 * @return
	 */
	@Override
	public boolean addResource(AuthResourceBean resourceBean) {
		return resourceService.addResource(resourceBean) > 0;
	}


	/**
	 * 权限管理-列表-新建删除
	 *
	 * @param resourceBean
	 * @return
	 */
	@Override
	public boolean deleteResource(AuthResourceBean resourceBean){
		return resourceService.deleteResource(resourceBean.getResourceId()) > 0;
	}

	/**
	 * 根据条件分页查询管理者列表
	 *
	 * @param managerBean
	 * @return
	 */
	public PageInfo<AuthUserManagerBean> getUserManagerPage(AuthUserManagerBean managerBean){

		return userManagerService.getUserManagerPage(managerBean);
	}

	/**
	 * 权限管理-部门列表-管理用户-删除用户角色关系
	 *
	 * @param authUserRoleBean
	 * @return
	 */
	public boolean delRoleBindByUserAndRole(AuthUserRoleBean authUserRoleBean){

		return roleService.delRoleBindByUserAndRole(authUserRoleBean) > 0;
	}

	/**
	 * 学生管理-删除学生组织关系
	 *
	 * @param authUserBean
	 * @return
	 */
	public boolean delStudentGroup(AuthUserBean authUserBean)throws Exception{

		return authUserService.delStudentGroup(authUserBean) > 0 ;
	}

	/**
	 * 学生管理-删除学生组织关系
	 *
	 * @param authUserBeans
	 * @return
	 */
	public boolean delStudentGroupBatch(List<AuthUserBean> authUserBeans)throws Exception{

		return authUserService.delStudentGroupBatch(authUserBeans) > 0 ;
	}

    @Override
    public boolean addAuthUserAuthorityManager(AuthUserAuthorityManagerBean condition)throws Exception {
		AuthUserManagerBean beans = new AuthUserManagerBean();
        boolean b = authUserAuthorityManagerService.addUserAuthorityManager(condition) > 0;
        if(b && condition.getUserType()==9) {
			BeanUtils.copyProperties(condition,beans);
			userManagerService.addUserManager(beans);
		 }
         return b;

    }

    @Override
    public PageInfo<AuthUserAuthorityManagerBean> getAuthUserAuthorityManagerPage(AuthUserAuthorityManagerBean condition) throws Exception{
        return authUserAuthorityManagerService.getUserAuthorityManagerPage(condition);
    }
	@Override
	public List<AuthUserAuthorityManagerBean> getAuthUserAuthorityManagerList(AuthUserAuthorityManagerBean condition) throws Exception{
		return authUserAuthorityManagerService.getUserAuthorityManagerList(condition);
	}


	@Override
	public List<AuthUserAuthorityManagerBean> getUserByAccountAndPwd(AuthUserAuthorityManagerBean record)  throws Exception{
		return authUserAuthorityManagerService.getUserByAccountAndPwd(record);
	}


}