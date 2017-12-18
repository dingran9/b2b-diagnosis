package com.eedu.diagnosis.manager.service;

import com.eedu.auth.beans.AuthUserBean;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 19:54
 * Describe:
 */
public interface AuthStudentService {
	/**
	 * 学生注册
	 * @param userBean
	 * @return
	 */
	boolean stuRegister(AuthUserBean userBean) throws Exception;

	/**
	 * 查找用户
	 * @param userBean
	 * @return
	 */
	List<AuthUserBean> getUserBean(AuthUserBean userBean) throws Exception;

	/**
	 * 修改用户
	 * @param condition
	 * @return
	 */
	boolean updateStuInfo(AuthUserBean condition) throws Exception;


	/**
	 * 后台学生管理列表
	 */
	List<AuthUserBean> stuManager(AuthUserBean userBean) throws Exception;

	/**
	 * 获取个人信息
	 * @param userBean
	 * @return
	 */
	AuthUserBean getStudentInfo(AuthUserBean userBean) throws Exception;

	PageInfo<AuthUserBean> getStudentInfoBySchoolAndGrade(AuthUserBean condition, Integer pageNum, Integer pageSize)throws Exception;
}
