package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthUserGroupBindBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/20
 * Time: 16:28
 * Describe:
 */
@Repository
public interface AuthUserGroupBindDao {

	/**
	 * 添加用户和组织的绑定关系
	 * @param userGroupBindBean
	 * @return
	 */
	int addUserGroupBind(AuthUserGroupBindBean userGroupBindBean);

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
	 * @param maps
	 * @return
	 */
	public List<AuthUserGroupBindBean> getStudentByGroupIdLists(Map<String, Object> maps);

	/**
	 * 批量增加
	 * @param list
	 */
    void batchSaveUserGroupBind(List<AuthUserGroupBindBean> list);


	/**
	 * 根据用户ID删除用户和组织的绑定关系
	 * @param authUserManagerBean
	 * @return
	 */
	int delUserGroupBindByUserID(AuthUserManagerBean authUserManagerBean);

	/**
	 * 根据用户ID批量删除用户和组织的绑定关系
	 * @param maps
	 * @return
	 */
	int delUserGroupBindByUserIDBatch(Map<String,Object> maps);


	int delUserGroupBindByUserIDAndGroupType(AuthUserGroupBindBean userGroupBindBean);

	List<AuthUserGroupBindBean> getUserGroupBindForRecord(AuthUserGroupBindBean userGroupBindBean);

	List<AuthUserGroupBindBean> getUserGroupBindBystudentIds(Map<String,Object> maps);


}
