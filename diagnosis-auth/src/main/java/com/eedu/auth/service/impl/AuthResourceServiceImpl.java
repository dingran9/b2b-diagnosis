package com.eedu.auth.service.impl;

import com.eedu.auth.beans.AuthResourceBean;
import com.eedu.auth.beans.AuthRoleBean;
import com.eedu.auth.beans.AuthRoleResourceBean;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.dao.AuthResourceDao;
import com.eedu.auth.dao.AuthRoleDao;
import com.eedu.auth.service.AuthResourceService;
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
 * Time: 18:37
 * Describe:
 */
@Service("resourceServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthResourceServiceImpl implements AuthResourceService {
	@Autowired
	private AuthResourceDao authResourceDao;
	@Autowired
	private AuthRoleDao authRoleDao;
	/**
	 * 查询所有资源
	 *
	 * @return
	 */
//	@Override
//	public List<AuthResourceBean> getResourceListByCondition(AuthResourceBean condition) {
//		List<AuthResourceBean> menuList = new ArrayList<>();
//		AuthResourceBean bean = new AuthResourceBean();
//		bean.setResourceType(ConstantEnum.MENU_VALUE.getType());
//		bean.setResourceParentId(0);
//		//查询所有顶级菜单
//		List<AuthResourceBean> resourceBeenList = authResourceDao.getResourceListByCondition(bean);
//		if(CollectionUtils.isEmpty(resourceBeenList)){
//			return null;
//		}
//
//		for(AuthResourceBean resourceBean : resourceBeenList){
//			AuthResourceBean authResourceBean = new AuthResourceBean();
//			authResourceBean.setResourceType(ConstantEnum.MENU_VALUE.getType());
//			authResourceBean.setResourceParentId(resourceBean.getResourceId());
//			//查询二级菜单
//			List<AuthResourceBean> resourceBeenTwoList = authResourceDao.getResourceListByCondition(authResourceBean);
//			if(!CollectionUtils.isEmpty(resourceBeenTwoList)){
//				resourceBean.setResourceBeen(resourceBeenTwoList);
//				for(AuthResourceBean resourceBeanTwo : resourceBeenTwoList){
//					AuthResourceBean treeBean = new AuthResourceBean();
//					treeBean.setResourceType(ConstantEnum.MENU_VALUE.getType());
//					treeBean.setResourceParentId(resourceBeanTwo.getResourceId());
//					//查询三级菜单
//					List<AuthResourceBean> resourceBeenTreeList = authResourceDao.getResourceListByCondition(treeBean);
//					resourceBeanTwo.setResourceBeen(resourceBeenTreeList);
//				}
//			}
//			menuList.add(resourceBean);
//		}
//		return menuList;
//	}

	/**
	 * 查询所有资源
	 *
	 * @return
	 */
	@Override
	public List<AuthResourceBean> getResourceListByCondition(AuthResourceBean condition) {
		List<AuthResourceBean> resourceBeenList = authResourceDao.getResourceListByCondition(condition);
		if(CollectionUtils.isEmpty(resourceBeenList)){
			return null;
		}
		return resourceBeenList;
	}

	/**
	 * 修改资源信息
	 *
	 * @param resourceBean
	 * @return
	 */
	@Override
	public int updateResource(AuthResourceBean resourceBean) {
		return authResourceDao.updateResource(resourceBean);
	}

	/**
	 * 添加资源信息
	 *
	 * @param resourceBean
	 * @return
	 */
	@Override
	public int addResource(AuthResourceBean resourceBean) {
		return authResourceDao.addResource(resourceBean);
	}

	/**
	 * 根据id删除资源信息
	 *
	 * @param resourceId
	 * @return
	 */
	@Override
	public int deleteResource(Integer resourceId) {
		return authResourceDao.deleteResource(resourceId);
	}

	/**
	 * 根据 roleId 查询角色和资源的绑定信息
	 *
	 * @param roleId
	 * @return
	 */
	@Override
	public List<AuthResourceBean> getResourceListByRoleId(Integer roleId) {

		List<AuthResourceBean> menuList = new ArrayList<>();
		List<AuthResourceBean> roleBindResourceList = authResourceDao.getResourceListByRoleId(roleId);
		if(CollectionUtils.isEmpty(roleBindResourceList)){
			return null;
		}
		for(AuthResourceBean roleBindResource : roleBindResourceList){
			if(roleBindResource.getResourceParentId() == 0){ //一级菜单
				AuthResourceBean authResourceBean = new AuthResourceBean();
				authResourceBean.setResourceType(ConstantEnum.MENU_VALUE.getType());
				authResourceBean.setResourceParentId(roleBindResource.getResourceId());
				//查询二级菜单
				List<AuthResourceBean> resourceBeenTwoList = authResourceDao.getResourceListByCondition(authResourceBean);
				if(!CollectionUtils.isEmpty(resourceBeenTwoList)){
					roleBindResource.setResourceBeen(resourceBeenTwoList);
					//查询三级菜单
					for(AuthResourceBean resourceBeanTwo : resourceBeenTwoList){
						AuthResourceBean treeBean = new AuthResourceBean();
						treeBean.setResourceType(ConstantEnum.MENU_VALUE.getType());
						treeBean.setResourceParentId(resourceBeanTwo.getResourceId());
						List<AuthResourceBean> resourceBeenTreeList = authResourceDao.getResourceListByCondition(treeBean);
						resourceBeanTwo.setResourceBeen(resourceBeenTreeList);
					}
				}
			}
			menuList.add(roleBindResource);
			}
		return menuList;
	}

	/**
	 * 根据用户ID查询资源信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List<AuthResourceBean> getResourceByUserId(Integer userId) {
		//根据用户ID查询的资源信息
		List<AuthResourceBean> resourceBeanList = authResourceDao.getResourceByUserId(userId);
		//组装数据
		List<AuthResourceBean> parentResourceBeanList = new ArrayList<>();

		if(CollectionUtils.isEmpty(resourceBeanList)){
			return new ArrayList<AuthResourceBean>();
		}
		for(int i = 0; i < resourceBeanList.size(); i++){
			AuthResourceBean resourceBean = resourceBeanList.get(i);
			if(null != resourceBean){
				//父标签
				if(resourceBean.getResourceParentId() == 0){
					//根据父标签查询二级标签
					AuthResourceBean twoResourceBean = new AuthResourceBean();
					twoResourceBean.setResourceType(ConstantEnum.MENU_VALUE.getType());
					twoResourceBean.setResourceParentId(resourceBean.getResourceParentId());
					List<AuthResourceBean> twoResourceBeanList = authResourceDao.getResourceListByCondition(twoResourceBean);
					resourceBean.setResourceBeen(twoResourceBeanList);
					for(int j = 0; j <= twoResourceBeanList.size(); j++){
						AuthResourceBean treeResourceBean = twoResourceBeanList.get(i);

						AuthResourceBean treeResourceBeanCondition = new AuthResourceBean();
						treeResourceBeanCondition.setResourceType(ConstantEnum.MENU_VALUE.getType());
						treeResourceBeanCondition.setResourceParentId(resourceBean.getResourceParentId());
						List<AuthResourceBean> treeResourceBeanList = authResourceDao.getResourceListByCondition(treeResourceBeanCondition);
						if(!CollectionUtils.isEmpty(treeResourceBeanList)){
							treeResourceBean.setResourceBeen(treeResourceBeanList);
						}
					}
					parentResourceBeanList.add(resourceBean);
				}
			}
		}

		return parentResourceBeanList;
	}
	/**
	 * 根据角色ID更新对应资源信息
	 *
	 * @param authRoleResourceBean
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int updateAuthRoleResources(AuthRoleResourceBean  authRoleResourceBean){
		int result = authResourceDao.deleteRoleResourceByRoleId(authRoleResourceBean.getRoleId());
		if(result >= 0){
			List<AuthResourceBean> authResourceBeans = authRoleResourceBean.getResourceList();
			for(AuthResourceBean authResourceBean : authResourceBeans){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("roleId",authRoleResourceBean.getRoleId());
            map.put("resourceId",authResourceBean.getResourceId());
            authResourceDao.addRoleResourceInfo(map);
			}
		}
        return result;
	}


	/**
	 * 添加角色并绑定角色资源信息
	 *
	 * @param authRoleBean
	 * @param authResourceBeans
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public int addRoleAndRoleResources(AuthRoleBean authRoleBean, List<AuthResourceBean> authResourceBeans){
		int result = authRoleDao.addRole(authRoleBean);
		if(result > 0){
			for(AuthResourceBean authResourceBean : authResourceBeans){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("roleId",authRoleBean.getRoleId());
				map.put("resourceId",authResourceBean.getResourceId());
				authResourceDao.addRoleResourceInfo(map);
			}
		}
		return result;
	}

	/**
	 * 查询所有资源信息并将角色绑定的资源赋予选中状态
	 *
	 * @param authRoleResourceBean
	 * @return
	 */
	@Override
    public List<AuthResourceBean> getAuthResourceByRole(AuthRoleResourceBean  authRoleResourceBean){

		AuthResourceBean bean = new AuthResourceBean();
		//查询所有资源
		List<AuthResourceBean> resourceBeenList = authResourceDao.getResourceListByCondition(bean);

		if(null != resourceBeenList && resourceBeenList.size() > 0) {
            //查询角色对应的资源
			List<AuthResourceBean> resourceBeans = authResourceDao.getResourceListByRoleId(authRoleResourceBean.getRoleId());

			if( null != resourceBeans && resourceBeans.size() > 0){

                for(AuthResourceBean authResourceBean : resourceBeenList){

                	for(AuthResourceBean resourceBean : resourceBeans ){
                        //判断选中状态
                		if(authResourceBean.getResourceId() == resourceBean.getResourceId())
							authResourceBean.setSelected(1);
					}

				}
			}
		}

		return resourceBeenList;
	}


}
