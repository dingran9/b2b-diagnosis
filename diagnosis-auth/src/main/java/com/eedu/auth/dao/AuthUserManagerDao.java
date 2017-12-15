package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthRoleConditionBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.beans.AuthUserManagerConditionBean;
import com.eedu.auth.beans.enums.EnumBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/20
 * Time: 9:27
 * Describe:
 */
@Repository
public interface AuthUserManagerDao {

	/**
	 * 添加管理者信息
	 * @param managerBean
	 * @return
	 */
	int addUserManager(AuthUserManagerBean managerBean);

	void batchSaveUserManager(List<AuthUserManagerBean> list);
	/**
	 * 删除管理者信息
	 * @param userId
	 * @return
	 */
	int delUserManager(Integer userId);

	/**
	 * 修改管理者信息
	 * @param managerBean
	 * @return
	 */
	int updateUserManager(AuthUserManagerBean managerBean);

	/**
	 * 根据条件查询管理者列表
	 * @param managerBean
	 * @return
	 */
	List<AuthUserManagerBean> getUserManagerList(AuthUserManagerBean managerBean);

	/**
	 * 根据帐号和密码查找用户
	 * @param managerBean
	 * @return
	 */
	List<AuthUserManagerBean> getUserByAccountAndPwd(AuthUserManagerBean managerBean);

	/**
	 * 根据用户名查询用户
	 * @param userPhone
	 * @return
	 */
	AuthUserManagerBean getUserByAccount(String userPhone);

	/**
	 * 根据手机号查询用户
	 * @param userPhone
	 * @return
	 */
	AuthUserManagerBean getUserByUserPhone(String userPhone);
	/**
	 * 根据userId集合查询
	 * @param maps key=userIdList
	 * @return
	 */
	List<AuthUserManagerBean> getUserByListId(Map<String,Object> maps);

	/**
	 * 根据用户Id查询用户
	 * @return
	 */
	AuthUserManagerBean getUserByUserId(Integer userId);

	/**
	 * 查询帐号和手机号是否存在
	 * @param userManagerBean
	 * @return
	 */
	AuthUserManagerBean getUserIsExist(AuthUserManagerBean userManagerBean);

	/**
	 * 后端管理 教师列表
	 * @param conditionBean
	 * @return
	 */
	List<AuthUserManagerConditionBean> getUserManagerListByCondition(AuthUserManagerConditionBean conditionBean);

	/**
	 * 权限管理  更新用户状态
	 * @param userManagerBean
	 * @return
	 */
    public int updateUserManagerStatus(AuthUserManagerBean userManagerBean);

	/**
	 * 一期  权限管理  用户登录获取左侧菜单列表(资源列表  一期只获取第一级)
	 * @param userManagerBean
	 * @return
	 */
    public List<Map<String,Object>> getUserManagerResourceList(AuthUserManagerBean userManagerBean);


	/**
	 * 二期  权限管理  用户登录获取左侧菜单列表(资源列表)
	 * @param userManagerBean
	 * @return
	 */
	public List<EnumBean> getUserManagerResourceByUserManager(AuthUserManagerBean userManagerBean);

	/**
	 * 根据角色信息查询对应的用户列表
	 * @param conditionBean
	 * @return
	 */
	public List<AuthUserManagerBean> getUserManagerByRole(AuthRoleConditionBean conditionBean);


	/**
	 * 一期  权限管理  系统超级管理员获取所有左侧菜单列表(资源列表  一期只获取第一级)
	 * @param userManagerBean
	 * @return
	 */
	public List<Map<String,Object>> getUserManagerResourceListByAdmin(AuthUserManagerBean userManagerBean);

	/**
	 * 批量更新用户account（账号）信息
	 * @param list
	 * @return
	 */
	int batchUpdateManagerAccount(List<AuthUserManagerBean> list);
    /**
     * 删除教师信息
     * @param userManagerBean
     * @return
     */
	int delTeacherManager(AuthUserManagerBean userManagerBean);

	/**
	 * 二期  权限管理  管理员用户登录获取左侧菜单列表(资源列表)
	 * @param userManagerBean
	 * @return
	 */
	public List<EnumBean> getUserManagerResourceByAdmin(AuthUserManagerBean userManagerBean);


	/**
	 * 设置教师，学校，年级，班级为null
	 * @param userManagerBean
	 * @return
	 */
	int updateUserManagerGroupNull(AuthUserManagerBean userManagerBean);
}
