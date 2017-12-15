package com.eedu.auth.service;

import com.eedu.auth.beans.AuthUserBean;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/14
 * Time: 17:11
 * Describe:
 */
public interface AuthUserService {


	/**
	 * 根据条件查询所有用户
	 * @param userBean
	 * @return
	 */
	List<AuthUserBean> getUserByCondition(AuthUserBean userBean) throws Exception;

	/**
	 * 添加用户-注册
	 * @param userBean
	 * @return
	 */
	int addUserInfo(AuthUserBean userBean)throws Exception;

	/**
	 * 修改用户信息
	 * @param userBean
	 * @return
	 */
	int updateUserInfo(AuthUserBean userBean)throws Exception;

	/**
	 * 根据帐号和密码查找用户
	 * @param userBean
	 * @return
	 */
	AuthUserBean getUserByAccountAndPwd(AuthUserBean userBean)throws Exception;

	/**
	 * 根据用户ID查询用户
	 * @return
	 */
	AuthUserBean getUserById(Integer userId) throws Exception;
	/**
	 * 获取个人信息
	 *
	 * @param condition
	 * @return
	 */
	AuthUserBean getStudentInfo(AuthUserBean condition)throws Exception;
	/**
	 * 获取学校，学年下的学生列表
	 *
	 * @param condition
	 * @return
	 */
	PageInfo<AuthUserBean> getUserBySchoolAndGrade(AuthUserBean condition,Integer pageNum,Integer pageSize)throws Exception ;

	/**
	 * 校级管理员 删除学生 实际为删除学生的组织绑定关系
	 *
	 * @param condition
	 * @return
	 */
	public int delStudentGroup(AuthUserBean condition)throws Exception;

	/**
	 *重新绑定学生的组织信息
	 * @param condition
	 * @return
	 */
	public int reBindStudentGroup(AuthUserBean condition) throws Exception;

	/**
	 * 修改学生个人信息
	 *
	 * @param condition
	 * @return
	 */
	public int updateStudentInfo(AuthUserBean condition) throws Exception;

	/**
	 * 异步更新学生的登录时间
	 * @param authUserBean
	 */
	public void saveStudentLoginTime(AuthUserBean authUserBean) throws Exception;

	/**
	 * 批量删除学生绑定的组织关系
	 * @param conditions
	 * @throws Exception
	 */
	public int delStudentGroupBatch(List<AuthUserBean> conditions)throws Exception;

    void batchSaveStudentInfo(List<AuthUserBean> authUserBeans);
}
