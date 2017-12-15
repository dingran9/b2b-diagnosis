package com.eedu.auth.service;

import com.eedu.auth.beans.AuthUserGroupBindBean;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 10:26
 * Describe:
 */
public interface AuthUserGroupBindService {

	/**
	 * 添加用户和组织的绑定关系
	 * @param userGroupBindBean
	 * @return
	 */
	int addUserGroupBind(AuthUserGroupBindBean userGroupBindBean);

	List<AuthUserGroupBindBean> batchSaveUserGroupBind(List<AuthUserGroupBindBean> list);
	/**
	 * 删除用户和组织的绑定关系
	 * @param id
	 * @return
	 */
	int delUserGroupBind(Integer id);

	/**
	 * 修改用户和组织的绑定关系
	 * @param userGroupBindBean
	 * @return
	 */
	int updateUserGroupBind(AuthUserGroupBindBean userGroupBindBean);

	/**
	 * 查询用户和组织的绑定关系
	 * @param userGroupBindBean
	 * @return
	 */
	List<AuthUserGroupBindBean> getUserGroupBindByCondition(AuthUserGroupBindBean userGroupBindBean);

	/**
	 * 根据ID查询用户组织绑定详情
	 * @param id
	 * @return
	 */
	AuthUserGroupBindBean getGroupInfoById(Integer id);

	/**
	 * 根据ID集合查询绑定的数据
	 *
	 * @param maps key:userType ,groupType ,userIdList ,groupIdList
	 * @return
	 */
	List<AuthUserGroupBindBean> getStudentByGroupIdLists(Map<String,Object> maps);

	/**
	 * 验证用户组织绑定关系，存在时更新，不存在添加
	 * @param userGroupBindBean
	 * @return
	 */
	boolean validateUserGroupBindInfo(AuthUserGroupBindBean userGroupBindBean);
}
