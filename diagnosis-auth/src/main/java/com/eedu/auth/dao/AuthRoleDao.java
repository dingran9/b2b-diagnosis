package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthRoleBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.beans.AuthUserRoleBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/15
 * Time: 14:58
 * Describe:
 */
@Repository
public interface AuthRoleDao {
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
	int addUserRoleInfo(Map<String,Object> maps);

	/**
	 * 根据Id 删除用户角色绑定信息
	 * @param id
	 * @return
	 */
	int delUserRoleInfo(Integer id);

	/**
	 * 根据Id修改绑定信息
	 * @param userRoleBean
	 * @return
	 */
	int updateUserRoleInfo(AuthUserRoleBean userRoleBean);

	/**
	 * 查询用户角色绑定信息
	 * @param userRoleBean
	 * @return
	 */
	List<AuthUserRoleBean> getUserRoleBindInfo(AuthUserRoleBean userRoleBean);


	/**
	 * 根据用户Id 删除用户角色绑定信息
	 * @param id
	 * @return
	 */
	int delUserRoleInfoByUserId(Integer id);

	/**
	 * 根据用户Id 角色ID 删除用户角色绑定信息
	 * @param userRoleBean
	 * @return
	 */
	int delUserRoleInfoByUserIdAndRoleId(AuthUserRoleBean userRoleBean);


	/**
	 * 查询多个用户角色绑定信息
	 * @param authUserManagerBeans
	 * @return
	 */
	List<AuthUserRoleBean> getUserRoleBindInfoList(List<AuthUserManagerBean> authUserManagerBeans);
}
