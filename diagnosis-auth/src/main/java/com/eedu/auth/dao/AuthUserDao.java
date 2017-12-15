package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthUserBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/14
 * Time: 17:12
 * Describe:
 */
@Repository
public interface AuthUserDao {

	/**
	 * 根据条件查询所有用户
	 * @param userBean
	 * @return
	 */
	List<AuthUserBean> getUserByCondition(AuthUserBean userBean);

	/**
	 * 添加用户-注册
	 * @param userBean
	 * @return
	 */
	int addUserInfo(AuthUserBean userBean);

	/**
	 * 修改用户信息
	 * @param userBean
	 * @return
	 */
	int updateUserInfo(AuthUserBean userBean);

	/**
	 * 根据帐号和密码查找用户
	 * @param userBean
	 * @return
	 */
	AuthUserBean getUserByAccountAndPwd(AuthUserBean userBean);

	/**
	 * 根据id查询用户
	 * @param userId
	 * @return
	 */
	AuthUserBean getUserById(Integer userId);
	/**
	 * 获取学校，学年下的学生列表
	 *
	 * @param condition
	 * @return
	 */
    List<AuthUserBean> getUserBySchoolAndGrade(AuthUserBean condition);

	/**
	 * 将学生表中的学校，年级，班级设置为null
	 *
	 * @param condition
	 * @return
	 */
	int updateUserGroupNull(AuthUserBean condition);

	/**
	 * 批量将学生表中的学校，年级，班级设置为null
	 *
	 * @param maps
	 * @return
	 */
	int updateUserGroupNullBatch(Map<String,Object> maps);

    void batchSaveStudentInfo(List<AuthUserBean> authUserBeans);
}
