package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthResourceBean;
import com.eedu.auth.beans.AuthRoleResourceBean;
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
public interface AuthResourceDao {

	/**
	 * 根据条件查询所有资源
	 * @param resourceBean
	 * @return
	 */
	List<AuthResourceBean> getResourceListByCondition(AuthResourceBean resourceBean);

	/**
	 * 修改资源信息
	 * @param resourceBean
	 * @return
	 */
	int updateResource(AuthResourceBean resourceBean);

	/**
	 * 添加资源信息
	 * @param resourceBean
	 * @return
	 */
	int addResource(AuthResourceBean resourceBean);

	/**
	 * 根据id删除资源信息
	 * @param resourceId
	 * @return
	 */
	int deleteResource(Integer resourceId);

	/**
	 * 根据 roleId 查询角色和资源的绑定信息
	 * @param roleId
	 * @return
	 */
	List<AuthResourceBean> getResourceListByRoleId(Integer roleId);

	/**
	 * 查询角色和资源的绑定信息
	 * @param roleResourceBean
	 * @return
	 */
	List<AuthRoleResourceBean> getRoleResourceBindInfo(AuthRoleResourceBean roleResourceBean);

	/**
	 * 根据用户ID查询资源信息
	 * @param userId
	 * @return
	 */
	List<AuthResourceBean> getResourceByUserId(Integer userId);


	/**
	 * 根据角色id删除角色对应资源信息
	 * @param id
	 * @return
	 */
	int deleteRoleResourceByRoleId(Integer id);

	/**
	 * 添加角色资源绑定信息
	 * @param maps
	 * @return
	 */
	int addRoleResourceInfo(Map<String,Object> maps);
}
