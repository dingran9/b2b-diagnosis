package com.eedu.auth.service.impl;

import com.eedu.auth.beans.AuthUserGroupBindBean;
import com.eedu.auth.dao.AuthUserGroupBindDao;
import com.eedu.auth.service.AuthUserGroupBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 10:27
 * Describe:
 */
@Service("userGroupBindServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthUserGroupBindServiceImpl implements AuthUserGroupBindService {
	@Autowired
	private AuthUserGroupBindDao userGroupBindDao;

	/**
	 * 添加用户和组织的绑定关系
	 *
	 * @param userGroupBindBean
	 * @return
	 */
	@Override
	public int addUserGroupBind(AuthUserGroupBindBean userGroupBindBean) {
		return userGroupBindDao.addUserGroupBind(userGroupBindBean);
	}

	@Override
	public List<AuthUserGroupBindBean> batchSaveUserGroupBind(List<AuthUserGroupBindBean> list) {
		userGroupBindDao.batchSaveUserGroupBind(list);
		return list;
	}

	/**
	 * 删除用户和组织的绑定关系
	 *
	 * @param id
	 * @return
	 */
	@Override
	public int delUserGroupBind(Integer id) {
		return userGroupBindDao.delUserGroupBind(id);
	}

	/**
	 * 修改用户和组织的绑定关系
	 *
	 * @param userGroupBindBean
	 * @return
	 */
	@Override
	public int updateUserGroupBind(AuthUserGroupBindBean userGroupBindBean) {
		return userGroupBindDao.updateUserGroupBind(userGroupBindBean);
	}

	/**
	 * 查询用户和组织的绑定关系
	 *
	 * @param userGroupBindBean
	 * @return
	 */
	@Override
	public List<AuthUserGroupBindBean> getUserGroupBindByCondition(AuthUserGroupBindBean userGroupBindBean) {
		return userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);
	}

	/**
	 * 根据ID查询用户组织绑定详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public AuthUserGroupBindBean getGroupInfoById(Integer id) {
		return userGroupBindDao.getGroupInfoById(id);
	}

	/**
	 * 根据ID集合查询绑定的数据
	 *
	 * @param maps key:userType ,groupType ,userIdList ,groupIdList
	 * @return
	 */
	@Override
	public List<AuthUserGroupBindBean> getStudentByGroupIdLists(Map<String, Object> maps) {

		return userGroupBindDao.getStudentByGroupIdLists(maps);
	}

	/**
	 * 验证用户组织绑定关系，存在时更新，不存在添加
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public boolean validateUserGroupBindInfo(AuthUserGroupBindBean condition) {
		AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
		userGroupBindBean.setUserId(condition.getUserId());
		userGroupBindBean.setUserType(condition.getUserType());
//		userGroupBindBean.setGroupId(condition.getGroupId());
		userGroupBindBean.setGroupType(condition.getGroupType());
		List<AuthUserGroupBindBean> userGroupBindBeanList = getUserGroupBindByCondition(userGroupBindBean);
		if(CollectionUtils.isEmpty(userGroupBindBeanList)){
			userGroupBindBean.setGroupId(condition.getGroupId());
			return addUserGroupBind(userGroupBindBean) > 0;
		}else{
			userGroupBindBean.setId(userGroupBindBeanList.get(0).getId());
			userGroupBindBean.setGroupId(condition.getGroupId());
			return updateUserGroupBind(userGroupBindBean) > 0;
		}
	}

}
