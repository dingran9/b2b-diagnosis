package com.eedu.diagnosis.manager.controller.auth;

import com.eedu.auth.beans.*;
import com.eedu.diagnosis.common.utils.GsonUtils;
import com.eedu.diagnosis.manager.model.request.RoleResourceModel;
import com.eedu.diagnosis.manager.model.request.UserManagerRoleModel;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.AuthAuthorizeService;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/17
 * Time: 17:38
 * Describe: 权限管理模块
 */
@RestController
@RequestMapping("/authorize")
@Slf4j
public class AuthAuthorizeController {

	@Autowired
	private AuthAuthorizeService authorizeService;

	/**
	 * 权限管理 列表
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authorityManager", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse authorityManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthRoleBean condition = GsonUtils.getGson().fromJson(requestBody,AuthRoleBean.class);
			if(null == condition){
				log.error("schoolId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"schoolId is null");
			}
			baseResponse.setResult(authorizeService.getRoleResourceList(condition));
		}catch(Exception ex){
			log.error("AuthAuthorizeController.authorityManager error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 权限管理-列表-管理用户
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authUserManager",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse authUsermanager(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthRoleConditionBean condition = GsonUtils.getGson().fromJson(requestBody,AuthRoleConditionBean.class);
			if(null == condition || null == condition.getRoleId() || condition.getRoleId() <= 0){
				log.error("roleId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"roleId is null");
			}
			if(null == condition || null == condition.getPageNum() || condition.getPageNum() <= 0){
				log.error("pagenum is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"pagenum is null");
			}
			if(null == condition || null == condition.getPageSize() || condition.getPageSize() <= 0){
				log.error("pagesize is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"pagesize is null");
			}
			baseResponse.setResult(authorizeService.getUserListByRoleId(condition));
		}catch(Exception e){
			log.error("AuthAuthorizeController.authUserManager",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}

	/**
	 * 权限管理-列表-管理用户-用户操作-禁用、启用
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserManagerStatus",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse updateUserManagerStatus(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
				log.error("id is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"id is null");
			}
			if(null == condition || null == condition.getStatus() || "".equals(condition.getStatus())){
				log.error("status is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"status is null");
			}
			boolean result = authorizeService.updateUserManagerStatus(condition);
			if(result)
//			baseResponse.setResult(authorizeService.getUserListByRoleId(condition.getRoleId()));
				baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(),"update user status success");
			else{
				return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(),"update user status fail");
			}

		}catch(Exception e){
			log.error("AuthAuthorizeController.authUserManager",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}

	/**
	 * 权限管理-列表-管理用户-用户操作-删除
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAuthUserManager",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse deleteAuthUserManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserRoleBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserRoleBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
				log.error("id is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"id is null");
			}
			boolean result = authorizeService.deleteAuthUserManager(condition.getUserId());
			if(result)
//			baseResponse.setResult(authorizeService.deleteAuthUserManager(condition.getRoleId()));
				baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(),"delete usermanager success");
			else {
				return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(),"delete usermanager fail");
			}
		}catch(Exception e){
			log.error("AuthAuthorizeController.authUserManager",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}

	/**
	 * 权限管理-列表-管理用户-用户操作-修改角色
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAuthUserRole",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse updateAuthUserRole(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserRoleBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserRoleBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() < 0){
				log.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
			}
			if(null == condition || null == condition.getAuthRoleBeans() || condition.getAuthRoleBeans().size() == 0 ){
				log.error("authRoleBeans is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"authRoleBeans is null");
			}

			boolean result = authorizeService.updateAuthUserRole(condition);

			if(result)
				baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(),"update  User  Role  success");
			else
				baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(),"update  User  Role  fail");
		}catch(Exception e){
			log.error("AuthAuthorizeController.authUserManager",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}

	/**
	 * 权限管理-列表-管理用户-用户操作-重置密码
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetUserPwd",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse resetUserPwd(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserRoleBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserRoleBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
				log.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
			}
			if(null == condition || StringUtils.isBlank(condition.getUserPassword())){
				log.error("userPassword is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userPassword is null");
			}
			baseResponse.setResult(authorizeService.resetUserPwd(condition));
		}catch(Exception e){
			log.error("AuthAuthorizeController.authUserManager",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}


	/**
	 * 权限管理-列表-编辑权限-获取所有资源权限
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAuthResources",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse getAuthResources(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthRoleBean condition = GsonUtils.getGson().fromJson(requestBody,AuthRoleBean.class);
			if(null == condition){
				log.error("schoolId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"schoolId is null");
			}
			AuthResourceBean authResourceBean = new AuthResourceBean();
			baseResponse.setResult(authorizeService.getResourceList(authResourceBean));
		}catch(Exception e){
			log.error("AuthAuthorizeController.getAuthResources",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}



	/**
	 * 权限管理-列表-编辑权限-修改角色权限
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAuthRoleResources",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse updateAuthRoleResources(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthRoleResourceBean condition = GsonUtils.getGson().fromJson(requestBody,AuthRoleResourceBean.class);
			if(null == condition || null == condition.getRoleId() || condition.getRoleId() < 0){
				log.error("roleId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"roleId is null");
			}
			if(null == condition || null == condition.getResourceList() || condition.getResourceList().size() == 0){
				log.error("roleId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"roleId is null");
			}

			baseResponse.setResult(authorizeService.updateAuthRoleResources(condition));
		}catch(Exception e){
			log.error("AuthAuthorizeController.updateAuthRoleResources",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}

	/**
	 * 权限管理-新增角色
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addRoleResource",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse addRoleResource(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			RoleResourceModel condition = GsonUtils.getGson().fromJson(requestBody,RoleResourceModel.class);
			if(null == condition || null == condition.getAuthRoleBean()) {
				log.error("role is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "role is null");
			}
			if(null == condition.getAuthRoleBean().getRoleName() || "".equals(condition.getAuthRoleBean().getRoleName()) ) {
				log.error("roleName is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "roleName is null");
			}
			if(null == condition || null == condition.getAuthResourceBeans() || condition.getAuthResourceBeans().size() == 0){
				log.error("resource is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"resource is null");
			}
			AuthRoleBean roleBean = new AuthRoleBean();
			roleBean.setRoleName(condition.getAuthRoleBean().getRoleName());
			List<AuthRoleBean> rolelist = authorizeService.getRoleList(roleBean);

			if(null != rolelist && rolelist.size()>0){
				log.error("roleName is having" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(),".roleName is having");
			}
			boolean result = authorizeService.addRoleAndRoleResource(condition.getAuthRoleBean(),condition.getAuthResourceBeans());

			if(result)
				baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(),"add role success");
			else
				baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(),"add role fail");

		}catch(Exception e){
			log.error("AuthAuthorizeController.addRoleResource",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}

	/**
	 * 权限管理-新增用户
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addUserManager",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse addUserManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			UserManagerRoleModel condition = GsonUtils.getGson().fromJson(requestBody,UserManagerRoleModel.class);
			if(null == condition || null == condition.getAuthUserBean()){
				log.error("usermanager is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"usermanager is null");
			}
			if(null == condition.getAuthUserBean().getUserName() || "".equals(condition.getAuthUserBean().getUserName())){
				log.error("userName is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userName is null");
			}
			if(null == condition.getAuthUserBean().getUserAccount() || "".equals(condition.getAuthUserBean().getUserAccount())){
				log.error("userAccount is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userAccount is null");
			}
			if(null == condition.getAuthUserBean().getUserPassword() || "".equals(condition.getAuthUserBean().getUserPassword())){
				log.error("userPassword is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userPassword is null");
			}
			if(null == condition.getAuthUserBean().getUserType() || "".equals(condition.getAuthUserBean().getUserType())){
				log.error("userType is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userType is null");
			}

			if(null == condition || null == condition.getAuthRoleBeans() || condition.getAuthRoleBeans().size() == 0){
				log.error("role is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"role is null");
			}
			AuthUserManagerBean userManagerBean = new AuthUserManagerBean();
			userManagerBean.setUserAccount(condition.getAuthUserBean().getUserAccount());
			List<AuthUserManagerBean>  userlist = authorizeService.getUserManagerList(userManagerBean);

			if(null != userlist && userlist.size() > 0){
				log.error("userAccount is having" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(),".userAccount is having");
			}

			boolean result = authorizeService.addUserAndUserRole(condition.getAuthUserBean(),condition.getAuthRoleBeans());

			if(result)
				baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(),"add usermanager success");
			else
				baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(),"add usermanager fail");

		}catch(Exception e){
			log.error("AuthAuthorizeController.addUserManager",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}


	/**
	 * 权限管理-列表-编辑权限-获取所有角色信息
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAuthRoles",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse getAuthRoles(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthRoleBean condition = GsonUtils.getGson().fromJson(requestBody,AuthRoleBean.class);
			if(null == condition){
				log.error("schoolId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"schoolId is null");
			}
			AuthRoleBean roleBean = new AuthRoleBean();
			baseResponse.setResult(authorizeService.getRoleList(roleBean));
		}catch(Exception e){
			log.error("AuthAuthorizeController.getAuthResources",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}

	/**
	 * 权限管理-列表-编辑权限-添加资源权限
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addResource",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse addResource(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthResourceBean condition = GsonUtils.getGson().fromJson(requestBody,AuthResourceBean.class);
			if(null == condition || null == condition.getResourceName() || "".equals(condition.getResourceName())){
				log.error("resourceName is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"resourceName is null");
			}
			if(null == condition || null == condition.getResourceDesc() || "".equals(condition.getResourceDesc())){
				log.error("resourceDesc is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"resourceDesc is null");
			}
			if(null == condition || null == condition.getResourceParentId() || condition.getResourceParentId() < 0){
				log.error("resourceParentId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"resourceParentId is null");
			}
			if(null == condition || null == condition.getResourceType() || condition.getResourceType() < 0){
				log.error("resourceType is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"resourceType is null");
			}
            boolean result = authorizeService.addResource(condition);
			if(result)
				baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(),"add resource success");
			else
				baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(),"add resource fail");

		}catch(Exception e){
			log.error("AuthAuthorizeController.addResource",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}



	/**
	 * 权限管理-列表-编辑权限-删除资源权限
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteResource",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse deleteResource(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthResourceBean condition = GsonUtils.getGson().fromJson(requestBody,AuthResourceBean.class);
			if(null == condition || null == condition.getResourceId() || condition.getResourceId() < 0){
				log.error("resourceId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"resourceId is null");
			}
			boolean result = authorizeService.deleteResource(condition);
			if(result)
				baseResponse.setResponse(baseResponse, ResponseCode.SUCCESS.toString(),"delete resource success");
			else
				baseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString(),"delete resource fail");

		}catch(Exception e){
			log.error("AuthAuthorizeController.addResource",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}

	/**
	 * 权限管理-分页查询用户列表
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserManagerPage",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse getUserManagerPage(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || null == condition.getUserType() || condition.getUserType() < 0){
				log.error("userType is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userType is null");
			}

			baseResponse.setResult(authorizeService.getUserManagerPage(condition));

		}catch(Exception e){
			log.error("AuthAuthorizeController.getUserManagerPage",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}


	/**
	 * 权限管理-部门列表-管理用户-删除用户角色关系
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delRoleBindByUserAndRole",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public BaseResponse delRoleBindByUserAndRole(@RequestParam("requestId") String requestId, @RequestBody String requestBody){
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserRoleBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserRoleBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() < 0){
				log.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
			}
			if(null == condition || null == condition.getRoleId() || condition.getRoleId() < 0){
				log.error("roleId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"roleId is null");
			}
			baseResponse.setResult(authorizeService.delRoleBindByUserAndRole(condition));

		}catch(Exception e){
			log.error("AuthAuthorizeController.delRoleBindByUserAndRole",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}

		return baseResponse;
	}


	/**
	 * 后端管理，删除学生组织
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteStudentGroupInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse deleteStudentGroupInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		log.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
				log.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
			}
			if (null == condition || null == condition.getUserType() || condition.getUserType() <= 0) {
				log.error("userType is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userType is null");
			}
			boolean bool = authorizeService.delStudentGroup(condition);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			log.error("AuthAuthorizeController.deleteStudentGroupInfo is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 后端管理，删除学生组织
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteStudentGroupInfoBatch", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse deleteStudentGroupInfoBatch(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		log.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			List<AuthUserBean> conditions = GsonUtils.getGson().fromJson(requestBody, new TypeToken<List<AuthUserBean>>(){}.getType());
			if(null == conditions || conditions.size() == 0){
				log.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
			}
			boolean bool = authorizeService.delStudentGroupBatch(conditions);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			log.error("AuthAuthorizeController.deleteStudentGroupInfoBatch is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}


}
