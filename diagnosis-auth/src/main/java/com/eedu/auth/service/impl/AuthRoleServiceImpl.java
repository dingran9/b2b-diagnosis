package com.eedu.auth.service.impl;

import com.eedu.auth.beans.*;
import com.eedu.auth.dao.AuthResourceDao;
import com.eedu.auth.dao.AuthRoleDao;
import com.eedu.auth.dao.AuthUserManagerDao;
import com.eedu.auth.service.AuthRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/15
 * Time: 18:17
 * Describe:
 */
@Service("roleServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthRoleServiceImpl implements AuthRoleService {
	@Autowired
	private AuthRoleDao authRoleDao;
	@Autowired
	private AuthResourceDao resourceDao;
	@Autowired
	private AuthUserManagerDao userManagerDao;
	/**
	 * 根据条件查询角色列表
	 *
	 * @param roleBean
	 * @return
	 */
	@Override
	public List<AuthRoleBean> getRoleListByCondition(AuthRoleBean roleBean) {
		return authRoleDao.getRoleListByCondition(roleBean);
	}

	/**
	 * 修改角色信息
	 *
	 * @param roleBean
	 * @return
	 */
	@Override
	public int updateRole(AuthRoleBean roleBean) {
		return authRoleDao.updateRole(roleBean);
	}

	/**
	 * 添加角色信息
	 *
	 * @param roleBean
	 * @return
	 */
	@Override
	public int addRole(AuthRoleBean roleBean) {
		return authRoleDao.addRole(roleBean);
	}

	/**
	 * 根据角色id删除角色信息
	 *
	 * @param roleId
	 * @return
	 */
	@Override
	public int deleteRole(Integer roleId) {
		//删除时需要删除角色和权限的绑定关系
		int count = authRoleDao.deleteRole(roleId);
		if(count != 0){
			AuthRoleResourceBean roleResourceBean = new AuthRoleResourceBean();
			roleResourceBean.setRoleId(roleId);
			List<AuthRoleResourceBean> roleResourceBeenList = resourceDao.getRoleResourceBindInfo(roleResourceBean);
			for(AuthRoleResourceBean roleResourceBean1 : roleResourceBeenList){
				authRoleDao.delUserRoleInfo(roleResourceBean1.getId());
			}
		}

		return count;
	}

	/**
	 * 根据用户Id查询用户拥有的角色信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<AuthRoleBean> getRoleByUserId(Integer userId) {
		return authRoleDao.getRoleByUserId(userId);
	}

	/**
	 * 添加用户和角色绑定关系，userId,roleId
	 *
	 * @param maps
	 * @return
	 */
	@Override
	public int addUserRoleInfo(Map<String, Object> maps) {
		return authRoleDao.addUserRoleInfo(maps);
	}

	/**
	 * 根据Id 删除用户角色绑定信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public int delUserRoleInfo(Integer id) {
		return authRoleDao.delUserRoleInfo(id);
	}

	/**
	 * 根据条件修改数据
	 *
	 * @param roleBean
	 * @return
	 */
	@Override
	public int updateUserRoleInfo(AuthUserRoleBean roleBean) {
		return authRoleDao.updateUserRoleInfo(roleBean);
	}

	/**
	 * 查询用户角色绑定信息
	 *
	 * @param userRoleBean
	 * @return
	 */
	@Override
	public List<AuthUserRoleBean> getUserRoleBindInfo(AuthUserRoleBean userRoleBean) {
		return authRoleDao.getUserRoleBindInfo(userRoleBean);
	}

	/**
	 * 权限管理-权限管理列表
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public List<AuthRoleResourceBean> getRoleResourceList(AuthRoleBean condition) {
		//根据条件查询角色列表
		List<AuthRoleBean> roleBeanList = authRoleDao.getRoleListByCondition(condition);
		if(!CollectionUtils.isEmpty(roleBeanList)){
			List<AuthRoleResourceBean> roleResourceBeanList = new ArrayList<>();
			for(AuthRoleBean roleBean : roleBeanList){
				AuthRoleResourceBean roleResourceBean = new AuthRoleResourceBean();
				roleResourceBean.setRoleId(roleBean.getRoleId());
				roleResourceBean.setRoleName(roleBean.getRoleName());
				//查询该角色绑定的用户
				AuthUserRoleBean userRoleBean = new AuthUserRoleBean();
				userRoleBean.setRoleId(roleBean.getRoleId());
				List<AuthUserRoleBean> userRoleBeanList = authRoleDao.getUserRoleBindInfo(userRoleBean);
				roleResourceBean.setPeopleCount(CollectionUtils.isEmpty(userRoleBeanList) ? 0 : userRoleBeanList.size());
				//查询该角色绑定的权限
				List<AuthResourceBean> resourceBeanList = resourceDao.getResourceListByRoleId(roleBean.getRoleId());
				roleResourceBean.getResourceList().addAll(resourceBeanList);
				roleResourceBeanList.add(roleResourceBean);
			}
			return roleResourceBeanList;
		}

		return null;
	}

	/**
	 * 根据角色查询该角色下的用户
	 *
	 * @param roleId
	 * @return
	 */
	@Override
	public List<AuthUserRoleBean> getUserListByRoleId(Integer roleId) {
		//查询该角色绑定的用户
		AuthUserRoleBean userRoleBean = new AuthUserRoleBean();
		userRoleBean.setRoleId(roleId);
		List<AuthUserRoleBean> userRoleBeanList = authRoleDao.getUserRoleBindInfo(userRoleBean);
		List<AuthUserRoleBean> returnData = new ArrayList<>();
		if(!CollectionUtils.isEmpty(userRoleBeanList)){
			for(AuthUserRoleBean userRole : userRoleBeanList){
				AuthUserManagerBean userManagerBean = userManagerDao.getUserByUserId(userRole.getUserId());
				if(null != userManagerBean){
					AuthUserRoleBean roleBean = new AuthUserRoleBean();
					roleBean.setId(userRole.getId());
					roleBean.setUserId(userManagerBean.getUserId());
					roleBean.setUserAccount(userManagerBean.getUserAccount());
					roleBean.setUserName(userManagerBean.getUserName());
					if(null!=userManagerBean.getStatus() && !"".equals(userManagerBean.getStatus()))
					roleBean.setStatus(Integer.parseInt(userManagerBean.getStatus()));
					else roleBean.setStatus(1);
					returnData.add(roleBean);
				}
			}
			return returnData;
		}
		return null;
	}


	/**
	 * 根据角色查询该角色下的用户  分页
	 *
	 * @param conditionBean
	 * @return
	 */
	@Override
	public PageInfo<AuthUserRoleBean> getUserListByRoleIdForPage(AuthRoleConditionBean conditionBean) {
		//查询该角色绑定的用户
		if (conditionBean.getPageNum() != null && conditionBean.getPageSize() != null) {
			//开启分页
			PageHelper.startPage(conditionBean.getPageNum(), conditionBean.getPageSize());
		}
		List<AuthUserManagerBean> userRoleBeanList = userManagerDao.getUserManagerByRole(conditionBean);
		List<AuthUserRoleBean> returnData = new ArrayList<>();
		if(null != userRoleBeanList && userRoleBeanList.size() > 0) {
			for (AuthUserManagerBean userManagerBean : userRoleBeanList) {
				AuthUserRoleBean roleBean = new AuthUserRoleBean();
				roleBean.setUserId(userManagerBean.getUserId());
				roleBean.setUserAccount(userManagerBean.getUserAccount());
				roleBean.setUserName(userManagerBean.getUserName());
				if (null != userManagerBean.getStatus() && !"".equals(userManagerBean.getStatus()))
					roleBean.setStatus(Integer.parseInt(userManagerBean.getStatus()));
				else roleBean.setStatus(1);
				returnData.add(roleBean);
			}
		}
		PageInfo<AuthUserRoleBean> page = new PageInfo<>(returnData);

		return page;
	}
	/**
	 * 根据用户Id 删除用户角色绑定信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public int delUserRoleInfoByUserId(Integer id) {
		return authRoleDao.delUserRoleInfoByUserId(id);
	}



	/**
	 * 权限管理-更新用户对应角色信息
	 * @param authUserRoleBean
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int updateAuthUserRole(AuthUserRoleBean authUserRoleBean){
		int result = authRoleDao.delUserRoleInfoByUserId(authUserRoleBean.getUserId());
		if(result >= 0) {
			List<AuthRoleBean> list = authUserRoleBean.getAuthRoleBeans();
			for (AuthRoleBean roleBean : list) {
				Map<String, Object> map = new HashMap();
				map.put("userId", authUserRoleBean.getUserId());
				map.put("roleId", roleBean.getRoleId());
//                map.put("status","0");
				authRoleDao.addUserRoleInfo(map);
			}
		}
      return result;
	}


	/**
	 * 添加用户并绑定用户角色信息
	 * @param authUserBean
	 * @param authRoleBeans
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class})
	public int addUserAndUserRole(AuthUserManagerBean authUserBean, List<AuthRoleBean> authRoleBeans){

		authUserBean.setStatus("0");
		int result = userManagerDao.addUserManager(authUserBean);
		if(result > 0){
			for(AuthRoleBean authRoleBean : authRoleBeans){
                Map<String,Object> map = new HashMap<String,Object>();
				map.put("userId",authUserBean.getUserId());
				map.put("roleId",authRoleBean.getRoleId());
//				map.put("status","0");
				authRoleDao.addUserRoleInfo(map);
			}
		}
		return result;
	}

    /**
     * 根据用户Id 删除用户以及角色绑定信息
     *
     * @param user_id
     * @return
     */
    @Override
	@Transactional(rollbackFor = {Exception.class})
    public int delUserAndUserRoleInfo(Integer user_id) {
        int result = userManagerDao.delUserManager(user_id);
        if (result > 0) {
            authRoleDao.delUserRoleInfoByUserId(user_id);
        }
        return result;
    }


	/**
	 * 根据用户Id 角色ID 删除用户，角色绑定关系
	 *
	 * @param authUserRoleBean
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int delRoleBindByUserAndRole(AuthUserRoleBean authUserRoleBean) {
		int result = authRoleDao.delUserRoleInfoByUserIdAndRoleId(authUserRoleBean);
		return result;
	}
}
