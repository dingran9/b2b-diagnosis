package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserManagerConditionBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/16
 * Time: 20:41
 * Describe:
 */
@Repository
public interface AuthGroupDao {
	/**
	 * 添加组织信息
	 * @param groupBean
	 * @return
	 */
	int addGroupInfo(AuthGroupBean groupBean);

	/**
	 * 根据Id删除组织信息
	 * @param groupId
	 * @return
	 */
	int delGroupInfoById(String groupId);

	/**
	 * 修改组织信息
	 * @param groupBean
	 * @return
	 */
	int updateGroupInfoById(AuthGroupBean groupBean);

	/**
	 * 根据条件查询组织信息
	 * @param groupBean
	 * @return
	 */
	List<AuthGroupBean> getGroupByCondition(AuthGroupBean groupBean);

	/**
	 * 根据ID查询组织信息
	 * @return
	 */
	AuthGroupBean getGroupInfoById(Integer groupId);

	/**
	 * 根据用户ID列表查询用户对应的班级信息
	 * @return
	 */
	List<Map<String,Object>> getManagerGroupByList(List<AuthUserManagerConditionBean> authUserManagerBeans);

	/**
	 * 根据班级名称查询班级 用于验重
	 * @param groupBean
	 * @return
	 */
	List<AuthGroupBean> getClassByName(AuthGroupBean groupBean);

	/**
	 * 根据id组查询组织信息
	 * @param ids
	 * @return
	 */
	List<AuthGroupBean> getGroupByIds(Map<String,List<Integer>> ids);
}
